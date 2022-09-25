package org.tanghao.hotpot.entity;

import lombok.Data;
import java.util.Date;

/**
 * 账号表
 *
 * @author TangHao
 * @date 2022-09-20 15:50:23
 */
@Data
public class Account {
    private Long id;// 账号id
    private Long userId;// 用户id
    private String openCode;// 登录账号
    private Integer category;// 账号类别
    /**
     * 创建时间、创建人、修改时间、修改人、逻辑删除
     */
    private Date createTime;// 创建时间
    private String creator;// 创建人
    private Date editTime;// 修改时间
    private String editor;// 修改人
    private Boolean deleted;// 逻辑删除
}
