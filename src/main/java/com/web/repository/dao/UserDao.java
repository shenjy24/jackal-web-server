package com.web.repository.dao;

import com.web.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserIdAndUserStatus(long userId, int status);
    UserEntity findByAccountAndUserStatus(String account, int status);
}
