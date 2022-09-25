package org.tanghao.hotpot.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.tanghao.hotpot.cache.IGlobalCache;
import org.tanghao.hotpot.utils.HttpServletResponseUtil;
import org.tanghao.hotpot.utils.JWTUtil;
import org.tanghao.hotpot.utils.JacksonUtil;
import org.tanghao.hotpot.utils.SpringContextHolder;
import org.tanghao.hotpot.vo.CommonResult;
import org.tanghao.hotpot.vo.LoginInfoVo;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 过滤器-处理/login登录接口
 *
 * @author TangHao
 * @date 2022-09-20 11:40:45
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    /**
     * 自定义登录url
     *
     * @param authenticationManager
     */
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/login");
    }

    /**
     * 第一步: 获取用户参数，并进行校验
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginInfoVo loginInfoVo = JacksonUtil.getObjectByInputStream(request.getInputStream(), LoginInfoVo.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInfoVo.getUsername(), loginInfoVo.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 第二步: 校验成功,通过authResult.getPrincipal()获取自定义UserDetailsService中返回的UserDetails对象,并生成JWT
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 获取UserDetails
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String openCode = user.getUsername();
        // 创建JWT
        String jwt = JWTUtil.createJWT(openCode);
        // 创建customToken
        String customToken = JWTUtil.TOKEN_PREFIX + jwt;
        // 将customToken相关数据添加至redis中
        Map<String, Object> data = new HashMap<>();
        data.put("openCode", openCode);
        IGlobalCache globalCache = SpringContextHolder.getBean(IGlobalCache.class);
        globalCache.set(customToken, JacksonUtil.convertObjectToJson(data), JWTUtil.EXPIRATION);
        // 将customToken添加至response中
        Map<String, Object> result = new HashMap<>();
        result.put(JWTUtil.TOKEN_HEADER, customToken);
        HttpServletResponseUtil.writeCommonResult(response, CommonResult.of(true, "登录成功", 200, result));
    }

    /**
     * 第二步: 校验失败
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        HttpServletResponseUtil.writeCommonResult(response, CommonResult.of(false, "用户名或密码错误", 200, null));
    }
}
