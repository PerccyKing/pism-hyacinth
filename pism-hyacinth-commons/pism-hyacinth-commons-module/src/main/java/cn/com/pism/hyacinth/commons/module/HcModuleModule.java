package cn.com.pism.hyacinth.commons.module;

import cn.com.pism.hyacinth.commons.module.enums.HcModuleEnums;

/**
 * @author PerccyKing
 * @since 2023/3/4 22:20
 */
public class HcModuleModule implements HcModule {
    @Override
    public HcModuleEnums getModuleName() {
        return HcModuleEnums.HC_MODULE;
    }
}
