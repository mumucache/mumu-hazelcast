package com.lovecws.mumu.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: hazelcast配置
 * @date 2017-09-15 15:30
 */
public class HazelcastConfiguration {

    private static String HAZELCAT_HOST="127.0.0.1:5701";

    /**
     * 获取到hazelcast 客户端实例
     *
     * @return
     */
    public static HazelcastInstance instance() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.addAddress(HAZELCAT_HOST);
        //创建客户端
        HazelcastInstance client=null;
        try {
            client = HazelcastClient.newHazelcastClient(clientConfig);
        }catch (Exception e){
            e.printStackTrace();
            //TODO 如果客户端连接不上 则自己创建一个本地服务
            client=server();
        }

        return client;
    }

    /**
     * 获取到hazelcast实例,创建服务
     *
     * @return
     */
    public static HazelcastInstance server() {
        Config config = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        return instance;
    }

}
