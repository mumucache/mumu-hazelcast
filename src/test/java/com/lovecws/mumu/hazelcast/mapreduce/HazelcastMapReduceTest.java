package com.lovecws.mumu.hazelcast.mapreduce;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: mapreduce测试
 * @date 2017-09-18 15:56
 */
public class HazelcastMapReduceTest {

    @Test
    public void mapreduce() throws ExecutionException, InterruptedException {
        new HazelcastMapReduce().mapReduce("babyMmMapReduce");
    }

}
