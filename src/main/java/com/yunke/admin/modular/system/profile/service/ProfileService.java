package com.yunke.admin.modular.system.profile.service;

import cn.hutool.core.lang.Console;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.cache.UserCache;
import com.yunke.admin.framework.cache.vo.UserCacheVO;
import com.yunke.admin.modular.system.profile.model.param.UpdateUserInfoParam;
import com.yunke.admin.modular.system.profile.model.vo.UserProfileInfo;
import com.yunke.admin.modular.system.user.model.entity.User;
import com.yunke.admin.modular.system.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class ProfileService {

    private final UserCache userCache;

    private final UserService userService;

    public UserProfileInfo getCurrentUserInfo() {
        String userId = SaUtil.getUserId();
        Console.log(userCache.get(userId));
        UserCacheVO user = userCache.get(userId);
        if(user == null){
            return null;
        }
        UserProfileInfo profileInfo = new UserProfileInfo();
        BeanUtil.copyProperties(user, profileInfo);
        Console.log(profileInfo);
        return profileInfo;
    }

    public void updateUserInfo(UpdateUserInfoParam updateUserInfoParam){
        User user = new User();
        BeanUtil.copyProperties(updateUserInfoParam,user);
        userService.checkPhoneUnique(user,true);
        userService.checkEmailUnique(user,true);
        userService.lambdaUpdate()
                .set(User::getUserName,updateUserInfoParam.getUserName())
                .set(User::getShortName,updateUserInfoParam.getShortName())
                .set(User::getSex,updateUserInfoParam.getSex())
                .set(User::getEmail,updateUserInfoParam.getEmail())
                .set(User::getPhone,updateUserInfoParam.getPhone())
                .set(User::getTelphone,updateUserInfoParam.getTelphone())
                .set(User::getBirthday,updateUserInfoParam.getBirthday())
                .set(User::getUpdateBy,SaUtil.getUserId())
                .set(User::getUpdateTime,new Date())
                .eq(User::getId,SaUtil.getUserId())
                .update();
        userCache.remove(updateUserInfoParam.getId());
    }

}
