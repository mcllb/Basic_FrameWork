package com.yunke.admin.framework.core.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.baomidou.lock.exception.LockException;
import com.yunke.admin.common.enums.OtherExceptionEnum;
import com.yunke.admin.common.enums.SaTokenExceptionEnum;
import com.yunke.admin.framework.core.response.ErrorResponseData;
import com.yunke.admin.framework.core.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * @className GlobalExceptionHandler
 * @description: 统一异常处理
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{
    /**
     * 默认异常
     */
    @ExceptionHandler(Exception.class)
    public ErrorResponseData exception(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        System.err.println("GlobalExceptionHandler====exception===");
        System.out.println(request.getRequestURL());
        exception.printStackTrace();
        log.error(exception.getMessage(), exception);
        return new ErrorResponseData(OtherExceptionEnum.SYSTEM_ERROR);
    }

    /**
     * SaToken 认证异常
     * @param exception
     * @return
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseData UnauthorizedException(NotLoginException exception){
        exception.printStackTrace();
        log.error(exception.getMessage(), exception);
        // 判断场景值，定制化异常信息
        String message = "";
        if(exception.getType().equals(NotLoginException.NOT_TOKEN)) {
            //未提供Token
            message = NotLoginException.NOT_TOKEN_MESSAGE;
        }
        else if(exception.getType().equals(NotLoginException.INVALID_TOKEN)) {
            //Token无效
            message = NotLoginException.INVALID_TOKEN_MESSAGE;
        }
        else if(exception.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            //Token已过期
            message = NotLoginException.TOKEN_TIMEOUT_MESSAGE;
        }
        else if(exception.getType().equals(NotLoginException.BE_REPLACED)) {
            //Token已被顶下线
            message = NotLoginException.BE_REPLACED_MESSAGE;
        }
        else if(exception.getType().equals(NotLoginException.KICK_OUT)) {
            //Token已被踢下线
            message = NotLoginException.KICK_OUT_MESSAGE;
        }else if(exception.getType().equals(NotLoginException.TOKEN_FREEZE)) {
            //token 已被冻结
            message = NotLoginException.TOKEN_FREEZE_MESSAGE;
        }else if(exception.getType().equals(NotLoginException.NO_PREFIX)) {
            //未按照指定前缀提交 token
            message = NotLoginException.NO_PREFIX_MESSAGE;
        }
        else {
            //当前会话未登录
            message = NotLoginException.DEFAULT_MESSAGE;
        }
        return new ErrorResponseData(SaTokenExceptionEnum.TOKEN_ERROR.getCode(),message);
    }

    /**
     * SaToken 权限异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public ErrorResponseData notPermissionException(NotPermissionException  exception) {
        log.error(exception.getMessage(), exception);
        String message = "您没有此权限：" + exception.getCode();
        return new ErrorResponseData(SaTokenExceptionEnum.NO_PERMISSON.getCode(),message);
    }

    /**
     * SaToken 角色异常
     */
    @ExceptionHandler(NotRoleException .class)
    public ErrorResponseData notRoleException(NotRoleException exception) {
        log.error(exception.getMessage(), exception);
        String message = "您没有此角色：" + exception.getRole();
        return new ErrorResponseData(SaTokenExceptionEnum.NO_ROLE.getCode(),message);
    }


    /**
     * Controller单个参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseData ConstraintViolationException(ConstraintViolationException exception){
        log.error(exception.getMessage(), exception);
        String message = exception.getMessage();
        message = message.substring(message.indexOf(":") + 1);
        return ResponseData.fail(message);
    }

    /**
     * 自定义参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        log.error(exception.getMessage(), exception);
        ObjectError objectError = exception.getBindingResult().getAllErrors().get(0);
        return ResponseData.fail(objectError.getDefaultMessage());
    }

    /**
     * 自定义参数校验异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseData methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        exception.printStackTrace();
        log.error(exception.getMessage(), exception);
        return new ErrorResponseData("参数类型错误");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseData BindException(BindException exception){
        exception.printStackTrace();
        log.error(exception.getMessage(), exception);
        String message = exception.getAllErrors().get(0).getDefaultMessage();
        return ResponseData.fail(message);
    }


    /**
     * 请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponseData httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        exception.printStackTrace();
        log.error(exception.getMessage(), exception);
        return new ErrorResponseData(OtherExceptionEnum.HTTP_METHOD_ERROR);
    }

    /**
     * 请求路径不存在异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseData noHandlerFoundException(NoHandlerFoundException exception){
        exception.printStackTrace();
        log.error(exception.getMessage(), exception);
        return new ErrorResponseData(OtherExceptionEnum.HTTP_HANDLER_ERROR);
    }


    /**
     * 请求参数异常
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, MissingServletRequestPartException.class})
    public ErrorResponseData parameterException(Exception exception) {
        exception.printStackTrace();
        log.error(exception.getMessage(), exception);
        return new ErrorResponseData(OtherExceptionEnum.PARAMETER_ERROR);
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(BaseException.class)
    public ErrorResponseData baseException(BaseException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponseData(exception.getCode(),exception.getMessage());
    }

    /**
     * 上传文件大小超过限制
     * @param exception
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ErrorResponseData maxUploadSizeExceededException(MaxUploadSizeExceededException exception){
        return new ErrorResponseData("上传文件大小超过限制");
    }


    /**
     * lock4j异常
     * @param exception
     * @return
     */
    @ExceptionHandler(LockException.class)
    public ErrorResponseData LockException(LockException exception){
        return new ErrorResponseData("操作超时");
    }

}
