package com.lovecws.mumu.hazelcast.HyperLogLog;

import com.hazelcast.cardinality.CardinalityEstimator;
import com.hazelcast.core.HazelcastInstance;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.io.Serializable;
import java.util.Random;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: hyperLogLog 大数据去除重复 计算最相近的数据
 * @date 2017-09-16 21:52
 */
public class HazelcastHyperLogLog {

    /**
     * 计算唯一性值 将多条数据(数据必须有一条属性不同，而其他的属性一致)存入hyperLogLog中，返回唯一性的值
     *
     * @param cardinalityEstimator
     */
    public void estimate(String cardinalityEstimator) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        CardinalityEstimator cardinality = hazelcastInstance.getCardinalityEstimator(cardinalityEstimator);
        Random random = new Random();
        for (int i = 0; i < 254; i++) {
            HazelcastIP hazelcastIP = new HazelcastIP("北京", "127.0.0." + random.nextInt(256));
            System.out.println(hazelcastIP);
            cardinality.add(hazelcastIP);
        }
        long estimate = cardinality.estimate();
        System.out.println(estimate);
    }

    private static class HazelcastIP implements Serializable {
        private String name;
        private String ip;

        public HazelcastIP(final String name, final String ip) {
            this.name = name;
            this.ip = ip;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(final String ip) {
            this.ip = ip;
        }

        @Override
        public String toString() {
            return "HazelcastIP{" +
                    "name='" + name + '\'' +
                    ", ip='" + ip + '\'' +
                    '}';
        }
    }
}
