insert into ${tableName}
<trim prefix="(" suffix=")" suffixOverrides=",">
    <if test="${keyProperty} != null">
        ${keyColumn},
    </if>
    <#list fieldList as field>
        <if test="${field.property} != null">
            ${field.column},
        </if>
    </#list>
</trim>
<trim prefix="values (" suffix=")" suffixOverrides=",">
    <if test="${keyProperty} != null">
        ${r'#{'}${keyProperty}${r'}'},
    </if>
    <#list fieldList as field>
        <if test="${field.property} != null">
            ${field.property},
        </if>
    </#list>
</trim>