package me.a632079.ctalk.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import me.a632079.ctalk.po.User;
import me.a632079.ctalk.repository.UserRepository;
import me.a632079.ctalk.service.UserService;
import me.a632079.ctalk.util.Argon2Util;
import me.a632079.ctalk.vo.PageVo;
import me.a632079.ctalk.vo.RegisterForm;
import me.a632079.ctalk.vo.UserPageForm;
import me.a632079.ctalk.vo.UserVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: UserService
 * @description: UserService - 用户功能
 * @version: v1.0.0
 * @author: haoduor
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MongoTemplate template;

    private final UserRepository userRepository;

    private final Argon2Util argon2Util;

    private final MapperFacade mapperFacade;

    public static final String DOCUMENT_NAME = "user";

    @Override
    public User addUser(RegisterForm form) {
        User user = User.builder()
                        .avatar("")
                        .email(form.getEmail())
                        .password(argon2Util.hash(form.getPassword()))
                        .nickName(form.getNickName())
                        // TODO 邮箱校验
                        .verify(true)
                        .build();

        return userRepository.insert(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                             .orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findOneByEmailEquals(email);
    }

    @Override
    public PageVo<UserVo> pageUser(UserPageForm form) {
        Query query = new Query();

        if (StrUtil.isNotBlank(form.getEmail())) {
            query.addCriteria(Criteria.where("email")
                                      .regex(form.getEmail()));
        }

        if (StrUtil.isNotBlank(form.getNickName())) {
            query.addCriteria(Criteria.where("nickName")
                                      .regex(form.getNickName()));
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(form.getPageNum() - 1, form.getPageSize(), sort);

        Long count = template.count(query, User.class);

        List<User> users = template.find(query.with(pageRequest), User.class);

        return PageVo.of(mapperFacade.mapAsList(users, UserVo.class), form, count);
    }

    @Override
    public List<User> listUserByIds(List<Long> ids) {
        return Lists.newArrayList(userRepository.findAllById(ids)
                                                .iterator());
    }

    @Override
    public boolean exist(Long id) {
        return userRepository.existsById(id);
    }

}
