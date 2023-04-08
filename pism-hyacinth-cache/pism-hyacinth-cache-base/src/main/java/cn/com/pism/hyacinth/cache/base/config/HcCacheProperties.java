package cn.com.pism.hyacinth.cache.base.config;

import cn.com.pism.hyacinth.commons.enums.cache.HcCacheTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author PerccyKing
 * @since 2023/3/18 17:07
 */
@ConfigurationProperties(prefix = "spring.hyacinth.cache")
@Component
public class HcCacheProperties {

    /**
     * 默认缓存类型
     */
    @Getter
    @Setter
    private HcCacheTypeEnum defaultCacheType;

    /**
     * <p>
     * 获取默认缓存实例
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 默认缓存实例
     * @since 2023/3/18 17:38
     */
    public String getDefaultInstance() {
        return defaultCacheType.getName();
    }
}
