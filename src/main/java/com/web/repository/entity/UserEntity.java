package com.web.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class UserEntity {
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;
    private String userName;
    private String userIcon;
    private String account;
    private String password;
    private Integer userStatus;
    private Timestamp createTime;
    private Timestamp updateTime;
}
