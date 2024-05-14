package me.a632079.ctalk.po;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document("accounts")
public class Account extends BasePo {
    private String username;
    private String password;
}
