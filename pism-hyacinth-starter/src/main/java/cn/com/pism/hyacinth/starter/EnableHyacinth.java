package cn.com.pism.hyacinth.starter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author PerccyKing
 * @since 2023/3/5 17:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({HcConfiguration.class})
@Documented
public @interface EnableHyacinth {
}
