package com.yunke.admin.framework.eventbus.base;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public abstract class BaseEvent {

    protected Map<String,Object> data = new HashMap<>();

    protected void put(String key,Object value){
        data.put(key, value);
    }

    protected Object get(String key){
        return data.get(key);
    }
}
