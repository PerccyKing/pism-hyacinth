package cn.com.pism.hyacinth.core.config.mybatis;

import cn.com.pism.hyacinth.core.config.mybatis.method.HcMybatisAbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 模版方法注入器
 *
 * @author PerccyKing
 * @since 2023/3/5 14:54
 */
@Component
public class HcMybatisMethodInjector extends DefaultSqlInjector implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        Map<String, HcMybatisAbstractMethod> mybatisMethods = applicationContext.getBeansOfType(HcMybatisAbstractMethod.class);
        mybatisMethods.forEach((k, v) -> methodList.add(v));
        return methodList;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
