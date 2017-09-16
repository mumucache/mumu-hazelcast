package com.lovecws.mumu.hazelcast.countlatch;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICountDownLatch;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: countLatch闭锁
 * @date 2017-09-16 15:21
 */
public class HazelcastCountLatch {

    /**
     * 分布式闭锁
     *
     * @param countLatchName 闭锁名称
     * @param count          闭锁数量
     * @throws InterruptedException
     */
    public void countLatch(String countLatchName, int count) throws InterruptedException {
        HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
        ICountDownLatch countDownLatch = hazelcastInstance.getCountDownLatch(countLatchName);
        int countDownLatchCount = countDownLatch.getCount();
        System.out.println("countDownLatchCount:" + countDownLatchCount);
        if (countDownLatchCount == 0) {
            countDownLatch.trySetCount(count);
        }
        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程-" + Thread.currentThread().getName() + "准备完毕....");
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await(100, TimeUnit.SECONDS);
        System.out.println("开始....");
        hazelcastInstance.shutdown();
    }

    /**
     * 场景模拟:主线程分配几个任务，然后等待每个任务完成之后，在继续进行主任务。
     * 分布式闭锁可以适用于：当一个应用等待另一个或者多个应用协调处理一件事情，当其他的应用处理完成之后，才继续处理之后的应用
     *
     * @param countLatchName
     */
    public void mainTaskApplication(String countLatchName, int count) throws InterruptedException {
        HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
        ICountDownLatch countDownLatch = hazelcastInstance.getCountDownLatch(countLatchName);
        //设置一个应用需要多少个其他的应用来协调完成
        countDownLatch.trySetCount(count);
        System.out.println("等待:" + count + "个子任务完成....");
        //阻塞等待100秒
        boolean await = countDownLatch.await(100, TimeUnit.SECONDS);
        if (await) {
            System.out.println("任务完成....");
        } else {
            System.out.println("子任务完成失败.....");
        }
    }

    /**
     * 模拟应用
     *
     * @param countLatchName 闭锁名称
     */
    public void taskApplication(String countLatchName) throws InterruptedException {
        HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
        ICountDownLatch countDownLatch = hazelcastInstance.getCountDownLatch(countLatchName);
        TimeUnit.SECONDS.sleep(new Random().nextInt(5));
        countDownLatch.countDown();
        System.out.println("任务：" + Thread.currentThread().getName() + "完成....");
        //hazelcastInstance.shutdown();
    }
}
