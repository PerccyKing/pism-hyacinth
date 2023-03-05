insert into ${tableName}
(${allSqlSelect})
values
<foreach collection="list" item="item" separator=",">
    (
    ${r'#{item.'}${keyProperty}${r'}'},
    <#list fieldList as field>
        ${r'#{item.'}${field.property}${r'}'}
        <#if field_has_next>
            ,
        </#if>
    </#list>
    )
</foreach>