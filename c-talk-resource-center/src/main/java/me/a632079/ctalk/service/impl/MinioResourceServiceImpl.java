package me.a632079.ctalk.service.impl;

import me.a632079.ctalk.dto.ResourceDto;
import me.a632079.ctalk.service.ResourceService;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;
import me.a632079.ctalk.po.ResourcePo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Date;

@Log4j2
@Service
public class MinioResourceServiceImpl implements ResourceService {

    private final String COLLECTION_NAME = "resources";

    @Resource
    private MinioClient client;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private MapperFacade mapperFacade;

    @Setter
    @Getter
    @Value("${minio.bucket-name}")
    private String bucketName;

    public boolean existsResource(String id) {
        boolean isExist = this.mongoTemplate.collectionExists(COLLECTION_NAME);
        if (!isExist) return false;
        var po = this.mongoTemplate.findById(
                id,
                ResourcePo.class,
                COLLECTION_NAME
        );
        return po != null;
    }

    @SneakyThrows
    @Override
    public ResourceDto getResource(String resourceName) {
        boolean isExist = this.mongoTemplate.collectionExists(COLLECTION_NAME);
        if (!isExist) return null;

        ResourcePo resourcePo = this.mongoTemplate.findById(resourceName, ResourcePo.class, COLLECTION_NAME);
        if (resourcePo == null) return null;
        try {
            client.statObject(StatObjectArgs.builder()
                                            .bucket(bucketName)
                                            .object(resourceName)
                                            .build());
        } catch (ErrorResponseException ex) {
            log.error(ex);
            return null; // not found the object.
        }
        GetObjectResponse response = client.getObject(GetObjectArgs.builder()
                                                                   .bucket(bucketName)
                                                                   .object(resourceName)
                                                                   .build());
        ResourceDto dto = mapperFacade.map(resourcePo, ResourceDto.class);
        dto.setBytes(response.readAllBytes());
        return dto;
    }

    @SneakyThrows
    @Override
    public boolean removeResource(String resourceName) {
        var isExist = existsResource(resourceName);
        if (!isExist) return true;
        try {
            client.removeObject(RemoveObjectArgs.builder()
                                                .bucket(bucketName)
                                                .object(resourceName)
                                                .build());
        } catch (ErrorResponseException ex) {
            log.error(ex);
            return false;
        }
        var criteria = Criteria.where("_id").is(resourceName);
        var query = new Query(criteria);
        var result = mongoTemplate.remove(query, COLLECTION_NAME);
        return result.getDeletedCount() > 0;
    }

    @SneakyThrows
    @Override
    public ResourcePo addResource(ResourceDto dto) {
        var isExist = existsResource(dto.getId());
        if (isExist) {
            return null;
        }
        Date now = new Date();
        dto.setCreated_at(now);
        dto.setUpdated_at(now);
        InputStream stream = dto.getData()
                                .getInputStream();
        try {
            client.putObject(PutObjectArgs.builder()
                                          .bucket(bucketName)
                                          .object(dto.getId())
                                          .contentType(dto.getMime())
                                          .stream(stream,
                                                  dto.getData()
                                                     .getSize(),
                                                  -1
                                          )
                                          .build());
        } catch (ErrorResponseException ex) {
            log.error(ex);
            return null;
        }
        ResourcePo resourcePo = mapperFacade.map(dto, ResourcePo.class);
        this.mongoTemplate.insert(resourcePo, COLLECTION_NAME);
        return resourcePo;
    }
}
