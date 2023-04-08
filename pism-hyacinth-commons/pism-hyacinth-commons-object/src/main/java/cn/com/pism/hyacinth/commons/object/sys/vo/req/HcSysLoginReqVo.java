package cn.com.pism.hyacinth.commons.object.sys.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author PerccyKing
 * @since 2023/3/26 21:44
 */
@Data
@ApiModel("登录请求参数")
public class HcSysLoginReqVo {

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 唯一id
     */
    @ApiModelProperty("唯一id")
    private String uniqueId;
}
