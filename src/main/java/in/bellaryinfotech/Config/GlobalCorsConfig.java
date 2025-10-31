package in.bellaryinfotech.Config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/") // Apply CORS to all endpoints
                        .allowedOriginPatterns(String.valueOf(Arrays.asList(
                                "http://localhost:5170",                       // ✅ Local dev
                                "https://localrealestate.bellaryinfotech.com", // ✅ Production frontend
                                "https://bellaryinfotech.com"                  // (Optional main site)
                        )))
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
} 