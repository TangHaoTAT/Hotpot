package org.tanghao.hotpot.entity;

import lombok.Data;
import java.util.Date;

/**
 * 用户表
 *
 * @author TangHao
 * @date 2022-09-20 17:42:53
 */
@Data
public class User {
    private Long id;// 用户id
    private Integer state;// 用户状态
    private String name;// 姓名
    private String headImgUrl;// 头像图片地址
    private String mobile;// 手机号
    private String password;// 登录密码
    /**
     * 创建时间、创建人、修改时间、修改人、逻辑删除
     */
    private Date createTime;// 创建时间
    private String creator;// 创建人
    private Date editTime;// 修改时间
    private String editor;// 修改人
    private Boolean deleted;// 逻辑删除
}
