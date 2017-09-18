package com.lovecws.mumu.hazelcast.map;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.util.concurrent.ExecutionException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式map
 * @date 2017-09-15 15:30
 */
public class HazelcastDistributedMap {

    /**
     * 获取存储的值
     *
     * @param map map名称
     * @param key map key
     * @return
     */
    public Object get(String map, String key) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        //如果map不存在 则自动创建
        IMap<Object, Object> instanceMap = hazelcastInstance.getMap(map);
        Object obj = instanceMap.get(key);
        hazelcastInstance.shutdown();
        return obj;
    }

    /**
     * 异步获取map值
     *
     * @param map map名称
     * @param key map key
     * @return
     */
    public Object getAsync(String map, String key) throws ExecutionException, InterruptedException {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        //如果map不存在 则自动创建
        IMap<Object, Object> instanceMap = hazelcastInstance.getMap(map);
        //异步调用
        ICompletableFuture<Object> mapAsync = instanceMap.getAsync(key);
        //等待结果值
        Object obj = mapAsync.get();
        hazelcastInstance.shutdown();
        return obj;
    }

    /**
     * 存储map
     *
     * @param map   map名称
     * @param key   键
     * @param value 值
     */
    public void put(String map, String key, Object value) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        IMap<Object, Object> instanceMap = hazelcastInstance.getMap(map);
        //map加锁
        instanceMap.lock(key);
        Object put = instanceMap.put(key, value);
        //map释放锁
        instanceMap.unlock(key);
        System.out.println(put);
        hazelcastInstance.shutdown();
    }

    /**
     * 删除key
     *
     * @param map map名称
     * @param key 键
     */
    public void remove(String map, String key) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        IMap<Object, Object> instanceMap = hazelcastInstance.getMap(map);
        Object obj = instanceMap.remove(key);
        System.out.println(obj);
        hazelcastInstance.shutdown();
    }
}    