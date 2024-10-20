package is.hi.hbv501g.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableSpringDataWebSupport
public class WebConfig {
    // Additional configuration can go here if needed
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/**")
    //         .allowedOrigins("http://localhost:3000") // Allow requests from your Next.js app
    //         .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Define allowed HTTP methods
    //         .allowedHeaders("*") // Allow all headers
    //         .allowCredentials(true); // If you need to allow cookies or credentials
    // }
}