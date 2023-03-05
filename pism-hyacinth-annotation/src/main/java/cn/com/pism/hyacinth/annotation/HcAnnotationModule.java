package cn.com.pism.hyacinth.annotation;

import cn.com.pism.hyacinth.commons.module.HcModule;
import cn.com.pism.hyacinth.commons.module.enums.HcModuleEnums;

/**
 * @author PerccyKing
 * @since 2023/3/4 21:34
 */
public class HcAnnotationModule implements HcModule {
    @Override
    public HcModuleEnums getModuleName() {
        return HcModuleEnums.HC_ANNOTATION;
    }
}
