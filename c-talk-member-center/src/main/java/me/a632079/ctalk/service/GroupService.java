package me.a632079.ctalk.service;

import me.a632079.ctalk.po.Group;
import me.a632079.ctalk.vo.GroupForm;
import me.a632079.ctalk.vo.GroupPageForm;
import me.a632079.ctalk.vo.GroupSetForm;
import me.a632079.ctalk.vo.PageVo;

import java.util.List;

/**
 * @className: GroupService
 * @description: GroupService - TODO
 * @version: v1.0.0
 * @author: haoduor
 */
public interface GroupService {
    Group createGroup(GroupForm form);

    Group setGroup(GroupSetForm form);

    void deleteGroup(Long gid);

    PageVo<Group> pageGroup(GroupPageForm form);

    List<Group> listGroupByGid(List<Long> gids);
}
