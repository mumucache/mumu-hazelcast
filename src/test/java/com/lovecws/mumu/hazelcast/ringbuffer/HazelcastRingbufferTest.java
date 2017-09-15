package com.lovecws.mumu.hazelcast.ringbuffer;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: TODO
 * @date 2017-09-15 17:36
 */
public class HazelcastRingbufferTest {

    @Test
    public void ringbuffer(){
        new HazelcastRingbuffer().ringbuffer("ringbuffer");
    }
}

