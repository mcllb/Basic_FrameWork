package com.yunke.admin.framework.core.exception;


public class BaseException extends RuntimeException {

    /**
     * 序列化号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 异常编码
     */
    private Integer code = 5000;

    /**
     * 获取异常编码
     *
     * @return
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置异常编码
     *
     * @param code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 构造一个不带参数的异常实例
     *
     */
    public BaseException() {
        super();
    }

    /**
     * 使用指定消息构造一个异常实例
     *
     * @param message
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * 使用指定消息构造和code一个异常实例
     *
     * @param message
     * @param code
     */
    public BaseException(String message, Integer code) {
        super(message);
        this.code = code;
    }

}
