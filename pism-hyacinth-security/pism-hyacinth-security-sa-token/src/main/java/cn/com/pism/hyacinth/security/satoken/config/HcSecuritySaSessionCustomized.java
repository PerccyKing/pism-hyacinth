package cn.com.pism.hyacinth.security.satoken.config;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.util.SaFoxUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;

import java.io.Serializable;

/**
 * Fastjson 定制版 SaSession，重写类型转换API
 *
 * @author kong
 * @since 2022-10-19
 */
public class HcSecuritySaSessionCustomized extends SaSession implements Serializable {


    public HcSecuritySaSessionCustomized() {
        super();
    }

    /**
     * 构建一个 SaSession 对象
     *
     * @param id Session 的 id
     */
    public HcSecuritySaSessionCustomized(String id) {
        super(id);
    }

    /**
     * 取值 (指定转换类型)
     *
     * @param <T> 泛型
     * @param key key
     * @param cs  指定转换类型
     * @return 值
     */
    @Override
    public <T> T getModel(String key, Class<T> cs) {
        if (SaFoxUtil.isBasicType(cs)) {
            return SaFoxUtil.getValueByType(get(key), cs);
        }
        return JSON.parseObject(getString(key), cs);
    }

    /**
     * 取值 (指定转换类型, 并指定值为Null时返回的默认值)
     *
     * @param <T>          泛型
     * @param key          key
     * @param cs           指定转换类型
     * @param defaultValue 值为Null时返回的默认值
     * @return 值
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getModel(String key, Class<T> cs, Object defaultValue) {
        Object value = get(key);
        if (valueIsNull(value)) {
            return (T) defaultValue;
        }
        if (SaFoxUtil.isBasicType(cs)) {
            return SaFoxUtil.getValueByType(get(key), cs);
        }
        return JSON.parseObject(getString(key), cs);
    }

    /**
     * 忽略 timeout 字段的序列化
     */
    @Override
    @JSONField(serialize = false)
    public long getTimeout() {
        return super.getTimeout();
    }

}
