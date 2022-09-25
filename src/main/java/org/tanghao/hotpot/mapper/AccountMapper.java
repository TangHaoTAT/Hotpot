package org.tanghao.hotpot.mapper;

import org.apache.ibatis.annotations.*;
import org.tanghao.hotpot.entity.Account;
import java.util.List;

/**
 * 账号Mapper
 *
 * @author TangHao
 * @date 2022-09-20 22:32:14
 */
@Mapper
public interface AccountMapper {
    /**
     * 获取账号列表
     *
     * @param userId 用户id
     * @return
     */
    @Results(id = "accountList", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "openCode", column = "open_code"),
            @Result(property = "category", column = "category"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "creator", column = "creator"),
            @Result(property = "editTime", column = "editTime"),
            @Result(property = "editor", column = "editor"),
            @Result(property = "deleted", column = "deleted"),
    })
    @Select(" select id, user_id, open_code, category, createTime, creator, editTime, editor, deleted from account where user_id = #{userId} ")
    List<Account> listAccountByUserId(@Param("userId") Long userId);
}
