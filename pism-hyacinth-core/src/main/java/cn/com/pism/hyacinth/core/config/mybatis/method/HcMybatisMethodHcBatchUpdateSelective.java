package cn.com.pism.hyacinth.core.config.mybatis.method;

import cn.com.pism.hyacinth.commons.enums.HcMybatisMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.com.pism.hyacinth.commons.enums.HcMybatisMethodEnum.HC_BATCH_UPDATE_SELECTIVE;

/**
 * 批量新增
 *
 * @author PerccyKing
 * @since 2023/3/5 16:45
 */
@Component
@Slf4j
public class HcMybatisMethodHcBatchUpdateSelective extends HcMybatisAbstractMethod {

    public HcMybatisMethodHcBatchUpdateSelective() {
        this(HC_BATCH_UPDATE_SELECTIVE);
    }

    public HcMybatisMethodHcBatchUpdateSelective(HcMybatisMethod method) {
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
        return this.addUpdateMappedStatement(getMapperClass(), List.class, methodName, sqlSource);
    }
}
