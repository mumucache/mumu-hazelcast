package com.lovecws.mumu.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.*;
import com.hazelcast.core.*;

import java.util.Collection;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: hazelcast配置
 * @date 2017-09-15 15:30
 */
public class HazelcastConfiguration {

    //private static String HAZELCAT_HOST = "192.168.11.25:5701";
    private static String HAZELCAT_HOST = "127.0.0.1:5701";

    private HazelcastInstance hazelcastInstance = null;//服务实例
    private HazelcastInstance hazelcastClient = null;//客户端实例

    static {
        String HAZELCATHOST = System.getenv("HAZELCAST_HOST");
        if (HAZELCATHOST != null) {
            HAZELCAT_HOST = HAZELCATHOST;
        }
    }

    /**
     * 获取到hazelcast 客户端实例
     *
     * @return
     */
    public synchronized HazelcastInstance instance() {
        if (hazelcastClient == null) {
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.getNetworkConfig()
                    .setConnectionAttemptLimit(1)
                    .setConnectionTimeout(2000)
                    .addAddress(HAZELCAT_HOST);
            EvictionConfig evictionConfig = new EvictionConfig()
                    .setEvictionPolicy(EvictionPolicy.LRU)
                    .setMaximumSizePolicy(EvictionConfig.MaxSizePolicy.ENTRY_COUNT)
                    .setSize(30000);

            NearCacheConfig nearCacheConfig = new NearCacheConfig()
                    .setName("mostlyReadCache")
                    .setInMemoryFormat(InMemoryFormat.OBJECT)
                    .setInvalidateOnChange(true)
                    .setEvictionConfig(evictionConfig)
                    .setLocalUpdatePolicy(NearCacheConfig.LocalUpdatePolicy.CACHE_ON_UPDATE);
            clientConfig.addNearCacheConfig(nearCacheConfig);

            clientConfig.setProperty("hazelcast.logging.type", "log4j");
            try {
                hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);
            } catch (Exception e) {
                e.printStackTrace();
                //TODO 如果客户端连接不上 则自己创建一个本地服务
                System.err.println("客户端链接失败[" + HAZELCAT_HOST + "],开启自定义Hazelcast服务");
                hazelcastInstance = server();
                hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);
            }
        }
        return hazelcastClient;
    }

    public synchronized HazelcastInstance simpleServer() {
        Config config = new Config();
        config.getNetworkConfig()
                .setPort(5701)
                .setPortAutoIncrement(true);
        //日志方式
        config.setProperty("hazelcast.logging.type", "log4j");
        return Hazelcast.newHazelcastInstance(config);
    }

    /**
     * 获取到hazelcast实例,创建服务 并且附带监听器
     *
     * @return
     */
    public synchronized HazelcastInstance server() {
        if (hazelcastInstance == null) {
            Config config = new Config();
            config.getNetworkConfig()
                    .setPort(5701)
                    .setPortAutoIncrement(true);

            //日志方式
            config.setProperty("hazelcast.logging.type", "log4j");
            hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        }
        //hazelcast集群中添加成员加入和退出监听器
        hazelcastInstance.getCluster().addMembershipListener(new MembershipListener() {
            @Override
            public void memberAdded(final MembershipEvent membershipEvent) {
                System.out.println("成员加入:" + membershipEvent.getMember());
            }

            @Override
            public void memberRemoved(final MembershipEvent membershipEvent) {
                System.out.println("成员移除:" + membershipEvent.getMember());
            }

            @Override
            public void memberAttributeChanged(final MemberAttributeEvent memberAttributeEvent) {
                System.out.println("成员属性修改:" + memberAttributeEvent.getMember());
            }
        });
        //分布式对象监听器
        hazelcastInstance.addDistributedObjectListener(new DistributedObjectListener() {
            @Override
            public void distributedObjectCreated(final DistributedObjectEvent distributedObjectEvent) {
                System.out.println("分布式对象创建:" + distributedObjectEvent.getDistributedObject());
            }

            @Override
            public void distributedObjectDestroyed(final DistributedObjectEvent distributedObjectEvent) {
                System.out.println("分布式对象销毁:" + distributedObjectEvent.getObjectName());
            }
        });
        //jvm关闭的时候 关闭hazelcast服务
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("shutdown hock...");
                hazelcastInstance.shutdown();
            }
        }));
        return hazelcastInstance;
    }

    /**
     * 获取系统分布式对象
     */
    public synchronized void distributedObjects() {
        HazelcastInstance instance = instance();
        instance.addDistributedObjectListener(new DistributedObjectListener() {
            @Override
            public void distributedObjectCreated(final DistributedObjectEvent distributedObjectEvent) {
                DistributedObject distributedObject = distributedObjectEvent.getDistributedObject();
                System.out.println("分布式对象创建" + distributedObject);

            }

            @Override
            public void distributedObjectDestroyed(final DistributedObjectEvent distributedObjectEvent) {
                DistributedObject distributedObject = distributedObjectEvent.getDistributedObject();
                System.out.println("分布式对象销毁" + distributedObject);
            }
        });
        Collection<DistributedObject> distributedObjects = instance.getDistributedObjects();
        for (DistributedObject distributedObject : distributedObjects) {
            System.out.println(distributedObject);
        }
        instance.shutdown();
    }
}
