package com.lovecws.mumu.hazelcast.list;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式list测试 Hazelcast List is similar to Hazelcast Set, but Hazelcast List also allows duplicate elements.
 * Hazelcast List also preserves the order of elements. Hazelcast List is a non-partitioned data structure where values and each
 * backup are represented by their own single partition. Hazelcast List cannot be scaled beyond the capacity of a single machine.
 * All items are copied to local and iteration occurs locally
 *
 * @date 2017-09-18 10:27
 */
public class HazelcastListTest {

    @Test
    public void list() {
        new HazelcastList().list("babyMmList");
    }
}
