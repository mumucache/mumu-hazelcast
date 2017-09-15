package com.lovecws.mumu.hazelcast.atomic;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式原子类
 * @date 2017-09-15 15:30
 */
public class HazelcastAtomicLong {

    /**
     * 获取分布式原子类
     *
     * @param atomicLongName 原子long名称
     * @return
     */
    public IAtomicLong atomicLong(String atomicLongName) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        IAtomicLong atomicLong = hazelcastInstance.getAtomicLong(atomicLongName);
        hazelcastInstance.shutdown();
        return atomicLong;
    }

    /**
     * 获取计数器并增加值1
     *
     * @param atomicLongName 原子long名称
     * @return
     */
    public long getAndIncrement(String atomicLongName) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        IAtomicLong atomicLong = hazelcastInstance.getAtomicLong(atomicLongName);
        long andIncrement = atomicLong.getAndIncrement();
        hazelcastInstance.shutdown();
        return andIncrement;
    }

}