package com.lovecws.mumu.hazelcast.IdGenerator;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: id生成器测试程序
 * @date 2017-09-16 21:37
 */
public class HazelcastIdGeneratorTest {

    @Test
    public void idGenerator() {
        new HazelcastIdGenerator().idGenerator("babyMmIdGenerator2", 1000);
    }


    @Test
    public void generator() throws InterruptedException {
        new HazelcastIdGenerator().generator("babyMmIdGenerator2", 1000);
    }
}
