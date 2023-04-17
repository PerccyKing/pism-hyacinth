package cn.com.pism.hyacinth.security.spring.service.impl;

import cn.com.pism.hyacinth.security.spring.service.HcSecuritySpringUserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author PerccyKing
 * @since 2023/4/8 22:46
 */
@Service
public class HcSecuritySpringUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private HcSecuritySpringUserService hcSecuritySpringUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return hcSecuritySpringUserService.loadUserByUsername(username);
    }
}
