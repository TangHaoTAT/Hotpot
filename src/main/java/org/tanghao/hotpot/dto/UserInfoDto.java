package org.tanghao.hotpot.dto;

import lombok.Data;
import org.tanghao.hotpot.entity.Account;
import org.tanghao.hotpot.entity.Permission;
import org.tanghao.hotpot.entity.Role;
import org.tanghao.hotpot.entity.User;
import java.util.List;

/**
 * 用户信息汇总
 *
 * @author TangHao
 * @date 2022-09-20 20:45:11
 */
@Data
public class UserInfoDto {
    private User user;// 用户
    private List<Account> accountList;// 账号列表
    private List<Role> roleList;// 角色列表
    private List<Permission> permissionList;// 权限列表
}
