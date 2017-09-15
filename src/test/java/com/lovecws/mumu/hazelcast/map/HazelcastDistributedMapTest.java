package com.lovecws.mumu.hazelcast.map;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: TODO
 * @date 2017-09-15 17:15
 */
public class HazelcastDistributedMapTest {

    private static final HazelcastDistributedMap hazelcastDistributedMap = new HazelcastDistributedMap();
    private static final String MAP_NAME = "babyMumuMap";

    @Test
    public void get() {
        Object lover = hazelcastDistributedMap.get(MAP_NAME, "lover");
        System.out.println(lover);
    }

    @Test
    public void getAsync() throws ExecutionException, InterruptedException {
        Object lover = hazelcastDistributedMap.getAsync(MAP_NAME, "lover");
        System.out.println(lover);
    }

    @Test
    public void put() {
        hazelcastDistributedMap.put(MAP_NAME, "lover", "cws");
    }


    @Test
    public void remove() {
        hazelcastDistributedMap.remove(MAP_NAME, "lover");
    }
}
