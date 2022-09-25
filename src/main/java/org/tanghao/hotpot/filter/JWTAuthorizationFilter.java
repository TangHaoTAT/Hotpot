package org.tanghao.hotpot.filter;

import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.CollectionUtils;
import org.tanghao.hotpot.cache.IGlobalCache;
import org.tanghao.hotpot.dto.UserInfoDto;
import org.tanghao.hotpot.entity.Permission;
import org.tanghao.hotpot.service.UserInfoService;
import org.tanghao.hotpot.utils.HttpServletResponseUtil;
import org.tanghao.hotpot.utils.JWTUtil;
import org.tanghao.hotpot.utils.JacksonUtil;
import org.tanghao.hotpot.utils.SpringContextHolder;
import org.tanghao.hotpot.vo.CommonResult;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 过滤器-处理所有http请求,并检查是否携带JWT
 *
 * @author TangHao
 * @date 2022-09-20 11:43:13
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 检查http请求头中是否带有正确的Authorization标头
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String customToken = request.getHeader(JWTUtil.TOKEN_HEADER);
        // 如果http请求头中没有Authorization标头则直接放行
        if (customToken == null || !customToken.startsWith(JWTUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            // 获取customToken在redis中的数据
            IGlobalCache globalCache = SpringContextHolder.getBean(IGlobalCache.class);
            String jsonData = (String) globalCache.get(customToken);
            if (jsonData == null) {
                throw new JwtException("token无效或token已过期");
            }
            // 重新设置过期时间
            globalCache.set(customToken, jsonData, JWTUtil.EXPIRATION);
            // 解析数据并将登录成功的用户信息添加至SecurityContextHolder.getContext()中
            Map<String, Object> data = JacksonUtil.convertJsonToMap(jsonData, String.class, Object.class);
            String openCode = (String) data.get("openCode");
            UserInfoService userInfoService = SpringContextHolder.getBean(UserInfoService.class);
            UserInfoDto userInfoDto = userInfoService.getUserInfoDtoByOpenCode(openCode);
            List<Permission> permissionList = userInfoDto.getPermissionList();
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (!CollectionUtils.isEmpty(permissionList)) {
                permissionList.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getCode())));
            }
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(openCode, null, authorities));
        }catch (JwtException e) {
            HttpServletResponseUtil.writeCommonResult(response, CommonResult.of(false, e.getMessage(), 200, null));
            return;
        }
        super.doFilterInternal(request, response, chain);
    }
}
