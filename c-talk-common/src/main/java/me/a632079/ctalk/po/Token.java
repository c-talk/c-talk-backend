package me.a632079.ctalk.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.a632079.ctalk.constant.CTalkConstant;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @className: Token
 * @description: Token - websocket 认证token
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("token")
public class Token {

    @JsonIgnore
    @Indexed(unique = true)
    private Long uid;

    @Indexed(unique = true)
    private String token;

    @Indexed(expireAfterSeconds = CTalkConstant.TOKEN_EXPIRE_TIME)
    private LocalDateTime expire;
}
