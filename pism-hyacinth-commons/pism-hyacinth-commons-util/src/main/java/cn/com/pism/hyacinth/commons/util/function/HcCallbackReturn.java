package cn.com.pism.hyacinth.commons.util.function;

/**
 * @author PerccyKing
 * @since 2023/3/13 0:39
 */
@FunctionalInterface
public interface HcCallbackReturn<R> {
    /**
     * <p>
     * 无参有返回值的回调方法
     * </p>
     * by PerccyKing
     *
     * @return {@link R} 返回值
     * @since 2023/3/13 0:40
     */
    R callback();
}
