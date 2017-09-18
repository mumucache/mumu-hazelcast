package com.lovecws.mumu.hazelcast.executor;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 线程调度器测试(客户端发送消息给节点)
 * @date 2017-09-15 16:42
 */
public class HazelcastDistributedExecutorServiceTest {

    @Test
    public void executor() {
        new HazelcastDistributedExecutorService().executor();
    }
}
