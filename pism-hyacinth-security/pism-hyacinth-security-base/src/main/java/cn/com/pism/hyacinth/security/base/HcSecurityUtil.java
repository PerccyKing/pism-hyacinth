package cn.com.pism.hyacinth.security.base;

import cn.com.pism.hyacinth.commons.object.bo.HcSysLoginUserInfo;
import org.springframework.stereotype.Component;

/**
 * @author PerccyKing
 * @since 2023/3/25 17:49
 */
@Component
public interface HcSecurityUtil {

    /**
     * <p>
     * 获取登录用户信息
     * </p>
     * by PerccyKing
     *
     * @return {@link HcSysLoginUserInfo} 登录用户信息
     * @since 2023/3/25 17:55
     */
    HcSysLoginUserInfo getLoginUserInfo();

    /**
     * <p>
     * 获取登录用户id
     * </p>
     * by PerccyKing
     *
     * @return {@link Long} 登录用户id
     * @since 2023/3/25 17:56
     */
    Long getLoginUserId();

    /**
     * <p>
     * 获取登录用户的用户名
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 登录用户用户名
     * @since 2023/3/25 17:57
     */
    String getLoginUserUsername();
}
