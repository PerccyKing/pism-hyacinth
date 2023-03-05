package cn.com.pism.hyacinth.core.config.freemarker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static cn.com.pism.hyacinth.commons.util.FreeMarkerUtil.createConfiguration;


/**
 * @author PerccyKing
 * @since 2023/3/5 17:36
 */
@Configuration
public class HcFreemarkerConfig {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Bean
    public freemarker.template.Configuration freeMarkerConfiguration() throws IOException {
        freemarker.template.Configuration configuration = createConfiguration();
        Resource resource = resourceLoader.getResource("classpath:/templates");
        configuration.setDirectoryForTemplateLoading(resource.getFile());
        return configuration;
    }
}
