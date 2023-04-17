package cn.com.pism.hyacinth.security.spring.service.impl;

import cn.com.pism.hyacinth.commons.object.bo.HcSysRoleBo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysSourceBo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysUserBo;
import cn.com.pism.hyacinth.exception.HcException;
import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import cn.com.pism.hyacinth.security.spring.service.HcSecuritySpringUserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PerccyKing
 * @since 2023/4/18 0:47
 */
@Service
public class HcSecuritySpringUserServiceImpl implements HcSecuritySpringUserService {

    @Resource
    private HcSecurityDataProvider hcSecurityDataProvider;

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
    @Override
    public UserDetails loadUserByUsername(String username) {
        HcSysUserBo userBo = hcSecurityDataProvider.getSysUserByUsername(username);
        if (userBo == null) {
            throw new HcException("用户名或密码错误");
        }
        String password = hcSecurityDataProvider.getPasswordByUser(userBo);
        List<HcSysRoleBo> roleBoList = hcSecurityDataProvider.getRoleListByLoginId(userBo.getId());
        List<HcSysSourceBo> hcSysSourceBos = new ArrayList<>();
        roleBoList.forEach(role -> hcSysSourceBos.addAll(hcSecurityDataProvider.getSourceListByRoleId(String.valueOf(role.getId()))));
        List<SimpleGrantedAuthority> authorities = hcSysSourceBos.stream()
                .map(bo -> new SimpleGrantedAuthority(bo.getCode())).toList();
        return new User(String.valueOf(userBo.getId()), password, authorities);
    }
}
