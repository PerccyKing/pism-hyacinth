package cn.com.pism.hyacinth.cache.base.config;

import cn.com.pism.hyacinth.commons.enums.cache.HcCacheTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import static cn.com.pism.hyacinth.commons.object.constant.HcSystemConstant.HC_CACHE_DEFAULT_INSTANCE;

/**
 * @author PerccyKing
 * @since 2023/3/18 22:05
 */
@Component
@Slf4j
public class HcPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

    private String defaultCacheType;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, ConfigurablePropertyResolver propertyResolver) throws BeansException {
        //获取一次配置的缓存类型
        this.defaultCacheType = propertyResolver.getProperty("spring.hyacinth.cache.default-cache-type");
        super.processProperties(beanFactoryToProcess, propertyResolver);
    }

    @Override
    protected void doProcessProperties(ConfigurableListableBeanFactory beanFactoryToProcess, StringValueResolver valueResolver) {
        StringValueResolver resolver = strVal -> {
            if (HC_CACHE_DEFAULT_INSTANCE.equals(strVal)) {
                return HcCacheTypeEnums.valueOf(defaultCacheType.toUpperCase()).getName();
            }
            return valueResolver.resolveStringValue(strVal);
        };
        super.doProcessProperties(beanFactoryToProcess, resolver);
    }
}
