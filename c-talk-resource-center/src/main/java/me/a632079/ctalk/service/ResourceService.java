package me.a632079.ctalk.service;

import me.a632079.ctalk.dto.ResourceDto;
import me.a632079.ctalk.po.ResourcePo;
import org.springframework.stereotype.Service;

@Service
public interface ResourceService {
    ResourceDto getResource(String resourceName);

    boolean removeResource(String resourceName);

    ResourcePo addResource(ResourceDto dto);
}
