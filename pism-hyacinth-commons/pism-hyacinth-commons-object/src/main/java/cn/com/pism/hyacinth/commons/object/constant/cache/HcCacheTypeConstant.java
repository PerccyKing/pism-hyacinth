package cn.com.pism.hyacinth.commons.object.constant.cache;

/**
 * @author PerccyKing
 * @since 2023/3/11 23:30
 */
public class HcCacheTypeConstant {
    private HcCacheTypeConstant() {
    }


    public static class Local {

        private Local() {
        }

        public static final String NATIVE = "HC_CACHE_LOCAL_NATIVE";
    }

    public static class Redis {

        private Redis() {
        }

        public static final String JEDIS = "HC_CACHE_REDIS_JEDIS";

        public static final String REDISSION = "HC_CACHE_REDIS_REDISSION";

        public static final String SPRING = "HC_CACHE_REDIS_SPRING";

    }
}
