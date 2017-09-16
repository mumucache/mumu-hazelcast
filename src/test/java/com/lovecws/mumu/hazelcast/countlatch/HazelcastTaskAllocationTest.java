package com.lovecws.mumu.hazelcast.countlatch;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: TODO
 * @date 2017-09-16 18:55
 */
public class HazelcastTaskAllocationTest {

    @Test
    public void allowTask() throws InterruptedException {
        new HazelcastTaskAllocation().allowTask("babymmcountdownlatch", 3);
    }
}
