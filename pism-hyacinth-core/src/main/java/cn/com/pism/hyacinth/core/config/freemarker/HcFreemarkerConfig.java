package cn.com.pism.hyacinth.core.config.freemarker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


/**
 * @author PerccyKing
 * @since 2023/3/5 17:36
 */
@Configuration
public class HcFreemarkerConfig {

    @Bean("HC_FreeMarkerConfigurer")
    public FreeMarkerConfigurer freeMarker() {
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
        config.setDefaultEncoding("UTF-8");
        config.setTemplateLoaderPath("classpath:/templates");
        return config;
    }

}
