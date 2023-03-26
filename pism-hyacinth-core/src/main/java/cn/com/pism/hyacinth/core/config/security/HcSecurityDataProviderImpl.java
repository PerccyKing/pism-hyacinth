package cn.com.pism.hyacinth.core.config.security;

import cn.com.pism.hyacinth.cache.base.HcCache;
import cn.com.pism.hyacinth.commons.object.bo.HcSysRoleBo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysSourceBo;
import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import cn.com.pism.hyacinth.security.base.config.HcSecurityProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cn.com.pism.hyacinth.commons.object.constant.HcSystemConstant.HC_CACHE_DEFAULT_INSTANCE;

/**
 * @author PerccyKing
 * @since 2023/3/26 2:06
 */
@Component
public class HcSecurityDataProviderImpl implements HcSecurityDataProvider {

    @Resource(name = HC_CACHE_DEFAULT_INSTANCE)
    private HcCache hcCache;

    /**
     * <p>
     * 根据登录id 获取拥有的角色列表
     * </p>
     * by PerccyKing
     *
     * @param loginId : 登录id
     * @return {@link List < HcSysRoleBo >} 角色列表
     * @since 2023/3/25 21:11
     */
    @Override
    public List<HcSysRoleBo> getRoleListByLoginId(Object loginId) {
        return Arrays.asList(new HcSysRoleBo(1L, "code", "code"));
    }

    /**
     * <p>
     * 通过角色id 获取角色关联的资源列表
     * </p>
     * by PerccyKing
     *
     * @param roleId : 角色id
     * @return {@link List< HcSysSourceBo >}
     * @since 2023/3/25 21:20
     */
    @Override
    public List<HcSysSourceBo> getSourceListByRoleId(String roleId) {
        return Arrays.asList(new HcSysSourceBo("test", "/test/test"));
    }

    /**
     * <p>
     * 获取白名单路径,不需要鉴权的路径与 {@link HcSecurityProperties#getIgnorePath} 不冲突
     * </p>
     * by PerccyKing
     *
     * @return {@link List<String>} 白名单列表
     * @since 2023/3/26 1:18
     */
    @Override
    public List<String> getWhiteList() {
        return Collections.emptyList();
    }

    /**
     * <p>
     * 获取所有需要授权的资源列表
     * </p>
     * by PerccyKing
     *
     * @return {@link List<HcSysSourceBo>}
     * @since 2023/3/26 1:40
     */
    @Override
    public List<HcSysSourceBo> getAllRequireAuthSource() {
        return Arrays.asList(new HcSysSourceBo("test", "/test/test"),
                new HcSysSourceBo("test1", "/test/test1"));
    }

    /**
     * <p>
     * 获取一个缓存实例，用于存放token等信息
     * </p>
     * by PerccyKing
     *
     * @return {@link HcCache} 缓存实例
     * @since 2023/3/26 12:56
     */
    @Override
    public HcCache getCache() {
        return hcCache;
    }
}
