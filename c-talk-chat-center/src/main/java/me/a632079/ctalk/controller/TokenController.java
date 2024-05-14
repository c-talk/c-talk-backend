package me.a632079.ctalk.controller;

import me.a632079.ctalk.po.Token;
import me.a632079.ctalk.service.TokenService;
import me.a632079.ctalk.util.UserInfoUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @className: TokenService
 * @description: TokenService - 令牌
 * @version: v1.0.0
 * @author: haoduor
 */

@RestController
@RequestMapping("/token")
public class TokenController {

    @Resource
    private TokenService tokenService;

    @GetMapping("/get")
    public Token getToken() {
        return tokenService.createToken(UserInfoUtil.getId());
    }
}
