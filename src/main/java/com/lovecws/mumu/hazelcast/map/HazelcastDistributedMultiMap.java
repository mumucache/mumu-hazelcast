package com.lovecws.mumu.hazelcast.map;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.util.Collection;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 可重复键MAP
 * @date 2017-09-15 15:30
 */
public class HazelcastDistributedMultiMap {

    /**
     * 可重复键的map
     *
     * @param multiMapName 重复键map
     */
    public void multiMap(String multiMapName) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        MultiMap<String, String> multiMap = hazelcastInstance.getMultiMap(multiMapName);
        multiMap.put("key", "value1");
        multiMap.put("key", "value2");
        multiMap.put("key", "value3");

        Collection<String> values = multiMap.get("key");

        // remove specific key/value pair
        multiMap.remove("key", "value2");
        Collection<String> values1 = multiMap.values();
        for(String value:values1){
            System.out.println(value);
        }
        System.out.println(multiMap);
        hazelcastInstance.shutdown();
    }
}