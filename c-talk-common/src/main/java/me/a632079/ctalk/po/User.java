package me.a632079.ctalk.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @className: User
 * @description: User - 用户
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document("user")
public class User extends BasePo {

    @Indexed
    private String nickName;

    @JsonIgnore
    private String password;

    @Indexed(unique = true)
    private String email;

    private String avatar;

    private boolean verify;
}
