package com.spring.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Predicates;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    // OpenAPI bean을 등록하여 Swagger UI에 적용되는 문서화 설정을 추가
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("게하로가연 API Documentation")
                        .version("v1")
                        .description("삼성SDS 25(상) 신입사원 입문교육 프로젝트"));
    }
    
    
 
    
}
