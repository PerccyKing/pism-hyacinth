package cn.com.pism.hyacinth.security.satoken.config;

import cn.com.pism.hyacinth.commons.object.bo.HcSysRoleBo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysSourceBo;
import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import cn.dev33.satoken.stp.StpInterface;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PerccyKing
 * @since 2023/3/25 15:25
 */
@Component
public class HcSecuritySaTokenStpInterfaceImpl implements StpInterface {

    @Resource
    private HcSecurityDataProvider hcSecurityDataProvider;

    /**
     * 返回指定账号id所拥有的权限码集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = new ArrayList<>();
        for (String roleId : getRoleList(loginId, loginType)) {
            List<HcSysSourceBo> sourceBos = hcSecurityDataProvider.getSourceListByRoleId(roleId);
            List<String> sourceCodeList = sourceBos.stream().map(HcSysSourceBo::getCode).toList();
            permissionList.addAll(sourceCodeList);
        }
        return permissionList;
    }

    /**
     * 返回指定账号id所拥有的角色标识集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<HcSysRoleBo> roleBos = hcSecurityDataProvider.getRoleListByLoginId(loginId);
        return roleBos.stream().map(role -> role.getId().toString()).collect(Collectors.toList());
    }
}