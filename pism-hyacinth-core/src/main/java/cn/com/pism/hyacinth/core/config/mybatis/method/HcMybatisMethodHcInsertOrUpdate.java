package cn.com.pism.hyacinth.core.config.mybatis.method;

import cn.com.pism.hyacinth.commons.enums.HcMybatisMethodItf;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.stereotype.Component;

import static cn.com.pism.hyacinth.commons.enums.HcMybatisMethodEnums.HC_INSERT_OR_UPDATE;

/**
 * 新增或更新
 *
 * @author PerccyKing
 * @since 2023/3/5 22:00
 */
@Component
public class HcMybatisMethodHcInsertOrUpdate extends HcMybatisAbstractMethod {
    public HcMybatisMethodHcInsertOrUpdate() {
        this(HC_INSERT_OR_UPDATE);
    }

    public HcMybatisMethodHcInsertOrUpdate(HcMybatisMethodItf method) {
        super(method);
    }


    /**
     * <p>
     * 注入mybatis自定义方法
     * </p>
     *
     * @param sqlSource : sqlSource
     * @return {@link MappedStatement} MappedStatement
     * @since 2023/3/5 15:24
     */
    @Override
    public MappedStatement injectMappedStatement(SqlSource sqlSource) {
        KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
        TableInfo tableInfo = getTableInfo();
        String keyColumn = null;
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (tableInfo.havePK()) {
            if (tableInfo.getIdType() == IdType.AUTO) {
                /* 自增主键 */
                keyGenerator = Jdbc3KeyGenerator.INSTANCE;
                // 去除转义符
                keyColumn = SqlInjectionUtils.removeEscapeCharacter(tableInfo.getKeyColumn());
            } else {
                if (null != tableInfo.getKeySequence()) {
                    keyGenerator = TableInfoHelper.genKeyGenerator(this.methodName, tableInfo, builderAssistant);
                    keyColumn = tableInfo.getKeyColumn();
                }
            }
        }
        return this.addInsertMappedStatement(getMapperClass(),
                getParameterType(),
                methodName,
                sqlSource,
                keyGenerator,
                tableInfo.getKeyProperty(),
                keyColumn);
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
    public Class<?> getParameterType() {
        return getModelClass();
    }
}
