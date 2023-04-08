package cn.com.pism.hyacinth.commons.object;

import lombok.Data;

/**
 * @author PerccyKing
 * @since 2023/4/8 18:34
 */
@Data
public abstract class HcCrypto {

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

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
    public abstract String encrypt(String data);


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
    public abstract String decrypt(String encryptStr);
}
