package com.cengel.code.wrapper.impl;

import com.cengel.code.model.dto.ColumnsDto;
import com.cengel.code.task.TypeConverter;
import com.cengel.code.util.TabColStrUtil;
import com.cengel.code.wrapper.TypeWrapper;
import com.cengel.starbucks.annotation.Description;
import com.cengel.starbucks.model.entity.BaseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhz
 * @version V1.0
 * @create 2018-03-07 18:45
 **/
public class ColumnsWrapper implements TypeWrapper<ColumnsDto> {

	//where隐藏字段
	private static String[] WhereHides = { BaseEntity.PROP_CREATE_TIME, BaseEntity.PROP_DELETED, BaseEntity.PROP_MODIFIED_TIME };

	@Description("Entity中不生成的字段")
	private static List<String> HideOnEntityFields;
	@Description("页面中不展示的字段")
	private static List<String> hideOnPageFields;

	static {
		HideOnEntityFields = new ArrayList<>();
		HideOnEntityFields.add(BaseEntity.PROP_ID);
		HideOnEntityFields.add(BaseEntity.PROP_DELETED);

		initHideOnPageList();
	}

	private static void initHideOnPageList() {
		hideOnPageFields = new ArrayList<>();
		for (Field field : BaseEntity.class.getDeclaredFields()) {
			hideOnPageFields.add(field.getName());
		}
	}

	@Override
	public ColumnsDto convert(ColumnsDto dto, TypeConverter typeConverter) {
		// 根据规则生成实体名称
		dto.setJavacName(TabColStrUtil.tabName2jfiled(dto.getName()));
		dto.setJavaName(TabColStrUtil.firstLowerCase(dto.getJavacName()));
		dto.setJavaType(typeConverter.toJavaType(dto.getDataType()));
		dto.setDataType(typeConverter.toJdbcType(dto.getDataType()));
		for (String whereHide : WhereHides) {
			if (whereHide.equalsIgnoreCase(dto.getJavaName())) {
				dto.setWhereHide(true);
			}
		}

		/********************|| 设置entity需要隐藏的字段 ||********************/
		HideOnEntityFields.forEach(baseField -> {
			if (baseField.equalsIgnoreCase(dto.getJavaName())) {
				dto.setVisible(false);
			}
		});

		/********************|| 设置页面需要隐藏的字段 ||********************/
		if (dto.getName().toUpperCase().endsWith("ID")) {
			dto.setHideOnPage(true);
		}
		hideOnPageFields.forEach(bf -> {
			if (bf.equalsIgnoreCase(dto.getJavaName())) dto.setHideOnPage(true);
		});

		return dto;
	}

	private static List<String> getBaseFiled(Class c) {
		List<String> ret = new ArrayList<String>();
		return getBaseFiled(c, ret);
	}

	//递归找父类
	private static List<String> getBaseFiled(Class cls, List<String> ret) {
		if (cls != null) {
			for (Field field : cls.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers())) continue;
				ret.add(field.getName());
			}
		}
		if (cls != null && cls.getSuperclass() != null) {
			getBaseFiled(cls.getSuperclass(), ret);
		}
		return ret;
	}

}
