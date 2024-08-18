package org.c4marathon.assignment.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/thumbnails/**")           // 해당 경로의 요청이 올 때
                .addResourceLocations("file:src/main/resources/upload/thumbnails/") // classpath 기준으로 'src/main/resources/upload/thumbnails' 디렉토리 밑에서 제공
                .setCachePeriod(0);                   // 캐싱 지정
    }
}