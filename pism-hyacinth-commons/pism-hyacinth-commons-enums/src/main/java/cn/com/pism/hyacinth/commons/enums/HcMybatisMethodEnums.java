package cn.com.pism.hyacinth.commons.enums;

/**
 * @author PerccyKing
 * @since 2023/3/5 14:59
 */
public enum HcMybatisMethodEnums implements HcMybatisMethodItf {

    /**
     * 批量插入
     */
    HC_BATCH_INSERT("hcBatchInsert", "批量新增"),

    /**
     * 批量更新
     */
    HC_BATCH_UPDATE("hcBatchUpdate", "批量更新"),

    /**
     * 选择更新部分字段
     */
    HC_BATCH_UPDATE_SELECTIVE("hcBatchUpdateSelective", "选择批量更新 只更新有值的字段"),

    /**
     * 新增或更新
     */
    HC_INSERT_OR_UPDATE("hcInsertOrUpdate", "新增或更新"),

    /**
     * 选择新增或更新
     */
    HC_INSERT_OR_UPDATE_SELECTIVE("hcInsertOrUpdateSelective", "选择新增或更新"),

    /**
     * 选择新增
     */
    HC_INSERT_SELECTIVE("hcInsertSelective", "选择新增"),

    /**
     * 按主键选择更新
     */
    HC_UPDATE_BY_PRIMARY_KEY_SELECTIVE("hcUpdateByPrimaryKeySelective", "按主键选择更新");

    private final String methodName;

    private final String desc;

    HcMybatisMethodEnums(String methodName, String desc) {
        this.methodName = methodName;
        this.desc = desc;
    }

    /**
     * <p>
     * 获取方法名称
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 方法名称
     * @since 2023/3/5 14:57
     */
    @Override
    public String getMethodName() {
        return this.methodName;
    }

    /**
     * <p>
     * 获取方法简介
     * </p>
     * by PerccyKing
     *
     * @return {@link String} 方法简介
     * @since 2023/3/5 14:58
     */
    @Override
    public String getDesc() {
        return this.desc;
    }
}
