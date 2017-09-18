package com.lovecws.mumu.hazelcast.set;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ISet;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式set
 * @date 2017-09-18 10:24
 */
public class HazelcastSet {

    public void set(String setName) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        ISet<Object> hazelcastInstanceSet = hazelcastInstance.getSet(setName);
        for (int i = 0; i < 10000; i++) {
            hazelcastInstanceSet.add("lover");
        }
        Iterator<Object> objectIterator = hazelcastInstanceSet.iterator();
        while (objectIterator.hasNext()) {
            Object next = objectIterator.next();
            System.out.println(next);
        }
        System.out.println("set数量" + hazelcastInstanceSet.size());
    }
}
