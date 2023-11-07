package com.web.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class UserEntity {
    @Id
    private Long userId;
    private String userName;
    private String userIcon;
    private String account;
    private String password;
    private Integer userStatus;
    private Timestamp createTime;
    private Timestamp updateTime;
}
