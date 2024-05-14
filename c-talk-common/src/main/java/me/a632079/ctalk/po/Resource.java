package me.a632079.ctalk.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.a632079.ctalk.enums.ResourceType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("resources")
public class Resource {
    private String id; // Cidr
    private String name; // maybe nullable?
    private ResourceType type;
    private Date created_at;
    private Date updated_at;
}
