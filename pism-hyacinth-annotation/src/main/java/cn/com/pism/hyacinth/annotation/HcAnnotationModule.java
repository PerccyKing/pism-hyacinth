package cn.com.pism.hyacinth.annotation;

import cn.com.pism.hyacinth.commons.model.HcModule;

/**
 * @author PerccyKing
 * @since 2023/3/4 21:34
 */
public class HcAnnotationModule implements HcModule {
    @Override
    public String getModuleName() {
        return "HC-ANNOTATION";
    }
}
