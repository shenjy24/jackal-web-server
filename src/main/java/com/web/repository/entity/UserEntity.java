package com.web.repository.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class UserEntity extends BaseEntity {
    @TableId
    private Long userId;
    private String userName;
    private String userIcon;
    private String account;
    private String password;
    private Integer userStatus;
}
