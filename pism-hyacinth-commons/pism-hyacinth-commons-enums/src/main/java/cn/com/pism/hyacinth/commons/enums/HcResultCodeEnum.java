package cn.com.pism.hyacinth.commons.enums;

/**
 * @author PerccyKing
 * @since 2023/4/2 19:01
 */
public enum HcResultCodeEnum implements HcResultCode {
    /**
     * 成功
     */
    SUCCESS(1, "成功"),

    /**
     * 失败
     */
    FAILED(0, "失败");

    private final Integer code;

    private final String msg;

    HcResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * <p>
     * 获取状态码
     * </p>
     * by PerccyKing
     *
     * @return {@link int} 状态码
     * @since 2023/4/2 18:50
     */
    @Override
    public int getCode() {
        return this.code;
    }

    /**
     * <p>
     * 状态码对应消息
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 消息
     * @since 2023/4/2 18:58
     */
    @Override
    public String getMsg() {
        return this.msg;
    }
}
