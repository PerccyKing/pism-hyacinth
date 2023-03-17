package cn.com.pism.hyacinth.cache.redis.jedis;

import redis.clients.jedis.Jedis;

/**
 * @author PerccyKing
 * @since 2023/3/11 23:16
 */
public interface HcCacheRedisJedis {

    /**
     * <p>
     * 获取jedis实例
     * </p>
     * by PerccyKing
     *
     * @return {@link Jedis} jedis实例
     * @since 2023/3/11 23:17
     */
    Jedis getInstance();
}
