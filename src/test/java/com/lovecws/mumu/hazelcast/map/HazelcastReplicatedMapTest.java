package com.lovecws.mumu.hazelcast.map;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: TODO
 * @date 2017-09-15 17:15
 */
public class HazelcastReplicatedMapTest {

    private static final HazelcastReplicatedMap hazelcastReplicatedMap = new HazelcastReplicatedMap();
    private static final String REPLICATEDMAP_NAME = "babyMumuReplicatedMap";

    @Test
    public void get() {
        Object lover = hazelcastReplicatedMap.get(REPLICATEDMAP_NAME, "lover");
        System.out.println(lover);
    }

    @Test
    public void put() {
        hazelcastReplicatedMap.put(REPLICATEDMAP_NAME, "lover", "cws");
    }

}
