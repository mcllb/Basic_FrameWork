package com.yunke.admin.framework.eventbus.event;

import com.yunke.admin.framework.eventbus.base.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CacheEvent extends BaseEvent {

    private EventType eventType;

    private String cacheName;

    private String key;

    private Object value;


    public enum EventType{
        /**
         * 移除缓存
         */
        REMOVE,
        /**
         * 刷新
         */
        REFRESH,
        /**
         * 加载
         */
        LOAD,
    }

}




