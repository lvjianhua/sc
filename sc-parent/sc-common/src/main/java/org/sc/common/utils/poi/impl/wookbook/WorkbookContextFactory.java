package org.sc.common.utils.poi.impl.wookbook;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sc.common.utils.poi.api.configuration.Configuration;
import org.sc.common.utils.poi.api.style.StyleConfiguration;
import org.sc.common.utils.poi.api.wookbook.WorkbookContext;
import org.sc.common.utils.poi.impl.configuration.DefaultConfiguration;
import org.sc.common.utils.poi.impl.style.defaults.DefaultStyleConfiguration;
public class WorkbookContextFactory {
	
	// if HSSF (.xls) or XSSF (.xlsx) format is used for new workbooks ?

    /**
     * <p>
     * If you use Dependency Injection, inject WorkbookContextFactory
     * and in your config wire/bind one of these 2 static factory methods as implementation.
     * </p>
     * <p>
     * Tip: Spring has &lt;bean factory-method="..." /&gt; attribute. Guice has @Provides methods.
     * </p>
     * @return xls workbook factory
     */
    public static WorkbookContext createWorkbook(boolean useHSSF) {
        return useWorkbook(newWorkBook(useHSSF));
    }
	
    public static WorkbookContext createWorkbook(boolean useHSSF, Configuration configuration) {
        return useWorkbook(newWorkBook(useHSSF), configuration);
    }

    public static WorkbookContext createWorkbook(boolean useHSSF, StyleConfiguration styleConfiguration) {
        return useWorkbook(newWorkBook(useHSSF), styleConfiguration);
    }

	private static Workbook newWorkBook(boolean useHSSF) {
		return useHSSF ? new HSSFWorkbook() : new XSSFWorkbook();
	}

    private static String getDefaultFontName(Workbook workbook) {
    	workbook.createCellStyle().setWrapText(true);;
        if (workbook instanceof HSSFWorkbook) {
            return HSSFFont.FONT_ARIAL;
        } else if (workbook instanceof XSSFWorkbook) {
            return XSSFFont.DEFAULT_FONT_NAME;
        } else {
            throw new IllegalArgumentException("Workbook is expected to be an instance of HSSFWorkbook or XSSFWorkbook: "
                    + workbook);
        }
    }
    
    /**
     * Use an existing WorkBook
     * @param workbook could be of type xls (HSSFWorkbook) or xlsx (XSSFWorkbook)
     * @return new context for existing workbook
     */
    public static WorkbookContext useWorkbook(Workbook workbook) {
        return new WorkbookContextImpl(workbook, new DefaultStyleConfiguration(), new DefaultConfiguration(),
                getDefaultFontName(workbook));
    }
    
    public static WorkbookContext useWorkbook(Workbook workbook, Configuration configuration) {
        return new WorkbookContextImpl(workbook, new DefaultStyleConfiguration(), configuration,
                getDefaultFontName(workbook));
    }

    public static WorkbookContext useWorkbook(Workbook workbook, StyleConfiguration styleConfiguration) {
        return new WorkbookContextImpl(workbook, styleConfiguration, new DefaultConfiguration(),
                getDefaultFontName(workbook));
    }
    
}
