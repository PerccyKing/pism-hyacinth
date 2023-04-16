package cn.com.pism.hyacinth.security.base.service;

import cn.com.pism.hyacinth.commons.object.sys.vo.req.HcSysLoginReqVo;
import cn.com.pism.hyacinth.commons.object.sys.vo.resp.HcSysTokenRespVo;

/**
 * @author PerccyKing
 * @since 2023/4/8 16:34
 */
public interface HcSecurityService {

    /**
     * <p>
     * 用户登录
     * </p>
     * by PerccyKing
     *
     * @param loginReqVo : 登录参数
     * @return {@link HcSysTokenRespVo}
     * @since 2023/4/8 17:17
     */
    HcSysTokenRespVo login(HcSysLoginReqVo loginReqVo);

    /**
     * <p>
     * 退出登录
     * </p>
     * by PerccyKing
     *
     * @since 2023/4/8 17:53
     */
    void logout();

    /**
     * <p>
     * 获取公钥
     * </p>
     * by PerccyKing
     *
     * @param uniqueId : 唯一id
     * @return {@link String} 公钥
     * @since 2023/4/8 17:44
     */
    String getPublicKey(String uniqueId);

    /**
     * <p>
     * 获取公钥加密数据
     * </p>
     * by PerccyKing
     *
     * @param uniqueId : 唯一id
     * @param data     : 代加密数据
     * @return {@link String} 加密后的数据
     * @since 2023/4/15 19:14
     */
    String publicKeyEncrypt(String uniqueId, String data);
}
