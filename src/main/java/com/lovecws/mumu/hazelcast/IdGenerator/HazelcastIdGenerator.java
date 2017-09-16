package com.lovecws.mumu.hazelcast.IdGenerator;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.util.concurrent.CountDownLatch;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式id生成器
 * @date 2017-09-16 21:34
 */
public class HazelcastIdGenerator {

    /**
     * id生成器
     *
     * @param idGeneratorName idGenerator名称
     * @param baseId          idGenerator 已baseId起步
     */
    public void idGenerator(String idGeneratorName, long baseId) {
        HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
        IdGenerator idGenerator = hazelcastInstance.getIdGenerator(idGeneratorName);
        //id生成器已 baseId 为起步
        idGenerator.init(100000);
        for (int i = 0; i < 100; i++) {
            long newId = idGenerator.newId();
            System.out.println(newId);
        }
        hazelcastInstance.shutdown();
    }

    /**
     * 多线程测试id生成器
     *
     * @param idGeneratorName idGenerator名称
     * @param baseId          已baseId起步
     * @throws InterruptedException
     */
    public void generator(String idGeneratorName, long baseId) throws InterruptedException {
        HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
        IdGenerator idGenerator = hazelcastInstance.getIdGenerator(idGeneratorName);
        //id生成器已 baseId 为起步
        CountDownLatch countDownLatch = new CountDownLatch(100);
        idGenerator.init(100000);
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(idGenerator.newId());
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
    }
}
