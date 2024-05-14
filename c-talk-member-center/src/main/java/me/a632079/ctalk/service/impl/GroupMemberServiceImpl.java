package me.a632079.ctalk.service.impl;

import me.a632079.ctalk.constant.CTalkConstant;
import me.a632079.ctalk.po.GroupMember;
import me.a632079.ctalk.repository.GroupMemberRepository;
import me.a632079.ctalk.service.GroupMemberService;
import me.a632079.ctalk.vo.PageForm;
import me.a632079.ctalk.vo.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @className: GroupMemberServiceImpl
 * @description: GroupMemberServiceImpl - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    @Resource
    private GroupMemberRepository repository;

    @Resource
    private MongoTemplate template;

    @Override
    public void addGroupMember(Long gid, Long uid, String role) {
        GroupMember member = GroupMember.builder()
                                        .gid(gid)
                                        .uid(uid)
                                        .role(List.of(role))
                                        .build();

        repository.insert(member);
    }

    @Override
    public void addGroupMemberOwner(Long gid, Long uid) {
        this.addGroupMember(gid, uid, CTalkConstant.GroupRole.OWNER);
    }

    @Override
    public void addGroupMemberAdmin(Long gid, Long uid) {
        this.addGroupMember(gid, uid, CTalkConstant.GroupRole.ADMIN);
    }

    @Override
    public void addGroupMember(Long gid, Long uid) {
        this.addGroupMember(gid, uid, CTalkConstant.GroupRole.MEMBER);
    }

    @Override
    public void deleteGroup(Long gid) {
        if (!repository.removeByGid(gid)) {
            // TODO 异常以及回滚策略
        }
    }

    @Override
    public PageVo<GroupMember> pageGroupMember(PageForm form) {
        return this.pageGroupMemberByGid(form, null);
    }

    @Override
    public PageVo<GroupMember> pageGroupMemberByGid(PageForm form, Long gid) {
        Query query = new Query();

        if (Objects.nonNull(gid)) {
            query.addCriteria(Criteria.where("gid")
                                      .is(gid));
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(form.getPageNum() - 1, form.getPageSize(), sort);

        Long count = template.count(query, GroupMember.class);

        List<GroupMember> result = template.find(query.with(pageRequest), GroupMember.class);

        return PageVo.of(result, form, count);
    }

    @Override
    public List<GroupMember> listMember(Long gid, int limit) {
        Query query = new Query();
        query.limit(limit);
        query.addCriteria(Criteria.where("gid")
                                  .is(gid));
        return template.find(query, GroupMember.class);
    }

}
