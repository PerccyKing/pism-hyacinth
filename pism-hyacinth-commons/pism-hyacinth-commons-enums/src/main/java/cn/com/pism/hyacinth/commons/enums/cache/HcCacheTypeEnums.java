package cn.com.pism.hyacinth.commons.enums.cache;

/**
 * @author PerccyKing
 * @since 2023/3/18 17:17
 */
public enum HcCacheTypeEnums implements HcCacheTypeItf {

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

    HcCacheTypeEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
