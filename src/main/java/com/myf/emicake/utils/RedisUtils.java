package com.myf.emicake.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName com.myf.emicake.utils RedisUtil
 * @Description Redis数据操作工具类
 * @Author Afengis
 * @Date 2021/2/6 19:51
 * @Version V1.0
 **/
@Slf4j
@Component
public class RedisUtils {

    @Autowired
    private JSONUtils jsonUtils;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ValueOperations<String, Object> valueOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;


    /**
     * 对redis中指定键对应的数据设置失效时间
     *
     * @param key  键
     * @param time 时间（秒），time要大于0
     * @return
     */
    public boolean expire(String key, long time) {
        boolean result = false;
        try {
            if (StringUtils.isNotBlank(key) && time > 0) {
                result = this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return result;
    }

    /**
     * 从redis中根据指定的key获取已设置的过期时间
     *
     * @param key 键，不能为null
     * @return 时间（秒），返回0代表为永久有效
     */
    public long getExpire(String key) {
        long time = 0L;
        try {
            if (StringUtils.isNotBlank(key)) {
                time = this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return time;
    }

    /**
     * 判断 redis 中是否存在指定的 key
     *
     * @param key 键，不能为null
     * @return true表示存在，false表示不存在
     */
    public boolean exists(String key) {
        boolean result = false;

        try {
            if (StringUtils.isNotBlank(key)) {
                result = this.redisTemplate.hasKey(key);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return result;
    }

    /**
     * 从 redis 中移除指定 key 对应的数据
     *
     * @param keys 可以传一个值或多个
     */
    @SuppressWarnings("unchecked")
    public long remove(String... keys) {
        long count = 0L;
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                boolean result = this.redisTemplate.delete(keys[0]);
                if (result) {
                    count = keys.length;
                }
            } else {
                count = this.redisTemplate.delete(CollectionUtils.arrayToList(keys));
            }
        }
        return count;
    }

    // ============================ String =============================
    /**
     * 从 redis 中获取指定 key 对应的 string 数据
     *
     * @param key 键，不能为null
     * @return key 对应的字符串数据
     */
    public <T> T get(String key) {
        T t = null;
        try {
            if (StringUtils.isNotBlank(key)) {
                t = (T) this.valueOperations.get(key);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return t;
    }

    /**
     * 从 redis 中获取指定 key 对应的 string 数据，并转换为 T 类型
     *
     * @param key   键，不能为null
     * @param clazz 类型，从 redis 获取后的对象直接转换为 T 类型
     * @return key 对应的数据
     */
    public <T> T get(String key, Class<T> clazz) {
        T t = null;
        try {
            if (StringUtils.isNotBlank(key)) {
                String str = (String) valueOperations.get(key);

                t = this.stringToBean(str, clazz);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }

        return t;
    }

    /**
     * 将指定的 key, value 放到 redis 中
     *
     * @param key   键，不能为null
     * @param value 值，不能为null
     * @return true表示成功，false表示失败
     */
    public <T> boolean set(String key, T value) {
        boolean result = false;

        try {
            if (StringUtils.isNotBlank(key)) {
                this.valueOperations.set(key, value);
                result = true;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return result;
    }

    /**
     * 将指定的 key, value 放到 redis 中，并设置过期时间
     *
     * @param key   键，不能为null
     * @param value 值，不能为null
     * @param time  时间（秒），time要大于0，如果time小于等于0，将设置无限期
     * @return true表示成功，false表示失败
     */
    public <T> boolean set(String key, T value, long time) {
        try {
            if (time > 0) {
                this.valueOperations.set(key, value, time, TimeUnit.MINUTES);
            } else {
                this.set(key, value);
            }
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 对 redis 中指定 key 的数据递增，并返回递增后的值
     * @param key   键，不能为null
     * @param delta 要增加几（大于0）
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return this.valueOperations.increment(key, delta);
    }

    /**
     * 对 redis 中指定 key 的数据递减，并返回递减后的值
     *
     * @param key   键，不能为null
     * @param delta 要减少几（大于0）
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }

        return this.valueOperations.decrement(key, delta);
    }




    // ============================ Hash =============================


    /**
     * 判断 redis 中指定 key 对应的 hash 表中是否有 hashKey
     *
     * @param key     键，不能为null
     * @param hashKey hash表中的键，不能为null
     * @return true表示存在，false表示不存在
     */
    public boolean hexists(String key, String hashKey) {

        return hashOperations.hasKey(key, hashKey);
    }


    /**
     * 从 redis 中获取指定 key 对应的 hash 表中的指定 hashKey 所对应的值
     *
     * @param key     键，不能为null
     * @param hashKey hash表中的键，不能为null
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public <T> T hget(String key, String hashKey) {
        return (T) hashOperations.get(key, hashKey);
    }

    /**
     * 向 redis 中指定 key 对应的 hash 表（如果 hash 表不存在则自动创建）中放入 hashKey,value 数据
     *
     * @param key     键，不能为null
     * @param hashKey hash表中的键，不能为null
     * @param value   值，不能为null
     * @return true表示成功，false表示失败
     */
    public <T> boolean hset(String key, String hashKey, T value) {
        try {
            hashOperations.put(key, hashKey, value);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 向 redis 中指定 key 对应的 hash 表（如果 hash 表不存在则自动创建）中放入 hashKey,value 数据，并设置过期时间
     *
     * @param key     键，不能为null
     * @param hashKey hash表中的键，不能为null
     * @param value   值，不能为null
     * @param time    过期时间（秒），注意:如果已存在的hash表有时间，这里将会替换原有的时间
     * @return true表示成功，false表示失败
     */
    public <T> boolean hset(String key, String hashKey, T value, long time) {
        try {
            hashOperations.put(key, hashKey, value);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 删除 redis 中指定 key 对应的 hash 表中等于 hashKeys 的数据
     *
     * @param key      键，不能为null
     * @param hashKeys hash表中的键，可以使多个，不能为null
     */
    public <T> void hdel(String key, T... hashKeys) {
        hashOperations.delete(key, hashKeys);
    }

    /**
     * 从 redis 中获取指定 key 对应的 hash 表，并返回相应的 map 对象
     *
     * @param key 键，不能为null
     * @return map对象，包含所有的键值对
     */
    public Map<?, ?> hmget(String key) {
        return hashOperations.entries(key);
    }

    /**
     * 向 redis 中放入指定 key，并设置对应的数据类型为 map
     *
     * @param key 键，不能为null
     * @param map 多个键值对应的map，不能为null
     * @return true表示成功，false表示失败
     */
    public boolean hmset(String key, Map<String, ?> map) {
        try {
            hashOperations.putAll(key, map);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 向 redis 中放入指定 key，并设置对应的数据类型为 map 以及过期时间
     *
     * @param key  键，不能为null
     * @param map  对应多个键值，不能为null
     * @param time 时间（秒），time要大于0，如果time小于等于0，将设置无限期
     * @return true表示成功，false表示失败
     */
    public boolean hmset(String key, Map<String, ?> map, long time) {
        try {
            hashOperations.putAll(key, map);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 对 redis 中指定 key 对应的 hash 表（如果 hash 表不存在则自动创建）递增，并返回新增后的值
     *
     * @param key   键，不能为null
     * @param item  项，不能为null
     * @param delta 要增加几（大于0）
     * @return 新增后的值
     */
    public double hincr(String key, String item, double delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0。");
        }
        return hashOperations.increment(key, item, delta);
    }

    /**
     * 对 redis 中指定 key 对应的 hash 表（如果 hash 表不存在则自动创建）递增，并返回新增后的值
     *
     * @param key   键，不能为null
     * @param item  项，不能为null
     * @param delta 要减少几（大于0）
     * @return
     */
    public double hdecr(String key, String item, double delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0。");
        }

        return hashOperations.increment(key, item, -delta);
    }

    /**
     * 从 redis 中移除指定 key 对应 hash 表中键为 values 的数据
     *
     * @param key    键，不能为null
     * @param values 值，不能为null
     * @return 移除的个数
     */
    public <T> long hremove(String key, T... values) {
        try {
            Long count = hashOperations.delete(key, values);
            return count;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return 0;
        }
    }



    /**
     * 根据指定的类型转换字符串/JSON字符串为相应的对象
     *
     * @param value 字符串/JSON字符串
     * @param clazz 类型
     * @param <T>   任意类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T stringToBean(String value, Class<T> clazz) {
        if (value == null || value.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == byte.class || clazz == Byte.class) {
            return (T) Byte.valueOf(value);
        } else if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(value);
        } else if (clazz == double.class || clazz == Double.class) {
            return (T) Double.valueOf(value);
        } else if (clazz == float.class || clazz == Float.class) {
            return (T) Float.valueOf(value);
        } else if (clazz == String.class) {
            return (T) value;
        } else {
            return jsonUtils.JSON2Object(value, clazz);
        }

    }

    /**
     * 将 bean 转换为字符串/JSON字符串
     *
     * @param value 任意类型对象
     * @return 字符串/JSON字符串
     */
    public <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }

        Class<?> clazz = value.getClass();
        if (clazz == byte.class || clazz == Byte.class) {
            return "" + value;
        } else if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else if (clazz == double.class || clazz == Double.class) {
            return "" + value;
        } else if (clazz == float.class || clazz == Float.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            return jsonUtils.Object2JSON(value);
        }

    }

}
