package com.web.domain;

import com.web.constant.YesNoEnum;
import com.web.repository.dao.UserDao;
import com.web.repository.entity.UserEntity;
import com.web.service.IdService;
import com.web.util.MD5Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final IdService idService;
    private final UserDao userDao;

    /**
     * 根据用户ID获取用户实体信息
     *
     * @param userId 用户ID
     * @return 用户实体信息
     */
    public UserEntity getUser(Long userId) {
        return userDao.findByUserIdAndUserStatus(userId, YesNoEnum.YES.getCode());
    }

    /**
     * 根据用户账户获取用户实体信息
     *
     * @param account 用户账户
     * @return 用户实体信息
     */
    public UserEntity getUser(String account) {
        return userDao.findByAccountAndUserStatus(account, YesNoEnum.YES.getCode());
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
                .userId(idService.objectId())
                .userName(account)
                .account(account)
                .password(MD5Util.saltMd5(password))
                .userStatus(YesNoEnum.YES.getCode())
                .createTime(new Timestamp(System.currentTimeMillis()))
                .updateTime(new Timestamp(System.currentTimeMillis()))
                .build();
        return userDao.save(user);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户
     */
    public UserEntity updateUser(UserEntity user) {
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return userDao.saveAndFlush(user);
    }
}
