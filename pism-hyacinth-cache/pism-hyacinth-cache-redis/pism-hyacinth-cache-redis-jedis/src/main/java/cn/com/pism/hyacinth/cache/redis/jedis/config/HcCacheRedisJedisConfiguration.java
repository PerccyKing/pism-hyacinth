package cn.com.pism.hyacinth.cache.redis.jedis.config;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @author PerccyKing
 * @since 2023/3/12 12:16
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class HcCacheRedisJedisConfiguration {

    @Resource
    private RedisProperties redisProperties;


    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        RedisProperties.Pool pool = redisProperties.getJedis().getPool();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        if (pool.getTimeBetweenEvictionRuns() != null) {
            config.setTimeBetweenEvictionRuns(pool.getTimeBetweenEvictionRuns());
        }
        if (pool.getMaxWait() != null) {
            config.setMaxWait(pool.getMaxWait());
        }
        return config;
    }


    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
        String password = redisProperties.getPassword();
        String host = redisProperties.getHost();
        int port = redisProperties.getPort();
        int database = redisProperties.getDatabase();
        Duration timeoutDuration = redisProperties.getTimeout();
        int timeout;
        if (timeoutDuration == null) {
            timeout = 1000;
        } else {
            timeout = (int) (timeoutDuration.getSeconds() * 1000);
        }
        if (StringUtils.isNotEmpty(password)) {
            return new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
        }
        return new JedisPool(jedisPoolConfig, host, port, timeout, null, database);
    }
}
