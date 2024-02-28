package com.web.service.user;

import com.web.config.response.BizException;
import com.web.constant.ErrorCode;
import com.web.domain.UserDomain;
import com.web.repository.entity.UserEntity;
import com.web.repository.entity.UserTokenEntity;
import com.web.service.AccessService;
import com.web.util.DateUtil;
import com.web.util.IdUtil;
import com.web.util.MD5Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 用户服务
 *
 * @author shenjy
 * @time 2023/11/7 10:44
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDomain userDomain;
    private final AccessService accessService;

    /**
     * 注册用户
     *
     * @param account  账户
     * @param password 密码
     */
    public UserEntity register(String account, String password) {
        UserEntity user = userDomain.getUser(account);
        if (user != null) {
            throw new BizException(ErrorCode.USER_ERROR1);
        }
        return userDomain.createUser(account, password);
    }

    /**
     * 登录
     *
     * @param account  账户
     * @param password 密码
     * @return 用户信息
     */
    public UserEntity login(String account, String password) {
        UserEntity user = userDomain.getUser(account);
        if (user == null) {
            throw new BizException(ErrorCode.USER_ERROR2);
        }
        // 校验密码
        if (!MD5Util.verifySaltMd5(password, user.getPassword())) {
            throw new BizException(ErrorCode.USER_ERROR2);
        }

        // 设置token
        UserTokenEntity userToken = this.saveOrUpdateUserToken(user.getUserId());
        // 设置Cookie
        accessService.setCookie(userToken.getToken());

        return user;
    }

    public UserTokenEntity saveOrUpdateUserToken(Long userId) {
        UserTokenEntity userToken = userDomain.getUserTokenByUserId(userId);
        String token = IdUtil.uuid();
        Timestamp expireTime = accessService.getTokenExpireTime();
        if (null == userToken) {
            userToken = new UserTokenEntity(userId, token, expireTime);
            userDomain.saveUserToken(userToken);
        } else {
            userToken.setToken(token);
            userToken.setExpireTime(expireTime);
            userDomain.updateUserToken(userToken);
        }
        return userToken;
    }

    /**
     * 退出登录
     */
    public void logoff(Long userId) {
        UserTokenEntity userToken = userDomain.getUserTokenByUserId(userId);
        if (userToken == null) {
            return;
        }
        userToken.setExpireTime(DateUtil.getCurrentTime());
        userDomain.updateUserToken(userToken);
        accessService.removeCookie();
    }

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     */
    public void updatePassword(long userId, String newPassword, String oldPassword) {
        UserEntity user = userDomain.getUser(userId);
        if (user == null) {
            throw new BizException(ErrorCode.USER_ERROR3);
        }
        if (!MD5Util.verifySaltMd5(oldPassword, user.getPassword())) {
            throw new BizException(ErrorCode.USER_ERROR2);
        }
        user.setPassword(MD5Util.saltMd5(newPassword));
        userDomain.updateUser(user);
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    public UserEntity getUser(Long userId) {
        return userDomain.getUser(userId);
    }

    public UserTokenEntity getUserTokenByToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return userDomain.getUserTokenByToken(token);
    }

    public void updateUserToken(UserTokenEntity userToken) {
        if (null == userToken) {
            return;
        }
        userDomain.updateUserToken(userToken);
    }
}
