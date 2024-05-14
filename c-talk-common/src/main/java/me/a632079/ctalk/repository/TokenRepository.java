package me.a632079.ctalk.repository;

import me.a632079.ctalk.po.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @className: TokenRepository
 * @description: TokenRepository - 令牌存储
 * @version: v1.0.0
 * @author: haoduor
 */
@Repository
public interface TokenRepository extends MongoRepository<Token, Long> {
    Token findFirstByUid(Long uid);
}
