package me.a632079.ctalk.vo;

import lombok.Data;
import me.a632079.ctalk.po.Group;
import me.a632079.ctalk.po.GroupMember;

import java.util.List;

/**
 * @className: GroupVo
 * @description: GroupVo - TODO
 * @version: v1.0.0
 * @author: haoduor
 */


@Data
public class GroupVo {
    private Group             group;
    private List<GroupMember> memberList;
    private boolean           isMember;
}
