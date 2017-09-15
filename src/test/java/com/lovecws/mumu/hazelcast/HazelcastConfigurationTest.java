package com.lovecws.mumu.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 测试hazelcast配置信息
 * @date 2017-09-15 16:05
 */
public class HazelcastConfigurationTest {

    @Test
    public void instance() {
        HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
        System.out.println(hazelcastInstance);
        hazelcastInstance.shutdown();
    }

    @Test
    public void server() throws InterruptedException {
        HazelcastConfiguration.server();
        TimeUnit.SECONDS.sleep(100);
    }

    public static void main(String[] args) {
        HazelcastConfiguration.server();
    }
}
