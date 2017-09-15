package com.lovecws.mumu.hazelcast.jcache;

import com.hazelcast.cache.ICache;
import org.junit.Test;

import javax.cache.Cache;
import java.util.Iterator;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: jcache测试
 * @date 2017-09-15 16:45
 */
public class HazelcastJCacheTest {

    private HazelcastJCache jCache=new HazelcastJCache();
    @Test
    public void cache(){
        ICache<String, Object> babMumuJCache = jCache.cache("babMumuJCache");
        Iterator<Cache.Entry<String, Object>> iterator = babMumuJCache.iterator();
        while (iterator.hasNext()){
            Cache.Entry<String, Object> entry = iterator.next();
            System.out.println(entry);
        }
    }

    @Test
    public void set(){
        jCache.set("babMumuJCache","lover","cws");
    }

    @Test
    public void get(){
        Object value = jCache.get("babMumuJCache", "lover");
        System.out.println(value);
    }

    @Test
    public void size(){
        jCache.set("babMumuJCache","lover","cws");
        jCache.set("babMumuJCache","lover1","cws");
        jCache.set("babMumuJCache","lover2","cws");
        int babMumuJCache = jCache.size("babMumuJCache");
        System.out.println(babMumuJCache);
    }
}
