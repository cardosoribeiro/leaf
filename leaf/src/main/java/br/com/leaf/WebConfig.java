package br.com.leaf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/webjars/**")
        //        .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //registry.addResourceHandler("/webjars/**")
        //        .addResourceLocations("/webjars/"); // Note the leading slash
        //registry.addResourceHandler("/static/**")
        //        .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");

        registry.addResourceHandler("/static/**") // Correct path for static resources
                .addResourceLocations("classpath:/static/"); // classpath:/static/

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/"); // classpath:/static/css/

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/"); // classpath:/static/js/

        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/"); // classpath:/static/images/

    }
}