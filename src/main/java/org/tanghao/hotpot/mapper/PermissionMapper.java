package org.tanghao.hotpot.mapper;

import org.apache.ibatis.annotations.*;
import org.tanghao.hotpot.entity.Permission;
import java.util.List;

/**
 * 权限Mapper
 *
 * @author TangHao
 * @date 2022-09-20 22:33:01
 */
@Mapper
public interface PermissionMapper {
    /**
     * 获取权限列表
     *
     * @param userId 用户id
     * @return
     */
    @Results(id = "permissionList", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "code", column = "code"),
            @Result(property = "name", column = "name"),
            @Result(property = "introduce", column = "introduce"),
            @Result(property = "category", column = "category"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "creator", column = "creator"),
            @Result(property = "editTime", column = "editTime"),
            @Result(property = "editor", column = "editor"),
            @Result(property = "deleted", column = "deleted"),
    })
    @Select(" select distinct permission.id, permission.code, permission.name, permission.introduce, permission.category, permission.createTime, permission.creator, permission.editTime, permission.editor, permission.deleted from permission " +
            " join role_permission rp on rp.permission_id = permission.id " +
            " where rp.role_id in (select role.id from role join user_role ur on ur.role_id = role.id where ur.user_id = #{userId})")
    List<Permission> listPermissionByUserId(@Param("userId") Long userId);
}
