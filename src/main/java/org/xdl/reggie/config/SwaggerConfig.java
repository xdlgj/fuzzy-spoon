package org.xdl.reggie.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration  // 表示这是一个配置类
@EnableSwagger2 // 开启swagger2
@EnableKnife4j
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("测试分组");
    }

    // 配置swagger的Docket的bean实例
    @Bean
    public Docket createApiDocket(Environment environment) {
        //设置swagger生效的环境
        Profiles profile = Profiles.of("dev");
        // 获取项目环境
        boolean enable = environment.acceptsProfiles(profile);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enable) // 是否启用swagger
                .select()
                // 配置要扫描接口的方式
                // basePackage：指定要扫描的包
                // none：都不扫描
                // any: 全部扫描默认值
                // withClassAnnotation: 扫描类上的注解
                // withMethodAnnotation: 扫描方法上的注解 GetMapping.class表示只扫描get方法
                .apis(RequestHandlerSelectors.basePackage("org.xdl.reggie.controller"))
                // 过滤请求路径
                .paths(PathSelectors.ant("/**"))
                .build();
    }
    // 作者信息
    private Contact contact = new Contact("xdl", "https://www.cnblogs.com/xdl-smile/p/11110531.html", "928645139@qq.com");
    //配置swagger头部信息
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "瑞吉外卖",  //标题
                "瑞吉外卖接口文档", //描述
                "v1.0", // 版本
                "https://note.youdao.com/web/#/file/WEB2243a345f9d2407ff2f227605df2357e/note/WEB3b56abcdeb6e646c0daad97c2eb07c94/", // 官网
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );

    }


}
