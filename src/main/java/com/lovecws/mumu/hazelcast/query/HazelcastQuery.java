package com.lovecws.mumu.hazelcast.query;

import com.alibaba.fastjson.JSON;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.hazelcast.query.SqlPredicate;
import com.lovecws.mumu.hazelcast.HazelcastConfiguration;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 快速查询map
 * @date 2017-09-15 15:30
 */
public class HazelcastQuery {

    public void query() {
        HazelcastInstance hazelcastInstance = new HazelcastConfiguration().instance();
        IMap<String, User> users = hazelcastInstance.getMap("usersMap123");
        //添加索引
        users.addIndex("username",true);
        System.out.println(JSON.toJSON(users));
        users.put("u1", new User("ganliang", 27, true));
        users.put("u2", new User("cws", 25, true));
        users.put("u3", new User("mumu", 1, true));
        users.put("u4", new User("lover", 27, true));
        users.put("u5", new User("baby", 27, true));
        users.put("u6", new User("ganzimu", 20, true));

        System.out.println(JSON.toJSON(users));
        Predicate sqlQuery = new SqlPredicate("active AND age BETWEEN 18 AND 21)");

        Predicate criteriaQuery = Predicates.and(
                Predicates.equal("active", true),
                Predicates.between("age", 18, 21)
        );
        Collection<User> result1 = users.values(sqlQuery);
        System.out.println("result1:"+result1);
        for (User user:result1){
            System.out.println(user);
        }
        Collection<User> result2 = users.values(criteriaQuery);
        System.out.println("result2:"+result1);
        for (User user:result2){
            System.out.println(user);
        }
        hazelcastInstance.shutdown();
    }

    public static class User implements Serializable {
        String username;
        int age;
        boolean active;

        public User(final String username, final int age, final boolean active) {
            this.username = username;
            this.age = age;
            this.active = active;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(final String username) {
            this.username = username;
        }

        public int getAge() {
            return age;
        }

        public void setAge(final int age) {
            this.age = age;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(final boolean active) {
            this.active = active;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", age=" + age +
                    ", active=" + active +
                    '}';
        }
    }
}