package cn.com.pism.hyacinth.commons.object.sys.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author PerccyKing
 * @since 2023/4/8 16:51
 */
@ApiModel("token信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class HcSysTokenRespVo {
    /**
     * token名字
     */
    @ApiModelProperty("token名字")
    private String name;

    /**
     * token
     */
    @ApiModelProperty("token")
    private String token;

    /**
     * token有效时间
     */
    @ApiModelProperty("token有效时间")
    private String duration;
}
