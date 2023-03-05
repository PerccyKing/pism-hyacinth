package cn.com.pism.hyacinth.core.config.mybatis.method;

import cn.com.pism.hyacinth.commons.enums.HcMybatisMethodItf;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.stereotype.Component;

import static cn.com.pism.hyacinth.commons.enums.HcMybatisMethodEnums.HC_UPDATE_BY_PRIMARY_KEY_SELECTIVE;

/**
 * 批量新增
 *
 * @author PerccyKing
 * @since 2023/3/5 16:45
 */
@Component
@Slf4j
public class HcMybatisMethodHcUpdateByPrimaryKeySelective extends HcMybatisAbstractMethod {

    public HcMybatisMethodHcUpdateByPrimaryKeySelective() {
        this(HC_UPDATE_BY_PRIMARY_KEY_SELECTIVE);
    }

    public HcMybatisMethodHcUpdateByPrimaryKeySelective(HcMybatisMethodItf method) {
        super(method);
    }

    /**
     * <p>
     * 注入mybatis自定义方法
     * </p>
     * by PerccyKing
     *
     * @param sqlSource : sqlSource
     * @return {@link MappedStatement} MappedStatement
     * @since 2023/3/5 15:24
     */
    @Override
    MappedStatement injectMappedStatement(SqlSource sqlSource) {
        return this.addUpdateMappedStatement(getMapperClass(), getModelClass(), methodName, sqlSource);
    }
}
