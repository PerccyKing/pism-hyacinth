package cn.com.pism.hyacinth.security.spring.config;

import cn.com.pism.hyacinth.commons.object.bo.HcSysRoleBo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysSourceBo;
import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PerccyKing
 * @since 2023/3/26 12:22
 */
@Component
public class HcSecuritySpringUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private HcSecurityDataProvider securityDataProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<HcSysRoleBo> roleBoList = securityDataProvider.getRoleListByLoginId(username);
        List<HcSysSourceBo> hcSysSourceBos = new ArrayList<>();
        roleBoList.forEach(role -> hcSysSourceBos.addAll(securityDataProvider.getSourceListByRoleId(String.valueOf(role.getId()))));
        return new User("", "", true, true, true, true, null);
    }
}
