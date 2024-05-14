package me.a632079.ctalk.repository;

import me.a632079.ctalk.po.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @className: GroupRepository
 * @description: GroupRepository - 群组
 * @version: v1.0.0
 * @author: haoduor
 */


public interface GroupRepository extends MongoRepository<Group, Long> {
    Group findFirstById(Long id);

    Group findFirstByCode(String code);
}
