package sopt._39sopkathonserverandroid1.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("안드로이드 1조 API")
                                .description("PLAY SOPT 솝커톤 안드로이드 1조 API")
                                .version("v1")
                );
    }
}