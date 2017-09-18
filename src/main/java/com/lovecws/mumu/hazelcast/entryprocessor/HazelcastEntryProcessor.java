package com.lovecws.mumu.hazelcast.entryprocessor;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.AbstractEntryProcessor;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.io.Serializable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式map
 * @date 2017-09-15 15:30
 */
public class HazelcastEntryProcessor {

    /**
     * @param mapName map名称
     * @param key     键
     */
    public void entryProcessor(String mapName, String key) throws InterruptedException {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        IMap<String, Object> hazelcastInstanceMap = hazelcastInstance.getMap(mapName);

        hazelcastInstanceMap.put(key, "value");

        Object executeOnKey = hazelcastInstanceMap.executeOnKey(key, new MyEntryProcessor());
        System.out.println(executeOnKey);
        System.out.println("entryProcessor线程-" + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(10);
        System.out.println("new value:" + hazelcastInstanceMap.get(key));
        hazelcastInstance.shutdown();
    }

    public static class MyEntryProcessor extends AbstractEntryProcessor implements Serializable {
        @Override
        public Object process(final Map.Entry entry) {
            if (entry != null) {
                entry.setValue(entry.getValue().toString() + new Random().nextInt(1000));
            }
            System.out.println("MyEntryProcessor线程-" + Thread.currentThread().getName());
            return entry.getValue();
        }
    }
}
