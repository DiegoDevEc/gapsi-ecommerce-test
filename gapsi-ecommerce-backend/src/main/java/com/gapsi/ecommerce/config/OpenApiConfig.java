package com.gapsi.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GAPSI E-Commerce API")
                        .version("1.0.0")
                        .description("API REST para gestión de proveedores")
                        .contact(new Contact()
                                .name("GAPSI")
                                .email("contact@gapsi.com")));
    }
}
