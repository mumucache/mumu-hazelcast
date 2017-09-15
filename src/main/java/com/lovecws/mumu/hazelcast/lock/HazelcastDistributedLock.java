package com.lovecws.mumu.hazelcast.lock;

import com.hazelcast.core.HazelcastInstance;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式锁
 * @date 2017-09-15 15:30
 */
public class HazelcastDistributedLock {

    /**
     * 当一个线程获取到分布式锁的时候，其他线程不能获取到这个锁，这能阻塞等待持有这个锁的线程释放锁
     *
     * @param lockName
     */
    public void lock(String lockName) {
        HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
        Lock lock = hazelcastInstance.getLock(lockName);
        lock.lock();
        try {
            System.out.println("线程-" + Thread.currentThread().getName() + "获取锁" + lockName + "....");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程-" + Thread.currentThread().getName() + "释放锁" + lockName + "....\n");
            lock.unlock();
        }
    }
}