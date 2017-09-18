package com.lovecws.mumu.hazelcast.HyperLogLog;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: hyperLogLog测试
 * @date 2017-09-18 10:04
 */
public class HazelcastHyperLogLogTest {

    @Test
    public void hyperLogLog() {
        HazelcastHyperLogLog hazelcastHyperLogLog = new HazelcastHyperLogLog();
        hazelcastHyperLogLog.estimate("babyMmHyperLogLog");
    }
}
