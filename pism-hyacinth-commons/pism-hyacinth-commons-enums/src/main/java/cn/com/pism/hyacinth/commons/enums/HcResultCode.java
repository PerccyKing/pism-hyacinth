package cn.com.pism.hyacinth.commons.enums;

/**
 * @author PerccyKing
 * @since 2023/4/2 18:38
 */
public interface HcResultCode {

    /**
     * <p>
     * 获取状态码
     * </p>
     * by PerccyKing
     *
     * @return {@link int} 状态码
     * @since 2023/4/2 18:50
     */
    int getCode();

    /**
     * <p>
     * 状态码对应消息
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 消息
     * @since 2023/4/2 18:58
     */
    String getMsg();
}
