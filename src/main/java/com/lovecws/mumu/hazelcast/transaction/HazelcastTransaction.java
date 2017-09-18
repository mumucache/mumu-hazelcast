package com.lovecws.mumu.hazelcast.transaction;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.TransactionalMap;
import com.hazelcast.core.TransactionalQueue;
import com.hazelcast.core.TransactionalSet;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionOptions;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式事务
 * @date 2017-09-18 16:23
 */
public class HazelcastTransaction {

    public void transaction() {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        TransactionOptions options = new TransactionOptions().setTransactionType(TransactionOptions.TransactionType.ONE_PHASE);
        TransactionContext transactionContext = hazelcastInstance.newTransactionContext(options);
        transactionContext.beginTransaction();
        TransactionalQueue queue = transactionContext.getQueue("myqueue");
        TransactionalMap map = transactionContext.getMap("mymap");
        TransactionalSet set = transactionContext.getSet("myset");

        try {
            Object obj = queue.poll();
            System.out.println(obj);
            map.put("1", "value1");
            set.add("value");
            transactionContext.commitTransaction();
        } catch (Throwable t) {
            transactionContext.rollbackTransaction();
        }
    }
}
