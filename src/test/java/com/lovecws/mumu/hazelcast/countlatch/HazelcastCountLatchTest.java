package com.lovecws.mumu.hazelcast.countlatch;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式闭锁
 * @date 2017-09-16 15:27
 */
public class HazelcastCountLatchTest {

    @Test
    public void countLatch() throws InterruptedException {
        new HazelcastCountLatch().countLatch("hazelcastCountLatch", 10);
    }

    @Test
    public void taskAllocate() throws InterruptedException {
        HazelcastCountLatch hazelcastCountLatch = new HazelcastCountLatch();
        CountDownLatch latchCountDownLatch = new CountDownLatch(1);
        //主线程开始分配任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    latchCountDownLatch.countDown();
                    hazelcastCountLatch.mainTaskApplication("babymmCountLatch", 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //等待主任务分配
        latchCountDownLatch.await();
        //确保主任务正在运行
        TimeUnit.SECONDS.sleep(10);

        //开始子任务
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        hazelcastCountLatch.taskApplication("babymmCountLatch");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        //System.out.println("任务完成...");
        //等待主线程完成任务
        TimeUnit.SECONDS.sleep(5);
    }
}
