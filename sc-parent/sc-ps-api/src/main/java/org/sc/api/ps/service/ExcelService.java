package org.sc.api.ps.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.sc.api.ps.model.vo.ExportDataVo;
import org.sc.common.model.vo.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
	
	 /**
     * 以Excel模板表导入角色信息
     * 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	 * @throws Exception 
     */
    Response importRole(MultipartFile file) throws EncryptedDocumentException, InvalidFormatException, Exception;

    /**
     * 导出角色信息列表
     * 
     * @return
     * @throws IOException 
     */
    String export() throws IOException;

    /**
     * 导出角色信息列表(返回文件二进制流)
     * 
     * @return
     */
	ExportDataVo exportFileStreams();


}
