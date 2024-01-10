package com.web.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@TableName("user_token")
public class UserTokenEntity {
    /**
     * 逻辑主键
     */
    @TableId(value = "token_id", type = IdType.ASSIGN_ID)
    private Long tokenId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    private Timestamp expireTime;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;

    public UserTokenEntity(Long userId, String token, Timestamp expireTime) {
        this.userId = userId;
        this.token = token;
        this.expireTime = expireTime;
    }
}
