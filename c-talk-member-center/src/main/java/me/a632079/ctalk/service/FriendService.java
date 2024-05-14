package me.a632079.ctalk.service;

import me.a632079.ctalk.po.Friend;
import me.a632079.ctalk.vo.PageForm;
import me.a632079.ctalk.vo.PageVo;

/**
 * @className: FriendService
 * @description: FriendService - 好友
 * @version: v1.0.0
 * @author: haoduor
 */
public interface FriendService {
    PageVo<Friend> pageFriend(PageForm form, Long uid);
}
