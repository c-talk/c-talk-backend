package me.a632079.ctalk.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.po.UserInfo;
import me.a632079.ctalk.util.UserInfoUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className: UserInfoInterceptor
 * @description: UserInfoInterceptor - 用户信息注入工具类
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@Component
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!StpUtil.isLogin()) {
            return false;
        }

        UserInfo info = new UserInfo();
        info.setId(StpUtil.getLoginIdAsLong());

        UserInfoUtil.setUserInfo(info);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoUtil.clear();
    }
}
