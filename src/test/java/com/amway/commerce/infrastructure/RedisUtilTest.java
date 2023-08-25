package com.amway.commerce.infrastructure;

import com.amway.commerce.string.ByteUtil;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-23
 * @desc:
 */
public class RedisUtilTest {


    private static String key1 = "key1";

    private static String key2 = "key2";

    private static String key3 = "key3";

    private static String key = "key*";

    private static String host = PropertiesUtil.getInstance("redis.properties").getProperty("redis-host");

    private static RedisUtil redisUtil;

    static {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(host);
        JedisConnectionFactory jedisConnectionFactory =
                new JedisConnectionFactory(redisStandaloneConfiguration);
        StringRedisTemplate stringRedisTemplate =
                new StringRedisTemplate(jedisConnectionFactory);
        redisUtil = RedisUtil.getInstance(stringRedisTemplate);
    }

    /**
     * 获取 key的存储类型
     */
    @Test
    public void type() {
        // STRING
        System.out.println(redisUtil.type(key1));
    }

    /**
     * 当且仅当 newKey不存在时，将 key改名为 newKey
     */
    @Test
    public void renameIfAbsent() {
        // true
        System.out.println(redisUtil.renameIfAbsent(key1, key2));
    }

    /**
     * 查找匹配的 key
     */
    @Test
    public void keys() {
        Set<String> keys = redisUtil.keys(key);
        // key3
        System.out.println(keys);
    }

    /**
     * 修改 key的名称
     */
    @Test
    public void rename() {
        redisUtil.rename(key2, key3);
        // [key3]
        System.out.println(redisUtil.keys(key));
    }

    /**
     * 从当前数据库中随机获取一个 key
     */
    @Test
    public void randomKey() {
        // key3
        System.out.println(redisUtil.randomKey());
    }

    /**
     * 获取 key剩余的过期时间，单位为秒
     */
    @Test
    public void getExpire() {
        Long time = redisUtil.getExpire(key3);
        // -1
        System.out.println(time);
        // -1
        System.out.println(redisUtil.getExpire(key3, TimeUnit.MINUTES));
    }

    /**
     * 设置 key过期时间
     */
    @Test
    public void expire() {
        // true
        System.out.println(redisUtil.expire(key3, 3, TimeUnit.SECONDS));
    }

    /**
     * 移除 key的过期时间，key将永久有效
     */
    @Test
    public void persist() {
        // true
        System.out.println(redisUtil.persist(key3));
    }

    /**
     * 设置 key过期时间
     */
    @Test
    public void expireAt() {
        // true
        System.out.println(redisUtil.expireAt(key3, new Date()));
    }

    /**
     * 是否存在 key
     */
    @Test
    public void hasKey() {
        // false
        System.out.println(redisUtil.hasKey(key3));
        // true
        System.out.println(redisUtil.hasKey(key1));
    }

    /**
     * 序列化 key
     */
    @Test
    public void dump() throws UnsupportedEncodingException {
        System.out.println(ByteUtil.bytesToString(redisUtil.dump(key1), "UTF-8"));
    }

    /**
     * 删除 key
     */
    @Test
    public void delete() {
        redisUtil.delete(key1);
        redisUtil.delete(key1);
        ArrayList<String> keys = new ArrayList<>();
        keys.add(key2);
        redisUtil.delete(keys);
    }

    /**
     * 如果当前数据库和给定数据库具有相同的 key，或者 key不存在于当前数据库，那么 MOVE没有任何效果
     */
    @Test
    public void move() {
        // true
        System.out.println(redisUtil.move(key1, 2));
        // false
        System.out.println(redisUtil.move("key5", 1));
        // false
        System.out.println(redisUtil.move(key1, 1));
    }

    // ================================= string相关操作 ================================= //

    /**
     * 设置 key的值
     * 获取 key的值
     */
    @Test
    public void set() {
        redisUtil.set(key1, "value2");
        // value2
        System.out.println(redisUtil.get(key1));
        // null
        System.out.println(redisUtil.get("key"));
        // alu
        System.out.println(redisUtil.get(key1, 1, 3));
        //
        System.out.println(redisUtil.get("key", 0, 1));
    }

    /**
     * 设置 key的值为 value，并返回 key的旧值
     */
    @Test
    public void getAndSet() {
        // value2
        System.out.println(redisUtil.getAndSet(key1, "value3"));
        // value3
        System.out.println(redisUtil.getAndSet(key1, "value2"));
    }

    /**
     * 判断指定的位置 ASCII码的 bit位是否为 1，若 key不存在，则返回 false
     */
    @Test
    public void getBit() {
        // true
        System.out.println(redisUtil.getBit(key1, 6));
        // false
        System.out.println(redisUtil.getBit("key6", 6));
    }

    /**
     * 批量获取多个 key的值
     */
    @Test
    public void multiGet() {
        ArrayList<String> keys = new ArrayList<>();
        keys.add(key1);
        keys.add(key2);
        keys.add("key5");
        // [ab, value2, null]
        System.out.println(redisUtil.multiGet(keys));
    }

    /**
     * 设置指定的位置 ASCII码的 bit位
     */
    @Test
    public void setBit() {
        // true
        System.out.println(redisUtil.setBit(key1, 5, true));
    }

    /**
     * 设置 key的值并加入过期时间
     */
    @Test
    public void setEx() {
        redisUtil.setEx(key1, "value1", 2, TimeUnit.MINUTES);
    }

    /**
     * 只有当 key不存在时设置 key的值
     */
    @Test
    public void setIfAbsent() {
        // false
        System.out.println(redisUtil.setIfAbsent(key1, "value2"));
        // false
        System.out.println(redisUtil.setIfAbsent(key1, "value2", 2, TimeUnit.MINUTES));
    }

    /**
     * 从偏移量 offset开始，用 value覆盖 key的值
     */
    @Test
    public void setRange() {
        redisUtil.setRange(key1, "v1v", 4);
    }

    /**
     * 获取 key的值长度
     */
    @Test
    public void size() {
        // 7
        System.out.println(redisUtil.size(key1));
        // 0
        System.out.println(redisUtil.size(key));
    }

    /**
     * 批量 set添加
     */
    @Test
    public void multiSet() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key01", "value01");
        map.put("key02", "value02");
        map.put("key04", "value04");
        redisUtil.multiSet(map);
    }

    /**
     * 当且仅当所有的 key都不存在时，设置一个或多个 key-value
     */
    @Test
    public void multiSetIfAbsent() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key01", "value01");
        map.put("key02", "value02");
        map.put("key04", "value04");
        System.out.println(redisUtil.multiSetIfAbsent(map));
    }

    /**
     * 将 key的值自增加，负数为自减
     */
    @Test
    public void incr() {
        // 0
        System.out.println(redisUtil.incr(key3, 1));
        // -1
        System.out.println(redisUtil.incr(key3, -1));
        // -2.9
        System.out.println(redisUtil.incr(key3, -1.9));

    }

    /**
     * 追加 value到 key原有值的末尾
     */
    @Test
    public void append() {
        // 7
        System.out.println(redisUtil.append(key1, "1q"));
    }

    // ================================= hash 相关操作 ================================= //

    static String hkey1 = "hkey1", field1 = "field1", value1 = "v1";

    static String hkey2 = "hkey2", field2 = "field2", value2 = "v2";

    static String hkey3 = "hkey3", field3 = "field3", value3 = "v3";

    /**
     * 获取 key集合中指定字段的值
     */
    @Test
    public void hGet() {
        // {field1=v1}
        System.out.println(redisUtil.hGet(hkey1));
        // {}
        System.out.println(redisUtil.hGet(hkey3));
        // v2
        System.out.println(redisUtil.hGet(hkey2, field2));
        // null
        System.out.println(redisUtil.hGet(hkey2, field1));
        // null
        System.out.println(redisUtil.hGet(hkey3, field1));
    }

    /**
     * 给 key集合中的 field键赋 value值
     */
    @Test
    public void hPut() {
        redisUtil.hPut(hkey1, field1, value1);
        redisUtil.hPut(hkey1, field2, value2);
        redisUtil.hPut(hkey2, field2, value2);
    }

    /**
     * 获取 key集合中所有给定字段的值
     */
    @Test
    public void hMultiGet() {
        ArrayList<Object> list1 = new ArrayList<>();
        list1.add(field1);
        list1.add(field2);
        list1.add(field3);
        // [v1, v2, null]
        System.out.println(redisUtil.hMultiGet(hkey1, list1));
        // [null, null, null]
        System.out.println(redisUtil.hMultiGet(hkey3, list1));
    }

    /**
     * 批量添加 key集合的 filed-value对
     */
    @Test
    public void hPutAll() {
        HashMap<String, String> map = new HashMap<>();
        map.put(field3, value1);
        map.put(field3, value2);
        map.put(field3, value3);
        map.put(field1, value3);
        redisUtil.hPutAll(hkey1, map);
    }

    /**
     * 仅当 field不存在时才设置
     */
    @Test
    public void hPutIfAbsent() {
        // false
        System.out.println(redisUtil.hPutIfAbsent(hkey1, field1, value1));
        // false
        System.out.println(redisUtil.hPutIfAbsent(hkey1, field2, value3));
        // true
        System.out.println(redisUtil.hPutIfAbsent(hkey2, field3, value3));
    }

    /**
     * 删除 key集合中一个或多个 field字段
     */
    @Test
    public void hDelete() {
        // 3
        System.out.println(redisUtil.hDelete(hkey1, field1, field2, field3));
        // 0
        System.out.println(redisUtil.hDelete(hkey1, field1));
        // 0
        System.out.println(redisUtil.hDelete(hkey3, field1));
        // 1
        System.out.println(redisUtil.hDelete(hkey3, field1, field3));
    }

    /**
     * 判断 field字段是否存在 key集合中
     */
    @Test
    public void hExists() {
        // false
        System.out.println(redisUtil.hExists(hkey1, field1));
        // true
        System.out.println(redisUtil.hExists(hkey3, field2));
        // true
        System.out.println(redisUtil.hExists(hkey2, field2));

    }

    /**
     * 为 key集合中的指定字段加上增量 increment
     */
    @Test
    public void hIncr() {
        // 10
        System.out.println(redisUtil.hIncr(hkey1, field1, 5));
        // 15.9
        System.out.println(redisUtil.hIncr(hkey1, field1, 5.9));
    }

    /**
     * 获取 key集合中所有字段
     */
    @Test
    public void hKeys() {
        // [field1]
        System.out.println(redisUtil.hKeys(hkey1));
        // []
        System.out.println(redisUtil.hKeys(hkey3));
        // [field2, field3]
        System.out.println(redisUtil.hKeys(hkey2));
    }

    /**
     * 获取 key集合中字段的数量
     */
    @Test
    public void hSize() {
        // 2
        System.out.println(redisUtil.hSize(hkey2));
        // 0
        System.out.println(redisUtil.hSize(hkey3));
    }

    /**
     * 获取 key集合中的所有值
     */
    @Test
    public void hValues() {
        // []
        System.out.println(redisUtil.hValues(hkey3));
        // [v2, v3]
        System.out.println(redisUtil.hValues(hkey2));
    }

    /**
     * 扫描集合
     */
    @Test
    public void hScan() {
        Cursor<Map.Entry<Object, Object>> entryCursor = redisUtil.hScan(hkey2, ScanOptions.NONE);
        while (entryCursor.hasNext()) {
            Map.Entry<Object, Object> next = entryCursor.next();
            Object key4 = next.getKey();
            System.out.println(key4);
        }
        System.out.println(redisUtil.hScan(hkey1, ScanOptions.NONE).next());
        System.out.println(redisUtil.hScan(hkey2, ScanOptions.NONE));
        System.out.println(redisUtil.hScan(hkey3, ScanOptions.NONE));
    }

    // ================================= list 相关操作 ================================= //

    private static String lkey1 = "lkey1", lvalue1 = "l1";

    private static String lkey2 = "lkey2", lvalue2 = "l2";

    private static String lkey3 = "lkey3", lvalue3 = "l3";

    /**
     * 从左边添加，存储在 list头部
     */
    @Test
    public void lLeftPush() {
        // 2
        System.out.println(redisUtil.lLeftPush(lkey1, lvalue1));
        // 3
        System.out.println(redisUtil.lLeftPush(lkey1, lvalue2));
        //
        System.out.println(redisUtil.lLeftPush(lkey1, lvalue1));
        // 4
        System.out.println(redisUtil.lLeftPush(lkey1, lvalue1, lvalue3));
        // -1
        System.out.println(redisUtil.lLeftPush(lkey1, lkey3, lkey2));
    }

    /**
     * 从左边批量添加元素
     */
    @Test
    public void lLeftPushAll() {
        // 9
        System.out.println(redisUtil.lLeftPushAll(lkey1, lkey3, lkey1, lkey2));

        ArrayList<String> list = new ArrayList<>();
        list.add(lvalue2);
        list.add(lvalue3);
        // 11
        System.out.println(redisUtil.lLeftPushAll(lkey1, list));
    }

    /**
     * 如果 list存在，则从左边添加元素
     */
    @Test
    public void lLeftPushIfPresent() {
        // 0
        System.out.println(redisUtil.lLeftPushIfPresent(lkey2, lvalue2));
        // 12
        System.out.println(redisUtil.lLeftPushIfPresent(lkey1, lvalue2));
    }

    /**
     * 从右边添加元素，存储在 list尾部
     */
    @Test
    public void lRightPush() {
        // 1
        System.out.println(redisUtil.lRightPush(lkey1, lkey2));
        // 2
        System.out.println(redisUtil.lRightPush(lkey1, lkey2));
        // 1
        System.out.println(redisUtil.lLeftPush(lkey3, lkey1, lkey1));
        // 0
        System.out.println(redisUtil.lRightPush(lkey2, lkey1, lkey3));
        // -1
        System.out.println(redisUtil.lRightPush(lkey1, lvalue2, lkey1));
    }

    /**
     * 从右边批量添加元素
     */
    @Test
    public void lRightPushAll() {
        // 4
        System.out.println(redisUtil.lRightPushAll(lkey1, lkey2, lkey1));

        ArrayList<String> list = new ArrayList<>();
        list.add(lvalue1);
        list.add(lvalue3);
        // 6
        System.out.println(redisUtil.lRightPushAll(lkey1, list));
    }

    /**
     * 如果 list存在，则从右边添加元素
     */
    @Test
    public void lRightPushIfPresent() {
        System.out.println(redisUtil.lRightPushIfPresent(lkey3, lvalue3));
    }

    /**
     * 在 index索引处添加元素
     */
    @Test
    public void lSet() {
        redisUtil.lSet(lkey1, 0, lvalue1);
    }

    /**
     * 移除左边第一个元素
     */
    @Test
    public void lLeftPop() {
        // l1
        System.out.println(redisUtil.lLeftPop(lkey1));
        // null
        System.out.println(redisUtil.lLeftPop(lkey3));
        // l1
        System.out.println(redisUtil.lLeftPop(lkey1, 3, TimeUnit.SECONDS));
        // null
        System.out.println(redisUtil.lLeftPop(lkey3, 3, TimeUnit.SECONDS));
    }

    /**
     * 移除右边第一个元素
     */
    @Test
    public void lRightPop() {
        // l1
        System.out.println(redisUtil.lRightPop(lkey1));
        // l2
        System.out.println(redisUtil.lRightPop(lkey1, 5, TimeUnit.SECONDS));
        // null
        System.out.println(redisUtil.lRightPop(lkey3, 5, TimeUnit.SECONDS));
    }

    /**
     * 移除 sourceKey右边第一个元素，并将其从左边加入到 destinationKey中
     */
    @Test
    public void lRightPopAndLeftPush() {
        //
        System.out.println(redisUtil.lRightPopAndLeftPush(lkey1, lkey2));
        //
        System.out.println(redisUtil.lRightPopAndLeftPush(lkey3, lkey2, 5, TimeUnit.SECONDS));
    }

    /**
     * 删除集合中值为 value的元素
     */
    @Test
    public void lRemove() {
        // 1
        System.out.println(redisUtil.lRemove(lkey2, 0, "l2"));
        // 0
        System.out.println(redisUtil.lRemove(lkey2, -1, "l4"));
        // 1
        System.out.println(redisUtil.lRemove(lkey2, -1, "l2"));
        // 1
        System.out.println(redisUtil.lRemove(lkey2, 1, "l1"));
    }

    /**
     * 截取 list元素
     */
    @Test
    public void lTrim() {
        redisUtil.lTrim(lkey2, 2, 5);
        redisUtil.lTrim(lkey1, 0, -1);
    }

    /**
     * 获取 list长度
     */
    @Test
    public void lLen() {
        System.out.println(redisUtil.lLen(lkey1));
        System.out.println(redisUtil.lLen(lkey2));
        System.out.println(redisUtil.lLen(lkey3));
    }

    /**
     * 获取列表中指定范围内的元素
     */
    @Test
    public void lRange() {
        System.out.println(redisUtil.lRange(lkey1, 0, -1));
    }

    /**
     * 获取列表中 index索引处的元素
     */
    @Test
    public void lIndex() {
        System.out.println(redisUtil.lIndex(lkey1, 5));
    }
}