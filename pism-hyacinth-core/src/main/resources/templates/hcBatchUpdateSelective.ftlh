update ${tableName}
<trim prefix="set" suffixOverrides=",">
    <#list fieldList as field>
        <trim prefix="${field.column} = case" suffix="end,">
            <foreach collection="list" index="index" item="item">
                <if test="item.${field.property} != null">
                    when ${keyColumn} = ${r'#{item.'}${keyProperty}${r'}'} then ${r'#{item.'}${field.property}${r'}'}
                </if>
            </foreach>
        </trim>
    </#list>
</trim>
where ${keyColumn} in
<foreach close=")" collection="list" item="item" open="(" separator=", ">
    ${r'#{item.'}${keyProperty}${r'}'}
</foreach>