package com.lovecws.mumu.hazelcast.set;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式set测试
 * @date 2017-09-18 10:27
 */
public class HazelcastSetTest {

    @Test
    public void set() {
        new HazelcastSet().set("babyMmSet");
    }
}
