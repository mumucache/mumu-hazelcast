package com.lovecws.mumu.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 测试hazelcast配置信息
 * @date 2017-09-15 16:05
 */
public class HazelcastConfigurationTest {

    private HazelcastConfiguration hazelcastConfiguration = new HazelcastConfiguration();

    @Test
    public void instance() {
        HazelcastInstance hazelcastInstance = hazelcastConfiguration.instance();
        System.out.println(hazelcastInstance);
        hazelcastInstance.shutdown();
    }

    @Test
    public void server() throws InterruptedException {
        hazelcastConfiguration.server();
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void serverWithListener() throws InterruptedException {
        HazelcastInstance server = hazelcastConfiguration.server();
        //触发成员加入监听器和成员退出监听器
        HazelcastInstance hazelcastInstance = hazelcastConfiguration.simpleServer();
        hazelcastInstance.shutdown();

        //触发成员对象创建和销毁监听器
        IMap<Object, Object> listenerMap = server.getMap("listenerMap");
        listenerMap.put("lover", "cws");
        listenerMap.destroy();
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void distributedObjects() {
        hazelcastConfiguration.distributedObjects();
    }

    public static void main(String[] args) {
        new HazelcastConfiguration().server();
    }
}
