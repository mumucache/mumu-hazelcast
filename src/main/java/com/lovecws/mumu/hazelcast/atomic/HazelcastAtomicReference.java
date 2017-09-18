package com.lovecws.mumu.hazelcast.atomic;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicReference;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 原子引用
 * @date 2017-09-18 13:43
 */
public class HazelcastAtomicReference {

    /**
     * 原子引用
     * @param atomicReferenceName
     */
    public void reference(String atomicReferenceName) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        IAtomicReference<Object> atomicReference = hazelcastInstance.getAtomicReference(atomicReferenceName);
        atomicReference.set("lovercws");
        Object o = atomicReference.get();
        System.out.println(o);
        hazelcastInstance.shutdown();
    }
}
