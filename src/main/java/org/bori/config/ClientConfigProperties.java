package org.bori.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@PropertySource("classpath:client_config.txt")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClientConfigProperties {
//    @Value("${CLIENT_URL}")
    private String clientUrl = "http://localhost:3000";
}
