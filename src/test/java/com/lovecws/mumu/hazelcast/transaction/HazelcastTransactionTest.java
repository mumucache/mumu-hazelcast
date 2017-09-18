package com.lovecws.mumu.hazelcast.transaction;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 事务测试
 * @date 2017-09-18 16:27
 */
public class HazelcastTransactionTest {

    @Test
    public void transaction() {
        new HazelcastTransaction().transaction();
    }
}
