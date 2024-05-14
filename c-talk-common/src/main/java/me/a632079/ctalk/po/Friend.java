package me.a632079.ctalk.po;

import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @className: Friend
 * @description: Friend - 好友
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("friend")
@EqualsAndHashCode(callSuper = true)
@CompoundIndex(
        name = "uid_friendId_idx",
        def = "{uid: 1, friendId: 1}",
        unique = true
)
public class Friend extends BasePo {
    @Indexed
    private Long uid;
    @Indexed
    private Long friendId;
}
