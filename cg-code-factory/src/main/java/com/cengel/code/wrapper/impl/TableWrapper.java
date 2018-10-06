package com.cengel.code.wrapper.impl;

import com.cengel.code.model.config.DefaultConfig;
import com.cengel.code.model.dto.TablesDto;
import com.cengel.code.task.TypeConverter;
import com.cengel.code.util.TabColStrUtil;
import com.cengel.code.wrapper.TypeWrapper;

/**
 * @author zhz
 * @version V1.0
 * @create 2018-03-07 18:45
 **/
public class TableWrapper implements TypeWrapper<TablesDto> {

    @Override
    public TablesDto convert(TablesDto table, TypeConverter typeConverter) {
        // 根据规则生成实体名称
        table.setJavacName(TabColStrUtil.tabName2jfiled(table.getTableName()));
        table.setJavaName(TabColStrUtil.firstLowerCase(table.getJavaName()));
        table.setWebPath(DefaultConfig.getWebPath());
        table.setOutPath(DefaultConfig.getOutPath());
        table.setBasePackage(DefaultConfig.getBasePackage());
        table.setSchema(DefaultConfig.getSchema());
        return table;
    }




}
