package org.tanghao.hotpot.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户登录时的请求参数
 *
 * @author TangHao
 * @date 2022-09-21 23:17:29
 */
@Data
@Schema
public class LoginInfoVo {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;
}
