package com.web.repository.qo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * UpdateUserQo
 *
 * @author shenjy
 * @time 2024/1/10 17:06
 */
@Data
public class UpdatePasswordQo {
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
}
