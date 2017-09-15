package com.lovecws.mumu.hazelcast.lock;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 测试分布式锁
 * @date 2017-09-15 17:06
 */
public class HazelcastDistributedLockTest {
    private static final HazelcastDistributedLock hazelcastDistributedLock = new HazelcastDistributedLock();

    @Test
    public void lock() throws InterruptedException {
        int LOCK_COUNT = 10;
        CountDownLatch latch = new CountDownLatch(LOCK_COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(LOCK_COUNT);
        for (int i = 0; i < LOCK_COUNT; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    hazelcastDistributedLock.lock("babymmlock");
                    latch.countDown();
                }
            });
        }
        latch.await();
    }
}
