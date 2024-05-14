package me.a632079.ctalk.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

/**
 * @className: BasePo
 * @description: BasePo - 基础实体类
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class BasePo {
    @MongoId
    private Long id;

    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime createTime;

    @Indexed
    private LocalDateTime updateTime;

    private Long createUid;

    private Long updateUid;

    private boolean isDeleted;

    @JsonIgnore
    public String getDocumentName() {
        return "";
    }
}
