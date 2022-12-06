package org.bori;

import org.bori.entities.User;
import org.bori.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"org.bori.repositories"})
@EnableConfigurationProperties
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsCfg = new CorsConfiguration();
        corsCfg.applyPermitDefaultValues();
        corsCfg.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        corsCfg.setAllowCredentials(true);
        corsCfg.addAllowedOrigin("*");
        corsCfg.addAllowedMethod(HttpMethod.DELETE);
        corsCfg.addAllowedMethod(HttpMethod.PUT);
        corsCfg.addExposedHeader("Access-Control-Allow-Origin");
        source.registerCorsConfiguration("/**", corsCfg);
        return source;
    }
}
