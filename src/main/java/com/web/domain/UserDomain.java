package com.web.domain;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.web.constant.YesNoEnum;
import com.web.repository.entity.UserEntity;
import com.web.repository.entity.UserTokenEntity;
import com.web.repository.mapper.UserMapper;
import com.web.repository.mapper.UserTokenMapper;
import com.web.util.DateUtil;
import com.web.util.MD5Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

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
        return new LambdaQueryChainWrapper<>(userMapper)
                .eq(UserEntity::getUserId, userId)
                .eq(UserEntity::getUserStatus, YesNoEnum.YES.getCode())
                .one();
    }

    /**
     * 根据用户账户获取用户实体信息
     *
     * @param account 用户账户
     * @return 用户实体信息
     */
    public UserEntity getUser(String account) {
        return new LambdaQueryChainWrapper<>(userMapper)
                .eq(UserEntity::getAccount, account)
                .eq(UserEntity::getUserStatus, YesNoEnum.YES.getCode())
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
        UserEntity user = UserEntity.builder()
                .userName(account)
                .account(account)
                .password(MD5Util.saltMd5(password))
                .userStatus(YesNoEnum.YES.getCode())
                .createTime(new Timestamp(System.currentTimeMillis()))
                .updateTime(new Timestamp(System.currentTimeMillis()))
                .build();
        userMapper.insert(user);
        return user;
    }

    /**
     * 修改用户信息
     *
     * @param user 用户
     */
    public void updateUser(UserEntity user) {
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userMapper.updateById(user);
    }

    public UserTokenEntity getUserTokenByToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new LambdaQueryChainWrapper<>(userTokenMapper)
                .eq(UserTokenEntity::getToken, token)
                .one();
    }

    public void saveUserToken(UserTokenEntity userToken) {
        if (null == userToken) {
            return;
        }
        userToken.setCreateTime(DateUtil.getCurrentTime());
        userToken.setUpdateTime(DateUtil.getCurrentTime());
        userTokenMapper.insert(userToken);
    }

    public void updateUserToken(UserTokenEntity userToken) {
        if (null == userToken || null == userToken.getTokenId()) {
            return;
        }
        userToken.setUpdateTime(DateUtil.getCurrentTime());
        userTokenMapper.updateById(userToken);
    }

    public UserTokenEntity getUserTokenByUserId(Long userId) {
        if (null == userId) {
            return null;
        }
        return new LambdaQueryChainWrapper<>(userTokenMapper)
                .eq(UserTokenEntity::getUserId, userId)
                .one();
    }

    public void removeToken(Long userId) {
        if (null == userId) {
            return;
        }
        LambdaUpdateWrapper<UserTokenEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserTokenEntity::getUserId, userId);
        wrapper.set(UserTokenEntity::getExpireTime, DateUtil.getCurrentTime());
        userTokenMapper.update(wrapper);
    }
}
