package com.lovecws.mumu.hazelcast.jcache;

import com.hazelcast.cache.CacheStatistics;
import com.hazelcast.cache.HazelcastCachingProvider;
import com.hazelcast.cache.ICache;
import com.hazelcast.core.HazelcastInstance;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 分布式jcache缓存
 * @date 2017-09-15 15:30
 */
public class HazelcastJCache {

    /**
     * 获取到cache实例
     *
     * @param cacheName 缓存名称
     * @return
     */
    public ICache<String, Object> cache(String cacheName) {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        String instanceName = hazelcastInstance.getName();

        Properties properties = new Properties();
        properties.setProperty(HazelcastCachingProvider.HAZELCAST_INSTANCE_NAME, instanceName);
        URI cacheManagerName = null;
        try {
            cacheManagerName = new URI("my-cache-manager");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //CacheManager manager = Caching.getCachingProvider().getCacheManager();
        CacheManager manager = Caching.getCachingProvider().getCacheManager(cacheManagerName, null, properties);

        MutableConfiguration<String, Object> configuration = new MutableConfiguration<String, Object>();

        configuration.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        Cache<String, Object> cache = manager.getCache(cacheName);
        if (cache == null) {
            cache = manager.createCache(cacheName, configuration);
        }
        return cache.unwrap(ICache.class);
    }

    /**
     * 设置cache
     *
     * @param cacheName 缓存名称
     * @param key       键
     * @param value     值
     */
    public void set(String cacheName, String key, Object value) {
        ICache<String, Object> icache = cache(cacheName);
        icache.put(key, value);
        icache.close();
    }

    /**
     * 获取到缓存的值
     *
     * @param cacheName 缓存名称
     * @param key       键
     * @return
     */
    public Object get(String cacheName, String key) {
        ICache<String, Object> icache = cache(cacheName);
        Object value = icache.get(key);
        icache.close();
        return value;
    }

    /**
     * 获取到缓存的大小
     *
     * @param cacheName 缓存名称
     * @return
     */
    public int size(String cacheName) {
        ICache<String, Object> icache = cache(cacheName);
        int size = icache.size();
        icache.close();
        CacheStatistics localCacheStatistics = icache.getLocalCacheStatistics();
        System.out.println(localCacheStatistics);
        return size;
    }

}
