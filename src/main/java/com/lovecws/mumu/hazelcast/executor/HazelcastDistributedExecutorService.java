package com.lovecws.mumu.hazelcast.executor;

import com.hazelcast.console.Echo;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.Member;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.io.Serializable;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式线程调度器
 * @date 2017-09-15 15:30
 */
public class HazelcastDistributedExecutorService {

    /**
     * 消息调度
     */
    public void executor() {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();

        IExecutorService ex = hazelcastInstance.getExecutorService("my-distributed-executor");
        ex.submit(new MessagePrinter("message to any node"));
        Member firstMember = hazelcastInstance.getCluster().getMembers().iterator().next();
        ex.executeOnMember(new MessagePrinter("message to very first member of the cluster"), firstMember);
        ex.executeOnAllMembers(new MessagePrinter("message to all members in the cluster"));
        ex.executeOnKeyOwner(new MessagePrinter("message to the member that owns the following key"), "key");
        ex.submit(new Echo("hello world"));
        hazelcastInstance.shutdown();
    }

    /**
     * 消息调度器
     */
    public static class MessagePrinter implements Runnable, Serializable {
        final String message;

        MessagePrinter(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println(message);
        }
    }
}