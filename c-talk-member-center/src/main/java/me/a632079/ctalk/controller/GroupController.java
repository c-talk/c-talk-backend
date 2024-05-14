package me.a632079.ctalk.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import me.a632079.ctalk.po.Group;
import me.a632079.ctalk.po.GroupMember;
import me.a632079.ctalk.po.Message;
import me.a632079.ctalk.repository.GroupMemberRepository;
import me.a632079.ctalk.repository.GroupRepository;
import me.a632079.ctalk.service.GroupMemberService;
import me.a632079.ctalk.service.GroupService;
import me.a632079.ctalk.service.MessageService;
import me.a632079.ctalk.vo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @className: GroupController
 * @description: GroupController - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private MongoTemplate template;

    @Resource
    private GroupService groupService;

    @Resource
    private GroupRepository groupRepository;

    @Resource
    private GroupMemberRepository groupMemberRepository;

    @Resource
    private GroupMemberService groupMemberService;

    @Resource
    private MessageService messageService;

    @Resource
    private MapperFacade mapperFacade;

    @PostMapping("/create")
    public Group createGroup(@RequestBody GroupForm groupForm) {
        return groupService.createGroup(groupForm);
    }

    @PostMapping("/join")
    public Group joinGroup(@RequestBody IdForm form) {
        Long uid = StpUtil.getLoginIdAsLong();

        Group group = groupRepository.findFirstById(form.getId());

        if (Objects.isNull(group)) {
            return null;
        }

        groupMemberService.addGroupMember(group.getId(), uid);

        return group;
    }

    @GetMapping("/invite/{code}")
    public GroupVo invite(@PathVariable String code) {
        Group group = groupRepository.findFirstByCode(code);
        List<GroupMember> memberList = groupMemberService.listMember(group.getId(), 5);
        GroupVo groupVo = new GroupVo();
        groupVo.setGroup(group);
        groupVo.setMemberList(memberList);
        return groupVo;
    }

    @PostMapping("/page")
    public PageVo<Group> pageGroup(@RequestBody GroupPageForm form) {
        return groupService.pageGroup(form);
    }

    @PostMapping("/page/{id}/with/message")
    public PageVo<JoinedGroupVo> pageWithMessage(@PathVariable Long id, @RequestBody PageForm pageForm) {
        JoinedGroupForm form = mapperFacade.map(pageForm, JoinedGroupForm.class);
        form.setUid(id);
        PageVo<JoinedGroupVo> vo = this.pageJoinedGroup(form);

        if (vo.isEmpty()) {
            return PageVo.empty();
        }

        List<Message> messages = messageService.getFirstGroupMessageByGids(CollUtil.map(vo.getItems(), JoinedGroupVo::getGid, true));

        Map<Long, Message> messageMap = messages.stream()
                                                .collect(Collectors.toMap(Message::getReceiver, e -> e, (a, b) -> a));

        vo.map(e -> {
            e.setMessage(messageMap.getOrDefault(e.getGid(), null));
            return e;
        });

        return vo;
    }

    @PostMapping("/page/joined")
    public PageVo<JoinedGroupVo> pageJoinedGroup(@RequestBody JoinedGroupForm form) {
        MatchOperation matchId = Aggregation.match(Criteria.where("uid")
                                                           .is(form.getUid()));

        // 创建 $lookup
        LookupOperation lookupOperation = LookupOperation.newLookup()
                                                         .from("group")
                                                         .localField("gid")
                                                         .foreignField("_id")
                                                         .as("group");

        UnwindOperation unwindOperation = Aggregation.unwind("group", true);

        Criteria criteria = new Criteria();

        if (StrUtil.isNotBlank(form.getGroupName())) {
            criteria.orOperator(
                    Criteria.where("group.name")
                            .regex(form.getGroupName())
            );
        }

        MatchOperation matchOperation = Aggregation.match(criteria);

        Aggregation countAggregation = Aggregation.newAggregation(
                matchId,
                lookupOperation,
                unwindOperation,
                matchOperation,
                Aggregation.count()
                           .as("total"));
        AggregationResults<Count> countResults = template.aggregate(countAggregation, "groupMember", Count.class);
        long total = countResults.getUniqueMappedResult() != null ? countResults.getUniqueMappedResult()
                                                                                .getTotal() : 0;

        if (0 == total) {
            return PageVo.empty();
        }

        // 分页
        Long skip = (long) (form.getPageNum() - 1) * form.getPageSize();
        SkipOperation skipOperation = Aggregation.skip(skip);
        LimitOperation limitOperation = Aggregation.limit(form.getPageSize());

        // 聚合查询
        Aggregation aggregation = Aggregation.newAggregation(
                matchId,
                lookupOperation,
                unwindOperation,
                matchOperation,
                skipOperation,
                limitOperation
        );

        // 执行聚合查询
        AggregationResults<JoinedGroupVo> results = template.aggregate(aggregation, "groupMember", JoinedGroupVo.class);

        return PageVo.of(results.getMappedResults(), form, total);
    }

    @GetMapping("/get/{id}")
    public GroupVo getGroupById(@PathVariable Long id) {
        GroupVo vo = new GroupVo();
        Group group = groupRepository.findFirstById(id);

        if (Objects.isNull(group)) {
            return null;
        }

        Long uid = StpUtil.getLoginIdAsLong();

        List<GroupMember> members = groupMemberService.listMember(group.getId(), 5);
        vo.setGroup(group);
        vo.setMemberList(members);
        if (group.getOwner()
                 .equals(uid)) {
            vo.setMember(true);
        } else {
            vo.setMember(groupMemberRepository.existsByUidAndGid(uid, group.getId()));
        }

        return vo;
    }

    @PostMapping("/set")
    public Group setGroup(@RequestBody GroupSetForm form) {
        return groupService.setGroup(form);
    }

    @PostMapping("/remove")
    public void removeGroup(@RequestBody IdForm form) {
        groupService.deleteGroup(form.getId());

        groupMemberService.deleteGroup(form.getId());
    }


    @Data
    class Count {
        private long total;
    }
}
