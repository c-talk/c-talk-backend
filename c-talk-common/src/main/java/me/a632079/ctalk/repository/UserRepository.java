package me.a632079.ctalk.repository;

import me.a632079.ctalk.po.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @className: UserService
 * @description: UserService - TODO
 * @version: v1.0.0
 * @author: haoduor
 */
@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    User findOneByEmailEquals(String email);

    User findFirstById(Long id);
}
