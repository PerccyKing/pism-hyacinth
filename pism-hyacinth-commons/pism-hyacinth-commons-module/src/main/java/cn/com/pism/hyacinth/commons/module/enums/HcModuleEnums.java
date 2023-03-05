package cn.com.pism.hyacinth.commons.module.enums;

/**
 * 模块
 *
 * @author PerccyKing
 * @since 2023/3/4 22:04
 */
public enum HcModuleEnums implements HcModuleItf {

    /**
     * 模块
     */
    HC_MODULE("模块", "HC-MODULE", ""),

    /**
     * 工具类
     */
    HC_COMMONS_UTIL("工具类", "HC-COMMONS-UTIL", ""),

    /**
     * 枚举
     */
    HC_COMMONS_ENUMS("枚举", "HC-COMMONS-ENUMS", ""),

    /**
     * 实体-service-mapper
     */
    HC_COMMONS_ESM("entity-service-mapper", "HC-COMMONS-ESM", ""),

    /**
     * 对象
     */
    HC_COMMONS_OBJECT("对象", "HC-COMMONS-OBJECT", "各种对象、PO、VO、BO、常量等"),

    /**
     * 注解
     */
    HC_ANNOTATION("注解", "HC-ANNOTATION", ""),
    ;

    /**
     * 模块名称
     */
    private final String name;

    /**
     * 模块代码
     */
    private final String code;

    /**
     * 模块描述
     */
    private final String desc;

    HcModuleEnums(String name, String code, String desc) {
        this.name = name;
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
