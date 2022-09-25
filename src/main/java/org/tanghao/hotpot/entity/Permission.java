package org.tanghao.hotpot.entity;

import lombok.Data;
import java.util.Date;

/**
 * 权限表
 *
 * @author TangHao
 * @date 2022-09-20 17:49:10
 */
@Data
public class Permission {
    private Long id;// 权限id
    private String code;// 权限代码
    private String name;// 权限名称
    private String introduce;// 权限介绍
    private Integer category;// 权限类别
    /**
     * 创建时间、创建人、修改时间、修改人、逻辑删除
     */
    private Date createTime;// 创建时间
    private String creator;// 创建人
    private Date editTime;// 修改时间
    private String editor;// 修改人
    private Boolean deleted;// 逻辑删除
}
