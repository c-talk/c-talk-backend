package me.a632079.ctalk.service.impl;

import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.po.Token;
import me.a632079.ctalk.repository.TokenRepository;
import me.a632079.ctalk.service.TokenService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * @className: TokenServiceImpl
 * @description: TokenServiceImpl - 令牌
 * @version: v1.0.0
 * @author: haoduor
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    private final static Object lock = new Object();

    @Override
    public Token createToken(Long id) {
        synchronized (lock) {
            Token one = tokenRepository.findFirstByUid(id);
            if (Objects.nonNull(one)) {
                return one;
            }

            Token token = Token.builder()
                               .uid(id)
                               // TODO 令牌生成方法需要修改
                               .token(RandomUtil.randomString(12))
                               .expire(LocalDateTime.now())
                               .build();
            return tokenRepository.insert(token);
        }
    }

    @Override
    public Optional<Token> getToken(String token) {
        Token tokenExample = Token.builder()
                                  .token(token)
                                  .build();
        return tokenRepository.findOne(Example.of(tokenExample));
    }
}
