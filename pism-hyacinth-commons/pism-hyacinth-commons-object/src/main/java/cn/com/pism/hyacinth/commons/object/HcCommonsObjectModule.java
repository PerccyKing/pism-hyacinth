package cn.com.pism.hyacinth.commons.object;

import cn.com.pism.hyacinth.commons.module.HcModule;
import cn.com.pism.hyacinth.commons.module.enums.HcModuleEnums;
import org.springframework.stereotype.Component;

import static cn.com.pism.hyacinth.commons.module.enums.HcModuleEnums.HC_COMMONS_OBJECT;

/**
 * @author PerccyKing
 * @since 2023/3/4 21:32
 */
@Component
public class HcCommonsObjectModule implements HcModule {
    @Override
    public HcModuleEnums getModuleName() {
        return HC_COMMONS_OBJECT;
    }
}
