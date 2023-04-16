package cn.com.pism.hyacinth.core.config.security;

import cn.com.pism.hyacinth.commons.object.HcCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.EqualsAndHashCode;

/**
 * @author PerccyKing
 * @since 2023/4/8 19:22
 */
@EqualsAndHashCode(callSuper = true)
public class HcRsaHcCrypto extends HcCrypto {

    private final RSA rsa;

    public HcRsaHcCrypto(RSA rsa) {
        this.rsa = rsa;
    }


    /**
     * <p>
     * 获取公钥
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 公钥
     * @since 2023/4/15 13:31
     */
    @Override
    public String getPublicKey() {
        return rsa.getPublicKeyBase64();
    }

    /**
     * <p>
     * 获取私钥
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 私钥
     * @since 2023/4/15 13:32
     */
    @Override
    public String getPrivateKey() {
        return rsa.getPrivateKeyBase64();
    }

    /**
     * <p>
     * 加密
     * </p>
     * by PerccyKing
     *
     * @param data : 待加密数据
     * @return {@link String} 加密后的数据
     * @since 2023/4/8 18:40
     */
    @Override
    public String encrypt(String data) {
        return rsa.encryptBase64(data, KeyType.PublicKey);
    }

    /**
     * <p>
     * 解密
     * </p>
     * by PerccyKing
     *
     * @param encryptStr : 加密后的字符串
     * @return {@link String}
     * @since 2023/4/8 18:43
     */
    @Override
    public String decrypt(String encryptStr) {
        return rsa.decryptStr(encryptStr, KeyType.PrivateKey);
    }
}
