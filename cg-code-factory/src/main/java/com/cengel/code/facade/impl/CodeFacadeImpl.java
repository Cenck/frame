package com.cengel.code.facade.impl;

import com.cengel.code.facade.CodeFacade;
import com.cengel.code.model.config.DefaultConfig;
import com.cengel.code.model.dto.TablesDto;
import com.cengel.code.model.pojo.CodeWritePojo;
import com.cengel.code.service.impl.TableServiceImpl;
import com.cengel.code.task.FileWriteTask;
import com.cengel.code.util.TabColStrUtil;
import com.cengel.starbucks.constant.Symbol;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/26 - 15:04
 * @Version V1.0
 **/
@Service
public class CodeFacadeImpl implements CodeFacade {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(5);

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	private TableServiceImpl     tableService;

	@Override
	public void execute() {
		//先删除旧数据
		this.delOldPdtData();
		try {
			if (DefaultConfig.getTableName() != null) {
				String[] tabList = DefaultConfig.getTableName().split(",");
				for (String tabName : tabList) {
					//TODO 取表信息
					TablesDto dto = tableService.getDto(tabName);
					logger.error("===========================");
					logger.warn("==：执行表: " + DefaultConfig.getSchema() + "-" + tabName);
					logger.error("===========================");
					//TODO 拆分表名，逐个写入
					buildAndExec(DefaultConfig.getTemplates(), dto);
				}
			}
			//所有线程启动就绪以后，监控线程执行完毕
			scheduledExecutor.shutdown();
			while (true) {
				if (scheduledExecutor.isTerminated()) {
					logger.warn("所有执行完毕,感谢使用！");
					break;
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			scheduledExecutor.shutdownNow();
			logger.warn(e.getMessage());
			logger.warn("出现错误，程序退出。");
		}

	}

	//删除旧数据
	private void delOldPdtData() {
		File root = new File(DefaultConfig.getOutPath());
		if (!root.exists()) {
			root.mkdir();
		} else {
			try {
				FileUtils.deleteDirectory(root);
			} catch (IOException e) {
				e.printStackTrace();
			}
			root.mkdir();
		}
	}

	private void buildAndExec(List<LinkedHashMap> templateList, TablesDto dto) {
		for (LinkedHashMap linkedHashMap : templateList) {
			String templatePath = DefaultConfig.getBean().getProjectPrefix() + "/" + linkedHashMap.get("temPath").toString();
			String name = linkedHashMap.get("name").toString();
			String pkg = linkedHashMap.get("pkg").toString();
			String fileNameSuffix = linkedHashMap.get("fileNameSuffix").toString();

			String fileName = "", fileJavaName = "";
			if (fileNameSuffix.contains("java") || fileNameSuffix.contains(".xml")) {
				fileJavaName = TabColStrUtil.firstUpperCase(dto.getJavaName());
				pkg = "classes." + pkg; //不生成外层包
			} else {
				fileJavaName = TabColStrUtil.firstLowerCase(dto.getJavaName());
				pkg = "templates" + DefaultConfig.getWebPath();
			}
			if (fileNameSuffix.contains(Symbol.WAVE)) {
				fileName = fileNameSuffix.replace(Symbol.WAVE, fileJavaName);
			} else {
				fileName = fileJavaName + fileNameSuffix;
			}
			String outData = executeTemplate(templatePath, dto);

			final CodeWritePojo pojo = new CodeWritePojo();
			pojo.setRootPath(DefaultConfig.getOutPath());
			pojo.setFileName(fileName);
			pojo.setPkg(pkg);
			pojo.setDatas(outData);
			scheduledExecutor.execute(new FileWriteTask(pojo));
		}

	}

	private String executeTemplate(String name, Object context) {

		StringWriter out = new StringWriter();
		Template template = null;
		try {
			template = this.freeMarkerConfigurer.getConfiguration().getTemplate(name + ".ftl");
			template.process(context, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out.toString();
	}

}
