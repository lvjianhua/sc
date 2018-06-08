package org.sc.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ReadExcelUtil {
	
	/**
	* @Description:读取网络文件excel
	* @param 
	* @return List<List<String>>
	* @throws
	* @author  dingzefeng
	 */
	public static List<List<String>> readExeclByFilePath(String filePath){
		List<List<String>> list = null;
		InputStream instream =null;
		try {
			URL url = new URL(filePath);
			URLConnection conn = url.openConnection();
			instream = conn.getInputStream();
			Workbook wb = WorkbookFactory.create(instream);
			list = readExcel(wb);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(instream!=null){
					instream.close();
				}
			} catch (Exception e2) {
			}
		}
		
		return list;
	}

	
	private static List<List<String>> readExcel(Workbook wb) throws Exception{
		Sheet sheet = null;
		List<List<String>> wbList = new ArrayList<List<String>>();
		try {
			
			for(int sheetIx = 0; sheetIx < wb.getNumberOfSheets(); sheetIx ++){
				sheet = wb.getSheetAt(sheetIx);
				if(sheet != null){
					if(wb.isSheetHidden(sheetIx)){//该表格隐藏
                		continue;
                	}
					int firstRowNum = sheet.getFirstRowNum();
					int lastRowNum = sheet.getLastRowNum();
					
					for(int rownum = firstRowNum;rownum <= lastRowNum;rownum ++){
						Row row = sheet.getRow(rownum);
						if(row != null){
							if(row.getZeroHeight()){//行高为0
								continue;
							}
							List<String> rowList = new ArrayList<String>();
							short firstCellNum = row.getFirstCellNum();
							short lastCellNum = row.getLastCellNum();
							
							if(firstCellNum < 0)firstCellNum = 0;
							for(short cellnum = firstCellNum;cellnum <= lastCellNum; cellnum ++){
								Cell cell = row.getCell(cellnum);
								if(cell != null){
									rowList.add(getCellValue(cell));
								}
							}
							wbList.add(rowList);
							rowList = null;
						}
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wbList;
	}


	/**
     * 取得单元格的值
     * @param cell
     * @return
     * @throws IOException
     */
	private static String getCellValue(Cell cell) throws IOException {
        String value = "";
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            value = cell.getRichStringCellValue().toString().replaceAll("[\n]", "<br>");
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {/**日期类型**/
                Date date = cell.getDateCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                value = sdf.format(date);
            } else {
                value = new DecimalFormat("#.######").format(cell.getNumericCellValue());
            }
        }else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            value = "";
        }
        return replaceZero(value);
    }
    
    private static String replaceZero(String val){
		if(validateDouble(val)){
			if(val.indexOf(".") != -1){
				return val.replaceAll("0+$", "").replaceAll("[.]$", "");
			}else{
				return val;
			}
		}else{
			return val;
		}
	}
    
    
    /**
    * @Description:通过流来读取excel
    * @param 
    * @return List<List<String>>
    * @throws
    * @author  dingzefeng
     */
    public static List<List<String>> readExeclByStream(InputStream is){
    	List<List<String>> list = null;
		try {
			Workbook wb = WorkbookFactory.create(is);
			list = readExcel(wb);
		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				is.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
    }
    
    
    /**
	 * 验证是否为数字
	 * @author dingzefeng
	 * @param str
	 * @return
	 */
	public static boolean validateDouble(String str){
		String reg = "[1-9]+[0-9]*(.?)[0-9]+$|[0-9]|0.[0-9]+";
		if(!validateBlank(str)){
			return Pattern.compile(reg).matcher(str.trim()).matches();
		}else{
			return false;
		}
	}
    
	/**
	 * 验证某一字符串是不是   null or ""
	 * @param src 验证的字符串
	 * @return true or false
	 */
	public static boolean validateBlank(String src){
		return (src == null) || src.trim().equals("") ;
	}
	
	
}
