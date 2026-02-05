package com.yunke.admin.framework.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @className CacheOperator
 * @description: 缓存操作的基础接口，可以实现不同种缓存实现
 * <p>泛型为cache的值类class类型</p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface CacheOperator<T> {

    /**
     * @description: 添加缓存
     * @param: key
     * @param: value
     * @return: void
     * @auth: tianlei
     * @date: 2026/1/14 12:18
     */
    void put(String key, T value);

    /**
     * @description: 添加缓存（带过期时间，单位是秒）
     * @param: key
     * @param: value
     * @param: timeoutSeconds
     * @return: void
     * @auth: tianlei
     * @date: 2026/1/14 12:18
     */
    void put(String key, T value, Long timeoutSeconds);

    /**
     * @description: 通过缓存key获取缓存
     * @param: key
     * @return: java.lang.Object
     * @auth: tianlei
     * @date: 2026/1/14 12:19
     */
    Object get(String key);

    /**
     * @description: 删除缓存
     * @param: key
     * @return: void
     * @auth: tianlei
     * @date: 2026/1/14 12:19
     */
    void remove(String... key);

    /**
     * @description: 删除全部缓存
     * @return: void
     * @auth: tianlei
     * @date: 2026/1/14 12:19
     */
    void removeAll();

    /**
     * @description: 获得缓存的所有key列表（不带common prefix的）
     * @return: java.util.Set<java.lang.String>
     * @auth: tianlei
     * @date: 2026/1/14 12:19
     */
    Set<String> getAllKeys();

    /**
     * @description: 获得缓存的所有值列表
     * @return: java.util.List<T>
     * @auth: tianlei
     * @date: 2026/1/14 12:20
     */
    List<T> getAllValues();

    /**
     * @description: 获取所有的key，value
     * @param:
     * @return: java.util.Map<java.lang.String,T>
     * @auth: tianlei
     * @date: 2026/1/14 12:20
     */
    Map<String, T> getAllKeyValues();

    /**
     * @description: 通用缓存的前缀，用于区分不同业务
     * <p>如果带了前缀，所有的缓存在添加的时候，key都会带上这个前缀</p>
     * @return: java.lang.String
     * @auth: tianlei
     * @date: 2026/1/14 12:20
     */
    String getCommonKeyPrefix();


    default void init(){
        this.removeAll();
        this.loadCache();
    };
    
    void destroy();

    void loadCache();

    default boolean exist(String key){
        return this.get(key) != null;
    }

}
