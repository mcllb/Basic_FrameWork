package com.yunke.admin.common.util;


public class StrUtil extends cn.hutool.core.util.StrUtil {

    private StrUtil(){

    }

    /**
     * 创建StringBuilder对象
     *
     * @param sb   初始StringBuilder
     * @param strs 初始字符串列表
     * @return StringBuilder对象
     */
    public static StringBuilder appendBuilder(StringBuilder sb, CharSequence... strs) {
        for (CharSequence str : strs) {
            sb.append(str);
        }
        return sb;
    }

}
