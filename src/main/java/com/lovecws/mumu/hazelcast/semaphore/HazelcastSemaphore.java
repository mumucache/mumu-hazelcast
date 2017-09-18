package com.lovecws.mumu.hazelcast.semaphore;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ISemaphore;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式信号量
 * @date 2017-09-16 20:52
 */
public class HazelcastSemaphore {

    /**
     * 初始化信号量
     *
     * @param semaphoreName  信号量名称
     * @param semaphoreCount 消耗量数量
     * @throws InterruptedException
     */
    public void semaphore(String semaphoreName, int semaphoreCount) throws InterruptedException {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        //初始化信号量
        ISemaphore semaphore = hazelcastInstance.getSemaphore(semaphoreName);
        semaphore.init(semaphoreCount);
        //调用线程消耗信号量
        CountDownLatch countDownLatch = new CountDownLatch(semaphoreCount * 2);
        for (int i = 0; i < semaphoreCount * 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("线程-" + Thread.currentThread().getName() + "获取到信号量");
                        TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                        System.out.println("线程-" + Thread.currentThread().getName() + "释放信号量");
                        countDownLatch.countDown();
                    }
                }
            }).start();
        }
        countDownLatch.await();
        hazelcastInstance.shutdown();
    }
}
