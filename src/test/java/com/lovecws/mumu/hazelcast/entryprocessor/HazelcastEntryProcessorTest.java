package com.lovecws.mumu.hazelcast.entryprocessor;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 测试
 * @date 2017-09-15 16:28
 */
public class HazelcastEntryProcessorTest {

    @Test
    public void entryProcessor() throws InterruptedException {
        new HazelcastEntryProcessor().entryProcessor("entryProcessorMap","babymm");
    }
}
