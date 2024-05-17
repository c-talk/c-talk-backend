package me.a632079.ctalk.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className: LockCallback
 * @description: LockCallback - TODO
 * @version: v1.0.0
 * @author: haoduor
 */


public interface LockCallback {
    Object onLockAcquired() throws IOException, TimeoutException;
}