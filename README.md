# mumu-hazelcat 分布式内存数据库
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/mumucache/mumu-riak/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.weibo/motan.svg?label=Maven%20Central)](https://github.com/mumucache/mumu-hazelcast)
[![Build Status](https://travis-ci.org/mumucache/mumu-hazelcast.svg?branch=master)](https://travis-ci.org/mumucache/mumu-hazelcast)
[![codecov](https://codecov.io/gh/mumucache/mumu-hazelcast/branch/master/graph/badge.svg)](https://codecov.io/gh/mumucache/mumu-hazelcast)
[![OpenTracing-1.0 Badge](https://img.shields.io/badge/OpenTracing--1.0-enabled-blue.svg)](http://opentracing.io)  
**The Leading Open Source In-Memory Data Grid,Full-featured**
- IMDG
- Small JAR with Minimal Dependencie
- JCache Provider
- Embedded or Client Server
- Apache 2 License
- API with Multiple Language Clients
## hazelcast简介  
hazelcast是一个开源的基于内存的数据网格缓存系统，将数据都缓存在内存(in heap)中，从而加快数据的存取，而且hazel可以通过简单的API操作来进行数据的操作，并且提供了非常丰富的功能。

![官网特性介绍](https://hazelcast.org/wp-content/uploads/2015/05/3-9_IMDG-Architecture_v4_Open-Source.png)

## hazelcast数据分区
当hazelcast只有一个节点的时候，数据被平均分配到271个分区（hazelcast默认分配271个分区，可以修改默认设置hazelcast.partition.count）上。  
![单节点分区](http://docs.hazelcast.org/docs/latest/manual/html-single/images/NodePartition.jpg)

当hazelcast有两个节点创建一个集群的时候，数据被平均分配到这两个节点上，并且节点之间相互备份。  
![两节点数据分布](http://docs.hazelcast.org/docs/latest/manual/html-single/images/BackupPartitions.jpg)

当有四个节点的时候，数据被平均分配到四个节点上。  
![四节点数据分布](http://docs.hazelcast.org/docs/latest/manual/html-single/images/4NodeCluster.jpg)  

**数据怎么被分区的**  
hazelcast分布式对象通过hash算法来分区，列如map的key，list的名称等。
-  计算key、name序列化字节数据
-  计算字节数据的hash值
-  将hash值和分区数量取摩。

**分区表**  
每当创建一个节点的时候都会自动生成分区表，分区表包含分区id和集群节点消息（什么节点包含什么数据），集群中最老的节点负责发送分区表给集群中其他的节点，以此来通知节点数据的变更（新增节点、节点宕机...）。hazelcast默认每隔15秒发送一次分区表信息，可以通过修改hazelcast.partition.table.send.interval配置信息来修改发送间隔。

分片的数据结构
- Map
- MultiMap
- Cache (Hazelcast JCache implementation)

非分片的数据结构
- Queue
- Set
- List
- Ringbuffer
- Lock
- Semaphore
- AtomicLong
- AtomicReference
- IdGenerator
- CountdownLatch
- Cardinality Estimator

## hazelcast集成方式


**hazelcast内嵌方式**  
直接在代码中内嵌hazelcast服务，使用方便的API就可以开启服务。
```
 Config config = new Config();
 HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
```
![](http://docs.hazelcast.org/docs/latest/manual/html-single/images/P2Pcluster.jpg)
***客户端/服务端模式***  
在服务端开启hazelcast服务，然后客户端连接服务端。
```
ClientConfig clientConfig = new ClientConfig();
clientConfig.addAddress(HAZELCAT_HOST);
HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);
```
![](http://docs.hazelcast.org/docs/latest/manual/html-single/images/CSCluster.jpg)

## 相关阅读  
[hazelcast官网](https://hazelcast.org)   
[hazelcast的坑爹事](http://blog.csdn.net/hengyunabc/article/details/18514563)  
[hazelcast文档](http://docs.hazelcast.org/docs/latest/manual/html-single/)  
[hazelcast配置信息](http://docs.hazelcast.org/docs/latest/manual/html-single/#system-properties)  

## 联系方式
**以上观点纯属个人看法，如有不同，欢迎指正。  
email:<babymm@aliyun.com>  
github:[https://github.com/babymm](https://github.com/babymm)**
