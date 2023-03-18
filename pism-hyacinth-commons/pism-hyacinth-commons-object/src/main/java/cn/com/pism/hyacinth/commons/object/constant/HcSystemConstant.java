package cn.com.pism.hyacinth.commons.object.constant;

/**
 * 系统常量
 *
 * @author PerccyKing
 * @since 2023/3/5 15:07
 */
public class HcSystemConstant {
    private HcSystemConstant() {
    }

    public static final String HC_MYBATIS_SQL_SCRIPT = "<script>\n %s \n</script>";

    public static final String OK = "OK";


    public static final String HC_CACHE_DEFAULT_INSTANCE = "${spring.hyacinth.cache.default-instance}";

}
