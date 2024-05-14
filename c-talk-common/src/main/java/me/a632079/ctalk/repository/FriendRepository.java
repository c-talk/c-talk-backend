package me.a632079.ctalk.repository;

import me.a632079.ctalk.po.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @className: FriendRepository
 * @description: FriendRepository - TODO
 * @version: v1.0.0
 * @author: haoduor
 */
@Repository
public interface FriendRepository extends MongoRepository<Friend, Long> {
    List<Friend> findAllByUid(Long uid);

    boolean existsByUidAndFriendId(Long uid, Long friendId);

    void removeByUidAndFriendId(Long uid, Long friendId);
}
