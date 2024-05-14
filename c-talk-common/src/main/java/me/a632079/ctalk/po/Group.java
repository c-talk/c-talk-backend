package me.a632079.ctalk.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@EqualsAndHashCode(callSuper = false)
public class Group extends BasePo {
    @Indexed
    private String name;

    @Indexed(unique = true)
    private String code;

    private String desc;
    private String avatar;
    private String banner;
    private Long   owner;
}
