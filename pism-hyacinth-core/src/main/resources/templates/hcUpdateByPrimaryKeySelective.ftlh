update ${tableName}
<set>
    <#list fieldList as field>
        <if test="${field.property} != null">
            ${field.column} = ${r'#{.'}${field.property}${r'}'},
        </if>
    </#list>
</set>
where ${keyColumn} = ${r'#{'}${keyProperty}${r'}'}