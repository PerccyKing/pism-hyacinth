package cn.com.pism.hyacinth.security.spring.config;

import cn.com.pism.hyacinth.cache.base.HcCache;
import cn.com.pism.hyacinth.commons.object.bo.HcSysLoginUserInfo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysSourceBo;
import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import cn.com.pism.hyacinth.security.base.config.HcSecurityProperties;
import cn.com.pism.hyacinth.security.spring.service.HcSecuritySpringUserService;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static cn.com.pism.hyacinth.commons.object.constant.HcCacheKeyConstant.LOGIN_TOKEN_KEY;
import static cn.com.pism.hyacinth.commons.object.constant.HcCacheKeyConstant.LOGIN_USER_INFO_KEY;
import static cn.com.pism.hyacinth.commons.object.constant.HcSystemConstant.HC_CACHE_DEFAULT_INSTANCE;

/**
 * @author PerccyKing
 * @since 2023/4/17 22:16
 */
@Component
public class HcAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Resource
    private HcSecurityDataProvider hcSecurityDataProvider;

    @Resource
    private HcSecurityProperties hcSecurityProperties;

    @Resource(name = HC_CACHE_DEFAULT_INSTANCE)
    private HcCache hcCache;

    @Resource
    private HcSecuritySpringUserService hcSecuritySpringUserService;


    @Nullable
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext requestAuthorizationContext) {
        HttpServletRequest request = requestAuthorizationContext.getRequest();

        List<String> ignorePath = hcSecurityProperties.getIgnorePath();
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
        //从header、url参数 中获取token信息
        String token = request.getHeader(hcSecurityProperties.getTokenName());
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(hcSecurityProperties.getTokenName());
        }
        if (StringUtils.isBlank(token)) {
            return new AuthorizationDecision(false);
        }
        //使用token 获取登录id
        String loginId = hcCache.get(String.format(LOGIN_TOKEN_KEY, hcSecurityProperties.getTokenName(), token));
        //获取用户信息
        String loginUserInfoStr = hcCache.get(String.format(LOGIN_USER_INFO_KEY, loginId));
        HcSysLoginUserInfo hcSysLoginUserInfo = JSON.parseObject(loginUserInfoStr, HcSysLoginUserInfo.class);
        UserDetails userDetails = hcSecuritySpringUserService.loadUserByUsername(hcSysLoginUserInfo.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginId, null, userDetails.getAuthorities());
        return validateSysSource(request, hcSysSourceBos, authentication);
    }

    /**
     * <p>
     * 验证系统资源
     * </p>
     * by PerccyKing
     *
     * @param request        : 请求参数
     * @param hcSysSourceBos : 系统资源
     * @param authentication : 携带权限的认证对象
     * @return {@link AuthorizationDecision}
     * @since 2023/4/17 22:20
     */
    private AuthorizationDecision validateSysSource(HttpServletRequest request,
                                                    List<HcSysSourceBo> hcSysSourceBos,
                                                    UsernamePasswordAuthenticationToken authentication) {
        for (HcSysSourceBo hcSysSourceBo : hcSysSourceBos) {
            RequestMatcher.MatchResult matcher = AntPathRequestMatcher.antMatcher(hcSysSourceBo.getUrl()).matcher(request);
            if (matcher.isMatch()) {
                if (userHasSource(authentication, hcSysSourceBo)) {
                    return new AuthorizationDecision(true);
                } else {
                    return new AuthorizationDecision(false);
                }
            }
        }
        return new AuthorizationDecision(true);
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
