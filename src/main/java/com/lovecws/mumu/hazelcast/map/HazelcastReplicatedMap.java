package com.lovecws.mumu.hazelcast.map;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ReplicatedMap;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 全部备份的map，当创建ReplicatedMap,所有的节点都会备份该map，当从最近的节点获取数据的时候会比较快
 * @date 2017-09-15 15:30
 */
public class HazelcastReplicatedMap {

    /**
     * 存入map
     *
     * @param mapName map名称
     * @param key     键
     * @param value   值
     */
    public void put(String mapName, String key, String value) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        ReplicatedMap<String, Object> map = hazelcastInstance.getReplicatedMap(mapName);
        Object put = map.put(key, value);
        System.out.println(put);
        hazelcastInstance.shutdown();
    }

    /**
     * 获取map的值
     *
     * @param mapName map名称
     * @param key     键
     * @return
     */
    public Object get(String mapName, String key) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        ReplicatedMap<String, String> map = hazelcastInstance.getReplicatedMap(mapName);
        Object value = map.get(key);
        hazelcastInstance.shutdown();
        return value;
    }
}