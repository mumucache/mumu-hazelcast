package com.lovecws.mumu.hazelcast.map;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: TODO
 * @date 2017-09-15 17:15
 */
public class HazelcastDistributedMultiMapTest {

    private static final HazelcastDistributedMultiMap hazelcastDistributedMultiMap = new HazelcastDistributedMultiMap();
    private static final String MULTIMAP_NAME = "babyMumuMultiMap";

    @Test
    public void multiMap() {
        hazelcastDistributedMultiMap.multiMap(MULTIMAP_NAME);
    }
}
