package org.tanghao.hotpot.utils;

import org.tanghao.hotpot.vo.CommonResult;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * HttpServletResponse工具类
 *
 * @author TangHao
 * @date 2022-09-20 14:44:09
 */
public class HttpServletResponseUtil {
    public static void writeCommonResult(HttpServletResponse response, CommonResult result) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try(PrintWriter out = response.getWriter()) {
            out.write(JacksonUtil.convertObjectToJson(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
