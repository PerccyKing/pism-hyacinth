package cn.com.pism.hyacinth.security.base;

import cn.com.pism.hyacinth.cache.base.HcCache;
import cn.com.pism.hyacinth.commons.object.HcCrypto;
import cn.com.pism.hyacinth.commons.object.bo.HcSysRoleBo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysSourceBo;
import cn.com.pism.hyacinth.commons.object.bo.HcSysUserBo;
import org.springframework.security.crypto.password.PasswordEncoder;

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
     * 获取白名单路径,不需要鉴权的路径与 {@link cn.com.pism.hyacinth.security.base.config.HcSecurityProperties#getIgnorePath} 不冲突
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

    /**
     * <p>
     * 获取一个缓存实例，用于存放token等信息
     * </p>
     * by PerccyKing
     *
     * @return {@link HcCache} 缓存实例
     * @since 2023/3/26 12:56
     */
    HcCache getCache();

    /**
     * <p>
     * 获取用户的密码
     * </p>
     * by PerccyKing
     *
     * @param userBo : 用户业务对象
     * @return {@link String} 加密后的密码
     * @since 2023/3/26 21:37
     */
    String getPasswordByUser(HcSysUserBo userBo);

    /**
     * <p>
     * 获取一个密码编码器
     * </p>
     * by PerccyKing
     *
     * @return {@link PasswordEncoder} 密码编码器
     * @since 2023/3/26 21:52
     */
    PasswordEncoder getPasswordEncoder();

    /**
     * <p>
     * 根据用户名获取用户信息
     * </p>
     * by PerccyKing
     *
     * @param username : 用户名
     * @return {@link HcSysUserBo} 用户信息
     * @since 2023/4/8 17:24
     */
    HcSysUserBo getSysUserByUsername(String username);

    /**
     * <p>
     * 获取加解密实例
     * </p>
     * <pre>
     *     公私钥为空的时候，创建一个全新的对象
     * </pre>
     * by PerccyKing
     *
     * @param publicKey  公钥，可为空
     * @param privateKey 私钥，可为空
     * @return {@link HcCrypto} 加解密实例
     * @since 2023/4/8 18:46
     */
    HcCrypto getCrypto(String publicKey, String privateKey);
}
