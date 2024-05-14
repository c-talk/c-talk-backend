package me.a632079.ctalk.vo;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @className: PageVo
 * @description: PageVo - TODO
 * @version: v1.0.0
 * @author: haoduor
 */


@Data
public class PageVo<T> {
    private List<T> items;
    private Long    total;
    private Integer page;
    private Integer pageSize;

    public static <T> PageVo<T> of(Page<T> page) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setItems(page.getContent());
        pageVo.setPage(page.getNumber());
        pageVo.setPageSize(page.getSize());
        pageVo.setTotal(page.getTotalElements());

        return pageVo;
    }

    public static <T> PageVo<T> of(List<T> data, PageForm form, Long total) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setItems(data);
        pageVo.setPage(form.getPageNum());
        pageVo.setPageSize(form.getPageSize());
        pageVo.setTotal(total);

        return pageVo;
    }

    public static <R> PageVo<R> of(List<R> data, PageVo<?> pageVo) {
        PageVo<R> res = new PageVo<>();
        res.setItems(data);
        res.setPage(pageVo.getPage());
        res.setPageSize(pageVo.getPageSize());
        res.setTotal(pageVo.getTotal());

        return res;
    }

    public static <R> PageVo<R> empty() {
        PageVo<R> res = new PageVo<>();
        res.setPage(0);
        res.setPageSize(0);
        res.setTotal(0L);

        return res;
    }

    public boolean isEmpty() {
        return total == 0L;
    }

    public <R> PageVo<R> trans(Function<T, R> mapFunc) {
        return PageVo.of(items.stream()
                              .map(mapFunc)
                              .collect(Collectors.toList()), this);
    }

    public void map(Function<T, T> mapFunc) {
        this.items = items.stream()
                          .map(mapFunc)
                          .collect(Collectors.toList());
    }
}
