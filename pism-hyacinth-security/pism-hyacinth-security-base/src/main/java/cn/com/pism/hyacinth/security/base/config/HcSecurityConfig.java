package cn.com.pism.hyacinth.security.base.config;

import cn.com.pism.hyacinth.commons.util.HcTokenGenerateProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author PerccyKing
 * @since 2023/3/27 23:07
 */
@Configuration
public class HcSecurityConfig {

    @Bean
    @ConditionalOnMissingBean(HcTokenGenerateProvider.class)
    public HcTokenGenerateProvider hcTokenGenerateProvider() {
        return loginId -> UUID.randomUUID().toString();
    }
}
