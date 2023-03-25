package cn.com.pism.hyacinth.commons.object.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 资源
 *
 * @author PerccyKing
 * @since 2023/3/25 21:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class HcSysSourceBo {

    /**
     * 资源编码
     */
    private String code;

    /**
     * 资源路径
     */
    private String url;
}
