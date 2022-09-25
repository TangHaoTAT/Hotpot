package org.tanghao.hotpot.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

/**
 * JWT工具类
 *
 * @author TangHao
 * @date 2022-09-20 12:11:34
 */
public class JWTUtil {
    public static final String TOKEN_HEADER = "Authorization";// 参数名称

    public static final String TOKEN_PREFIX = "Bearer ";// 前缀信息

    public static final long EXPIRATION = 2 * 3600L;// 过期时间7200秒,即2小时
    /**
     * 根据userId创建JWT
     *
     * @param openCode 登录账号
     * @return JWT
     */
    public static String createJWT(String openCode) {
        return Jwts
                .builder()
                .signWith(io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.HS512))
                .setSubject(openCode)
                .setIssuedAt(new Date())
                .compact();
    }

    /**
     * 获取JWT中的登录账号
     *
     * @param jwt
     * @return 登录账号
     */
    public static String getOpenCodeByJWT(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.HS512))
                .build()
                .parseClaimsJwt(jwt)
                .getBody()
                .getSubject();
    }
}
