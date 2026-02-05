package com.yunke.admin.framework.core.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;

import java.util.Arrays;

/**
 * @className OpLogAnnotionOpTypeEnum
 * @description: 日志注解操作类型枚举
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@EnumDict(EnumDictTypeConstant.OP_LOG_OP_TYPE)
public enum OpLogAnnotionOpTypeEnum implements AbstractDictEnum {

    /**
     * 其它
     */
    OTHER("OTHER","其它"),

    /**
     * 新增
     */
    INSERT("INSERT","新增"),

    /**
     * 删除
     */
    DELETE("DELETE","删除"),

    /**
     * 修改
     */
    UPDATE("UPDATE","修改"),

    /**
     * 查询
     */
    QUERY("QUERY","查询"),

    /**
     * 详情
     */
    DETAIL("DETAIL","详情"),

    /**
     * 导入
     */
    IMPORT("IMPORT","导入"),

    /**
     * 导出
     */
    EXPORT("EXPORT","导出"),

    /**
     * 文件上传
     */
    UPLOAD("UPLOAD","文件上传"),

    /**
     * 文件下载
     */
    DOWNLOAD("DOWNLOAD","文件下载"),

    /**
     * 清空数据
     */
    CLEAN("CLEAN","清空数据"),
    ;

    private final String code;

    private final String text;

    OpLogAnnotionOpTypeEnum(String code, String text){
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getDictType() {
        String dictType = AnnotationUtil.getAnnotationValue(this.getClass(), EnumDict.class);
        return dictType;
    }

    @Override
    public Dict getDict() {
        Dict dict = Dict.create();
        Arrays.stream(OpLogAnnotionOpTypeEnum.values()).forEach(operLogOpTypeEnum -> {
            dict.set(operLogOpTypeEnum.getCode(),operLogOpTypeEnum.getText());
        });
        return dict;
    }
}
