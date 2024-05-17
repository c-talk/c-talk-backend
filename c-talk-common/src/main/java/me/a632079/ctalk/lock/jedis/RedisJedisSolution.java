package me.a632079.ctalk.lock.jedis;

import me.a632079.ctalk.lock.Lock;
import me.a632079.ctalk.lock.LockClient;
import me.a632079.ctalk.lock.client.jedis.RedisJedisClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * redis 解决方案
 * 
 * @author caszhou
 * @date 2023/4/20
 */
@Component
public class RedisJedisSolution implements LockClient {

    @Override
    public LockClient init() {
        return this;
    }

    @Override
    public Lock newLock(String lockKey) {
        return newLock(lockKey, false);
    }

    @Override
    public Lock newLock(String lockKey, boolean reentrant) {
        RedisJedisClient client = RedisJedisClient.getInstance();
        return new RedisJedisLock(client, lockKey, reentrant);
    }
}
