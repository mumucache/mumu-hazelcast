package com.lovecws.mumu.hazelcast.ringbuffer;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式队列
 * @date 2017-09-15 15:30
 */
public class HazelcastRingbuffer {

    /**
     * @param ringBuffer
     */
    public void ringbuffer(String ringBuffer) {
        HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
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
                for (; ; ) {
                    System.out.println(rb.readOne(seq));
                    seq++;
                }
            } catch (InterruptedException e) {
            }
        }
    }
}