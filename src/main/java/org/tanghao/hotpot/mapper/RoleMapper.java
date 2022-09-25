package org.tanghao.hotpot.mapper;

import org.apache.ibatis.annotations.*;
import org.tanghao.hotpot.entity.Role;
import java.util.List;

/**
 * 角色Mapper
 *
 * @author TangHao
 * @date 2022-09-20 22:32:43
 */
@Mapper
public interface RoleMapper {
    /**
     * 获取角色列表
     *
     * @param userId 用户id
     * @return
     */
    @Results(id = "roleList", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "code", column = "code"),
            @Result(property = "name", column = "name"),
            @Result(property = "introduce", column = "introduce"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "creator", column = "creator"),
            @Result(property = "editTime", column = "editTime"),
            @Result(property = "editor", column = "editor"),
            @Result(property = "deleted", column = "deleted"),
    })
    @Select(" select role.id, role.code, role.name, role.introduce, role.createTime, role.creator, role.editTime, role.editor, role.deleted from role " +
            " join user_role ur on ur.role_id = role.id " +
            " where ur.user_id = #{userId} ")
    List<Role> listRoleByUserId(@Param("userId") Long userId);
}
