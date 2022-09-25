package org.tanghao.hotpot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tanghao.hotpot.dto.UserInfoDto;
import org.tanghao.hotpot.mapper.AccountMapper;
import org.tanghao.hotpot.mapper.PermissionMapper;
import org.tanghao.hotpot.mapper.RoleMapper;
import org.tanghao.hotpot.mapper.UserMapper;

/**
 * 用户信息Service
 *
 * @author TangHao
 * @date 2022-09-20 21:51:49
 */
@Service
public class UserInfoService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 获取用户信息汇总
     *
     * @param openCode 登录账号
     * @return
     */
    public UserInfoDto getUserInfoDtoByOpenCode(String openCode) {
        return userMapper.getUserInfoDtoByOpenCode(openCode);
    }
}
