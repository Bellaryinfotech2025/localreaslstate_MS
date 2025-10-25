package in.bellaryinfotech.Config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
    	
    	 CorsConfiguration config = new CorsConfiguration();

         // ✅ Allow all required frontend domains
         config.setAllowedOriginPatterns(Arrays.asList(
             "http://localhost:5173",    
             "http://lre.bellaryinfotech.com",
             "http://195.35.45.56:5170",
             "http://localrealestate.bellaryinfotech.com"
         ));
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/")   // allow all paths
                        .allowedOrigins("*") // allow all origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}