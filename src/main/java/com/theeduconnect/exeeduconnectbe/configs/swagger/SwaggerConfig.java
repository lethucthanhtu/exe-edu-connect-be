package com.theeduconnect.exeeduconnectbe.configs.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Info info =
                new Info()
                        .title("EduConnect_API")
                        .version("1.0")
                        .description("Contains a list of endpoints with descriptions.");
        return new OpenAPI().info(info);
    }
}
