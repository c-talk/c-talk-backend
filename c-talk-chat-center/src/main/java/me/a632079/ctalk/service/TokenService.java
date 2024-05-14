package me.a632079.ctalk.service;

import me.a632079.ctalk.po.Token;

import java.util.Optional;

/**
 * @className: TokenService
 * @description: TokenService - TODO
 * @version: v1.0.0
 * @author: haoduor
 */
public interface TokenService {
    Token createToken(Long id);

    Optional<Token> getToken(String token);
}
