package com.goldstine.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.*;

public class JedisDemo1 {
    public static void main(String[] args) {
        //测试redis
        Jedis jedis = new Jedis("192.168.56.10", 6379);
        String ping = jedis.ping();
        System.out.println(ping);//返回PONG值，则表示redis操作成功
        //需要关闭防火墙，直接通过systemctl status firewalld ,如果linux系统的防火墙没有关闭，可能连接redis超时
        jedis.close();
    }
    @Test
    public void test1(){
        Jedis jedis = new Jedis("192.168.56.10", 6379);

        //往redis中添加数据
        jedis.set("name","lucy");
        //获取
        String name = jedis.get("name");
        System.out.println(name);
        System.out.println("============================");

        //同时加入多个key-value
        jedis.mset("k1","v1","k2","v2","k3","v3");
        List<String> mget = jedis.mget("k1", "k2");
        for (String s : mget) {
            System.out.println(s);
        }
        System.out.println("===============================");


        Set<String> keys = jedis.keys("*");
        Iterator<String> iterator = keys.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println("================================");
        jedis.close();
    }

    //通过jedis操作list
    @Test
    public void test2(){
        Jedis jedis = new Jedis("192.168.56.10", 6379);
        Long lpush = jedis.lpush("key1", "sd", "liu", "as");
        System.out.println(lpush);
        List<String> k1 = jedis.lrange("key1", 0, -1);
        System.out.println(k1.toString());
jedis.close();
    }
    //通过jedis操作set
    @Test
    public void test3(){
        Jedis jedis = new Jedis("192.168.56.10", 6379);
        Long sadd = jedis.sadd("name", "guanyu", "zhangfei", "liubei");
        System.out.println(sadd);
        Set<String> name = jedis.smembers("name");
        System.out.println(name);
        System.out.println("======================");
        for (String s : name) {
            System.out.println(s);
        }
jedis.close();
    }
    //通过jedis操作hash
    @Test
    public void test4(){
        Jedis jedis = new Jedis("192.168.56.10", 6379);
        Map map = new HashMap();
        map.put("name","liulei");
        map.put("age","12");
        map.put("sex","male");
        String key1 = jedis.hmset("key1", map);//返回值为字符串OK
        System.out.println(key1);
        String hget = jedis.hget("key1", "name");
        System.out.println(hget);
        System.out.println("=======================");
        Long hset = jedis.hset("key1", "name", "goldstine");
        System.out.println(hset);
        String name=jedis.hget("key1","name");
        System.out.println(name);
        System.out.println("====================");

        Boolean hexists = jedis.hexists("key1", "liu");
        System.out.println("liu:"+hexists);

        //输出所有的key
        Set<String> key11 = jedis.hkeys("key1");
        for (String s : key11) {
            System.out.println(s);
        }
        System.out.println("=============================");
        //输出所有的value
        List<String> key12 = jedis.hvals("key1");
        for (String s : key12) {
            System.out.println(s);
        }
        jedis.close();
    }
    //jedis操作zset
    @Test
    public void test5(){
        Jedis jedis = new Jedis("192.168.56.10", 6379);
        jedis.zadd("key2",100d,"shanghai");
        Set<String> key2 = jedis.zrange("key2", 0, -1);
        for (String s : key2) {
            System.out.println(s);

        }
        jedis.close();
    }
}
