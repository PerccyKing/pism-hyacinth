package cn.com.pism.hyacinth.security.base.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author PerccyKing
 * @since 2023/4/11 14:04
 */
@Component
public class HcSecurityPropertiesPostProcessor implements BeanPostProcessor {

    private static final List<String> IGNORE_PATH = Arrays.asList("/security/**");

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof HcSecurityProperties hcSecurityProperties) {
            List<String> configIgnorePath = hcSecurityProperties.getIgnorePath();
            if (CollectionUtils.isEmpty(configIgnorePath)) {
                configIgnorePath = new ArrayList<>();
            }
            configIgnorePath.addAll(IGNORE_PATH);
            hcSecurityProperties.setIgnorePath(configIgnorePath);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
