package me.a632079.ctalk.service;

import me.a632079.ctalk.po.GroupMember;
import me.a632079.ctalk.vo.PageForm;
import me.a632079.ctalk.vo.PageVo;

import java.util.List;

/**
 * @className: GroupMemberService
 * @description: GroupMemberService - TODO
 * @version: v1.0.0
 * @author: haoduor
 */


public interface GroupMemberService {

    void addGroupMember(Long gid, Long uid, String role);

    void addGroupMemberOwner(Long gid, Long uid);

    void addGroupMemberAdmin(Long gid, Long uid);

    void addGroupMember(Long gid, Long uid);

    void deleteGroup(Long gid);

    PageVo<GroupMember> pageGroupMember(PageForm form);

    PageVo<GroupMember> pageGroupMemberByGid(PageForm form, Long gid);


    List<GroupMember> listMember(Long gid, int limit);
}
