package me.a632079.ctalk.po;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.a632079.ctalk.enums.ResourceType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("resources")
public class ResourcePo {
    @MongoId
    private String       id; // Cidr
    private String       name; // maybe nullable?
    private String       mime;
    private ResourceType type;
    private Date         created_at;

    @JsonIgnore
    private Date updated_at;
}
