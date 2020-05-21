package com.fatal.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
@Getter
@Setter
public class Swagger2
{
    /**
     * API接口包路径
     */
    private String basePackage;

    /**
     * API页面标题
     */
    private String title;

    /**
     * API描述
     */
    private String description;

    /**
     * 服务条款地址
     */
    private String termsOfServiceUrl;

    /**
     * 版本号
     */
    private String version;

    /**
     * 联系人
     */
    private Contact contact;

    @Bean
    public Docket swaggerPlugin()
    {
        // List<Parameter> parameters = new ArrayList<>();
        // Parameter token = new ParameterBuilder().name("token").description("登陆令牌").modelRef(new ModelRef("string")).parameterType("header").build();
        // parameters.add(token);
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage(basePackage)).build()/*
                                                      .globalOperationParameters(parameters)*/;
    }

    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder().title(title).description(description).termsOfServiceUrl(termsOfServiceUrl).version(version).contact(contact).build();
    }
}