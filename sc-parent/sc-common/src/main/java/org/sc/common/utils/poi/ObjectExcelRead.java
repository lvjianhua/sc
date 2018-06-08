package org.sc.common.utils.poi;

import org.sc.common.utils.GetDate;
import org.sc.common.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 从EXCEL导入到数据库
 * 创建人：FH 创建时间：2014年12月23日
 * @version
 */
public class ObjectExcelRead {

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
		List<List<String>> newlist = new ArrayList<>();
		try {
			URL url = new URL(filePath);
			URLConnection conn = url.openConnection();
			instream = conn.getInputStream();
			list =readExcel(instream,0,0,0);
			if(list!=null&&list.size()>0){
				Integer num =list.get(0).size();
				for (List<String> strings: list) {
					if(strings.size()==num){
						newlist.add(strings);
					}
				}
			}
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
		return newlist;
	}


	/**
	 * @param startrow //开始行号
	 * @param startcol //开始列号
	 * @param sheetnum //sheet
	 * @return list
	 */
	public static List<List<String>> readExcel(InputStream io, int startrow, int startcol, int sheetnum) {
		List<List<String>> varList = new ArrayList<>();

		try {
			HSSFWorkbook wb = new HSSFWorkbook(io);
			HSSFSheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

			for (int i = startrow; i < rowNum; i++) {					//行循环开始

				List<String> temp=new ArrayList<>();
				HSSFRow row = sheet.getRow(i); 							//行
				int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) {				//列循环开始

					HSSFCell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;
					if (null != cell) {
						switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
							case 0:
								if (DateUtil.isCellDateFormatted(cell)) {/**日期类型**/
									cellValue = GetDate.formatDate(cell.getDateCellValue(),GetDate.datetimeFormat);
								} else {
									cellValue = new DecimalFormat("#.####").format(cell.getNumericCellValue());
								}
								break;
							case 1:
								cellValue = cell.getStringCellValue();
								break;
							case 2:
								cellValue = GetDate.formatDate(cell.getDateCellValue(),GetDate.datetimeFormat);
								break;
							case 3:
								cellValue = "";
								break;
							case 4:
								cellValue = String.valueOf(cell.getBooleanCellValue());
								break;
							case 5:
								cellValue = String.valueOf(cell.getErrorCellValue());
								break;
						}
					} else {
						cellValue = "";
					}

					temp.add(cellValue);

				}
				varList.add(temp);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return varList;
	}

	/**
	 * @param filepath //文件路径
	 * @param filename //文件名
	 * @param startrow //开始行号
	 * @param startcol //开始列号
	 * @param sheetnum //sheet
	 * @return list
	 */
	public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<Object> varList = new ArrayList<Object>();

		try {
			File target = new File(filepath, filename);
			FileInputStream fi = new FileInputStream(target);
			HSSFWorkbook wb = new HSSFWorkbook(fi);
			HSSFSheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

			for (int i = startrow; i < rowNum; i++) {					//行循环开始

				HashMap varpd = new HashMap<>();
				HSSFRow row = sheet.getRow(i); 							//行
				int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) {				//列循环开始
					
					HSSFCell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;
					if (null != cell) {
						switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
						case 0:
							cellValue = String.valueOf((int) cell.getNumericCellValue());
							break;
						case 1:
							cellValue = cell.getStringCellValue();
							break;
						case 2:
							cellValue = cell.getNumericCellValue() + "";
							// cellValue = String.valueOf(cell.getDateCellValue());
							break;
						case 3:
							cellValue = "";
							break;
						case 4:
							cellValue = String.valueOf(cell.getBooleanCellValue());
							break;
						case 5:
							cellValue = String.valueOf(cell.getErrorCellValue());
							break;
						}
					} else {
						cellValue = "";
					}
					
					varpd.put("var"+j, cellValue);
					
				}
				varList.add(varpd);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return varList;
	}
}
