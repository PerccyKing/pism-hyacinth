package cn.com.pism.hyacinth.security.spring;

import cn.com.pism.hyacinth.commons.object.bo.HcSysLoginUserInfo;
import cn.com.pism.hyacinth.commons.object.sys.bo.HcSysLoginBo;
import cn.com.pism.hyacinth.commons.object.sys.bo.HcSysTokenBo;
import cn.com.pism.hyacinth.security.base.HcSecurityUtil;
import org.springframework.stereotype.Service;

/**
 * @author PerccyKing
 * @since 2023/4/8 21:52
 */
@Service
public class HcSecuritySpringUtil implements HcSecurityUtil {

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
        return null;
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
        return null;
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

        return null;
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

    }
}
