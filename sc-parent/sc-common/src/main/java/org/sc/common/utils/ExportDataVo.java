package org.sc.common.utils;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author 丁泽锋
 * @create 
 **/
@Data
public class ExportDataVo  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/** 文件名称 **/
    @ApiModelProperty(value = "文件名称")
    private String fileName;
	
	
    /** 文件二进制流 **/
    @ApiModelProperty(value = "文件二进制流")
    private byte[] fileByte;

}
