package com.pointlion.mvc.common.utils.office.excel;

import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;

public class DbImportExcelUtils {
	@SuppressWarnings("deprecation")
	public static String getCellStringValue(Cell cell) {
        String cellValue = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                DecimalFormat df = new DecimalFormat("0");
                cellValue = df.format(cell.getNumericCellValue());
                break;

            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = cell.getStringCellValue();
                break;

            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;

            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = cell.getCellFormula() + "";
                break;

            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;

            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;

            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}
