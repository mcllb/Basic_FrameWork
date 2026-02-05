package com.yunke.admin.common.util;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SqlUtil {

    public final static String[] DEFAULT_SQL_KEYWORDS = {"master", "truncate", "insert", "select"
            , "delete", "update", "declare", "alter", "drop", "sleep"};


    public final static String DEFAULT_REPLACE_STR = StrUtil.EMPTY;

    public final static SqlKeyWordWrapper DEFAULT_SQL_WRAPPER;

    static {
        DEFAULT_SQL_WRAPPER = new SqlKeyWordWrapper();
    }

    private SqlUtil(){

    }

    /**
     * 替换sql中的关键字
     *
     * @param sql
     * @param keywords
     * @param sqlKeyWordWrapper
     * @param useStrict 是否严格模式，严格模式下将不判断sql文本格式
     * @return
     */
    public static String replaceKeyWords(String sql,String[] keywords,SqlKeyWordWrapper sqlKeyWordWrapper,boolean useStrict){
        if(StrUtil.isEmpty(sql)){
            return sql;
        }
        String[] KEY_WORDS = DEFAULT_SQL_KEYWORDS;
        if(ArrayUtil.isNotEmpty(keywords)){
            KEY_WORDS = ArrayUtil.append(KEY_WORDS, keywords);
            //去重
            KEY_WORDS = ArrayUtil.distinct(KEY_WORDS);
        }
        String newSql = sql;
        for(String keyword : KEY_WORDS){
            newSql = replaceKeyWord(newSql,keyword,sqlKeyWordWrapper,useStrict);
        }
        log.debug("replaceKeyWords sql={},newSql={},keywords={},sqlKeyWordWrapper={},useStrict{}",sql,newSql,keywords,sqlKeyWordWrapper,useStrict);
        return newSql;
    }

    public static String replaceKeyWords(String sql,SqlKeyWordWrapper sqlKeyWordWrapper,boolean useStrict){
        return replaceKeyWords(sql, DEFAULT_SQL_KEYWORDS, sqlKeyWordWrapper, useStrict);
    }

    public static String replaceKeyWords(String sql,String[] keywords,SqlKeyWordWrapper sqlKeyWordWrapper){
        return replaceKeyWords(sql, keywords, sqlKeyWordWrapper, false);
    }

    public static String replaceKeyWords(String sql,String[] keywords){
        return replaceKeyWords(sql,keywords,false);
    }

    public static String replaceKeyWords(String sql,String[] keywords,boolean useStrict){
        return replaceKeyWords(sql, keywords, DEFAULT_SQL_WRAPPER, false);
    }

    public static String replaceKeyWords(String sql,boolean useStrict){
        return replaceKeyWords(sql, DEFAULT_SQL_WRAPPER, useStrict);
    }

    public static String replaceKeyWords(String sql){
        return replaceKeyWords(sql, DEFAULT_SQL_WRAPPER, false);
    }



    /**
     * 替换sql中的关键字
     *
     * @param sql
     * @param keyword
     * @param wrapper
     * @param useStrict 是否严格模式，严格模式下将不判断sql文本格式
     * @return
     */
    public static String replaceKeyWord(String sql,String keyword,SqlKeyWordWrapper wrapper,boolean useStrict){
        if(StrUtil.isEmpty(sql)){
            return sql;
        }
        if(StrUtil.isEmpty(keyword)){
            return sql;
        }
        String replace = buildReplace(keyword,wrapper);
        if(useStrict){
            if(StrUtil.containsIgnoreCase(sql,keyword)){
                String newSql = StrUtil.replaceIgnoreCase(sql, keyword, replace);
                return newSql;
            }
        }else{
            if(sql.length() <= keyword.length() + 4){
                return sql;
            }
            if(StrUtil.containsIgnoreCase(sql," " + keyword)
                    || StrUtil.containsIgnoreCase(sql,keyword + " ")
                    || StrUtil.containsIgnoreCase(sql," " + keyword + " "))
            {
                return StrUtil.replaceIgnoreCase(sql, keyword, replace);
            }
        }
        return sql;
    }

    public static String replaceKeyWord(String sql,String keyword,boolean useStrict){
        return replaceKeyWord(sql,keyword,DEFAULT_SQL_WRAPPER,useStrict);
    }

    public static String replaceKeyWord(String sql,String keyword,SqlKeyWordWrapper sqlKeyWordWrapper){
        return replaceKeyWord(sql,keyword,sqlKeyWordWrapper,false);
    }

    public static String replaceKeyWord(String sql,String keyword){
        return replaceKeyWord(sql,keyword,DEFAULT_SQL_WRAPPER,false);
    }

    private static String buildReplace(String keyword,SqlKeyWordWrapper sqlKeyWordWrapper){
        String replace = DEFAULT_REPLACE_STR;
        if(sqlKeyWordWrapper != null){
            if(StrUtil.isNotEmpty(sqlKeyWordWrapper.getReplaceStr())){
                replace = sqlKeyWordWrapper.getReplaceStr();
            }else {
                if(StrUtil.isNotEmpty(sqlKeyWordWrapper.getLeft())){
                    replace = sqlKeyWordWrapper.getLeft() + keyword;
                }
                if(StrUtil.isNotEmpty(sqlKeyWordWrapper.getRight())){
                    if(StrUtil.isNotEmpty(replace)){
                        replace = replace + sqlKeyWordWrapper.getRight();
                    }else{
                        replace = keyword + sqlKeyWordWrapper.getRight();
                    }
                }
            }
        }
        return replace;
    }

    /**
     * SQL关键字包装
     *
     * 优先级 replaceStr left right
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    static class SqlKeyWordWrapper {
        private String replaceStr = "";

        private String left = "#";

        private String right = "#";

    }

    public static void main(String[] args) {
        String keyword = "select";
        String sql = "select * from sys_demo where 1=1 ";
        System.out.println();
        boolean contains = StrUtil.containsIgnoreCase(sql,keyword);
        System.out.println("contains====" + contains);
        String replace = buildReplace(keyword, DEFAULT_SQL_WRAPPER);
        System.out.println("replace====" + replace);
        if(contains){
            String newSql = StrUtil.replaceIgnoreCase(sql, keyword, replace);
            System.out.println("newSql====" + newSql);
        }

    }

}
