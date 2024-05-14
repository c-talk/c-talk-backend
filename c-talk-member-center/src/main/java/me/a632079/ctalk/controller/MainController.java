package me.a632079.ctalk.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import me.a632079.ctalk.enums.CTalkErrorCode;
import me.a632079.ctalk.exception.CTalkExceptionFactory;
import me.a632079.ctalk.po.User;
import me.a632079.ctalk.repository.UserRepository;
import me.a632079.ctalk.service.UserService;
import me.a632079.ctalk.util.Argon2Util;
import me.a632079.ctalk.vo.LoginForm;
import me.a632079.ctalk.vo.RegisterForm;
import me.a632079.ctalk.vo.UserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @className: MainController
 * @description: MainController
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@RestController
public class MainController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserService userService;

    @Resource
    private Argon2Util argon2Util;

    @Resource
    private MapperFacade mapperFacade;

    @PostMapping("/login")
    public UserVo login(@RequestBody LoginForm form, HttpSession session) {

        if (StpUtil.isLogin()) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.HAS_LOGIN);
        }

        // TODO 需要验证码校验
        User user = userService.getUserByEmail(form.getEmail());

        // 用户不存在
        if (Objects.isNull(user)) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.EMAIL_OR_PASSWORD_WRONG);
        }

        //密码校验
        if (!argon2Util.verify(user.getPassword(), form.getPassword())) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.EMAIL_OR_PASSWORD_WRONG);
        }

        //id写入sa-token
        StpUtil.login(user.getId());

        log.info("用户{} 登录", user.getId());

        UserVo userVo = mapperFacade.map(user, UserVo.class);
        userVo.setToken(StpUtil.getTokenValue());
        return userVo;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterForm form) {
        if (StpUtil.isLogin()) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.HAS_LOGIN);
        }

        // 2次密码
        if (!form.getPassword()
                 .equals(form.getRePassword())) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.PASSWORD_NOT_PASS);
        }

        User user = userService.getUserByEmail(form.getEmail());
        // 邮箱唯一
        if (Objects.nonNull(user)) {
            throw CTalkExceptionFactory.bizException(CTalkErrorCode.EMAIl);
        }

        // 创建用户
        userService.addUser(form);
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
