package com.yunke.admin.framework.core.util;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.RandomUtil;
import com.yunke.admin.modular.system.user.model.entity.User;
import org.springframework.stereotype.Component;

/**
 * @className PasswordHelper
 * @description:
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Component
public class PasswordHelper {

    public void encryptPassword(User user) {
        //生成随机盐
        String salt = RandomUtil.randomString(32);
        user.setSalt(salt);
        String newPassword = SaSecureUtil.aesEncrypt(user.getSalt(),user.getPassword());
        user.setPassword(newPassword);
    }

    public boolean verifyPassword(User user, String password) {
        String aesPwd = SaSecureUtil.aesEncrypt(user.getSalt(), password);
        return user.getPassword().equals(aesPwd);
    }

    public static String encryptAesPassword(String salt,String password){
        return SaSecureUtil.aesEncrypt(salt,password);
    }

    public static String decryptAesPassword(String salt,String password){
        return SaSecureUtil.aesDecrypt(salt,password);
    }

    public static void main(String[] args) {
        //System.out.println(RandomUtil.randomString(32));
        System.out.println(PasswordHelper.encryptAesPassword("e10a003388ad0aa7b11b1014aa5ad1ad","123456"));
    }
}
