package cn.com.pism.hyacinth.commons.module.enums;

/**
 * @author PerccyKing
 * @since 2023/3/4 22:04
 */
public interface HcModuleItf {

    /**
     * <p>
     * 模块名称
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 模块名称
     * @since 2023/3/4 22:05
     */
    String getName();

    /**
     * <p>
     * 模块代码
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 模块代码
     * @since 2023/3/4 22:11
     */
    String getCode();

    /**
     * <p>
     * 模块描述
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 描述信息
     * @since 2023/3/4 22:12
     */
    String getDesc();
}
