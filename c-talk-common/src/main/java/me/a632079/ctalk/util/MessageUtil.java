package me.a632079.ctalk.util;

import cn.hutool.crypto.SecureUtil;
import me.a632079.ctalk.po.Friend;
import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.vo.FriendVo;

/**
 * @className: MessageUtil
 * @description: MessageUtil - 消息工具
 * @version: v1.0.0
 * @author: haoduor
 */


public class MessageUtil {

    public static String hash(Message message) {
        return hash(message.getReceiver(), message.getSender());
    }

    public static String hash(Friend friend) {
        return hash(friend.getUid(), friend.getFriendId());
    }

    public static String hash(FriendVo friend) {
        return hash(friend.getUid(), friend.getFriendId());
    }

    public static String hash(Long a, Long b) {
        String res = "";
        if (a > b) {
            return SecureUtil.md5(res + a + b);
        } else {
            return SecureUtil.md5(res + b + a);
        }
    }
}
