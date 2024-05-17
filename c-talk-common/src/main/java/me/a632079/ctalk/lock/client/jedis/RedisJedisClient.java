package me.a632079.ctalk.lock.client.jedis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.a632079.ctalk.lock.property.LockProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.DigestUtils;


import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.*;
import redis.clients.jedis.commands.JedisClusterCommands;
import redis.clients.jedis.commands.JedisCommands;
import redis.clients.jedis.params.SetParams;

/**
 * redis 客户端：居于jedis实现
 *
 * @author caszhou
 * @date 2023/4/20
 */
@Slf4j
@Configuration
public class RedisJedisClient {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    /**
     * 单机链接池
     */
    private static JedisPool jedisPool = null;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(30);
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(5);
        jedisPool = new JedisPool(poolConfig, host, port, 2000);
        return jedisPool;
    }

    private volatile static RedisJedisClient client;

    /**
     * 双锁单例
     */
    public static RedisJedisClient getInstance() {
        if (client == null) {
            synchronized (RedisJedisClient.class) {
                if (client == null) {
                    client = new RedisJedisClient();
                }
            }
        }
        return client;
    }

    /**
     * Jedis 命令对象
     */
    protected abstract class BaseCommand {
        /**
         * 运行生命周期
         */
        @SuppressWarnings("unchecked")
        public <T> T exeLife() {
            T obj = null;
            if (null != jedisPool) {
                try (Jedis jedis = jedisPool.getResource()) {
                    obj = (T)exe(jedis);
                } catch (Exception e) {
                    log.error(StringUtils.EMPTY, e);
                }
            }
            return obj;
        }

        /**
         * 执行命令
         */
        public abstract Object exe(JedisCommands commands);

        /**
         * 执行命令-cluster
         */
        public abstract Object exe(JedisClusterCommands commands);
    }

    /**
     * set NX PX
     */
    public String setNxPx(String key, String value, long second) {
        return new BaseCommand() {
            @Override
            public String exe(JedisCommands commands) {
                String realKey = getRealKey(key);
                return commands.set(realKey, value, SetParams.setParams().nx().ex(second));
            }

            @Override
            public Object exe(JedisClusterCommands commands) {
                String realKey = getRealKey(key);
                return commands.set(realKey, value, SetParams.setParams().nx().ex(second));
            }
        }.exeLife();
    }

    /**
     * 设置过期时间
     */
    public Long expire(String key, long second) {
        return new BaseCommand() {
            @Override
            public Long exe(JedisCommands commands) {
                return commands.expire(key, second);
            }

            @Override
            public Object exe(JedisClusterCommands commands) {
                return commands.expire(key, second);
            }
        }.exeLife();
    }

    /**
     * 删除缓存项(校验value)
     */
    public Long delete(String key, String value) {
        // TODO: 此处需要用lua才能保证原子性，否则有一定几率误删锁，增加AP Redis多重获锁的几率。 Jedis框架的ShardedJedis不支持Lua，改造成本较大，偷个懒。坐等夏老板修复
        return new BaseCommand() {
            @Override
            public Long exe(JedisCommands commands) {
                if (commands.exists(key) && commands.get(key).equals(value)) {
                    return commands.del(key);
                }
                return 0L;
            }

            @Override
            public Object exe(JedisClusterCommands commands) {
                if (commands.exists(key) && commands.get(key).equals(value)) {
                    return commands.del(key);
                }
                return 0L;
            }
        }.exeLife();
    }

    public static String getRealKey(String key) {
        return key.length() > 32 ? DigestUtils.md5DigestAsHex(key.getBytes()) : key;
    }
}
