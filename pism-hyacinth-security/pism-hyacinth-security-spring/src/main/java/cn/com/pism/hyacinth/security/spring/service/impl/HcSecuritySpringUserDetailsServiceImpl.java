package cn.com.pism.hyacinth.security.spring.service.impl;

import cn.com.pism.hyacinth.commons.object.bo.HcSysRoleBo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysSourceBo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysUserBo;
import cn.com.pism.hyacinth.exception.HcException;
import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import jakarta.annotation.Resource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PerccyKing
 * @since 2023/4/8 22:46
 */
@Service
public class HcSecuritySpringUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private HcSecurityDataProvider hcSecurityDataProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HcSysUserBo userBo = hcSecurityDataProvider.getSysUserByUsername(username);
        String password = hcSecurityDataProvider.getPasswordByUser(userBo);
        if (userBo == null) {
            throw new HcException("用户名或密码错误");
        }
        List<HcSysRoleBo> roleBoList = hcSecurityDataProvider.getRoleListByLoginId(userBo.getId());
        List<HcSysSourceBo> hcSysSourceBos = new ArrayList<>();
        roleBoList.forEach(role -> hcSysSourceBos.addAll(hcSecurityDataProvider.getSourceListByRoleId(String.valueOf(role.getId()))));
        List<SimpleGrantedAuthority> authorities = hcSysSourceBos.stream()
                .map(bo -> new SimpleGrantedAuthority(bo.getCode())).toList();
        return new User(username, password, authorities);

    }
}
