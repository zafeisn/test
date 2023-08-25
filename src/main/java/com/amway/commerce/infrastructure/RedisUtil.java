package com.amway.commerce.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: Jason.Hu
 * @date: 2023-08-23
 * @desc: Redis工具类 - spring-data-redis v2.3.4.RELEASE
 */
@Component
public class RedisUtil {

    /**
     * 使用 SpringBoot自动注入 StringRedisTemplate
     */
    @Autowired
    private static StringRedisTemplate stringRedisTemplate;

    private static RedisUtil redisUtil;

    /**
     * 使用含参构造器，手动注入 StringRedisTemplate
     */
    public static RedisUtil getInstance(StringRedisTemplate stringRedisTemplate) {
        if (redisUtil == null) {
            redisUtil = new RedisUtil(stringRedisTemplate);
        }
        return redisUtil;
    }

    /**
     * 空构造器
     */
    public RedisUtil() {
    }

    /**
     * 含参构造器
     */
    private RedisUtil(StringRedisTemplate redisTemplate) {
        stringRedisTemplate = redisTemplate;
    }

    /**
     * 获取 stringRedisTemplate
     */
    public static StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    // ================================= key相关操作 ================================= //

    /**
     * 删除单个 key
     *
     * @param key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除
     *
     * @param keys
     */
    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    /**
     * 序列化 key
     *
     * @param key key
     * @return 字节数组
     */
    public byte[] dump(String key) {
        return stringRedisTemplate.dump(key);
    }

    /**
     * 是否存在 key
     *
     * @param key
     * @return true表示存在，false表示不存在
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 设置 key过期时间
     *
     * @param key     key
     * @param timeout 有效时长
     * @param unit    时间单位
     * @return true表示设置成功，false表示设置失败
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置 key过期时间
     *
     * @param key
     * @param date unix时间戳
     * @return true表示设置成功，false表示设置失败
     */
    public Boolean expireAt(String key, Date date) {
        return stringRedisTemplate.expireAt(key, date);
    }

    /**
     * 查找匹配的 key
     *
     * @param pattern 匹配模式，可以为模糊匹配 *
     * @return 匹配到的 key集合
     */
    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    /**
     * 将当前数据库的 key移动到给定的数据库 db当中
     * 如果当前数据库和给定数据库具有相同的 key，或者 key不存在于当前数据库，则返回 false
     *
     * @param key     key
     * @param dbIndex 数据库索引
     * @return true表示移动成功，false表示移动失败
     */
    public Boolean move(String key, int dbIndex) {
        return stringRedisTemplate.move(key, dbIndex);
    }

    /**
     * 移除 key的过期时间，key将永久有效，若 key已过期，则返回 false
     *
     * @param key key
     * @return true表示设置成功，false表示设置失败，若 key已过期，则返回 false
     */
    public Boolean persist(String key) {
        return stringRedisTemplate.persist(key);
    }

    /**
     * 获取 key剩余的过期时间
     *
     * @param key  key
     * @param unit 时间单位
     * @return 剩余时间，-1表示永久有效，-2表示已过期
     */
    public Long getExpire(String key, TimeUnit unit) {
        return stringRedisTemplate.getExpire(key, unit);
    }

    /**
     * 获取 key剩余的过期时间，单位为秒
     *
     * @param key key
     * @return 剩余时间，-1表示永久有效，-2表示已过期
     */
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 从当前数据库中随机获取一个 key
     *
     * @return key
     */
    public String randomKey() {
        return stringRedisTemplate.randomKey();
    }

    /**
     * 修改 key的名称
     *
     * @param oldKey 原名称
     * @param newKey 新名称
     */
    public void rename(String oldKey, String newKey) {
        stringRedisTemplate.rename(oldKey, newKey);
    }

    /**
     * 当且仅当 newKey不存在时，将 key修改为 newKey
     *
     * @param oldKey 原名称
     * @param newKey 新名称
     * @return true表示设置成功，false为失败
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return stringRedisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 获取 key的存储类型
     *
     * @param key key
     * @return 存储类型
     */
    public DataType type(String key) {
        return stringRedisTemplate.type(key);
    }

    // ================================= string相关操作 ================================= //

    /**
     * 设置 key的值
     * 若 key存在，则覆盖 value值
     *
     * @param key   key键
     * @param value value值
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取 key的 value值
     *
     * @param key key键
     * @return key对应的值，若 key不存在，则返回 null
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 获取 key中从 start到 end的子字符
     *
     * @param key   key键
     * @param start 从 0开始
     * @param end   end包含
     * @return start到 end的子字符，闭区间。若 key不存在，则返回 null
     */
    public String get(String key, long start, long end) {
        return stringRedisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 设置 key的值为 value，并返回 key的旧值
     *
     * @param key   key键
     * @param value value值
     * @return key的旧值
     */
    public String getAndSet(String key, String value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 判断指定的位置 ASCII码的 bit位是否为 1，若 key不存在，则返回 false
     * 例如：a的 ASCII码为 01100001 getBit(a, 0) -> 0，getBit(a, 7) -> 1
     * 例如：ab的 ASCII码为 0110000101100010 getBit(ab, 8) -> 0，getBit(ab, 14) -> 1
     *
     * @param key    key键
     * @param offset 偏移量
     * @return true表示为 1，false表示为 0
     */
    public Boolean getBit(String key, long offset) {
        return stringRedisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 批量获取多个 key的值，若 key不存在，则返回 null
     *
     * @param keys 多个 key
     * @return 多个 value
     */
    public List<String> multiGet(Collection<String> keys) {
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 设置或者清空 key的 value在 offset处的 bit值
     *
     * @param key   key键
     * @param value true为 1，false为 0
     * @return bit旧值，true为 1，false为 0
     */
    public boolean setBit(String key, long offset, boolean value) {
        return stringRedisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 设置 key的值并加入过期时间
     *
     * @param key     key键
     * @param value   value值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public void setEx(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 只有当 key不存在时设置 key的值
     *
     * @param key   key键
     * @param value value值
     * @return key存在返回 false，不存在返回 true
     */
    public boolean setIfAbsent(String key, String value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 只有当 key不存在时设置 key的值以及过期时间
     *
     * @param key     key键
     * @param value   value值
     * @param timeout 时长
     * @param unit    时间单位
     * @return key存在返回 false，不存在返回 true
     */
    public boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
    }

    /**
     * 从第offset个字节开始，用 value覆盖 key的值
     *
     * @param key    key键
     * @param value  value值
     * @param offset 位置
     */
    public void setRange(String key, String value, long offset) {
        stringRedisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取 key的值长度，若 key不存在，则返回 0
     *
     * @param key key键
     * @return value值的长度
     */
    public Long size(String key) {
        return stringRedisTemplate.opsForValue().size(key);
    }

    /**
     * 批量 set添加
     *
     * @param maps map键值对集合
     */
    public void multiSet(Map<String, String> maps) {
        stringRedisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 当且仅当所有的 key都不存在时，设置一个或多个 key-value
     *
     * @param maps map键值对集合
     * @return 若 key存在则返回 false，都不存在返回 true
     */
    public boolean multiSetIfAbsent(Map<String, String> maps) {
        return stringRedisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * 将 key的值自增加，负数为自减
     *
     * @param key       key键
     * @param increment long类型
     * @return value的新值
     */
    public Long incr(String key, long increment) {
        return stringRedisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 将 key的值自增加，负数为自减
     *
     * @param key       key键
     * @param increment double类型
     * @return value的新值
     */
    public Double incr(String key, double increment) {
        return stringRedisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 追加 value到 key原有值的末尾
     *
     * @param key   key键
     * @param value value值
     * @return value的新值长度
     */
    public Integer append(String key, String value) {
        return stringRedisTemplate.opsForValue().append(key, value);
    }

    // ================================= hash 相关操作 ================================= //

    /**
     * 获取 key集合中指定字段的值，若 key或 field不存在，则返回 null
     *
     * @param key   hash键值对集合
     * @param field filed字段
     * @return key和 field字段所对应的 value值，若 key或 field不存在，则返回 null
     */
    public Object hGet(String key, String field) {
        return stringRedisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取 key集合中所有字段，若 key不存在，则返回空 map
     *
     * @param key hash键值对集合
     * @return key所对应的所有 field字段和 value值，若 key不存在，则返回空 map
     */
    public Map<Object, Object> hGet(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取 key集合中所有给定字段的值，若 key或 field不存在，则返回 null
     *
     * @param key    hash键值对集合
     * @param fields filed字段
     * @return key所对应的 filed字段的 value值，若 key或 field不存在，则返回 null
     */
    public List<Object> hMultiGet(String key, Collection<Object> fields) {
        return stringRedisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * 给 key集合中的 field键赋 value值，key不可以与其他数据类型的 key重复，若 key中的 filed字段存在，则覆盖 value值
     *
     * @param key   hash键值对集合
     * @param field filed字段
     * @param value value值
     */
    public void hPut(String key, String field, String value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 批量添加 key集合的 filed-value对，若 key中的 filed字段存在，则覆盖 value值
     *
     * @param key  hash键值对集合
     * @param maps filed-value对
     */
    public void hPutAll(String key, Map<String, String> maps) {
        stringRedisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * 仅当 field不存在时才添加
     *
     * @param key   hash键值对集合
     * @param field filed字段
     * @param value value值
     * @return true表示添加成功，false表示添加失败
     */
    public Boolean hPutIfAbsent(String key, String field, String value) {
        return stringRedisTemplate.opsForHash().putIfAbsent(key, field, value);
    }

    /**
     * 删除 key集合中一个或多个 field字段
     *
     * @param key    hash键值对集合
     * @param fields filed字段
     * @return 成功删除 filed的个数
     */
    public Long hDelete(String key, Object... fields) {
        return stringRedisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 判断 field字段是否存在 key集合中
     *
     * @param key   hash键值对集合
     * @param field filed字段
     * @return true表示存在，false表示不存在
     */
    public boolean hExists(String key, String field) {
        return stringRedisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 为 key集合中的指定字段加上增量 increment，若 key或者 field不存在，则会设置 increment为该 field字段的值
     *
     * @param key       hash键值对集合
     * @param field     field字段
     * @param increment 增量，long类型
     * @return field字段的 value新值，若 key或者 field不存在，则会设置 increment为该 field字段的值
     */
    public Long hIncr(String key, Object field, long increment) {
        return stringRedisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 为 key集合中的指定字段加上增量 increment，若 key或者 field不存在，则会设置 increment为该 field字段的值
     *
     * @param key       hash键值对集合
     * @param field     field字段
     * @param increment 增量，double类型
     * @return field字段的 value新值，若 key或者 field不存在，则会设置 increment为该 field字段的值
     */
    public Double hIncr(String key, Object field, double increment) {
        return stringRedisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 获取 key集合中所有 filed字段，若 key不存在，则返回空 set
     *
     * @param key hash键值对集合
     * @return key的所有 field，若 key不存在，则返回空 set
     */
    public Set<Object> hKeys(String key) {
        return stringRedisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取 key集合中 filed字段的数量
     *
     * @param key hash键值对集合
     * @return key中 field字段的数量
     */
    public Long hSize(String key) {
        return stringRedisTemplate.opsForHash().size(key);
    }

    /**
     * 获取 key集合中的所有值，若 key不存在，则返回空列表
     *
     * @param key hash键值对集合
     * @return key的所有 value值，若 key不存在，则返回空列表
     */
    public List<Object> hValues(String key) {
        return stringRedisTemplate.opsForHash().values(key);
    }

    /**
     * 扫描 key，以定位到当前 key来获取 field字段
     * 通过返回的 Cursor对象去判断当前 key是否存在 field字段，若存在则进行遍历取出
     *
     * @param key     key集合
     * @param options 扫描选项
     * @return 定位到当前 key的 Cursor对象
     */
    public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return stringRedisTemplate.opsForHash().scan(key, options);
    }

    // ================================= list 相关操作 ================================= //

    /**
     * 获取列表中 index索引处的元素
     *
     * @param key   list集合
     * @param index index位置索引
     * @return 单个目标元素
     */
    public String lIndex(String key, long index) {
        return stringRedisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取列表中指定范围内的元素
     *
     * @param key   list集合
     * @param start 开始位置， 0是开始位置
     * @param end   结束位置，-1返回所有
     * @return 元素列表
     */
    public List<String> lRange(String key, long start, long end) {
        return stringRedisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 从左边添加，存储在 list头部
     * 例如：第一次添加 l1，第二次添加 l2， 第三次添加 l3
     * 则排列为： l3 l2 l1
     *
     * @param key   list集合
     * @param value value值
     * @return key中 value值的数量
     */
    public Long lLeftPush(String key, String value) {
        return stringRedisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 从左边批量添加元素
     *
     * @param key   list集合
     * @param value value值
     * @return key中 value值的数量
     */
    public Long lLeftPushAll(String key, String... value) {
        return stringRedisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 从左边批量添加元素
     *
     * @param key   list集合
     * @param value value值
     * @return key中 value值的数量
     */
    public Long lLeftPushAll(String key, Collection<String> value) {
        return stringRedisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 从左边添加元素，若 key不存在，则添加不成功并返回 0
     *
     * @param key   list集合
     * @param value value值
     * @return key中 value值的数量，若 key不存在，则添加不成功并返回 0
     */
    public Long lLeftPushIfPresent(String key, String value) {
        return stringRedisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 如果 pivot存在，在第一次出现 pivot的左边添加 value
     * 若 key不存在，则返回 0；若 pivot不存在，则返回 -1
     *
     * @param key   list集合
     * @param pivot 可能存在 list集合中的某个值
     * @param value value值
     * @return key中 value值的数量，若 key不存在，则返回 0；若 pivot不存在，则返回 -1
     */
    public Long lLeftPush(String key, String pivot, String value) {
        return stringRedisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 从右边添加元素，存储在 list尾部
     *
     * @param key   list集合
     * @param value value值
     * @return key中 value值的数量
     */
    public Long lRightPush(String key, String value) {
        return stringRedisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 从右边批量添加元素
     *
     * @param key   list集合
     * @param value value值
     * @return key中 value值的数量
     */
    public Long lRightPushAll(String key, String... value) {
        return stringRedisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 从右边批量添加元素
     *
     * @param key   list集合
     * @param value 值
     * @return key中 value值的数量
     */
    public Long lRightPushAll(String key, Collection<String> value) {
        return stringRedisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 如果 list存在，则从右边添加元素，key中 value值的数量，若 key不存在，则添加不成功并返回 0
     *
     * @param key   list集合
     * @param value value值
     * @return key中 value值的数量，若 key不存在，则添加不成功并返回 0
     */
    public Long lRightPushIfPresent(String key, String value) {
        return stringRedisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 如果 pivot存在，在第一次出现 pivot的右边添加 value
     * 若 key不存在，则返回 0；若 pivot不存在，则返回 -1
     *
     * @param key   list集合
     * @param pivot 可能存在 list集合中的某个值
     * @param value value值
     * @return key中 value值的数量，若 key不存在，则返回 0；若 pivot不存在，则返回 -1
     */
    public Long lRightPush(String key, String pivot, String value) {
        return stringRedisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 在 index索引处添加元素，若该位置有元素存在则会被覆盖
     *
     * @param key   list集合
     * @param index index索引位置
     * @param value value值
     */
    public void lSet(String key, long index, String value) {
        stringRedisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除左边第一个元素
     *
     * @param key list集合
     * @return 被删除的元素，若 key不存在或者列表没有元素，则返回 null
     */
    public String lLeftPop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移出左边第一个元素，若列表没有元素会阻塞，直到等待超时或发现可弹出元素为止
     *
     * @param key     list集合
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return 被删除的元素，若 key不存在或者列表没有元素，则会阻塞到等待超时并返回 null，或阻塞到发现可弹出元素为止
     */
    public String lLeftPop(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除右边第一个元素
     *
     * @param key list集合
     * @return 被删除的元素，若 key不存在或者列表没有元素，则返回 null
     */
    public String lRightPop(String key) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移除右边第一个元素，如果列表没有元素会阻塞，直到等待超时或发现可弹出元素为止
     *
     * @param key     list集合
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return 被删除的元素，若 key不存在或者列表没有元素，则会阻塞到等待超时并返回 null，或阻塞到发现可弹出元素为止
     */
    public String lRightPop(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 移除 sourceKey集合的右边第一个元素，并将其从左边加入到 destinationKey集合中
     *
     * @param sourceKey      所在 list集合
     * @param destinationKey 目标 list集合
     * @return 被删除的元素，若 key不存在或者列表没有元素，则会返回 null
     */
    public String lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return stringRedisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * 移除 sourceKey集合的右边第一个元素，并将其从左边加入到 destinationKey集合中
     * 若 key不存在或者列表没有元素，则会阻塞到等待超时并返回 null，或阻塞到发现可弹出元素为止
     *
     * @param sourceKey      所在 list集合
     * @param destinationKey 目标 list集合
     * @param timeout        等待时间
     * @param unit           时间单位
     * @return 被删除的元素，若 key不存在或者列表没有元素，则会阻塞到等待超时并返回 null，或阻塞到发现可弹出元素为止
     */
    public String lRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return stringRedisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于 value值的元素
     *
     * @param key   list集合
     * @param index index=0，删除所有值等于 value的元素；
     *              index>0，从头部开始删除第一个值等于 value的元素；
     *              index<0，从尾部开始删除第一个值等于 value的元素；
     * @param value value值
     * @return 被删除的元素个数
     */
    public Long lRemove(String key, long index, String value) {
        return stringRedisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * 截取 list元素
     *
     * @param key   list集合
     * @param start 开始位置
     * @param end   结束位置
     */
    public void lTrim(String key, long start, long end) {
        stringRedisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取 list长度
     *
     * @param key list集合
     * @return list集合大小，若 key不存在，则返回 0
     */
    public Long lLen(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

}
