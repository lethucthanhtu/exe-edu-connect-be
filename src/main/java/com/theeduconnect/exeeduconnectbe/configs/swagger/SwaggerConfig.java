package com.theeduconnect.exeeduconnectbe.configs.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
        SecurityRequirement securityRequirement =
                new SecurityRequirement().addList("Bearer Authentication");
        Components components =
                new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme());
        return new OpenAPI().info(info).addSecurityItem(securityRequirement).components(components);
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
