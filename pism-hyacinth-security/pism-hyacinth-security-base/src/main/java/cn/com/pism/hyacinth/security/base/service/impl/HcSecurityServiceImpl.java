package cn.com.pism.hyacinth.security.base.service.impl;

import cn.com.pism.hyacinth.cache.base.HcCache;
import cn.com.pism.hyacinth.commons.object.HcCrypto;
import cn.com.pism.hyacinth.commons.object.sys.bo.HcSysLoginBo;
import cn.com.pism.hyacinth.commons.object.sys.bo.HcSysTokenBo;
import cn.com.pism.hyacinth.commons.object.sys.vo.req.HcSysLoginReqVo;
import cn.com.pism.hyacinth.commons.object.sys.vo.resp.HcSysTokenRespVo;
import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import cn.com.pism.hyacinth.security.base.HcSecurityUtil;
import cn.com.pism.hyacinth.security.base.service.HcSecurityService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

import static cn.com.pism.hyacinth.commons.object.constant.HcCacheKeyConstant.*;
import static cn.com.pism.hyacinth.commons.object.constant.HcSystemConstant.HC_CACHE_DEFAULT_INSTANCE;

/**
 * @author PerccyKing
 * @since 2023/4/8 16:36
 */
@Service
public class HcSecurityServiceImpl implements HcSecurityService {

    @Resource
    private HcSecurityUtil hcSecurityUtil;

    @Resource
    private HcSecurityDataProvider hcSecurityDataProvider;

    @Resource(name = HC_CACHE_DEFAULT_INSTANCE)
    private HcCache hcCache;

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
    @Override
    public HcSysTokenRespVo login(HcSysLoginReqVo loginReqVo) {
        HcSysLoginBo loginBo = new HcSysLoginBo();
        HcCrypto crypto = getCrypto(loginReqVo.getUniqueId());
        loginBo.setPassword(crypto.decrypt(loginReqVo.getPassword()));
        loginBo.setUsername(crypto.decrypt(loginReqVo.getUsername()));
        HcSysTokenBo tokenBo = hcSecurityUtil.login(loginBo);
        return HcSysTokenRespVo.builder()
                .token(tokenBo.getToken())
                .name(tokenBo.getName())
                .duration(tokenBo.getDuration())
                .build();
    }

    /**
     * <p>
     * 退出登录
     * </p>
     * by PerccyKing
     *
     * @since 2023/4/8 17:53
     */
    @Override
    public void logout() {
        hcSecurityUtil.logout();
    }

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
    @Override
    public String getPublicKey(String uniqueId) {
        return getCrypto(uniqueId).getPublicKey();
    }

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
    @Override
    public String publicKeyEncrypt(String uniqueId, String data) {
        return getCrypto(uniqueId).encrypt(data);
    }

    /**
     * <p>
     * 获取加密解密工具
     * </p>
     * by PerccyKing
     *
     * @param uniqueId : 唯一id
     * @return {@link HcCrypto} 获取加解密实例
     * @since 2023/4/8 21:27
     */
    public HcCrypto getCrypto(String uniqueId) {
        String key = KEY_PAIR_KEY + uniqueId;
        Map<String, String> keyPairMap = hcCache.hGetAll(key);
        HcCrypto crypto;
        if (CollectionUtils.isEmpty(keyPairMap)) {
            crypto = hcSecurityDataProvider.getCrypto(null, null);
            //添加缓存
            hcCache.hSet(key, PUBLIC_KEY, crypto.getPublicKey());
            hcCache.hSet(key, PRIVATE_KEY, crypto.getPrivateKey());
            hcCache.expire(key, 300);
        } else {
            crypto = hcSecurityDataProvider.getCrypto(keyPairMap.get(PUBLIC_KEY), keyPairMap.get(PRIVATE_KEY));
        }
        return crypto;
    }
}
