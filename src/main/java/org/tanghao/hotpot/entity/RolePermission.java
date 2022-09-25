package org.tanghao.hotpot.entity;

import lombok.Data;
import java.util.Date;

/**
 * 角色-权限表
 *
 * @author TangHao
 * @date 2022-09-20 17:58:07
 */
@Data
public class RolePermission {
    private Long id;// id
    private Long roleId;// 角色id
    private Long permissionId;// 权限id
    /**
     * 创建时间、创建人、修改时间、修改人、逻辑删除
     */
    private Date createTime;// 创建时间
    private String creator;// 创建人
    private Date editTime;// 修改时间
    private String editor;// 修改人
    private Boolean deleted;// 逻辑删除
}
