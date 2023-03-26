package cn.com.pism.hyacinth.security.satoken.config;

import cn.com.pism.hyacinth.cache.base.HcCache;
import cn.com.pism.hyacinth.security.base.HcSecurityDataProvider;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.strategy.SaStrategy;
import cn.dev33.satoken.util.SaFoxUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author PerccyKing
 * @since 2023/3/26 12:58
 */
@Component
@Slf4j
public class HcSecuritySaTokenDao implements SaTokenDao {


    private final HcCache hcCache;

    public HcSecuritySaTokenDao(HcSecurityDataProvider securityDataProvider) {
        this.hcCache = securityDataProvider.getCache();
        SaStrategy.me.createSession = HcSecuritySaSessionCustomized::new;
    }

    /**
     * 获取Value，如无返空
     *
     * @param key 键名称
     * @return value
     */
    @Override
    public String get(String key) {
        return hcCache.get(key);
    }

    /**
     * 写入Value，并设定存活时间 (单位: 秒)
     *
     * @param key     键名称
     * @param value   值
     * @param timeout 过期时间(值大于0时限时存储，值=-1时永久存储，值=0或小于-2时不存储)
     */
    @Override
    public void set(String key, String value, long timeout) {
        hcCache.setEx(key, value, timeout);
    }

    /**
     * 更新Value (过期时间不变)
     *
     * @param key   键名称
     * @param value 值
     */
    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    /**
     * 删除Value
     *
     * @param key 键名称
     */
    @Override
    public void delete(String key) {
        hcCache.del(key);
    }

    /**
     * 获取Value的剩余存活时间 (单位: 秒)
     *
     * @param key 指定key
     * @return 这个key的剩余存活时间
     */
    @Override
    public long getTimeout(String key) {
        return hcCache.ttl(key);
    }

    /**
     * 修改Value的剩余存活时间 (单位: 秒)
     *
     * @param key     指定key
     * @param timeout 过期时间
     */
    @Override
    public void updateTimeout(String key, long timeout) {
        hcCache.expire(key, timeout);
    }

    /**
     * 获取Object，如无返空
     *
     * @param key 键名称
     * @return object
     */
    @Override
    public Object getObject(String key) {
        String val = hcCache.hGet(key, "val");
        String type = hcCache.hGet(key, "type");
        if (StringUtils.isAnyBlank(val, type)) {
            return null;
        }
        try {
            Class<?> aClass = Class.forName(type);
            return JSON.parseObject(val, aClass);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 写入Object，并设定存活时间 (单位: 秒)
     *
     * @param key     键名称
     * @param object  值
     * @param timeout 存活时间 (值大于0时限时存储，值=-1时永久存储，值=0或小于-2时不存储)
     */
    @Override
    public void setObject(String key, Object object, long timeout) {
        hcCache.hSet(key, "val", JSON.toJSONString(object));
        hcCache.hSet(key, "type", object.getClass().getCanonicalName());
        hcCache.expire(key, timeout);
    }

    /**
     * 更新Object (过期时间不变)
     *
     * @param key    键名称
     * @param object 值
     */
    @Override
    public void updateObject(String key, Object object) {
        long expire = getObjectTimeout(key);
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.setObject(key, object, expire);
    }

    /**
     * 删除Object
     *
     * @param key 键名称
     */
    @Override
    public void deleteObject(String key) {
        hcCache.del(key);
    }

    /**
     * 获取Object的剩余存活时间 (单位: 秒)
     *
     * @param key 指定key
     * @return 这个key的剩余存活时间
     */
    @Override
    public long getObjectTimeout(String key) {
        return hcCache.ttl(key);
    }

    /**
     * 修改Object的剩余存活时间 (单位: 秒)
     *
     * @param key     指定key
     * @param timeout 过期时间
     */
    @Override
    public void updateObjectTimeout(String key, long timeout) {
        hcCache.expire(key, timeout);
    }

    /**
     * 搜索数据
     *
     * @param prefix   前缀
     * @param keyword  关键字
     * @param start    开始处索引
     * @param size     获取数量  (-1代表从start处一直取到末尾)
     * @param sortType 排序类型（true=正序，false=反序）
     * @return 查询到的数据集合
     */
    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        Set<String> keys = hcCache.keys(prefix + "*" + keyword + "*");
        return SaFoxUtil.searchList(new ArrayList<>(keys), start, size, sortType);
    }

    /**
     * 获取Session，如无返空
     *
     * @param sessionId sessionId
     * @return SaSession
     */
    @Override
    public SaSession getSession(String sessionId) {
        Object obj = getObject(sessionId);
        if (obj == null) {
            return null;
        }
        return JSON.parseObject(obj.toString(), HcSecuritySaSessionCustomized.class);
    }
}
