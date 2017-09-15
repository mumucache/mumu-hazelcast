package com.lovecws.mumu.hazelcast.queue;

import com.hazelcast.core.HazelcastInstance;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式队列
 * @date 2017-09-15 15:30
 */
public class HazelcastDistributedQueue {

    /**
     * 分布式队列
     *
     * @param queueName 队列名称
     * @throws InterruptedException
     */
    public void queue(String queueName) throws InterruptedException {
        HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
        BlockingQueue<String> queue = hazelcastInstance.getQueue(queueName);
        queue.offer("item");
        String item = queue.poll();

        //Timed blocking Operations
        queue.offer("anotheritem", 500, TimeUnit.MILLISECONDS);
        String anotherItem = queue.poll(5, TimeUnit.SECONDS);

        //Indefinitely blocking Operations
        queue.put("yetanotheritem");
        String yetanother = queue.take();
    }
}     