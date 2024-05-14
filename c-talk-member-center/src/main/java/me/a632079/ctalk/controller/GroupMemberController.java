package me.a632079.ctalk.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import me.a632079.ctalk.enums.CTalkErrorCode;
import me.a632079.ctalk.exception.CTalkExceptionFactory;
import me.a632079.ctalk.po.GroupMember;
import me.a632079.ctalk.po.User;
import me.a632079.ctalk.repository.GroupMemberRepository;
import me.a632079.ctalk.service.GroupMemberService;
import me.a632079.ctalk.service.UserService;
import me.a632079.ctalk.vo.GroupMemberVo;
import me.a632079.ctalk.vo.PageForm;
import me.a632079.ctalk.vo.PageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className: GroupMemberController
 * @description: GroupMemberController - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@RestController
@RequestMapping("/group/member")
public class GroupMemberController {

    @Resource
    private GroupMemberService groupMemberService;

    @Resource
    private GroupMemberRepository groupMemberRepository;

    @Resource
    private UserService userService;

    @Resource
    private MapperFacade mapperFacade;

    @PostMapping("/leave/{gid}")
    public void exist(@PathVariable Long gid) {
        Long uid = StpUtil.getLoginIdAsLong();

        if (!groupMemberRepository.existsByUidAndGid(uid, gid)) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.GROUP_NOT_JOINED);
        }

        groupMemberRepository.removeByUidAndGid(uid, gid);
    }

    @PostMapping("/page/{gid}")
    public PageVo<GroupMemberVo> list(@PathVariable Long gid, @RequestBody PageForm form) {
        PageVo<GroupMember> groupMember = groupMemberService.pageGroupMemberByGid(form, gid);
        Map<Long, User> userMap = userService.listUserByIds(groupMember.getItems()
                                                                       .stream()
                                                                       .map(GroupMember::getUid)
                                                                       .collect(Collectors.toList()))
                                             .stream()
                                             .collect(Collectors.toMap(User::getId, e -> e, (a, b) -> a));
        return groupMember.trans(e -> {
            GroupMemberVo vo = mapperFacade.map(e, GroupMemberVo.class);
            User user = userMap.getOrDefault(vo.getUid(), null);
            vo.setUserInfo(user);
            return vo;
        });
    }
}
