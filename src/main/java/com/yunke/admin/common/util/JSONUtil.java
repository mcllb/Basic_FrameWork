package com.yunke.admin.common.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class JSONUtil extends cn.hutool.json.JSONUtil {

    public static final String[] EMPTY_SQL_WORDS = ArrayUtil.newArray(String.class,0);

    private JSONUtil(){

    }

    public static void trimJSONObject(JSONObject jsonObject) {
        if(jsonObject == null || jsonObject.isEmpty()){
            return;
        }
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            trimJSONObject(jsonObject, key);
        }
        log.debug("trimJSONObject src={},new={}",jsonObject,jsonObject);
    }

    public static void trimJSONArray(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            trimJSONArray(jsonArray, i);
        }
        log.debug("trimJSONObject src={},new={}",jsonArray,jsonArray);
    }

    public static void trimJSONObject(JSONObject jsonObject, String key) {
        if(jsonObject == null || jsonObject.isEmpty()){
            return;
        }
        Object keyObj = jsonObject.get(key);
        if (keyObj == null) {
            return;
        }
        if (keyObj instanceof String) {
            jsonObject.set(key, ((String) keyObj).trim());
        } else if (keyObj instanceof JSONObject) {
            //解析json 对象
            trimJSONObject(((JSONObject) keyObj));
        } else if (keyObj instanceof JSONArray) {
            //解析json 数组
            trimJSONArray(((JSONArray) keyObj));
        }
    }

    public static void trimJSONArray(JSONArray jsonArray, int index) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return;
        }
        Object keyObj = jsonArray.get(index);
        if (keyObj == null) {
            return;
        }
        if (keyObj instanceof String) {
            jsonArray.set(index, ((String) keyObj).trim());
        } else if (keyObj instanceof JSONObject) {
            //解析json 对象
            trimJSONObject(((JSONObject) keyObj));
        } else if (keyObj instanceof JSONArray) {
            //解析json 数组
            trimJSONArray(((JSONArray) keyObj));
        }
    }

    public static void  replaceJSONObjectSqlKeyWords(JSONObject jsonObject, String[] sqlKeyWords, SqlUtil.SqlKeyWordWrapper sqlKeyWordWrapper,boolean useStrict) {
        if(jsonObject == null || jsonObject.isEmpty()){
            return;
        }
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            replaceJSONObjectSqlKeyWords(jsonObject, key,sqlKeyWords,sqlKeyWordWrapper,useStrict);
        }
        log.debug("replaceJSONObjectSqlKeyWords src={},new={},sqlKeyWords={},sqlKeyWordWrapper={},useStrict={}",jsonObject,jsonObject,sqlKeyWords,sqlKeyWordWrapper,useStrict);
    }

    public static void  replaceJSONObjectSqlKeyWords(JSONObject jsonObject, String[] sqlKeyWords, SqlUtil.SqlKeyWordWrapper sqlKeyWordWrapper) {
        if(jsonObject == null || jsonObject.isEmpty()){
            return;
        }
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            replaceJSONObjectSqlKeyWords(jsonObject, key,sqlKeyWords,sqlKeyWordWrapper,false);
        }
    }

    public static void  replaceJSONObjectSqlKeyWords(JSONObject jsonObject, String[] sqlKeyWords) {
        if(jsonObject == null || jsonObject.isEmpty()){
            return;
        }
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            replaceJSONObjectSqlKeyWords(jsonObject, key,sqlKeyWords,SqlUtil.DEFAULT_SQL_WRAPPER,false);
        }
    }

    public static void  replaceJSONObjectSqlKeyWords(JSONObject jsonObject, String[] sqlKeyWords,boolean useStric) {
        if(jsonObject == null || jsonObject.isEmpty()){
            return;
        }
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            replaceJSONObjectSqlKeyWords(jsonObject, key,sqlKeyWords,SqlUtil.DEFAULT_SQL_WRAPPER,useStric);
        }
    }

    public static void  replaceJSONObjectSqlKeyWords(JSONObject jsonObject) {
        if(jsonObject == null || jsonObject.isEmpty()){
            return;
        }
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            replaceJSONObjectSqlKeyWords(jsonObject, key,EMPTY_SQL_WORDS ,SqlUtil.DEFAULT_SQL_WRAPPER,false);
        }
    }

    public static void  replaceJSONObjectSqlKeyWords(JSONObject jsonObject,boolean useStrict) {
        if(jsonObject == null || jsonObject.isEmpty()){
            return;
        }
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            replaceJSONObjectSqlKeyWords(jsonObject, key,EMPTY_SQL_WORDS ,SqlUtil.DEFAULT_SQL_WRAPPER,useStrict);
        }
    }

    public static void replaceJSONOArraySqlKeyWords(JSONArray jsonArray, String[] sqlKeyWords, SqlUtil.SqlKeyWordWrapper sqlKeyWordWrapper,boolean useStrict) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            replaceJSONOArraySqlKeyWords(jsonArray, i,sqlKeyWords,sqlKeyWordWrapper,useStrict);
        }
        log.debug("replaceJSONOArraySqlKeyWords src={},new={},sqlKeyWords={},sqlKeyWordWrapper={},useStrict={}",jsonArray,jsonArray,sqlKeyWords,sqlKeyWordWrapper,useStrict);
    }

    public static void replaceJSONOArraySqlKeyWords(JSONArray jsonArray, String[] sqlKeyWords, SqlUtil.SqlKeyWordWrapper sqlKeyWordWrapper) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            replaceJSONOArraySqlKeyWords(jsonArray, i,sqlKeyWords,sqlKeyWordWrapper,false);
        }
    }

    public static void replaceJSONOArraySqlKeyWords(JSONArray jsonArray, String[] sqlKeyWords) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            replaceJSONOArraySqlKeyWords(jsonArray, i,sqlKeyWords,SqlUtil.DEFAULT_SQL_WRAPPER,false);
        }
    }

    public static void replaceJSONOArraySqlKeyWords(JSONArray jsonArray, String[] sqlKeyWords,boolean useStrict) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            replaceJSONOArraySqlKeyWords(jsonArray, i,sqlKeyWords,SqlUtil.DEFAULT_SQL_WRAPPER,useStrict);
        }
    }

    public static void replaceJSONOArraySqlKeyWords(JSONArray jsonArray,boolean useStrict) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            replaceJSONOArraySqlKeyWords(jsonArray, i,EMPTY_SQL_WORDS,SqlUtil.DEFAULT_SQL_WRAPPER,useStrict);
        }
    }

    public static void replaceJSONOArraySqlKeyWords(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            replaceJSONOArraySqlKeyWords(jsonArray, i,EMPTY_SQL_WORDS,SqlUtil.DEFAULT_SQL_WRAPPER,false);
        }
    }



    public static void replaceJSONObjectSqlKeyWords(JSONObject jsonObject, String key, String[] sqlKeyWords, SqlUtil.SqlKeyWordWrapper sqlKeyWordWrapper,boolean useStrict) {
        if(jsonObject == null || jsonObject.isEmpty()){
            return;
        }
        Object keyObj = jsonObject.get(key);
        if (keyObj == null) {
            return;
        }
        if (keyObj instanceof String) {
            String newKeyObj = SqlUtil.replaceKeyWords((String)keyObj,sqlKeyWords,sqlKeyWordWrapper,useStrict);
            jsonObject.set(key, newKeyObj);
        } else if (keyObj instanceof JSONObject) {
            //解析json 对象
            replaceJSONObjectSqlKeyWords(((JSONObject) keyObj));
        } else if (keyObj instanceof JSONArray) {
            //解析json 数组
            replaceJSONOArraySqlKeyWords(((JSONArray) keyObj));
        }
    }

    public static void replaceJSONOArraySqlKeyWords(JSONArray jsonArray, int index, String[] sqlKeyWords, SqlUtil.SqlKeyWordWrapper sqlKeyWordWrapper,boolean useStrict) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return;
        }
        Object keyObj = jsonArray.get(index);
        if (keyObj == null) {
            return;
        }
        if (keyObj instanceof String) {
            jsonArray.set(index, SqlUtil.replaceKeyWords((String)keyObj,sqlKeyWords,sqlKeyWordWrapper,useStrict));
        } else if (keyObj instanceof JSONObject) {
            //解析json 对象
            replaceJSONObjectSqlKeyWords(((JSONObject) keyObj),sqlKeyWords,sqlKeyWordWrapper,useStrict);
        } else if (keyObj instanceof JSONArray) {
            //解析json 数组
            replaceJSONOArraySqlKeyWords(((JSONArray) keyObj),sqlKeyWords,sqlKeyWordWrapper,useStrict);
        }
    }

    public static JSONObject  xssFilter(JSONObject jsonObject){
        if(jsonObject == null || jsonObject.isEmpty()){
            return jsonObject;
        }
        JSONObject newJSONObject = createObj();
        for(String key : jsonObject.keySet()){
            Object value = jsonObject.get(key);
            if(value != null){
                if(value instanceof String){
                    String newValue = HtmlUtil.filter((String)value);
                    newJSONObject.set(key,newValue);
                }else if(value instanceof JSONObject){
                    JSONObject newValue = xssFilter((JSONObject)value);
                    newJSONObject.set(key,newValue);
                }else if(value instanceof JSONArray){
                    JSONArray newValue = xssFilter((JSONArray)value);
                    newJSONObject.set(key,newValue);
                }else{
                    newJSONObject.set(key,value);
                }
            }else{
                newJSONObject.set(key,value);
            }
        }
        return newJSONObject;
    }

    public static JSONArray xssFilter(JSONArray jsonArray){
        if(jsonArray != null || jsonArray.isEmpty()){
            return jsonArray;
        }
        JSONArray newJSONArray = JSONUtil.createArray();
        for(Object value : jsonArray){
            if(value == null){
                newJSONArray.add(value);
            }else{
                if(value instanceof String){
                    String newValue = HtmlUtil.filter((String)value);
                    newJSONArray.add(newValue);
                }else if(value instanceof JSONObject){
                    JSONObject newValue = xssFilter((JSONObject)value);
                    newJSONArray.add(newValue);
                }else if(value instanceof JSONArray){
                    JSONArray newValue = xssFilter((JSONArray)value);
                    newJSONArray.add(newValue);
                }else{
                    newJSONArray.add(value);
                }
            }
        }
        return newJSONArray;
    }


    //public static void testTrim(){
    //    Map<String, Object> map = MapUtil.newHashMap();
    //    map.put("a1","ss  ");
    //    map.put("a2","");
    //    map.put("a3","  dd");
    //    map.put("a4","  ddd44  ");
    //    map.put("a5","  ddd  44  ");
    //    map.put("a6","ddd  44");
    //    map.put("list", CollUtil.newArrayList("d1 "," d3d ","ddd f  "));
    //
    //    Map<String, Object> map2 = MapUtil.newHashMap();
    //    map2.put("B1","ss  ");
    //    map2.put("B2","");
    //    map2.put("B3","  dd");
    //
    //    map.put("map2", map2);
    //
    //    Console.log("map={}",map);
    //    JSONObject jsonObject = JSONUtil.parseObj(map);
    //    Console.log("jsonObject={}",jsonObject);
    //    trimJSONObject(jsonObject);
    //    Console.log("jsonObject2={}",jsonObject);
    //    Console.log("map2={}",map);
    //}

    //public static void testReplaceSqlKewords(){
    //    Map<String, Object> map = MapUtil.newHashMap();
    //    map.put("a1","select * from sys_demo where 1=1  ");
    //    map.put("a2","SELECT * from sys_demo where 1=1  ");
    //    map.put("a3","  UPDATE sys_demo SET AGE = 1 ");
    //    map.put("a4","  Insert into sys_demo(1,2,3)  ");
    //    map.put("list", CollUtil.newArrayList("d1 "," d3d ","ddd f  ","ddd f UPDATE SELECTdelete "));
    //
    //    Map<String, Object> map2 = MapUtil.newHashMap();
    //    map2.put("drop","ss  drop table sys_demo  ");
    //    map2.put("alter"," alter   table sys_demo add column name varchar(50) ");
    //    map2.put("delete","  dd  delete from sys_demo where 1=2  ");
    //
    //    map.put("map2", map2);
    //
    //    Console.log("map={}",map);
    //    JSONObject jsonObject = JSONUtil.parseObj(map);
    //    Console.log("jsonObject={}",jsonObject);
    //    replaceJSONObjectSqlKeyWords(jsonObject,true);
    //    System.out.println("\r\n");
    //    System.out.println("\r\n");
    //    Console.log("jsonObject2={}",jsonObject);
    //    System.out.println("\r\n");
    //    System.out.println("\r\n");
    //    Console.log("map2={}",map);
    //}







    //public static  void testXssFilter() {
    //    Map<String, Object> map = MapUtil.newHashMap();
    //    map.put("a1","select * from sys_demo where div DIV label <label> htmllabel </label>  ");
    //    map.put("list", CollUtil.newArrayList("ALTER('111') "," alter('hello') "));
    //    Map<String, Object> map2 = MapUtil.newHashMap();
    //    map2.put("drop","ss  span <span>testspan </span> table sys_demo  ");
    //    map2.put("alter"," alter   table sys_demo add column name varchar(50) <h5>h5555</> ");
    //    map.put("map2", map2);
    //
    //    //String jsonStr = toJsonStr(map);
    //    JSONObject jsonObject0 = parseObj(map);
    //    Console.log("jsonObject={}",jsonObject0);
    //
    //    JSONObject jsonObject1 = xssFilter(jsonObject0);
    //    Console.log("jsonObject1={}",jsonObject1);
    //
    //    Console.log("jsonObject02={}",jsonObject0);
    //
    //}


    public static void main(String[] args) {
        //testReplaceSqlKewords();

        //testXssFilter();
    }

}
