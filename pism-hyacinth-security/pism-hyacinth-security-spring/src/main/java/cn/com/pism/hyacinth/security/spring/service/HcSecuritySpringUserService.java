package cn.com.pism.hyacinth.security.spring.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author PerccyKing
 * @since 2023/4/18 0:46
 */
public interface HcSecuritySpringUserService {

    /**
     * <p>
     * 根据用户名加载用户xixni
     * </p>
     * by PerccyKing
     *
     * @param username : 用户名
     * @return {@link UserDetails} 用户信息
     * @since 2023/4/18 0:48
     */
    UserDetails loadUserByUsername(String username);
}
