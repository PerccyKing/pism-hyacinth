package cn.com.pism.hyacinth.commons.util.function;

/**
 * @author PerccyKing
 * @since 2023/3/12 13:01
 */
@FunctionalInterface
public interface HcCallback<P> {

    /**
     * <p>
     * 回调方法
     * </p>
     * by PerccyKing
     *
     * @param p : 入参
     * @since 2023/3/12 13:02
     */
    void callback(P p);
}
