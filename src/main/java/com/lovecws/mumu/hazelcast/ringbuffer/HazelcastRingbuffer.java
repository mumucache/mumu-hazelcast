package com.lovecws.mumu.hazelcast.ringbuffer;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式环 将固定数量的数据组成一个环，当数据存满之后，覆盖最先创建的数据
 * @date 2017-09-15 15:30
 */
public class HazelcastRingbuffer {

    /**
     * @param ringBuffer
     */
    public void ringbuffer(String ringBuffer) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        final Ringbuffer<Integer> rb = hazelcastInstance.getRingbuffer(ringBuffer);
        new MyThread(rb).start();

        for (int k = 0; k < 100; k++) {
            rb.add(k);
        }
    }

    private static class MyThread extends Thread {
        private final Ringbuffer<Integer> rb;

        public MyThread(Ringbuffer<Integer> rb) {
            this.rb = rb;
        }

        @Override
        public void run() {
            try {
                long seq = rb.tailSequence();
                if (seq > 0) {
                    for (; ; ) {
                        System.out.println(rb.readOne(seq));
                        seq++;
                    }
                }
            } catch (InterruptedException e) {
            }
        }
    }
}