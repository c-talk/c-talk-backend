package me.a632079.ctalk.vo;

import lombok.Data;
import me.a632079.ctalk.po.Group;
import me.a632079.ctalk.po.Message;

import java.time.LocalDateTime;

/**
 * @className: JoinedGroupVo
 * @description: JoinedGroupVo - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class JoinedGroupVo {
    private Long          id;
    private Long          uid;
    private Long          gid;
    private Group         group;
    private Message       message;
    private LocalDateTime createTime;
}
