package cn.com.pism.hyacinth.security.spring.config;

import cn.com.pism.hyacinth.commons.object.bo.HcSysSourceBo;
import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import cn.com.pism.hyacinth.security.base.config.HcSecurityProperties;
import cn.com.pism.hyacinth.security.spring.service.impl.HcSecuritySpringUserDetailsServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private HcSecurityProperties hcSecurityProperties;

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
        List<String> ignorePath = hcSecurityProperties.getIgnorePath();

        http
                .csrf().disable()
                //不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .access((authenticationSupplier, requestAuthorizationContext) -> {
                    HttpServletRequest request = requestAuthorizationContext.getRequest();

                    List<String> whiteList = hcSecurityDataProvider.getWhiteList();
                    //先判断是否匹配不需要鉴权的列表
                    List<String> allIgnore = new ArrayList<>();
                    allIgnore.addAll(ignorePath);
                    allIgnore.addAll(whiteList);
                    for (String ignore : allIgnore) {
                        RequestMatcher.MatchResult matcher = AntPathRequestMatcher.antMatcher(ignore).matcher(request);
                        if (matcher.isMatch()) {
                            return new AuthorizationDecision(true);
                        }
                    }
                    //获取所有需要鉴权的资源
                    List<HcSysSourceBo> hcSysSourceBos = hcSecurityDataProvider.getAllRequireAuthSource();
                    Authentication authentication = authenticationSupplier.get();
                    for (HcSysSourceBo hcSysSourceBo : hcSysSourceBos) {
                        RequestMatcher.MatchResult matcher = AntPathRequestMatcher.antMatcher(hcSysSourceBo.getUrl()).matcher(request);
                        if (matcher.isMatch() && userHasSource(authentication, hcSysSourceBo)) {
                            return new AuthorizationDecision(true);
                        }
                    }
                    return new AuthorizationDecision(false);
                });

        return http.build();
    }

    /**
     * <p>
     * 校验登录用户是否持有权限
     * </p>
     * by PerccyKing
     *
     * @param authentication : 身份
     * @param hcSysSourceBo  : 资源
     * @return {@link boolean}
     * @since 2023/4/10 0:17
     */
    private boolean userHasSource(Authentication authentication, HcSysSourceBo hcSysSourceBo) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(hcSysSourceBo.getCode())) {
                return true;
            }
        }
        return false;
    }

}
