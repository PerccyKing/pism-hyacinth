package cn.com.pism.hyacinth.security.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author PerccyKing
 * @since 2023/3/25 18:27
 */
@Configuration
@EnableWebSecurity
public class HcSecuritySpringConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("").hasAuthority("")
                        .anyRequest().denyAll());
        return http.build();
    }
}
