package org.sc.common.utils.poi.api.wookbook;

import org.apache.poi.ss.usermodel.Workbook;
import org.sc.common.utils.poi.api.configuration.ConfigurationProvider;
import org.sc.common.utils.poi.api.sheet.SheetContext;
import org.sc.common.utils.poi.api.style.StyleConfigurable;
import org.sc.common.utils.poi.api.style.StyleConfiguration;
import org.sc.common.utils.poi.api.style.StyleRegistry;
import org.apache.poi.ss.usermodel.CellStyle;
/**
 * Workbook context.
 * 
 * @author i.voshkulat
 *
 */
public interface WorkbookContext extends ConfigurationProvider, StyleRegistry, StyleConfiguration, StyleConfigurable<WorkbookContext> {

	/**
	 * Create new sheet within the current workbook.
	 * 
	 * @param sheetName sheet name
     * @return created {@link SheetContext}
	 */
	public SheetContext createSheet(String sheetName);
	
	/**
	 * Use an existing sheet within the current workbook
	 *
	 * @param sheetName sheet to use
	 * @return existing {@link SheetContext}
	 * @throws java.lang.IllegalArgumentException if a sheet with the given name doesn't exist
	 */
	public SheetContext useSheet(String sheetName); 
		
    /**
     * Retrieve POI workbook referred to by current {@link WorkbookContext}.
     * Please refrain from using the exposed {@link Workbook} directly unless you need functionality of POI not provided by {@link WorkbookContext}.
     * To retrieve a workbook for saving to a file look at {@link #toNativeBytes()} instead.
     * 
     * @return native POI {@link Workbook}
     */	
	public Workbook toNativeWorkbook();
	
	/**
	 * Create .xls file binary representation for current {@link WorkbookContext}
	 * 
	 * @return excel file binary representation
	 */
	public byte[] toNativeBytes();

	/** 
	 * xls and xlsx files have different default fonts 
	 *
	 * @return font name
	 */
	public String getDefaultFontName();
	
	
	public CellStyle createLeftStyleCell(WorkbookContext workbookCtx,boolean wrapText);
	
	
	public CellStyle createCenterStyleCell(WorkbookContext workbookCtx,boolean wrapText);
	
	public CellStyle createRedStyleCell(WorkbookContext workbookCtx,boolean wrapText);
	
	public CellStyle createTopStyleCell(WorkbookContext workbookCtx,boolean wrapText);
	
	public CellStyle createRightStyleCell(WorkbookContext workbookCtx,boolean wrapText);
	
	
}
