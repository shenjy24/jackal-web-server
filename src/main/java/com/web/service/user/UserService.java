package com.web.service.user;

import com.web.config.GlobalConfig;
import com.web.config.response.BizException;
import com.web.constant.CookieConstant;
import com.web.constant.ErrorCode;
import com.web.domain.UserDomain;
import com.web.repository.entity.UserEntity;
import com.web.repository.vo.UserView;
import com.web.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private final GlobalConfig configItem;

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

        int expireTime = DateUtil.currentSecond() + CookieConstant.TOKEN_EXPIRED_TIME;
        String token = TokenUtil.generateToken(user.getUserId(), expireTime);
        CookieUtil.setCookie(ServletUtil.getResponse(), CookieConstant.COOKIE_KEY_TOKEN, token,
                CookieConstant.TOKEN_EXPIRED_TIME, configItem.getServerDomain());

        return user;
    }

    /**
     * 退出登录
     */
    public void logoff() {
        CookieUtil.removeCookie(ServletUtil.getResponse(), CookieConstant.COOKIE_KEY_TOKEN, configItem.getServerDomain());
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

    /**
     * 转换成返回结构
     *
     * @param user 用户实体
     * @return 用户返回结构
     */
    public UserView toUserView(UserEntity user) {
        if (null == user) {
            return UserView.builder().build();
        }
        return UserView.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userIcon(user.getUserIcon())
                .account(user.getAccount())
                .build();
    }
}
