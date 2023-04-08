package cn.com.pism.hyacinth.core.config.security;

import cn.com.pism.hyacinth.commons.object.HcCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * @author PerccyKing
 * @since 2023/4/8 19:22
 */
public class HcRsaHcCrypto extends HcCrypto {

    private final RSA rsa;

    public HcRsaHcCrypto(RSA rsa) {
        this.rsa = rsa;
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
        return rsa.decryptStr(encryptStr,KeyType.PrivateKey);
    }
}
