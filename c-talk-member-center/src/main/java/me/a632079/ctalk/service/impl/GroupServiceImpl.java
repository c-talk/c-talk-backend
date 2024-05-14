package me.a632079.ctalk.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.func.VoidFunc1;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import me.a632079.ctalk.po.Group;
import me.a632079.ctalk.repository.GroupRepository;
import me.a632079.ctalk.service.GroupMemberService;
import me.a632079.ctalk.service.GroupService;
import me.a632079.ctalk.vo.GroupForm;
import me.a632079.ctalk.vo.GroupPageForm;
import me.a632079.ctalk.vo.GroupSetForm;
import me.a632079.ctalk.vo.PageVo;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: GroupServiceImpl
 * @description: GroupServiceImpl - 群组
 * @version: v1.0.0
 * @author: haoduor
 */
@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupRepository groupRepository;

    @Resource
    private GroupMemberService groupMemberService;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private MongoTemplate template;

    @Override
    public Group createGroup(GroupForm form) {
        Long uid = StpUtil.getLoginIdAsLong();
        Group group = mapperFacade.map(form, Group.class);

        group.setOwner(uid);
        // 邀请码
        group.setCode(RandomUtil.randomString(10));
        group = groupRepository.insert(group);

        // 创建群组所有者
        groupMemberService.addGroupMemberOwner(group.getId(), uid);
        return group;
    }

    @Override
    public Group setGroup(GroupSetForm form) {
        Group group = groupRepository.findFirstById(form.getId());

        testThenSet(StrUtil.isNotBlank(form.getAvatar()), group::setAvatar, form.getAvatar());
        testThenSet(StrUtil.isNotBlank(form.getBanner()), group::setBanner, form.getBanner());
        testThenSet(StrUtil.isNotBlank(form.getCode()), group::setCode, form.getCode());
        testThenSet(StrUtil.isNotBlank(form.getDesc()), group::setDesc, form.getDesc());
        testThenSet(StrUtil.isNotBlank(form.getName()), group::setName, form.getName());

        groupRepository.save(group);
        return group;
    }

    @Override
    public void deleteGroup(Long gid) {
        // TODO 所有者检查
        groupRepository.deleteById(gid);
    }

    @Override
    public PageVo<Group> pageGroup(GroupPageForm form) {
        Query query = new Query();

        if (StrUtil.isNotBlank(form.getName())) {
            query.addCriteria(Criteria.where("name")
                                      .regex(form.getName()));
        }

        if (StrUtil.isNotBlank(form.getCode())) {
            query.addCriteria(Criteria.where("code")
                                      .is(form.getCode()));
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(form.getPageNum() - 1, form.getPageSize(), sort);

        Long count = template.count(query, Group.class);

        List<Group> result = template.find(query.with(pageRequest), Group.class);

        return PageVo.of(result, form, count);
    }

    @Override
    public List<Group> listGroupByGid(List<Long> gids) {
        return Lists.newArrayList(groupRepository.findAllById(gids)
                                                 .iterator());
    }

    @SneakyThrows
    private <T> void testThenSet(boolean test, VoidFunc1<T> setter, T value) {
        if (test) {
            setter.call(value);
        }
    }
}
