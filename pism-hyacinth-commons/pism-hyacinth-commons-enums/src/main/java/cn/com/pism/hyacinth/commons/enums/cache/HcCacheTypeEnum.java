package cn.com.pism.hyacinth.commons.enums.cache;

/**
 * @author PerccyKing
 * @since 2023/3/18 17:17
 */
public enum HcCacheTypeEnum implements HcCacheType {

    /**
     * 本地原生
     */
    LOCAL_NATIVE("HC_CACHE_LOCAL_NATIVE"),

    /**
     * redis_jedis 实现
     */
    JEDIS("HC_CACHE_REDIS_JEDIS"),


    /**
     * redis redisTemplate 实现
     */
    REDIS_TEMPLATE("HC_CACHE_REDIS_SPRING"),

    ;

    private final String name;

    HcCacheTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
