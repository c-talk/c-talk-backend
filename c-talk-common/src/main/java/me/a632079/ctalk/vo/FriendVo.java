package me.a632079.ctalk.vo;

import lombok.Data;
import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.po.User;

/**
 * @className: FriendVo
 * @description: FriendVo - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class FriendVo {
    private Long    uid;
    private Long    friendId;
    private User    friend;
    private Message message;
}
