package cn.com.pism.hyacinth.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author PerccyKing
 * @since 2023/3/5 17:10
 */
@Configuration
@ComponentScan(
        basePackages = {
                "cn.com.pism.hyacinth"
        }
)
@EnableCaching
@Slf4j
public class HcConfiguration {
}
