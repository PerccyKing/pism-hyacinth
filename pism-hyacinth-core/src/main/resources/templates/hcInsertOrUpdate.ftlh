insert into ${tableName}
        (${allSqlSelect})
        values (
${r'#{'}${keyProperty}${r'}'},
<#list fieldList as field>
    ${r'#{'}${field.property}${r'}'}
    <#if field_has_next>
        ,
    </#if>
</#list>
            )
        on duplicate key update ${keyColumn}          = ${r'#{'}${keyProperty}${r'}'},
<#list fieldList as field>
    ${field.column} = ${r'#{'}${field.property}${r'}'}
    <#if field_has_next>
        ,
    </#if>
</#list>