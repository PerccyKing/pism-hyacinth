package cn.com.pism.hyacinth.commons.object.constant;

/**
 * @author PerccyKing
 * @since 2023/3/25 21:15
 */
public class HcCacheKeyConstant {

    private HcCacheKeyConstant() {
    }

    public static final String PERMISSION_LIST_KEY = "PERMISSION_LIST";

    /**
     * 公私钥键值对
     */
    public static final String KEY_PAIR_KEY = "hc:key_pair:";

    /**
     * 公钥
     */
    public static final String PUBLIC_KEY = "public";

    /**
     * 私钥
     */
    public static final String PRIVATE_KEY = "private";

    /**
     * 登录用户信息缓存key
     */
    public static final String LOGIN_USER_INFO_KEY = "hc:security:login:user:info:%s";

    /**
     * 登录的token
     */
    public static final String LOGIN_TOKEN_KEY = "hc:security:login:%s:%s";
}
