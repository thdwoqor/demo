package com.querypie.config;

import com.querypie.controller.LoginUserId;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    private static final String AUTHORIZATION = "Authorization";

    private static final SecurityScheme userId = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION);

    static {
        SpringDocUtils.getConfig()
                .addAnnotationsToIgnore(LoginUserId.class);
    }

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Api Documentation")
                .description("Api Documentation");

        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes(AUTHORIZATION, userId)
                )
                .info(info)
                .addServersItem(new Server().url("/"));
    }

    @Bean
    public OperationCustomizer authOperationMarker() {
        return (operation, handlerMethod) -> {
            Arrays.stream(handlerMethod.getMethodParameters())
                    .filter(methodParameter -> methodParameter.hasParameterAnnotation(LoginUserId.class))
                    .findAny()
                    .ifPresent(it -> operation.addSecurityItem(
                            new SecurityRequirement().addList(AUTHORIZATION)
                    ));
            return operation;
        };
    }

}
