package org.tanghao.hotpot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.tanghao.hotpot.cache.IGlobalCache;
import org.tanghao.hotpot.filter.JWTAuthenticationFilter;
import org.tanghao.hotpot.filter.JWTAuthorizationFilter;
import org.tanghao.hotpot.utils.HttpServletResponseUtil;
import org.tanghao.hotpot.utils.JWTUtil;
import org.tanghao.hotpot.vo.CommonResult;

/**
 * Security配置
 *
 * @author TangHao
 * @date 2022-09-19 23:42:27
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IGlobalCache globalCache;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()// 开启跨域
                .and()
                .csrf().disable()// 禁用CSRF
                .authorizeRequests()
                .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()// 放行swagger
                .antMatchers("/openApi/**").permitAll()
                .anyRequest().authenticated()// 所有http请求需要通过security认证授权才能访问
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))// 自定义登录拦截方式
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))// 自定义请求拦截方式
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 不需要session
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {// 匿名用户访问无权限资源时的异常异常处理
                    HttpServletResponseUtil.writeCommonResult(response, CommonResult.of(false, "没有访问权限", 200, null));
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {// 认证过的用户访问无权限资源时的异常处理
                    HttpServletResponseUtil.writeCommonResult(response, CommonResult.of(false, "没有访问权限", 200, null));
                })
                .and()
                .logout().logoutSuccessHandler((request, response, authentication) -> {// 自定义退出登录处理;
                    String jwt = request.getHeader(JWTUtil.TOKEN_HEADER);
                    globalCache.del(jwt);
                    HttpServletResponseUtil.writeCommonResult(response, CommonResult.of(true, "退出登录", 200, null));
                });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }
}
