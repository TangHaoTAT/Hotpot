package org.tanghao.hotpot.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * API返回信息封装
 *
 * @author TangHao
 * @date 2022-09-19 22:14:24
 */
@Getter
@Schema(description = "响应消息")
public class CommonResult {
    @Schema(description = "请求是否成功")
    private Boolean success;

    @Schema(description = "提示信息")
    private String msg;

    @Schema(description = "错误码")
    private Integer code;

    @Schema(description = "请求结果")
    private Object data;

    private CommonResult(Boolean success, String msg, Integer code, Object data) {
        this.success = success;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    /**
     * 请求成功
     *
     * @return 响应消息
     */
    public static CommonResult success(){
        return new CommonResult(true, "请求成功", 200, null);
    }

    /**
     * 请求成功
     *
     * @param data 请求结果
     * @return 响应消息
     */
    public static CommonResult success(Object data){
        return new CommonResult(true, "请求成功", 200, data);
    }

    /**
     * 获取响应消息
     *
     * @param success 请求是否成功
     * @param msg 提示信息
     * @param code 错误码
     * @param data 请求结果
     * @return 响应消息
     */
    public static CommonResult of(Boolean success, String msg, Integer code, Object data){
        return new CommonResult(success, msg, code, data);
    }

}
