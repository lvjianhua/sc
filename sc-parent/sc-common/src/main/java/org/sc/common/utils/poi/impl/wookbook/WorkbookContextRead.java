package org.sc.common.utils.poi.impl.wookbook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;

import org.sc.common.utils.GetDate;
import org.sc.common.utils.Reflections;

public class WorkbookContextRead {

	public Object readReflect(Iterator<Cell> cells, Object model)
			throws NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ParseException {
		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		while (cells.hasNext()) {
			Cell cell = cells.next();
			for (int j = 0; j < field.length; j++) { // 遍历所有属性
				String name = field[j].getName(); // 获取属性的名字
				String type = field[j].getGenericType().toString(); // 获取属性的类型
				System.out.println(name + type);
				if (cell.getColumnIndex() == j) {
					String cellComment = getCellValue(cell);
					System.out.println(cellComment);
					if (StringUtils.isNotEmpty(cellComment)) {
						if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
							Reflections.invokeSetter(model, name, cellComment);
						}
						if (type.equals("class java.lang.Integer")) {
							Reflections.invokeSetter(model, name,
									Integer.valueOf(cellComment));
						}
						if (type.equals("class java.lang.Double")) {
							Reflections.invokeSetter(model, name,
									Double.valueOf(cellComment));
						}
						if (type.equals("class java.lang.Boolean")) {
							Reflections.invokeSetter(model, name,
									Boolean.valueOf(cellComment));
						}
						if (type.equals("class java.util.Date")) {
							Reflections.invokeSetter(model, name, GetDate.parseDate(cellComment,GetDate.yyyymmddhhmmss));
						}
					}
				}
			}
		}
		return model;
	}

	public String getCellValue(Cell cell) {
		String ret;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			ret = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			ret = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			ret = null;
			break;
		case Cell.CELL_TYPE_FORMULA:
			Workbook wb = cell.getSheet().getWorkbook();
			CreationHelper crateHelper = wb.getCreationHelper();
			FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
			ret = getCellValue(evaluator.evaluateInCell(cell));
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				Date theDate = cell.getDateCellValue();
				ret = GetDate.formatDate(theDate, GetDate.yyyymmddhhmmss);
			} else {
				ret = NumberToTextConverter.toText(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			ret = cell.getRichStringCellValue().getString();
			break;
		default:
			ret = null;
		}
		System.out.println(ret
				+ "+++++++++++++++++++++++++++++++++++++++++++++");
		return ret; // 有必要自行trim
	}
}
