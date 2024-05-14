package me.a632079.ctalk.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @className: PageForm
 * @description: PageForm - 公共分页
 * @version: v1.0.0
 * @author: haoduor
 */

@Data
public class PageForm {
    @Min(value = 1, message = "最小不能少于1}")
    @NotNull(message = "页码不能为空")
    private Integer pageNum;

    @Min(value = 1, message = "最小不能少于1}")
    @NotNull(message = "分页数量不能为空")
    private Integer pageSize;
}
