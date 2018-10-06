package com.cengel.hibernate.controller;

import com.cengel.hibernate.service.BaseService;
import com.cengel.starbucks.model.entity.BEntity;
import com.cengel.starbucks.model.obj.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/7/25 - 20:58
 * @Version V1.0
 **/
public abstract class BaseController<S extends BaseService, T extends BEntity> {

	@Autowired
	protected S service;

	@ResponseBody
	@RequestMapping(value = "insert")
	public Response insert(T t) {
		service.insert(t);
		return Response.success();
	}

	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Response save(T t) {
		service.saveOrUpdate(t);
		return Response.success();
	}

	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.PATCH)
	public Response update(T t) {
		service.update(t);
		return Response.success();
	}

	@ResponseBody
	@RequestMapping(value = "del", method = RequestMethod.DELETE)
	public Response del(Integer id) {
		service.del(id);
		return Response.success();
	}
}
