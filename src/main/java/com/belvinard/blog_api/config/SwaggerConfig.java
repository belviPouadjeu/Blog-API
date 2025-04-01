package com.belvinard.blog_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8081}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Serveur local")
                ))
                .info(new Info()
                        .title("Task Management API")
                        .version("1.0")
                        .description("API complète pour la gestion des tâches")
                        .contact(new Contact()
                                .name("Support Technique")
                                .email("support@belvi.com")
                                .url("https://belvi.com/contact"))
                        .license(new License()
                                .name("Licence MIT")
                                .url("https://opensource.org/licenses/MIT"))
                        .termsOfService("https://belvi.com/terms"));
    }
}
