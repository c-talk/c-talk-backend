package me.a632079.ctalk.service;

import me.a632079.ctalk.po.User;
import me.a632079.ctalk.vo.PageVo;
import me.a632079.ctalk.vo.RegisterForm;
import me.a632079.ctalk.vo.UserPageForm;
import me.a632079.ctalk.vo.UserVo;

import java.util.List;

/**
 * @className: UserService
 * @description: UserService - TODO
 * @version: v1.0.0
 * @author: haoduor
 */


public interface UserService {
    User addUser(RegisterForm form);

    User getUserById(Long id);

    User getUserByEmail(String email);

    PageVo<UserVo> pageUser(UserPageForm form);

    List<User> listUserByIds(List<Long> ids);

    boolean exist(Long id);
}
