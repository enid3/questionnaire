package com.github.enid3.questionnaire.conf;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                        "/fonts/**",
                        "/css/**",
                        "/js/**")
                .addResourceLocations(
                        "classpath:/static/fonts/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:7070")
                .allowedHeaders("*")
                .allowedMethods("GET", "PUT", "DELETE", "POST");
    }


}
