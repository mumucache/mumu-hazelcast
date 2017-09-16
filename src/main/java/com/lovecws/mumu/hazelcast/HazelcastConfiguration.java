package com.lovecws.mumu.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Properties;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: hazelcast配置
 * @date 2017-09-15 15:30
 */
public class HazelcastConfiguration {

    private static String HAZELCAT_HOST = "127.0.0.1:5701";

    private static HazelcastInstance hazelcastInstance = null;//服务实例
    private static HazelcastInstance hazelcastClient = null;//客户端实例

    /**
     * 获取到hazelcast 客户端实例
     *
     * @return
     */
    public static synchronized HazelcastInstance instance() {
        if (hazelcastInstance == null) {
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.addAddress(HAZELCAT_HOST);
            try {
                hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
            } catch (Exception e) {
                e.printStackTrace();
                //TODO 如果客户端连接不上 则自己创建一个本地服务
                System.err.println("客户端链接失败[" + HAZELCAT_HOST + "],开启自定义Hazelcast服务");
                hazelcastInstance = server();
            }
        }
        return hazelcastInstance;
    }

    /**
     * 获取到hazelcast实例,创建服务
     *
     * @return
     */
    public static synchronized HazelcastInstance server() {
        if (hazelcastClient == null) {
            Config config = new Config();
            hazelcastClient = Hazelcast.newHazelcastInstance(config);
        }
        return hazelcastClient;
    }

}
