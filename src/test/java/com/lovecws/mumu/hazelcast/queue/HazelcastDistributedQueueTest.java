package com.lovecws.mumu.hazelcast.queue;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式队列测试
 * @date 2017-09-18 10:15
 */
public class HazelcastDistributedQueueTest {

    @Test
    public void queue() throws InterruptedException {
        HazelcastDistributedQueue hazelcastDistributedQueue = new HazelcastDistributedQueue();
        hazelcastDistributedQueue.queue("babyMmQUeue");
    }

    @Test
    public void proceducerAndConsumer() throws InterruptedException {
        HazelcastDistributedQueue hazelcastDistributedQueue = new HazelcastDistributedQueue();
        //开启生产者线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    hazelcastDistributedQueue.proceducer("babyMmQueue", 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //等待1s
        TimeUnit.SECONDS.sleep(1);
        CountDownLatch latch = new CountDownLatch(2);
        //开启两个消费者线程
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HazelcastDistributedQueue distributedQueue = new HazelcastDistributedQueue();
                    try {
                        distributedQueue.consumer("babyMmQueue");
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        latch.await();
    }
}
