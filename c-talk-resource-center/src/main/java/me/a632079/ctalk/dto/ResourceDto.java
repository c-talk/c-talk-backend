package me.a632079.ctalk.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.a632079.ctalk.po.ResourcePo;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = false)
@Data
public class ResourceDto extends ResourcePo {
    private MultipartFile data;
    private byte[]        bytes;
}
