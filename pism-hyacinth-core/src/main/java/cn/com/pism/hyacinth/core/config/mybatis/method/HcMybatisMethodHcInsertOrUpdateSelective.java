package cn.com.pism.hyacinth.core.config.mybatis.method;

import cn.com.pism.hyacinth.commons.enums.HcMybatisMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static cn.com.pism.hyacinth.commons.enums.HcMybatisMethodEnum.HC_INSERT_OR_UPDATE_SELECTIVE;

/**
 * 批量新增
 *
 * @author PerccyKing
 * @since 2023/3/5 16:45
 */
@Component
@Slf4j
public class HcMybatisMethodHcInsertOrUpdateSelective extends HcMybatisMethodHcInsertOrUpdate {

    public HcMybatisMethodHcInsertOrUpdateSelective() {
        this(HC_INSERT_OR_UPDATE_SELECTIVE);
    }

    public HcMybatisMethodHcInsertOrUpdateSelective(HcMybatisMethod method) {
        super(method);
    }

    /**
     * <p>
     * 获取mapper的参数类型
     * </p>
     * by PerccyKing
     *
     * @return {@link Class} 参数类型
     * @since 2023/3/5 21:59
     */
    @Override
    public Class<?> getParameterType() {
        return getModelClass();
    }
}
