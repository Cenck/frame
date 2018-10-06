package com.cengel.starbucks.io;

import com.cengel.starbucks.util.ObjectUtil;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/8/2 - 17:23
 * @Version V1.0
 **/
public class YamlReader {

	private Yaml yaml;

	private LinkedHashMap RootMap;

	public YamlReader(String yamlPath) {
		this.loadYml(yamlPath);
	}

	private void loadYml(String ymlPath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(ymlPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		yaml = new Yaml();
		RootMap = yaml.load(fis);
	}

	public <T> T readAndFill(String aisle, T t) {
		LinkedHashMap linkedHashMap = (LinkedHashMap) read(aisle);
		PropUtil.readAndFill(linkedHashMap, t);
		return t;
	}

	public Object read(String aisle) {
		LinkedHashMap<String, ?> root = RootMap;
		aisle = aisle.trim();
		LinkedHashMap<String, ?> oo = root;
		Object ret = null;
		for (String s : aisle.split("\\.")) {
			ret = oo.get(s);
			if (ret instanceof LinkedHashMap) {
				oo = (LinkedHashMap<String, ?>) ret;
			}
		}
		if (ObjectUtil.isNull(oo)) {
			throw new RuntimeException("在summer.yml中未找到" + aisle + "的属性配置");
		}
		return ret;
	}

}
