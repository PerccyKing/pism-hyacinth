package cn.com.pism.hyacinth.commons.model;

/**
 * @author PerccyKing
 * @since 2023/3/4 18:39
 */
public interface HcModule {
    /**
     * <p>
     * 获取模块名称
     * </p>
     * <pre>
     *     所有模块都应首先实现这个接口
     * </pre>
     * by PerccyKing
     *
     * @return {@link String} 模块名称
     * @since 2023/3/4 18:39
     */
    String getModuleName();
}
