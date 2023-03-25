package cn.com.pism.hyacinth.commons.object.bo;

import lombok.Data;

/**
 * 登录用户信息
 * @author PerccyKing
 * @since 2023/3/25 17:52
 */
@Data
public class HcSysLoginUserInfo {

    /**
     * 用户系统主键id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatarUrl;
}
