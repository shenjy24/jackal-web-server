package com.web.domain;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.web.constant.YesNoEnum;
import com.web.repository.entity.UserEntity;
import com.web.repository.entity.UserTokenEntity;
import com.web.repository.mapper.UserMapper;
import com.web.repository.mapper.UserTokenMapper;
import com.web.util.MD5Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 用户域
 *
 * @author shenjy
 * @time 2023/11/7 10:41
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserDomain {
    private final UserMapper userMapper;
    private final UserTokenMapper userTokenMapper;

    /**
     * 根据用户ID获取用户实体信息
     *
     * @param userId 用户ID
     * @return 用户实体信息
     */
    public UserEntity getUser(Long userId) {
        if (null == userId) {
            return null;
        }
        return userMapper.selectById(userId);
    }

    /**
     * 根据用户账户获取用户实体信息
     *
     * @param account 用户账户
     * @return 用户实体信息
     */
    public UserEntity getUser(String account) {
        if (StringUtils.isBlank(account)) {
            return null;
        }
        return new LambdaQueryChainWrapper<>(userMapper)
                .eq(UserEntity::getAccount, account)
                .one();
    }

    /**
     * 创建用户
     *
     * @param account  账户
     * @param password 密码
     * @return 用户实体
     */
    public UserEntity createUser(String account, String password) {
        UserEntity user = new UserEntity()
                .setUserName(account)
                .setAccount(account)
                .setPassword(MD5Util.saltMd5(password))
                .setUserStatus(YesNoEnum.YES.getCode());
        userMapper.insert(user);
        return user;
    }

    /**
     * 修改用户信息
     *
     * @param user 用户
     */
    public void updateUser(UserEntity user) {
        if (user == null || user.getUserId() == null) {
            return;
        }
        userMapper.updateById(user);
    }

    public void saveUserToken(UserTokenEntity userToken) {
        if (null == userToken) {
            return;
        }
        userTokenMapper.insert(userToken);
    }

    public void updateUserToken(UserTokenEntity userToken) {
        if (null == userToken || null == userToken.getTokenId()) {
            return;
        }
        userTokenMapper.updateById(userToken);
    }

    public UserTokenEntity getUserTokenByToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new LambdaQueryChainWrapper<>(userTokenMapper)
                .eq(UserTokenEntity::getToken, token)
                .one();
    }

    public UserTokenEntity getUserTokenByUserId(Long userId) {
        if (null == userId) {
            return null;
        }
        return new LambdaQueryChainWrapper<>(userTokenMapper)
                .eq(UserTokenEntity::getUserId, userId)
                .one();
    }
}
