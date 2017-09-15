package com.lovecws.mumu.hazelcast.atomic;

import com.hazelcast.core.IAtomicLong;
import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: TODO
 * @date 2017-09-15 16:16
 */
public class HazelcastAtomicLongTest {

    private static final HazelcastAtomicLong atomicLong = new HazelcastAtomicLong();

    @Test
    public void atomicLong() {
        IAtomicLong babyMuAtomicLong = atomicLong.atomicLong("babyMuAtomicLong");
        System.out.println(babyMuAtomicLong);
    }

    @Test
    public void getAndIncrement() {
        long babyMuAtomicLong = atomicLong.getAndIncrement("babyMuAtomicLong");
        System.out.println(babyMuAtomicLong);
    }
}
