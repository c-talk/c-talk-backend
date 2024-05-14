package me.a632079.ctalk.repository;

import me.a632079.ctalk.po.GroupMember;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @className: GroupMemberRepository
 * @description: GroupMemberRepository - 群组成员
 * @version: v1.0.0
 * @author: haoduor
 */
public interface GroupMemberRepository extends MongoRepository<GroupMember, Long> {
    List<GroupMember> findAllByUid(Long uid);

    List<GroupMember> findAllByUidAndGid(Long uid, Long gid);

    boolean existsByUidAndGid(Long uid, Long gid);

    void removeByUidAndGid(Long uid, Long gid);

    boolean removeByGid(Long gid);
}
