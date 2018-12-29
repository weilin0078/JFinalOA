package com.pointlion.sys.mvc.common.utils.office.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	@SuppressWarnings("resource")
	public static List<List<String>> excelToList(String filePath) throws FileNotFoundException, IOException{
		List<List<String>> result = new ArrayList<List<String>>();
        // 读取，全部sheet表及数据
		Workbook workbook = null;
        if (filePath.toLowerCase().endsWith("xls")) {
            workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
        } else {
            workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        }
        Sheet sheet = workbook.getSheetAt(0);
        int maxRowNum = sheet.getLastRowNum()+1;
        for (int j = 0; j < maxRowNum; j++) {// 循环所有行
        	List<String> rowList = new ArrayList<String>();
            Row row = sheet.getRow(j);//读取行
            if (row != null) {
                for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
                	String cellStr = row.getCell(k) != null?DbImportExcelUtils.getCellStringValue(row.getCell(k)):"";
                	rowList.add(cellStr);
                }
            }
            result.add(rowList);
        }
        return result;
	}
}
