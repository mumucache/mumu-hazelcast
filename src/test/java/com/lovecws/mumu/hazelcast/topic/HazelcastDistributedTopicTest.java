package com.lovecws.mumu.hazelcast.topic;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: TODO
 * @date 2017-09-15 17:38
 */
public class HazelcastDistributedTopicTest {

    @Test
    public void topic() throws InterruptedException {
        new HazelcastDistributedTopic().topic("babyMuTopic");
    }
}
