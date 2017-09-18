package com.lovecws.mumu.hazelcast.benchmark;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: jmh测试map并发性
 * @date 2017-09-16 21:11
 */
public class hazelcastBenchmark {

    public static HazelcastInstance hazelcastInstance;
    public static IMap<Object, Object> benchmarkMap;
    static {
        hazelcastInstance= new HazelcastConfiguration().instance();
        benchmarkMap = hazelcastInstance.getMap("benchmarkMap");
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void mapPut(){
        benchmarkMap.put(""+System.currentTimeMillis(),""+System.nanoTime());
    }

    @Test
    public void benchmark() throws RunnerException {
        Options options = new OptionsBuilder()
                .warmupIterations(10)
                .measurementIterations(10)
                .include(hazelcastBenchmark.class.getSimpleName()+".mapPut$")
                .forks(1)
                .threads(1)
                .build();
        new Runner(options).run();
        hazelcastInstance.shutdown();
    }
}
