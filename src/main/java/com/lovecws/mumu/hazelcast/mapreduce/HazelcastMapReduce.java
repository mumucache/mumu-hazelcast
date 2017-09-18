package com.lovecws.mumu.hazelcast.mapreduce;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.*;
import com.hazelcast.mapreduce.aggregation.impl.LongSumAggregation;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.io.Serializable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: mapreduce
 * @date 2017-09-18 15:36
 */
public class HazelcastMapReduce implements Serializable {

    public void mapReduce(String jobTrackerName) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        JobTracker jobTracker = hazelcastInstance.getJobTracker(jobTrackerName);
        IMap<String, String> map = hazelcastInstance.getMap("articles");
        map.put("a1", "lovecws");
        map.put("a2", "lovebabymm");
        map.put("a3", "lovecwzijing");

        KeyValueSource<String, String> source = KeyValueSource.fromMap(map);
        Job<String, String> job = jobTracker.newJob(source);
        System.out.println(job);
        Map<String, Long> result = null;
        try {
            ICompletableFuture<Map<String, Long>> future = job
                    .mapper(new TokenizerMapper())
                    .combiner(new WordCountCombinerFactory())
                    .reducer(new WordCountReducerFactory())
                    .submit();
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            hazelcastInstance.shutdown();
        }
        System.out.println(result);
    }

    public static class TokenizerMapper implements Mapper<String, String, String, Long>, Serializable {
        private static final Long ONE = Long.valueOf(1L);

        @Override
        public void map(final String key, final String document, final Context<String, Long> context) {
            StringTokenizer tokenizer = new StringTokenizer(document.toLowerCase());
            while (tokenizer.hasMoreTokens()) {
                context.emit(tokenizer.nextToken(), ONE);
            }
        }
    }

    public static class WordCountCombinerFactory implements CombinerFactory<String, Long, Long>, Serializable {
        @Override
        public Combiner<Long, Long> newCombiner(final String s) {
            return new Combiner<Long, Long>() {
                private long sum = 0;

                @Override
                public void combine(final Long aLong) {
                    sum++;
                }

                @Override
                public Long finalizeChunk() {
                    return sum;
                }

                @Override
                public void reset() {
                    sum = 0;
                }
            };
        }
    }

    public static class WordCountReducerFactory implements ReducerFactory<String, Long, Long>, Serializable {
        @Override
        public Reducer<Long, Long> newReducer(final String s) {
            return new Reducer<Long, Long>() {
                private volatile long sum = 0;

                @Override
                public void reduce(final Long value) {
                    sum += value.longValue();
                }

                @Override
                public Long finalizeReduce() {
                    return sum;
                }
            };
        }
    }
}
