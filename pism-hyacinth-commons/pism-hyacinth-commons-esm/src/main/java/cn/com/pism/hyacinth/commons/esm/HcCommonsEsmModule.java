package cn.com.pism.hyacinth.commons.esm;

import cn.com.pism.hyacinth.commons.module.HcModule;
import cn.com.pism.hyacinth.commons.module.enums.HcModuleEnums;

import static cn.com.pism.hyacinth.commons.module.enums.HcModuleEnums.HC_COMMONS_ESM;

/**
 * @author PerccyKing
 * @since 2023/3/4 21:55
 */
public class HcCommonsEsmModule implements HcModule {
    @Override
    public HcModuleEnums getModuleName() {
        return HC_COMMONS_ESM;
    }
}
