package com.yzb.sec.config.redis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author meilin
 */
@Component
public class RedisClusterClient {

    private final Logger log = LoggerFactory.getLogger(RedisClusterClient.class);

    /**
     * 失效时间一天
     */
    public static final int EXPIRE_DAY = 3600 * 24;
    /**
     * 失效时间半天
     */
    public static final int EXPIRE_HELF_DAY = 3600 * 12;
    /**
     * 失效时间一小时
     */
    public static final int EXPIRE_HOUR = 3600;
    /**
     * 失效时间半小时
     */
    public static final int EXPIRE_HALF_HOUR = 1800;
    /**
     * 失效时间十分钟
     */
    public static final int EXPIRE_TEN_MIN = 600;
    /**
     * 失效时间一分钟
     */
    public static final int EXPIRE_ONE_MIN = 60;

    /**
     * key为空
     */
    private static final String KEY_EMPTY = "key值为空";
    /**
     * 参数值为空
     */
    private static final String NO_VALUE = "参数值为空";

    @Resource
    private RedisTemplate redisTemplate;

    private Gson gson = new GsonBuilder().create();

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }

    }

    /**
     * 开始事务
     */
    public void multi() {
        redisTemplate.multi();
    }

    /**
     * 结束事务
     */
    public void exec() {
        redisTemplate.exec();
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, String value, long time) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * object缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean setObject(String key, Object value) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            return set(key, gson.toJson(value));
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * object缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean setObject(String key, Object value, long time) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            return set(key, gson.toJson(value), time);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * object缓存取出
     *
     * @param key 键
     * @return true成功 false失败
     */
    public <T> T getObject(String key, Class<T> clazz) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            String json = get(key);
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 缓存列表
     *
     * @param key     缓存的key
     * @param objList 列表数据
     * @return
     */
    public boolean setList(String key, List<?> objList) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            String json = gson.toJson(objList);
            return set(key, json);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 缓存列表
     *
     * @param key     缓存的key
     * @param objList 列表数据
     * @return
     */
    public boolean setList(String key, List<?> objList, long time) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            String json = gson.toJson(objList);
            return set(key, json, time);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 从缓存中获取列表数据
     *
     * @param key  缓存的key
     * @param type 列表的对象
     * @return
     */
    public <T> List<T> getList(String key, Type type) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            String json = get(key);
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除key值
     *
     * @param key 键值
     */
    public boolean delCacheByKey(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForValue().getOperations().delete(key);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 删除key值
     *
     * @param key 键值
     */
    public boolean delCacheByKey(String... key) {
        try {
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    redisTemplate.delete(key[0]);
                    return true;
                } else {
                    redisTemplate.delete(CollectionUtils.arrayToList(key));
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 根据key获取有效期
     *
     * @param key 键值
     * @return 有效期时间
     */
    public long getExpireTime(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        long time = redisTemplate.getExpire(key);
        return time;
    }

    /**
     * 根据key获取有效期 指定时间类型---秒
     *
     * @param key 键值
     * @return 有效期时间
     */
    public long getExpireTimeForSec(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        long time = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return time;
    }

    /**
     * 根据key获取有效期 指定时间类型---分
     *
     * @param key 键值
     * @return 有效期时间
     */
    public long getExpireTimeForMin(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        long time = redisTemplate.getExpire(key, TimeUnit.MINUTES);
        return time;
    }

    /**
     * 根据key获取有效期 指定时间类型---分
     *
     * @param key 键值
     * @return 有效期时间
     */
    public long getExpireTimeForHour(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        long time = redisTemplate.getExpire(key, TimeUnit.HOURS);
        return time;
    }

    /**
     * 根据key获取有效期 指定时间类型---天
     *
     * @param key 键值
     * @return 有效期时间
     */
    public long getExpireTimeForDay(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        long time = redisTemplate.getExpire(key, TimeUnit.DAYS);
        return time;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param date 过期的时间戳
     * @return 设置成功或失败
     */
    public boolean expireAt(String key, long date) {
        try {
            if (date > 0) {
                byte[] rawKey = rawKey(key);
                redisTemplate.execute(new RedisCallback<Boolean>() {

                    @Override
                    public Boolean doInRedis(RedisConnection connection) {
                        return connection.expireAt(rawKey, new Date(date).getTime() / 1000);
                    }
                }, true);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }


    }

    /**
     * 使用相应的key序列化方式获取key的byte[]数组
     */
    @SuppressWarnings("unchecked")
    private byte[] rawKey(Object key) {
        if (keySerializer() == null && key instanceof byte[]) {
            return (byte[]) key;
        }
        return keySerializer().serialize(key);
    }

    /**
     * 获取key序列化方式
     */
    @SuppressWarnings("rawtypes")
    private RedisSerializer keySerializer() {
        return redisTemplate.getKeySerializer();
    }


    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 递增
     * 注：集群模式下请谨慎使用
     *
     * @param key   键
     * @param delta 递增因子
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * 注：集群模式下请谨慎使用
     *
     * @param key   键
     * @param delta 递增因子
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 为多个键分别设置它们的值
     *
     * @param map map集合 存储key和value
     * @return
     */
    public boolean setByMap(Map<String, String> map) {
        if (map.isEmpty()) {
            log.info(NO_VALUE);
            return false;
        }
        try {
            redisTemplate.opsForValue().multiSet(map);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 为多个键获取值
     *
     * @param keys 键值集合
     * @return
     */
    public Object setByMap(List<String> keys) {
        if (keys.isEmpty()) {
            log.info(NO_VALUE);
            return null;
        }
        try {
            return redisTemplate.opsForValue().multiGet(keys);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 为键追加值 不存在则创建
     *
     * @param key   键值
     * @param value 值
     * @return
     */
    public Integer strAppend(String key, String value) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForValue().append(key, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 截取key所对应的value字符串
     *
     * @param key   键值
     * @param start 开始值
     * @param end   结束值
     * @return
     */
    public String get(String key, long start, long end) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForValue().get(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, String> map) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, String> map, long time) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, String value) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, String value, long time) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public boolean hdel(String key, String... item) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForHash().delete(key, item);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * 注：集群模式下请谨慎使用
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * 注：集群模式下请谨慎使用
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    /**
     * 从哈希中获取给定hashKey的值
     *
     * @param key  键值
     * @param keys 需要取的值
     * @return
     */
    public List hmGet(String key, List keys) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForHash().multiGet(key, keys);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取key所对应的散列表的key
     *
     * @param key 键
     * @return
     */
    public Set hKeys(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForHash().keys(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取key所对应的散列表的大小个数
     *
     * @param key 键
     * @return
     */
    public long hSize(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            return redisTemplate.opsForHash().size(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取整个哈希存储的值根据key
     *
     * @param key 键
     * @return
     */
    public List hValues(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForHash().values(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用Cursor在key的hash中迭代，相当于迭代器
     *
     * @param key         键
     * @param scanOptions 属性设置
     * @return
     */
    public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions scanOptions) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            if (null != scanOptions) {
                return redisTemplate.opsForHash().scan(key, scanOptions);
            } else {
                return redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set sGet(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, String value) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, String... values) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 数据取两个key 对应的 set的交集
     *
     * @param key1 第一个set集合的键
     * @param key2 第二个set集合的键
     * @param key3 存储结果的set
     * @return 保存成功>0
     */
    public long intersectAndStore(String key1, String key2, String key3) {
        if (null == key1 || "".equals(key1)
                || null == key2 || "".equals(key2)
                || null == key3 || "".equals(key3)
        ) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            return redisTemplate.opsForSet().intersectAndStore(key1, key2, key3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据zset集合中的值获取 排序值
     *
     * @param key    key
     * @param member 集合中的值
     * @return 排序值
     */
    public Double zScore(String key, String member) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0.0D;
        }
        try {
            return redisTemplate.opsForZSet().score(key, member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 将数据放入zset缓存
     *
     * @param key   键
     * @param value 值
     * @param score 排序键
     * @return 成功个数
     */
    public boolean zAdd(String key, String value, double score) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据下标获取zset中内容
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return set集合
     */
    public Set<String> zrange(String key, long start, long end) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
            return zset.range(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据下标倒序获取zset中内容
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return set集合
     */
    public Set<String> zReverseRange(String key, long start, long end) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
            return zset.reverseRange(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 根据排序键的范围倒序获取zset中内容
     *
     * @param key    键
     * @param score1 排序键开始
     * @param score2 排序键结束
     * @return set集合
     */
    public Set<String> zReverseRangeByScore(String key, double score1, double score2) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
            return zset.reverseRangeByScore(key, score1, score2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据排序键的范围获取有序集合
     *
     * @param key    键
     * @param score1 排序键开始
     * @param score2 排序键结束
     * @return set集合
     */
    public Set<String> zRangeByScore(String key, double score1, double score2) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
            return zset.rangeByScore(key, score1, score2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取排序信息，并从散列里取出对应的id数据倒序
     *
     * @param page     页
     * @param pageSize 页数
     * @param order    zset集合
     * @return 货币信息数据
     */
    public <T> List<T> getSortDataDESC(int page, int pageSize, String order, Class<T> clz) {
        int start = page;
        int end = start + pageSize - 1;
        if (page == 1) {
            start = 0;
        }
        List<T> list = new ArrayList<>();
        //按照保存的数据的倒叙
        Set<String> coinIdNames = zReverseRange(order, start, end);
        for (String coinKey : coinIdNames) {
            //根据id取得资讯详情
            T data = getObject(coinKey, clz);
            if (data != null) {
                list.add(data);
            }
        }

        return list;
    }


    /**
     * 获取排序信息，并从散列里取出对应的id数据正序
     *
     * @param page     页
     * @param pageSize 页数
     * @param order    zset集合
     * @return 货币信息数据
     */
    public <T> List<T> getSortDataASC(int page, int pageSize, String order, Class<T> clz) {
        int start = page;
        int end = start + pageSize - 1;
        if (page == 1) {
            start = 0;
        }
        List<T> list = new ArrayList<>();
        //按照保存的数据的倒叙
        Set<String> coinIdNames = zrange(order, start, end);
        for (String coinKey : coinIdNames) {
            //根据id取得资讯详情
            T data = getObject(coinKey, clz);
            list.add(data);
        }

        return list;
    }

    /**
     * 获取有序集合的所有内容
     *
     * @param key 键
     * @return set集合
     */
    public Set<String> setMembers(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            SetOperations<String, String> set = redisTemplate.opsForSet();
            return set.members(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 zset 数量
     *
     * @param key zset键
     * @return 总数量
     */
    public Long zCard(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
            return zSet.zCard(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 从zset集合中移除
     *
     * @param key    zset键
     * @param member zset值
     * @return 总数量
     */
    public Long zRemove(String key, String member) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
            return zSet.remove(key, new String[]{member});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 有序集中指定成员的排名(从小到大)
     *
     * @param key    zset键
     * @param member zset值
     * @return 排名
     */
    public Long zRank(String key, String member) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
            return zSet.rank(key, member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 有序集中指定成员的排名(从大到小)
     *
     * @param key    zset键
     * @param member zset值
     * @return 排名
     */
    public Long zReverseRank(String key, String member) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
            return zSet.reverseRank(key, member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, String... values) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return 个数
     */
    public long sGetSetSize(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, String... values) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除并返回集合中的一个随机元素
     *
     * @param key 键
     * @return 值
     */
    public Object sPop(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForSet().pop(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将元素从一个集合移动到另一个集合
     *
     * @param key     键
     * @param destKey 目标键值
     * @return 是否成功
     */
    public boolean sMove(String key, String value, String destKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            return redisTemplate.opsForSet().move(key, value, destKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * key对应的无序集合与otherKey对应的无序集合求交集
     *
     * @param key      键
     * @param otherKey 对应的key
     * @return 值
     */
    public Set sIntersect(String key, String otherKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForSet().intersect(key, otherKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * key无序集合与otherkey无序集合的交集存储到destKey无序集合中
     *
     * @param key      键
     * @param otherKey 对应的key
     * @return 个数
     */
    public long sIntersectStore(String key, String otherKey, String destKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * key对应的无序集合与多个otherKey对应的无序集合求交集
     *
     * @param key  键
     * @param keys 对应的key集合
     * @return 值
     */
    public Set sIntersect(String key, List<String> keys) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForSet().intersect(key, keys);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * key对应的无序集合与多个otherKey对应的无序集合求交集存储到destKey无序集合中
     *
     * @param key     键
     * @param keys    对应的key的集合
     * @param destKey 目标键
     * @return 个数
     */
    public long sIntersectStore(String key, List<String> keys, String destKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            return redisTemplate.opsForSet().intersectAndStore(key, keys, destKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * key无序集合与otherKey无序集合的并集
     *
     * @param key      键
     * @param otherKey 对应key
     * @return 值
     */
    public Set sUnion(String key, String otherKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForSet().union(key, otherKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * key无序集合与多个otherKey无序集合的并集
     *
     * @param key      键
     * @param otherKey 对应key
     * @return 值
     */
    public Set sUnion(String key, List<String> otherKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForSet().union(key, otherKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * key无序集合与otherKey无序集合的并集存储到destKey无序集合中
     *
     * @param key      键
     * @param otherKey 对应key
     * @param destKey  目标键
     * @return 个数
     */
    public long sUnion(String key, String otherKey, String destKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * key无序集合与多个otherKey无序集合的并集存储到destKey无序集合中
     *
     * @param key      键
     * @param otherKey 对应键
     * @param destKey  目标键
     * @return 值
     */
    public long sUnion(String key, List<String> otherKey, String destKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * key无序集合与otherKey无序集合的差集
     *
     * @param key      键
     * @param otherKey 对应key
     * @return 值
     */
    public Set sDiff(String key, String otherKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForSet().difference(key, otherKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * key无序集合与多个otherKey无序集合的差集
     *
     * @param key      键
     * @param otherKey 对应key
     * @return 值
     */
    public Set sDiff(String key, List<String> otherKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForSet().difference(key, otherKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * key无序集合与otherKey无序集合的差集存储到destKey无序集合中
     *
     * @param key      键
     * @param otherKey 对应key
     * @param destKey  目标key
     * @return 个数
     */
    public long sDiff(String key, String otherKey, String destKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * key无序集合与多个otherKey无序集合的差集存储到destKey无序集合中
     *
     * @param key      键
     * @param otherKey 对应key
     * @param destKey  目标key
     * @return 个数
     */
    public long sDiff(String key, List<String> otherKey, String destKey) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 随机获取key无序集合中的一个元素
     *
     * @param key 键
     * @return 值
     */
    public Object sRandom(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForSet().randomMember(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取多个key无序集合中的元素（去重），count表示个数
     *
     * @param key   键
     * @param count 个数
     * @return 值
     */
    public List random(String key, long count) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForSet().randomMembers(key, count);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取多个key无序集合中的元素（去重），count表示个数
     *
     * @param key   键
     * @param count 个数
     * @return
     */
    public Set sDistinctRandom(String key, long count) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForSet().distinctRandomMembers(key, count);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 遍历set
     *
     * @param key         键
     * @param scanOptions 属性 无则传null
     * @return set游标
     */
    public Cursor sScan(String key, ScanOptions scanOptions) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            if (null != scanOptions) {
                return redisTemplate.opsForSet().scan(key, scanOptions);
            } else {
                return redisTemplate.opsForSet().scan(key, ScanOptions.NONE);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * member是否是名称为key的set的元素
     *
     * @param key    key
     * @param member 比较值
     * @return true 是
     */
    public boolean sIsMember(String key, String member) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            if (null != member) {
                return redisTemplate.opsForSet().isMember(key, member);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return 值
     */
    public List lGet(String key, long start, long end) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    public long lGetListSize(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 值
     */
    public Object lGetIndex(String key, long index) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public boolean lSet(String key, String value) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return 是否成功
     */
    public boolean lSet(String key, String value, long time) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public boolean lSet(String key, List<String> value) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return 是否成功
     */
    public boolean lSet(String key, List<String> value, long time) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public boolean lSetFromLeft(String key, String value) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return 是否成功
     */
    public boolean lSetFromLeft(String key, String value, long time) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public boolean lSetFromLeft(String key, List<String> value) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return 是否成功
     */
    public boolean lSetFromLeft(String key, List<String> value, long time) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return 是否成功
     */
    public boolean lUpdateIndex(String key, long index, String value) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个 count> 0：删除等于从头到尾移动的值的元素。 count <0：删除等于从尾到头移动的值的元素。 count = 0：删除等于value的所有元素。
     * @param value 值
     * @return 移除个数
     */
    public long lRemove(String key, long count, String value) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return 0L;
        }
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修剪现有列表，使其只包含指定的指定范围的元素，起始和停止都是基于0的索引
     *
     * @param key   键
     * @param start 起始值
     * @param end   结束值
     * @return 是否成功
     */
    public boolean lTrim(String key, long start, long end) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().trim(key, start, end);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 弹出最左边的元素，弹出之后该值在列表中将不复存在
     *
     * @param key 键
     * @return 值
     */
    public Object lLeftPop(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     键
     * @param timeout 超时时间
     * @param unit    time工具
     * @return 值
     */
    public Object lLeftPop(String key, long timeout, TimeUnit unit) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForList().leftPop(key, timeout, unit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 弹出最右边的元素，弹出之后该值在列表中将不复存在
     *
     * @param key 键
     * @return 值
     */
    public Object lRightPop(String key) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     键
     * @param timeout 超时时间
     * @param unit    time工具
     * @return 值
     */
    public Object lRightPop(String key, long timeout, TimeUnit unit) {
        if (null == key || "".equals(key)) {
            log.info(KEY_EMPTY);
            return null;
        }
        try {
            return redisTemplate.opsForList().rightPop(key, timeout, unit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用于移除列表的最后一个元素，并将该元素添加到另一个列表
     *
     * @param sourceKey      键
     * @param destinationKey 目标键
     * @return 值
     */
    public boolean rightPopAndLeftPush(String sourceKey, String destinationKey) {
        if (null == sourceKey || "".equals(sourceKey) || null == destinationKey || "".equals(destinationKey)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }

    /**
     * 用于移除列表的最后一个元素，并将该元素添加到另一个列表
     *
     * @param sourceKey      键
     * @param destinationKey 目标键
     * @param timeout        超时
     * @param unit           time工具
     * @return 值
     */
    public boolean rightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        if (null == sourceKey || "".equals(sourceKey) || null == destinationKey || "".equals(destinationKey)) {
            log.info(KEY_EMPTY);
            return false;
        }
        try {
            redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }
    }


}
