package me.a632079.ctalk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.service.MessageService;
import me.a632079.ctalk.vo.MessageForm;
import me.a632079.ctalk.vo.MessageHistoryForm;
import me.a632079.ctalk.vo.PageVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @className: MessageController
 * @description: MessageController - 消息
 * @version: v1.0.0
 * @author: haoduor
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @PostMapping("/send/private")
    public Message sendPrivateMessage(@RequestBody MessageForm form) throws JsonProcessingException {
        return messageService.addPrivateMessage(form);
    }

    @PostMapping("/send/group")
    public Message sendGroupMessage(@RequestBody MessageForm form) throws JsonProcessingException {
        return messageService.addGroupMessage(form);
    }

    @PostMapping("/history/private")
    public PageVo<Message> pagePrivateMessage(@RequestBody @Validated MessageHistoryForm form) {
        return messageService.pagePrivateMessage(form);
    }

    @PostMapping("/history/group")
    public PageVo<Message> pageGroupMessage(@RequestBody @Validated MessageHistoryForm form) {
        return messageService.pageGroupMessage(form);
    }
}
