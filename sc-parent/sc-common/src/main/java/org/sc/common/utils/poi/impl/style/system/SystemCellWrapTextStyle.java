package org.sc.common.utils.poi.impl.style.system;

import org.apache.poi.ss.usermodel.CellStyle;
import org.sc.common.utils.poi.api.style.AdditiveStyle;
import org.sc.common.utils.poi.api.wookbook.WorkbookContext;


public enum SystemCellWrapTextStyle implements AdditiveStyle {
	
	WRAP_TEXT;
	
	@Override
	public void enrich(WorkbookContext workbookContext, CellStyle style) {
		style.setWrapText(true);
	}

	@Override
	public Enum<SystemStyleType> getType() {
		return SystemStyleType.CELL_WRAP_TEXT;
	}

}