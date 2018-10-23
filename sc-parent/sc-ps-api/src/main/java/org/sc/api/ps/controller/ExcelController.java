package org.sc.api.ps.controller;

import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.sc.api.ps.model.vo.ExportDataVo;
import org.sc.api.ps.service.ExcelService;
import org.sc.common.controller.BaseController;
import org.sc.common.model.vo.Response;
import org.sc.common.utils.web.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * spring boot集成poi实现Excel的导入导出
 * 
 * @author lv
 */
@Api(value = "excel",description = "Excel导入导出")
@RestController
@RequestMapping(value = "/api/ps/excel")
public class ExcelController  extends BaseController {
	@Autowired
	private ExcelService excelService;
	
	@ApiOperation(value = "以Excel模板表导入角色信息")
	@RequestMapping(value="/importRole",method = RequestMethod.POST)
	public Response importRole(@RequestBody MultipartFile file) throws Exception {
        if (file==null) {
            return ResponseUtil.error("file不能为空");
        }
		return excelService.importRole(file);
	}
	
	
	@ApiOperation(value = "导出角色信息列表")
	@RequestMapping(value="/export",method = RequestMethod.GET)
	public Response export() throws Exception {
        return ResponseUtil.ok(excelService.export());
	}
	
	
	@ApiOperation(value = "导出角色信息列表(返回文件二进制流)")
	@RequestMapping(value="/exportFileStreams",method = RequestMethod.GET)
	public ResponseEntity<byte[]> exportFileStreams() throws Exception {
		ExportDataVo vo = excelService.exportFileStreams();
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String(vo.getFileName().getBytes("UTF-8"), "iso-8859-1");
		headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(vo.getFileByte(), headers,HttpStatus.CREATED);
	}
	
	
}
