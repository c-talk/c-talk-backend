package me.a632079.ctalk.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.po.Friend;
import me.a632079.ctalk.po.GroupMember;
import me.a632079.ctalk.service.FriendService;
import me.a632079.ctalk.vo.PageForm;
import me.a632079.ctalk.vo.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @className: FriendServiceImpl
 * @description: FriendServiceImpl - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@Service
public class FriendServiceImpl implements FriendService {
    @Resource
    private MongoTemplate template;

    @Override
    public PageVo<Friend> pageFriend(PageForm form, Long uid) {
        Query query = new Query();

        if (Objects.nonNull(uid)) {
            query.addCriteria(Criteria.where("uid")
                                      .is(uid));
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(form.getPageNum() - 1, form.getPageSize(), sort);

        Long count = template.count(query, GroupMember.class);

        List<Friend> result = template.find(query.with(pageRequest), Friend.class);

        return PageVo.of(result, form, count);
    }
}
