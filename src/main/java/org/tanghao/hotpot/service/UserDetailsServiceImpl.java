package org.tanghao.hotpot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tanghao.hotpot.dto.UserInfoDto;
import org.tanghao.hotpot.entity.Permission;
import org.tanghao.hotpot.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义UserDetailsService
 *
 * @author TangHao
 * @date 2022-09-20 11:36:49
 */
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoDto userInfoDto = userInfoService.getUserInfoDtoByOpenCode(username);
        if (userInfoDto != null) {
            User user = userInfoDto.getUser();
            List<Permission> permissionList = userInfoDto.getPermissionList();
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (!CollectionUtils.isEmpty(permissionList)) {
                permissionList.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getCode())));
            }
            return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
        }
        return null;
    }

}
