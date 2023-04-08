package cn.com.pism.hyacinth.commons.object.vo;

import cn.com.pism.hyacinth.commons.enums.HcResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static cn.com.pism.hyacinth.commons.enums.HcResultCodeEnum.SUCCESS;

/**
 * @author PerccyKing
 * @since 2023/3/27 22:43
 */
@ApiModel("通用响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class HcResult<T> {

    /**
     * 响应数据
     */
    @ApiModelProperty("响应数据")
    private T data;

    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private Integer code;

    /**
     * 消息
     */
    @ApiModelProperty("消息")
    private String msg;

    /**
     * <p>
     * 通用响应
     * </p>
     * by PerccyKing
     *
     * @param data : 响应数据
     * @param code : 响应状态码
     * @param msg  : 响应消息
     * @return {@link HcResult<T>}
     * @since 2023/4/8 17:04
     */
    public static <T> HcResult<T> response(T data, Integer code, String msg) {
        HcResult<T> result = new HcResult<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static <T> HcResult<T> response(T data, HcResultCode code) {
        return response(data, code.getCode(), code.getMsg());
    }

    /**
     * <p>
     * 成功
     * </p>
     * by PerccyKing
     *
     * @return {@link HcResult<Object>}
     * @since 2023/4/8 16:53
     */
    public static HcResult<Object> success() {
        return response(null, SUCCESS);
    }

    public static <T> HcResult<T> success(T data) {
        return response(data, SUCCESS);
    }

}
