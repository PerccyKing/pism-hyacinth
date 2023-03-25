package cn.com.pism.hyacinth.security.base;

import cn.com.pism.hyacinth.commons.object.bo.HcSysRoleBo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysSourceBo;

import java.util.List;

/**
 * 提供数据支持
 *
 * @author PerccyKing
 * @since 2023/3/19 23:55
 */
public interface HcSecurityDataProvider {

    /**
     * <p>
     * 根据登录id 获取拥有的角色列表
     * </p>
     * by PerccyKing
     *
     * @param loginId : 登录id
     * @return {@link List<HcSysRoleBo>} 角色列表
     * @since 2023/3/25 21:11
     */
    List<HcSysRoleBo> getRoleListByLoginId(Object loginId);

    /**
     * <p>
     * 通过角色id 获取角色关联的资源列表
     * </p>
     * by PerccyKing
     *
     * @param roleId : 角色id
     * @return {@link List<HcSysSourceBo>}
     * @since 2023/3/25 21:20
     */
    List<HcSysSourceBo> getSourceListByRoleId(String roleId);

    /**
     * <p>
     * 获取白名单路径,不需要鉴权的路径与 {@link cn.com.pism.hyacinth.security.base.config.HcSecurityProperties#ignorePath} 不冲突
     * </p>
     * by PerccyKing
     *
     * @return {@link List<String>} 白名单列表
     * @since 2023/3/26 1:18
     */
    List<String> getWhiteList();

    /**
     * <p>
     * 获取所有需要授权的资源列表
     * </p>
     * by PerccyKing
     *
     * @return {@link List<HcSysSourceBo>}
     * @since 2023/3/26 1:40
     */
    List<HcSysSourceBo> getAllRequireAuthSource();
}
