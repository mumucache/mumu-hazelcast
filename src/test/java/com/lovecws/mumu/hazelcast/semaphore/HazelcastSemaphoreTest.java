package com.lovecws.mumu.hazelcast.semaphore;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式信号量测试
 * @date 2017-09-16 21:04
 */
public class HazelcastSemaphoreTest {

    @Test
    public void semaphore() throws InterruptedException {
        HazelcastSemaphore hazelcastSemaphore = new HazelcastSemaphore();
        hazelcastSemaphore.semaphore("babymmsemaphore",10);
    }
}
