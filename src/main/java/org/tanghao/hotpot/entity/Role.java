package org.tanghao.hotpot.entity;

import lombok.Data;
import java.util.Date;

/**
 * 角色表
 *
 * @author TangHao
 * @date 2022-09-20 17:52:00
 */
@Data
public class Role {
    private Long id;// 角色id
    private String code;// 角色代码
    private String name;// 角色名称
    private String introduce;// 角色介绍
    /**
     * 创建时间、创建人、修改时间、修改人、逻辑删除
     */
    private Date createTime;// 创建时间
    private String creator;// 创建人
    private Date editTime;// 修改时间
    private String editor;// 修改人
    private Boolean deleted;// 逻辑删除
}
