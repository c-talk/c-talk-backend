package me.a632079.ctalk.service;

import io.lettuce.core.internal.ExceptionFactory;
import me.a632079.ctalk.exception.CTalkExceptionFactory;
import me.a632079.ctalk.lock.Lock;
import me.a632079.ctalk.lock.jedis.RedisJedisSolution;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static me.a632079.ctalk.enums.CTalkErrorCode.LOCK_ACQUIRE_ERROR;

@Service
public class LockService {

    private RedisJedisSolution redisJedisSolution = new RedisJedisSolution();

    public Object lock(String lockName, LockCallback lockCallback) throws IOException, TimeoutException {
        try (Lock lock = redisJedisSolution.newLock(lockName, true)) {
            boolean state = lock.acquire(10, 0.5D, 5);
            if (state) {
                return lockCallback.onLockAcquired();
            } else {
                throw CTalkExceptionFactory.bizException(LOCK_ACQUIRE_ERROR);
            }
        }
    }

    public Object lock(Long id, int ttl, double interval, int maxRetry, LockCallback lockCallback) throws IOException, TimeoutException {
        try (Lock lock = redisJedisSolution.newLock(String.valueOf(id), true)) {
            boolean state = lock.acquire(ttl, interval, maxRetry);
            if (state) {
                return lockCallback.onLockAcquired();
            } else {
                throw CTalkExceptionFactory.bizException(LOCK_ACQUIRE_ERROR);
            }
        }
    }
}