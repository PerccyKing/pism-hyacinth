package cn.com.pism.hyacinth.security.spring.config;

import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import cn.com.pism.hyacinth.security.spring.service.impl.HcSecuritySpringUserDetailsServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author PerccyKing
 * @since 2023/3/25 18:27
 */
@Configuration
@EnableWebSecurity
public class HcSecuritySpringConfig {

    @Resource
    private HcSecurityDataProvider hcSecurityDataProvider;

    @Resource
    private HcAuthorizationManager hcAuthorizationManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return hcSecurityDataProvider.getPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new HcSecuritySpringUserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .access(hcAuthorizationManager);

        return http.build();
    }


}
