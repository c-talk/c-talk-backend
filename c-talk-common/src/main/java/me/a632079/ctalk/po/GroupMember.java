package me.a632079.ctalk.po;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @className: GroupMember
 * @description: GroupMember - 群组成员
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document("groupMember")
public class GroupMember extends BasePo {
    @Indexed
    private Long gid;

    @Indexed
    private Long uid;

    private String alias;

    private List<String> role;
}
