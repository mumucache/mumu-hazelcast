package com.lovecws.mumu.hazelcast.list;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.util.ListIterator;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式list
 * @date 2017-09-18 10:24
 */
public class HazelcastList {

    public void list(String listName) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        IList<Object> hazelcastInstanceList = hazelcastInstance.getList(listName);
        for (int i = 0; i < 10000; i++) {
            hazelcastInstanceList.add("lover");
        }
        ListIterator<Object> objectListIterator = hazelcastInstanceList.listIterator();
        while (objectListIterator.hasNext()) {
            Object next = objectListIterator.next();
            System.out.println(next);
        }
        System.out.println("list数量"+hazelcastInstanceList.size());
    }
}
