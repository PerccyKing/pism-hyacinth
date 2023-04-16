package cn.com.pism.hyacinth.security.spring;

import cn.com.pism.hyacinth.cache.base.HcCache;
import cn.com.pism.hyacinth.commons.object.bo.HcSysLoginUserInfo;
import cn.com.pism.hyacinth.commons.object.sys.bo.HcSysLoginBo;
import cn.com.pism.hyacinth.commons.object.sys.bo.HcSysTokenBo;
import cn.com.pism.hyacinth.commons.util.HcTokenGenerateProvider;
import cn.com.pism.hyacinth.exception.HcException;
import cn.com.pism.hyacinth.security.base.HcSecurityUtil;
import cn.com.pism.hyacinth.security.base.config.HcSecurityProperties;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import static cn.com.pism.hyacinth.commons.object.constant.HcCacheKeyConstant.LOGIN_TOKEN_KEY;
import static cn.com.pism.hyacinth.commons.object.constant.HcCacheKeyConstant.LOGIN_USER_INFO_KEY;
import static cn.com.pism.hyacinth.commons.object.constant.HcSystemConstant.HC_CACHE_DEFAULT_INSTANCE;

/**
 * @author PerccyKing
 * @since 2023/4/8 21:52
 */
@Service
public class HcSecuritySpringUtil implements HcSecurityUtil {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private HcTokenGenerateProvider hcTokenGenerateProvider;

    @Resource
    private HcSecurityProperties hcSecurityProperties;

    @Resource(name = HC_CACHE_DEFAULT_INSTANCE)
    private HcCache hcCache;

    /**
     * <p>
     * 获取登录用户信息
     * </p>
     * by PerccyKing
     *
     * @return {@link HcSysLoginUserInfo} 登录用户信息
     * @since 2023/3/25 17:55
     */
    @Override
    public HcSysLoginUserInfo getLoginUserInfo() {
        String res = hcCache.get(String.format(LOGIN_USER_INFO_KEY, getLoginUserId()));
        return JSON.parseObject(res, HcSysLoginUserInfo.class);
    }

    /**
     * <p>
     * 获取登录用户id
     * </p>
     * by PerccyKing
     *
     * @return {@link Long} 登录用户id
     * @since 2023/3/25 17:56
     */
    @Override
    public Long getLoginUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * <p>
     * 获取登录用户的用户名
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 登录用户用户名
     * @since 2023/3/25 17:57
     */
    @Override
    public String getLoginUserUsername() {
        return getLoginUserInfo().getUsername();
    }

    /**
     * <p>
     * 用户登录
     * </p>
     * by PerccyKing
     *
     * @param loginBo : 登录参数
     * @return {@link HcSysTokenBo} token 信息
     * @since 2023/3/26 21:47
     */
    @Override
    public HcSysTokenBo login(HcSysLoginBo loginBo) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginBo.getUsername(), loginBo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (authenticate.isAuthenticated()) {
            HcSysTokenBo hcSysTokenBo = new HcSysTokenBo();
            hcSysTokenBo.setDuration(String.valueOf(hcSecurityProperties.getTimeout()));
            hcSysTokenBo.setName(hcSecurityProperties.getTokenName());
            hcSysTokenBo.setToken(hcTokenGenerateProvider.generateToken(authenticate.getName()));
            //缓存token信息
            HcSysLoginUserInfo loginUserInfo = new HcSysLoginUserInfo();
            Long loginUserId = Long.valueOf(((User) authenticate.getPrincipal()).getUsername());
            loginUserInfo.setId(loginUserId);
            loginUserInfo.setUsername(loginBo.getUsername());
            hcCache.setEx(String.format(LOGIN_USER_INFO_KEY, loginUserId), JSON.toJSONString(loginUserInfo), hcSecurityProperties.getTimeout());
            //缓存token
            hcCache.setEx(String.format(LOGIN_TOKEN_KEY, hcSecurityProperties.getTokenName(), hcSysTokenBo.getToken()),
                    String.valueOf(loginUserInfo.getId()), hcSecurityProperties.getTimeout());
            return hcSysTokenBo;
        }
        throw new HcException("用户名或密码错误");
    }

    /**
     * <p>
     * 退出登录
     * </p>
     * by PerccyKing
     *
     * @since 2023/4/8 18:02
     */
    @Override
    public void logout() {
        //删除登录缓存,让token过期
    }
}
