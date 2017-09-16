package com.lovecws.mumu.hazelcast.countlatch;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICountDownLatch;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 使用countlatch模拟主任务分配子任务，当所有子任务完成之后在继续主任务
 * @date 2017-09-16 18:30
 */
public class HazelcastTaskAllocation {

    /**
     * 分配任务
     */
    public void allowTask(String countlatchName,int taskCount) throws InterruptedException {
        //主任务闭锁
        CountDownLatch mainCountDownLatch = new CountDownLatch(1);
        //开启主任务
        new hazelcastMainTask(mainCountDownLatch,countlatchName,taskCount).start();
        //等待1秒钟 主任务准备...
        TimeUnit.SECONDS.sleep(1);

        //子任务闭锁
        CountDownLatch childCountDownLatch = new CountDownLatch(1);
        for (int i = 0; i < taskCount; i++) {
            new hazelcastChildTask(childCountDownLatch,countlatchName).start();
        }
        //等待所有的子任务全部完成
        childCountDownLatch.await();
        //等待主任务完成
        mainCountDownLatch.await();
    }

    /**
     * 主任务
     */
    public static class hazelcastMainTask extends Thread {
        private CountDownLatch mainCountDownLatch = null;
        private String countDownLatchName;
        private int taskCount;

        public hazelcastMainTask(CountDownLatch mainCountDownLatch, String countDownLatchName, int taskCount) {
            this.mainCountDownLatch = mainCountDownLatch;
            this.countDownLatchName = countDownLatchName;
            this.taskCount = taskCount;
        }

        @Override
        public void run() {
            super.run();
            HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
            ICountDownLatch countDownLatch = hazelcastInstance.getCountDownLatch(countDownLatchName);
            //设定主任务安排的子任务数量
            countDownLatch.trySetCount(taskCount);
            System.out.println("主任务分配【" + taskCount + "个子任务...】");
            try {
                //主任务等待子任务100秒
                boolean await = countDownLatch.await(100, TimeUnit.SECONDS);
                if (await) {
                    System.out.println("【" + taskCount + "】个子任务全部完成，续集主任务...");
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("主任务完成");
                } else {
                    System.out.println("子任务未完成任务,任务结束...");
                }
                mainCountDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                hazelcastInstance.shutdown();
            }
        }
    }

    /**
     * 子任务
     */
    public static class hazelcastChildTask extends Thread {
        private CountDownLatch childCountDownLatch = null;
        private String countDownLatchName;

        public hazelcastChildTask(CountDownLatch childCountDownLatch, String countDownLatchName) {
            this.childCountDownLatch = childCountDownLatch;
            this.countDownLatchName = countDownLatchName;
        }

        @Override
        public void run() {
            super.run();
            HazelcastInstance hazelcastInstance = HazelcastConfiguration.instance();
            ICountDownLatch countDownLatch = hazelcastInstance.getCountDownLatch(countDownLatchName);
            //执行子任务
            System.out.println("开始执行子任务【" + Thread.currentThread().getName() + "】...");
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子任务【" + Thread.currentThread().getName() + "】执行完成");
            countDownLatch.countDown();
            childCountDownLatch.countDown();
            //hazelcastInstance.shutdown();
        }
    }
}
