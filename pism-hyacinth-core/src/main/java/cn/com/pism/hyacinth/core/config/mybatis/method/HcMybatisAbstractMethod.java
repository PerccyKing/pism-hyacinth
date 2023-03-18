package cn.com.pism.hyacinth.core.config.mybatis.method;

import cn.com.pism.hyacinth.commons.enums.HcMybatisMethodItf;
import cn.com.pism.hyacinth.exception.HcException;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

import static cn.com.pism.hyacinth.commons.object.constant.HcSystemConstant.HC_MYBATIS_SQL_SCRIPT;
import static cn.com.pism.hyacinth.commons.util.HcFreeMarkerUtil.processTemplateIntoString;
import static org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties.DEFAULT_SUFFIX;

/**
 * mybatis 自定义方法
 *
 * @author PerccyKing
 * @since 2023/3/5 15:14
 */
public abstract class HcMybatisAbstractMethod extends AbstractMethod {

    @Resource(name = "HC_FreeMarkerConfigurer")
    private transient FreeMarkerConfigurer freeMarkerConfigurer;

    @Getter
    private Class<?> mapperClass;

    @Getter
    private Class<?> modelClass;

    @Getter
    private TableInfo tableInfo;

    @Getter
    @Setter
    private transient HcMybatisMethodItf method;

    /**
     * @param method 方法
     * @since 3.5.0
     */
    protected HcMybatisAbstractMethod(HcMybatisMethodItf method) {
        super(method.getMethodName());
        this.method = method;
    }

    /**
     * 注入自定义 MappedStatement
     *
     * @param mapperClass mapper 接口
     * @param modelClass  mapper 泛型
     * @param tableInfo   数据库表反射信息
     * @return MappedStatement
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        this.modelClass = modelClass;
        this.mapperClass = mapperClass;
        this.tableInfo = tableInfo;
        String sql;
        try {
            Configuration freeMarkerConfiguration = freeMarkerConfigurer.getConfiguration();
            Template template = freeMarkerConfiguration.getTemplate(methodName + DEFAULT_SUFFIX);
            sql = processTemplateIntoString(template, tableInfo);
        } catch (IOException | TemplateException e) {
            throw new HcException("模版加载失败，mapper:" + mapperClass.getSimpleName() + " method:" + methodName);
        }

        SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(HC_MYBATIS_SQL_SCRIPT, sql), modelClass);
        return injectMappedStatement(sqlSource);
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
    abstract MappedStatement injectMappedStatement(SqlSource sqlSource);


}
