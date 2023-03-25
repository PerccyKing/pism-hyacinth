package cn.com.pism.hyacinth.security.satoken.config;

import cn.com.pism.hyacinth.commons.object.bo.HcSysSourceBo;
import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import cn.com.pism.hyacinth.security.base.config.HcSecurityProperties;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PerccyKing
 * @since 2023/3/25 18:27
 */
@Configuration
@Slf4j
public class HcSecuritySaTokenConfig implements WebMvcConfigurer {

    @Resource
    private HcSecurityDataProvider securityDataProvider;

    @Resource
    private HcSecurityProperties securityProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //忽略鉴权的路径
        List<String> ignorePath = new ArrayList<>();
        ignorePath.addAll(securityProperties.getIgnorePath());
        ignorePath.addAll(securityDataProvider.getWhiteList());
        InterceptorRegistration registration = registry.addInterceptor(new SaInterceptor(auth -> {
            SaRouter.match(ignorePath)
                    .check(router -> log.info("not require authentication path :{}", SaHolder.getRequest().getRequestPath()))
                    .stop();
            //除白名单和忽略鉴权的路径之外，需要校验一次是否登录
            SaRouter.match("/**").check(StpUtil::checkLogin);
            //获取系统中需要鉴权的路径列表
            List<HcSysSourceBo> sourceBos = securityDataProvider.getAllRequireAuthSource();
            sourceBos.forEach(source -> SaRouter.match(source.getUrl()).check(() -> StpUtil.checkPermission(source.getCode())).stop());
        })).addPathPatterns("/**");
        if (!securityProperties.isEnabled()) {
            registration.excludePathPatterns("/**");
        }
    }
}
