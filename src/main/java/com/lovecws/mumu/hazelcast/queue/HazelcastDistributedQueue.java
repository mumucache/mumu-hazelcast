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
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        BlockingQueue<String> queue = hazelcastInstance.getQueue(queueName);
        //先清空队列数据
        queue.clear();
        //直接存入、取出队列数据
        queue.offer("item");
        String item = queue.poll();
        System.out.println("队列直接获取数据:" + item);

        //阻塞固定时间
        queue.offer("anotheritem", 500, TimeUnit.MILLISECONDS);
        String anotherItem = queue.poll(5, TimeUnit.SECONDS);
        System.out.println("队列阻塞固定时间获取数据:" + anotherItem);

        //一直阻塞
        queue.put("yetanotheritem");
        String yetanother = queue.take();
        System.out.println("队列阻塞获取数据:" + yetanother);
    }

    /**
     * 生产者 相对列里面添加数据
     *
     * @param queueName 队列名称
     * @throws InterruptedException
     */
    public void proceducer(String queueName, int queueCount) throws InterruptedException {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        BlockingQueue<String> queue = hazelcastInstance.getQueue(queueName);
        //先清空队列数据
        queue.clear();
        for (int i = 0; i < queueCount; i++) {
            queue.put("lovercws" + i);
            System.out.println("produce: " + "lovercws" + i);
            Thread.sleep(1000);
        }
        queue.put("-1");
        hazelcastInstance.shutdown();
    }

    /**
     * 消费者 从队列里面获取数据，每一条数据只能被一个消费者消费，当生产者添加数据比较快的时候，可以添加多个消费者来负载均衡队列数据
     *
     * @param queueName 队列名称
     * @throws InterruptedException
     */
    public void consumer(String queueName) throws InterruptedException {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        BlockingQueue<String> queue = hazelcastInstance.getQueue(queueName);
        while (true) {
            String take = queue.take();
            System.out.println("consumer-" + Thread.currentThread().getName() + ":" + take);
            Thread.sleep(2000);
            if ("-1".equalsIgnoreCase(take)) {
                break;
            }
        }
        queue.put("-1");//当这个消费者接受到结束通知，但是其他的消费者就不会接受到结束通知（一条数据只能被一个队列获取），所以发送向队列中发送结束通知
        System.out.println("consumer-" + Thread.currentThread().getName() + ":完成");
        hazelcastInstance.shutdown();
    }
}     