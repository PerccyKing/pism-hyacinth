package cn.com.pism.hyacinth.commons.object.sys.bo;

import lombok.Data;

/**
 * @author PerccyKing
 * @since 2023/4/8 16:43
 */
@Data
public class HcSysLoginBo {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
