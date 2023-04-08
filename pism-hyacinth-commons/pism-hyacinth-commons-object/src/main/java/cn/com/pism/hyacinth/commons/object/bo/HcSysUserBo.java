package cn.com.pism.hyacinth.commons.object.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author PerccyKing
 * @since 2023/3/26 21:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class HcSysUserBo {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;
}
