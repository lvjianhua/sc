package org.sc.common.utils.poi.impl.wookbook;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.sc.common.utils.poi.api.configuration.Configuration;
import org.sc.common.utils.poi.api.sheet.SheetContext;
import org.sc.common.utils.poi.api.style.Style;
import org.sc.common.utils.poi.api.style.StyleConfiguration;
import org.sc.common.utils.poi.api.wookbook.WorkbookContext;
import org.sc.common.utils.poi.impl.sheet.SheetContextImpl;
import org.sc.common.utils.poi.impl.style.InheritableStyleConfiguration;

public class WorkbookContextImpl extends InheritableStyleConfiguration<WorkbookContext> implements WorkbookContext {

    private final Workbook workbook;
    
    private final Map<Style, CellStyle> registeredStyles = new HashMap<Style, CellStyle>();
    
    private final Configuration configuration;
	private final String defaultFontName;

	WorkbookContextImpl(Workbook workbook, StyleConfiguration styleConfiguration, Configuration configuration,
						String defaultFontName) {
    	super(styleConfiguration);
        this.workbook = workbook;
        this.configuration = configuration;
		this.defaultFontName = defaultFontName;
	}
    
    @Override
    public SheetContext createSheet(String sheetName) {
        return new SheetContextImpl(workbook.createSheet(sheetName), this);
    }
    
	@Override
	public SheetContext useSheet(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		checkArgument(sheet != null, "Sheet %s doesn't exist in workbook", sheetName);
		return new SheetContextImpl(sheet, this);
	}    
    
	@Override
	public CellStyle registerStyle(Style style) {
		checkArgument(style != null, "Style is null");

		CellStyle registeredStyle = registeredStyles.get(style);
		
		if (registeredStyle == null) {
			registeredStyle = workbook.createCellStyle();
			style.enrich(this, registeredStyle);
			registeredStyles.put(style, registeredStyle);
		}
		
		return registeredStyle;
	}
    
    @Override
    public byte[] toNativeBytes() {
    	try {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		workbook.write(baos);
    		return baos.toByteArray();
    	} catch (IOException e) {
    		throw new RuntimeException("Quite unlikely case as we are working with an in-memory data. Wrap to avoid handling checked exception", e);
		}
    }

	@Override
	public String getDefaultFontName() {
		return defaultFontName;
	}

	@Override
	public Workbook toNativeWorkbook() {
		return workbook;
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public CellStyle createCenterStyleCell(WorkbookContext workbookCtx,boolean wrapText) {
		CellStyle center = workbookCtx.toNativeWorkbook().createCellStyle();
		center.setWrapText(wrapText);
		center.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
		center.setAlignment(HorizontalAlignment.CENTER);// 居中
		return center;
	}
	
	@Override
	public CellStyle createRightStyleCell(WorkbookContext workbookCtx,boolean wrapText) {
		CellStyle right = workbookCtx.toNativeWorkbook().createCellStyle();
		right.setWrapText(wrapText);
		right.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
		right.setAlignment(HorizontalAlignment.RIGHT);// 右对齐
		return right;
	}
	
	@Override
	public CellStyle createLeftStyleCell(WorkbookContext workbookCtx,boolean wrapText) {
		CellStyle left = workbookCtx.toNativeWorkbook().createCellStyle();
		left.setWrapText(wrapText);
		left.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
		left.setAlignment(HorizontalAlignment.LEFT);// 左对齐
		return left;
	}

	@Override
	public CellStyle createRedStyleCell(WorkbookContext workbookCtx,boolean wrapText) {
		CellStyle red = workbookCtx.toNativeWorkbook().createCellStyle();
		red.setWrapText(wrapText);
		red.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
		red.setAlignment(HorizontalAlignment.CENTER);// 居中
		Font redFont = workbookCtx.toNativeWorkbook().createFont();
		redFont.setColor(HSSFColorPredefined.RED.getIndex());
		redFont.setBold(true);
		red.setFont(redFont);
		return red;
	}

	@Override
	public CellStyle createTopStyleCell(WorkbookContext workbookCtx,boolean wrapText) {
		CellStyle top = workbookCtx.toNativeWorkbook().createCellStyle();
		top.setWrapText(wrapText);
		top.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
		top.setAlignment(HorizontalAlignment.CENTER);// 居中
		Font topFont = workbookCtx.toNativeWorkbook().createFont();
		topFont.setColor(HSSFColorPredefined.BLACK.getIndex());
		topFont.setBold(true);
		top.setFont(topFont);
		return top;
	}

	
}