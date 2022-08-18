package com.hutu.hutunote.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean
    public Docket docket() {
        // 添加接口请求头参数配置 没有的话 可以忽略
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        /*tokenPar.name("token")
                .description("令牌")
                .defaultValue("")
                .modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();
        pars.add(tokenPar.build());*/
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Note")
                .description("Note文档")
                .termsOfServiceUrl("http://localhost:8080")
                .version("1.0")
                .build();

    }
}
