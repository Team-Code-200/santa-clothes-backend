package io.wisoft.capstonedesign.global.config;

import io.wisoft.capstonedesign.global.interceptor.BearerAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final BearerAuthInterceptor bearerAuthInterceptor;

    public WebMvcConfig(BearerAuthInterceptor bearerAuthInterceptor) {
        this.bearerAuthInterceptor = bearerAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(bearerAuthInterceptor)
                .addPathPatterns("/api/users/**")

                .addPathPatterns("/api/donates/new")
                .addPathPatterns("/api/donates/{id}")
                .excludePathPatterns("/api/donates/top")
                .excludePathPatterns("/api/donates/pants")
                .excludePathPatterns("/api/donates/shoes")
                .excludePathPatterns("/api/donates/etc")

                .addPathPatterns("/api/finds/new")
                .addPathPatterns("/api/find/{id}")
                .excludePathPatterns("/api/finds/top")
                .excludePathPatterns("/api/finds/pants")
                .excludePathPatterns("/api/finds/shoes")
                .excludePathPatterns("/api/finds/etc")

                .addPathPatterns("/api/informations/**")

                .addPathPatterns("/api/shops/**")
                .excludePathPatterns("/api/shops/details/{id}")
                .excludePathPatterns("/api/shops")

                .addPathPatterns("/api/donate-orders/**")

                .addPathPatterns("/api/find-orders/**")

                .addPathPatterns("/api/shop-orders/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
