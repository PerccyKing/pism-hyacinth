package cn.com.pism.hyacinth.security.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PerccyKing
 * @since 2023/3/23 23:37
 */
@ConfigurationProperties(prefix = "spring.hyacinth.security")
@Component
@Data
public class HcSecurityProperties {

    /**
     * 忽略鉴权的路径
     */
    private List<String> ignorePath = new ArrayList<>();

    /**
     * 是否启用安全校验，关闭不会对请求进行鉴权
     */
    private boolean enabled = true;

    /**
     * token名称
     */
    private String tokenName = "hc-token";

    /**
     * token有效期 默认30天，单位秒
     */
    private Long timeout = 30 * 24 * 60 * 60L;
}
