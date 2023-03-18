package cn.com.pism.hyacinth.commons.enums.cache;

import lombok.Getter;

/**
 * @author PerccyKing
 * @since 2023/3/18 17:17
 */
public enum HcCacheTypeEnums implements HcCacheTypeItf {

    /**
     * jedis
     */
    JEDIS("HC_CACHE_REDIS_JEDIS");

    @Getter
    private final String name;

    HcCacheTypeEnums(String name) {
        this.name = name;
    }
}
