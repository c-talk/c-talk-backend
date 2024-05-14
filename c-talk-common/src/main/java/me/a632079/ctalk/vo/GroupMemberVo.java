package me.a632079.ctalk.vo;

import lombok.Data;
import me.a632079.ctalk.po.User;

import java.util.List;

/**
 * @className: GroupMemberVo
 * @description: GroupMemberVo - TODO
 * @version: v1.0.0
 * @author: haoduor
 */


@Data
public class GroupMemberVo {
    private Long gid;
    private Long uid;

    private User userInfo;

    private String alias;

    private List<String> role;
}
