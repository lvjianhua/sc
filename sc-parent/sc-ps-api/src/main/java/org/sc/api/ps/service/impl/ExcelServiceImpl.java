package org.sc.api.ps.service.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.sc.api.ps.model.vo.ExportDataVo;
import org.sc.api.ps.service.ExcelService;
import org.sc.common.model.vo.Response;
import org.sc.common.utils.GetDate;
import org.sc.common.utils.poi.api.row.RowContext;
import org.sc.common.utils.poi.api.sheet.SheetContext;
import org.sc.common.utils.poi.api.wookbook.WorkbookContext;
import org.sc.common.utils.poi.impl.wookbook.WorkbookContextFactory;
import org.sc.common.utils.web.ResponseUtil;
import org.sc.facade.ps.model.table.Role;
import org.sc.facade.ps.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.monitorjbl.xlsx.StreamingReader;

@Service
public class ExcelServiceImpl implements ExcelService {

	@Autowired
	private RoleService roleService;

	@Override
	public Response importRole(MultipartFile file) throws Exception {
		try {
			// 流式的读取 xlsx 文件，将一些特别大的文件拆分成小的文件去读。
			Workbook workbook = StreamingReader.builder().rowCacheSize(1000)
					.bufferSize(4096).open(file.getInputStream());
			String value = "";
			int i = 0;
			for (Sheet sheet : workbook) {
				Role answer = null;
				for (Row r : sheet) {
					if (i == 0) {// 遍历每一行，注意：第 0 行为标题
						i = 1;
						continue;
					}
					int ceIndex = 0;
					answer = new Role();
					for (Cell cell : r) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							value = cell.getStringCellValue();
						} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							if (file.getOriginalFilename().endsWith(".xls")) {
								DataFormatter formatter = new DataFormatter();
								value = formatter.formatCellValue(cell);
							} else {
								value = cell.getStringCellValue();
							}
						}
						switch (ceIndex) {
						case 0:
							answer.setRoleName(value);// 角色名
							break;
						case 1:
							answer.setRoleCode(value);// 角色编号
							break;
						case 2:
							answer.setParentId(value);// 父级id
							break;
						}
						ceIndex++;
					}
					roleService.addRole(answer);// 新增角色
				}
			}
		} catch (IOException e) {
			return ResponseUtil.error(e.getMessage());
		}
		return ResponseUtil.ok();
	}

	@Override
	public String export() throws IOException {

	    HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("角色列表");
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 5));
        //设置第一列单元格宽度
        sheet.setColumnWidth(0,100*100);
        //设置第二列单元格宽度
        sheet.setColumnWidth(1,100*100);
        //创建第一行
        HSSFRow row0 = sheet.createRow(0);
        //创建第二行
        HSSFRow row1 = sheet.createRow(1);
        //设置第一行单元格高度
        row0.setHeight((short) 400);
        //创建第一行第一列单元格
        HSSFCell cell0_1 = row0.createCell(0);
        //创建第二行第一列单元格
        HSSFCell cell0_2 = row1.createCell(0);
        //设置单元格的值
        cell0_1.setCellValue("角色列表");
        //改变字体样式，步骤
        HSSFFont hssfFont = wb.createFont();
        //设置字体,红色
        hssfFont.setColor(HSSFFont.COLOR_RED);
        //字体粗体显示
        hssfFont.setBold(true);
        hssfFont.setFontName("宋体");
        // 字体大小
        hssfFont.setFontHeightInPoints((short) 22);
        //设置样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(hssfFont);
        //设置单元格背景色
        cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.DASH_DOT_DOT);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
 
        //3.单元格使用样式，设置第一行第一列单元格样式
        cell0_1.setCellStyle(cellStyle);
        cell0_2.setCellStyle(cellStyle);
        //创建表头
        setTitle(wb, sheet);
        List<Role> roles = roleService.getRoles("");
        //新增数据行，并且设置单元格数据
        int rowNum = 4;
        for (Role role:roles) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(role.getRoleName());
            row.createCell(1).setCellValue(role.getRoleCode());
            row.createCell(2).setCellValue(role.getParentId());
            rowNum++;
        }
        //生成excel文件
        FileOutputStream fileOut = new FileOutputStream("e:\\"+System.currentTimeMillis()+".xls");
        wb.write(fileOut);
        fileOut.close();

		return "1";
	}

	/***
	 * 设置表头
	 * 
	 * @param workbook
	 * @param sheet
	 */
	private void setTitle(HSSFWorkbook workbook, HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(3);
		
		// 设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 20 * 256);
		sheet.setColumnWidth(2, 20 * 256);

		// 设置为居中加粗
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);

		HSSFCell cell;
		cell = row.createCell(0);
		cell.setCellValue("角色名");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("角色编号");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("父级id");
		cell.setCellStyle(style);
	}

	
	@Override
	public ExportDataVo exportFileStreams() {
		ExportDataVo vo=new ExportDataVo();
		vo.setFileName(System.currentTimeMillis() + ".xls");
		WorkbookContext workbookCtx = WorkbookContextFactory.createWorkbook(true);
		CellStyle center = workbookCtx.toNativeWorkbook().createCellStyle();
		center.setWrapText(true);
        CellStyle left = workbookCtx.toNativeWorkbook().createCellStyle();
        SheetContext sheetCtx = workbookCtx.createSheet("角色列表");
		RowContext top = sheetCtx.nextRow().skipCell().setRowHeight(40);
		top.text("角色名", center).setColumnWidth(10);
		top.text("角色编号", center).setColumnWidth(15);
		top.text("父级id", center).setColumnWidth(15);
		List<Role> roles = roleService.getRoles("");
		for(int i=0;i<roles.size();i++){
			Role role = roles.get(i);
			RowContext tempSkipCell = sheetCtx.nextRow().skipCell().setRowHeight(25);
			tempSkipCell.text(role.getRoleName(),center);
			tempSkipCell.text(role.getRoleCode(),center);
			tempSkipCell.text(role.getParentId(),center);
		}
		
		vo.setFileByte(workbookCtx.toNativeBytes());
		return vo;
	}
}
