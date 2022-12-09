package org.bori.config;

import lombok.*;
import org.springframework.context.annotation.Configuration;

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
