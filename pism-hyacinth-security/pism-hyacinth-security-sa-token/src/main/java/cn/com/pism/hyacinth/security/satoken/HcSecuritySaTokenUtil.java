package cn.com.pism.hyacinth.security.satoken;

import cn.com.pism.hyacinth.commons.object.bo.HcSysLoginUserInfo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysUserBo;
import cn.com.pism.hyacinth.commons.object.sys.bo.HcSysLoginBo;
import cn.com.pism.hyacinth.commons.object.sys.bo.HcSysTokenBo;
import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import cn.com.pism.hyacinth.security.base.HcSecurityUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author PerccyKing
 * @since 2023/3/26 23:24
 */
@Component
public class HcSecuritySaTokenUtil implements HcSecurityUtil {

    @Resource
    private HcSecurityDataProvider hcSecurityDataProvider;

    private static final String LOGIN_USER_INFO_KEY = "hc:security:login:user:info";

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
        Object loginUserInfo = StpUtil.getSession().get(LOGIN_USER_INFO_KEY);
        return JSON.parseObject(loginUserInfo.toString(), HcSysLoginUserInfo.class);
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
        return StpUtil.getLoginIdAsLong();
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
        PasswordEncoder passwordEncoder = hcSecurityDataProvider.getPasswordEncoder();
        HcSysUserBo sysUserBo = hcSecurityDataProvider.getSysUserByUsername(loginBo.getUsername());
        //获取密码
        String password = hcSecurityDataProvider.getPasswordByUser(HcSysUserBo.builder().username(loginBo.getUsername()).build());
        //检查密码是否匹配
        if (passwordEncoder.matches(loginBo.getPassword(), password)) {
            StpUtil.login(sysUserBo.getId());
        }
        return null;
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
        StpUtil.logout();
    }

}
