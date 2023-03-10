package cn.com.pism.hyacinth.commons.util;

import cn.com.pism.hyacinth.commons.module.HcModule;
import cn.com.pism.hyacinth.commons.module.enums.HcModuleEnums;

/**
 * @author PerccyKing
 * @since 2023/3/4 21:23
 */
public class HcCommonsUtilModule implements HcModule {
    @Override
    public HcModuleEnums getModuleName() {
        return HcModuleEnums.HC_COMMONS_UTIL;
    }
}
