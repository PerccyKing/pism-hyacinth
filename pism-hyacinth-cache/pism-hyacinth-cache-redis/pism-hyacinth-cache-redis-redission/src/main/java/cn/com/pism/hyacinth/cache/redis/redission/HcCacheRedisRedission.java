package cn.com.pism.hyacinth.cache.redis.redission;

import org.redisson.api.RedissonClient;

/**
 * @author PerccyKing
 * @since 2023/3/12 0:33
 */
public interface HcCacheRedisRedission {

    /**
     * <p>
     * 获取Redission客户端
     * </p>
     * by PerccyKing
     *
     * @return {@link RedissonClient} redission 客户端
     * @since 2023/3/12 0:35
     */
    RedissonClient getInstance();
}
