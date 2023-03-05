package cn.com.pism.hyacinth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体填充默认值
 *
 * @author PerccyKing
 * @since 2023/3/4 22:53
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface HcEntityDefaultVal {
}
