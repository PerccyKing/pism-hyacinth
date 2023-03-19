package cn.com.pism.hyacinth.cache.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author PerccyKing
 * @since 2023/3/19 13:02
 */
public class HcCacheUtil {

    private HcCacheUtil() {
    }


    /**
     * <p>
     * 将k-v 转换为map
     * </p>
     * by PerccyKing
     *
     * @param keysValues : [{k-v},{k-v}]
     * @return {@link Map}
     * @since 2023/3/19 0:52
     */
    public static Map<String, String> keysValusToMap(String[] keysValues) {
        Map<String, String> map = new HashMap<>(keysValues.length / 2);
        int i = 1;
        String key = null;
        for (String keysValue : keysValues) {
            if (i % 2 == 1) {
                key = keysValue;
                map.put(keysValue, null);
            } else {
                map.put(key, keysValue);
            }
            i++;
        }
        return map;
    }

}
