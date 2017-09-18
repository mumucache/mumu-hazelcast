package com.lovecws.mumu.hazelcast.atomic;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 原子引用
 * @date 2017-09-18 13:46
 */
public class HazelcastAtomicReferenceTest {

    @Test
    public void reference() {
        new HazelcastAtomicReference().reference("babyMmReference");
    }
}
