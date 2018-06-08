package org.sc.common.dao.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.sc.common.controller.BaseController;
import org.sc.common.model.vo.Response;
import org.sc.common.service.BaseLogicService;
import org.sc.common.utils.web.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

public abstract class BaseLogicController<T> extends BaseController {
	private BaseLogicService<T> baseDaoService;

	public BaseLogicController(BaseLogicService<T> baseDaoService) {
		this.baseDaoService = baseDaoService;
	}

	@ApiOperation(value = "添加对象")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Response<T> save(@ApiParam(value = "entity对象", name = "object", required = true) @RequestBody T object) {
		return ResponseUtil.ok(baseDaoService.save(object));
	}

	@ApiOperation(value = "更新对象")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response<T> update(@ApiParam(value = "entity对象", name = "object", required = true) @RequestBody T object) {
		return ResponseUtil.ok(baseDaoService.update(object));
	}

	@ApiOperation(value = "根据Id查询对象")
	@RequestMapping(value = "/getById", method = {RequestMethod.GET})
	public Response<T> getById(@ApiParam(value = "entity对象Id", name = "id", required = true) @RequestParam String id) {
		return ResponseUtil.ok(baseDaoService.getById(id));
	}
}
