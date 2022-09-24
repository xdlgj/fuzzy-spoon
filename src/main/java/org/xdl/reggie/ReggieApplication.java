package org.xdl.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
@ServletComponentScan // 使过滤器生效
public class ReggieApplication {
    public static void main(String[] args) {
        ApplicationContext ctx =  SpringApplication.run(ReggieApplication.class, args);
        log.info("项目启动完成...");
        String[] beans = ctx.getBeanDefinitionNames();
        int count = 0;
        for (String bean : beans) {
            count += 1;
            log.info("容器中的bean{}：{}", count, bean);
        }
    }

}
