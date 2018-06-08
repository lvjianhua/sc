package org.sc.api.ps.model.vo;

import lombok.Data;

@Data
public class SecurityConfigVo {

	public SecurityConfigVo(String attribute) {
		this.attribute = attribute;
	}

	private String attribute;
}
