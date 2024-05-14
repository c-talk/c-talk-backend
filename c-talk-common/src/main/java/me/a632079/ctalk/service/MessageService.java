package me.a632079.ctalk.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.vo.MessageForm;
import me.a632079.ctalk.vo.MessageHistoryForm;
import me.a632079.ctalk.vo.PageVo;

import java.util.List;

public interface MessageService {
    Message addPrivateMessage(MessageForm form) throws JsonProcessingException;

    Message addGroupMessage(MessageForm form) throws JsonProcessingException;

    List<Message> getFirstPrivateMessageByFriend(List<Long> sender, Long receiver);

    List<Message> getFirstGroupMessageByGids(List<Long> gidList);

    List<Message> getPrivateMessage(Long senderId, Long receiverId);

    List<Message> getGroupMessage(Long id);

    PageVo<Message> pagePrivateMessage(MessageHistoryForm form);

    PageVo<Message> pageGroupMessage(MessageHistoryForm form);
}
