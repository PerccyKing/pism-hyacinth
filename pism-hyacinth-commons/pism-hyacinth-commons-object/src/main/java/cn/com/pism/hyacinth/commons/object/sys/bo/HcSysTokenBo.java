package cn.com.pism.hyacinth.commons.object.sys.bo;

import lombok.Data;

/**
 * token 信息
 *
 * @author PerccyKing
 * @since 2023/4/8 17:22
 */
@Data
public class HcSysTokenBo {
    /**
     * token名字
     */
    private String name;

    /**
     * token
     */
    private String token;

    /**
     * token有效时间 单位：秒
     */
    private String duration;
}
