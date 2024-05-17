package me.a632079.ctalk.lock;

/**
 * @className: Lock
 * @description: Lock - TODO
 * @version: v1.0.0
 * @author: haoduor
 */


public interface Lock extends AutoCloseable {
    /**
     * 获取锁，如果没得到，不阻塞
     */
    boolean acquire(int ttl);

    /**
     * 获取锁，直到超时
     */
    boolean acquire(int ttl, double interval, int maxRetry);

    /**
     * 释放锁
     */
    void release();

    /**
     * 释放相关资源
     */
    @Override
    void close();
}
