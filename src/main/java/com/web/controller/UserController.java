package com.web.controller;

import com.web.config.access.Anonymous;
import com.web.config.access.UserId;
import com.web.repository.entity.UserEntity;
import com.web.repository.qo.UserQo;
import com.web.repository.vo.UserVo;
import com.web.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户前端控制器
 *
 * @author shenjy
 * @time 2023/11/7 10:43
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * 用户注册
     *
     * @param req 请求参数
     * @return 用户信息
     */
    @Anonymous
    @PostMapping("/register")
    public UserVo register(@Valid @RequestBody UserQo req) {
        UserEntity user = userService.register(req.getAccount(), req.getPassword());
        return this.toUserView(user);
    }

    /**
     * 用户登录
     *
     * @param req 请求参数
     * @return 用户信息
     */
    @Anonymous
    @PostMapping("/login")
    public UserVo login(@Valid @RequestBody UserQo req) {
        UserEntity user = userService.login(req.getAccount(), req.getPassword());
        return this.toUserView(user);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logoff")
    public void logoff(@UserId Long userId) {
        userService.logoff(userId);
    }

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     */
    @PostMapping("/updatePassword")
    public void updatePassword(@UserId Long userId,
                               @RequestParam String newPassword,
                               @RequestParam String oldPassword) {
        userService.updatePassword(userId, newPassword, oldPassword);
        userService.logoff(userId);
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @PostMapping("/getUser")
    public UserVo getUser(@UserId Long userId) {
        UserEntity user = userService.getUser(userId);
        return this.toUserView(user);
    }

    /**
     * 转换成返回结构
     *
     * @param user 用户实体
     * @return 用户返回结构
     */
    private UserVo toUserView(UserEntity user) {
        if (null == user) {
            return new UserVo();
        }
        return new UserVo()
                .setUserId(user.getUserId())
                .setUserName(user.getUserName())
                .setUserIcon(user.getUserIcon())
                .setAccount(user.getAccount());
    }
}
