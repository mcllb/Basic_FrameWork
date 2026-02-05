package com.yunke.admin.framework.components;

import com.yunke.admin.modular.system.user.model.param.UserImportParam;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserImportValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserImportParam.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"userName","userName.empty","用户名不能为空");

        //其他逻辑校验
    }

    public static void main(String[] args) {
        UserImportParam userImportParam = new UserImportParam();
        //执行校验
        Errors errors = new BeanPropertyBindingResult(userImportParam,"userImportParam");
        new UserImportValidator().validate(userImportParam,errors);

        //如果校验失败返回校验信息
        if(errors.hasErrors()){

        }

    }

}
