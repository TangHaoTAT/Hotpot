package org.tanghao.hotpot.mapper;

import org.apache.ibatis.annotations.*;
import org.tanghao.hotpot.dto.UserInfoDto;
import org.tanghao.hotpot.entity.User;

/**
 * 用户Mapper
 *
 * @author TangHao
 * @date 2022-09-20 22:31:42
 */
@Mapper
public interface UserMapper {
    /**
     * 获取用户信息汇总
     *
     * @param openCode 登录账号
     * @return
     */
    @Results(id = "userInfoDto", value = {
            @Result(property = "user", column = "user_id", one = @One(select = "org.tanghao.hotpot.mapper.UserMapper.getUserByUserId")),
            @Result(property = "accountList", column = "user_id", many = @Many(select = "org.tanghao.hotpot.mapper.AccountMapper.listAccountByUserId")),
            @Result(property = "roleList", column = "user_id", many = @Many(select = "org.tanghao.hotpot.mapper.RoleMapper.listRoleByUserId")),
            @Result(property = "permissionList", column = "user_id", many = @Many(select = "org.tanghao.hotpot.mapper.PermissionMapper.listPermissionByUserId"))
    })
    @Select(" select user_id from account where deleted = false and open_code = #{openCode} ")
    UserInfoDto getUserInfoDtoByOpenCode(@Param("openCode") String openCode);

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return
     */
    @Results(id = "user", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "state", column = "state"),
            @Result(property = "name", column = "name"),
            @Result(property = "headImgUrl", column = "head_img_url"),
            @Result(property = "mobile", column = "mobile"),
            @Result(property = "password", column = "password"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "creator", column = "creator"),
            @Result(property = "editTime", column = "editTime"),
            @Result(property = "editor", column = "editor"),
            @Result(property = "deleted", column = "deleted")
    })
    @Select(" select id, state, name, head_img_url, mobile, password, createTime, creator, editTime, editor, deleted from user where id = #{userId} ")
    User getUserByUserId(@Param("userId") Long userId);
}
