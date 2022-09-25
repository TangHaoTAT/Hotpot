package org.tanghao.hotpot.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import java.util.Arrays;

/**
 * Swagger配置
 *
 * @author TangHao
 * @date 2022-09-19 22:46:30
 */
@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI HotpotOpenAPI() {
                // 信息
                Info info = new Info()
                        .title("Hotpot")
                        .description("Hotpot API Doc")
                        .version("0.0.1-SNAPSHOT")
                        .license(new License()
                                .name("MIT")
                                .url("https://mit-license.org/"));

                // 鉴权组件
                Components components = new Components()
                        .addSecuritySchemes("Bearer jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")// 参数名称
                                .description("JWT认证授权"));

                // 鉴权要求
                SecurityRequirement securityRequirement = new SecurityRequirement()
                        .addList("Bearer jwt", Arrays.asList("read", "write"));

                // 自定义额外接口
                ObjectSchema commonResponseSchema = new ObjectSchema();
                commonResponseSchema
                        .description("响应消息")
                        .addProperty("success", new BooleanSchema().description("请求是否成功"))
                        .addProperty("msg", new StringSchema().description("提示信息"))
                        .addProperty("code", new IntegerSchema().description("错误码"))
                        .addProperty("data", new ObjectSchema().description("请求结果"));
                MediaType commonResponseMediaType = new MediaType();
                commonResponseMediaType
                        .schema(commonResponseSchema);
                Content commonResponseContent = new Content();
                commonResponseContent
                        .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, commonResponseMediaType);
                ApiResponse commonApiResponse = new ApiResponse();
                commonApiResponse
                        .description(HttpStatus.OK.getReasonPhrase())
                        .content(commonResponseContent);

                String loginPath = "/login";// 自定义登录url
                ObjectSchema loginRequestSchema = new ObjectSchema();
                loginRequestSchema
                        .description("用户登录时的请求参数")
                        .addProperty("username", new StringSchema().description("用户名"))
                        .addProperty("password", new StringSchema().description("密码"));
                MediaType loginRequestMediaType = new MediaType();
                loginRequestMediaType
                        .schema(loginRequestSchema);
                Content loginRequestContent = new Content();
                loginRequestContent
                        .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, loginRequestMediaType);
                RequestBody loginRequestBody = new RequestBody();
                loginRequestBody
                        .content(loginRequestContent);
                ApiResponses loginApiResponses = new ApiResponses();
                loginApiResponses
                        .addApiResponse(String.valueOf(HttpStatus.OK.value()), commonApiResponse);
                PathItem loginPathItem = new PathItem();
                loginPathItem
                        .post(new Operation()
                                .summary("登录")
                                .requestBody(loginRequestBody)
                                .responses(loginApiResponses)
                                .addTagsItem("Authorization"));

                Paths paths = new Paths()
                        .addPathItem(loginPath, loginPathItem);

                return new OpenAPI()
                        .info(info)
                        .components(components)
                        .security(Arrays.asList(securityRequirement))
                        .paths(paths);
        }
}
