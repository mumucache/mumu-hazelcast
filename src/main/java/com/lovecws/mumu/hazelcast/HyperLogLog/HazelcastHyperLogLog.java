package com.lovecws.mumu.hazelcast.HyperLogLog;

import com.hazelcast.cardinality.CardinalityEstimator;
import com.hazelcast.core.HazelcastInstance;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: hyperLogLog 大数据去除重复 计算最相近的数据
 * @date 2017-09-16 21:52
 */
public class HazelcastHyperLogLog {

    public void hyperLogLog(String cardinalityEstimator){
        HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
        CardinalityEstimator cardinality = hazelcastInstance.getCardinalityEstimator(cardinalityEstimator);

    }
}
