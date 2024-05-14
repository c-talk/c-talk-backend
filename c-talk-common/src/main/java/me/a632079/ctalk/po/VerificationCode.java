package me.a632079.ctalk.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.a632079.ctalk.enums.VerificationType;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class VerificationCode {
    private String code;
    private Long uid;
    private VerificationType type;
    private Date exp;
}
