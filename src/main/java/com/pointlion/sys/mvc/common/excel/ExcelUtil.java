package com.pointlion.sys.mvc.common.excel;

import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.pointlion.sys.mvc.common.excel.annotation.ExcelField;
import com.pointlion.sys.mvc.common.utils.DateUtils;
import com.pointlion.sys.mvc.common.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;


public class ExcelUtil {

    public static void exports(String sheetName,List<?> list,HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet=wb.createSheet(sheetName);
            for (int i= 0;i<list.size();i++) {
                Object obj = list.get(i);
                Class clazz = obj.getClass();
                if(i== 0) {
                    HSSFRow row=sheet.createRow(i);
                    Field[] fields = clazz.getDeclaredFields();
                    int tmp = 0;
                    for (Field field :fields) {
                        HSSFCell cell = row.createCell(tmp);
                        ExcelField excelFiled = field.getAnnotation(ExcelField.class);
                        /*判断是否只支持导入*/
                        if (excelFiled == null||excelFiled.isOnlyImport()) continue;
                        cell.setCellValue(excelFiled.value());
                        tmp++;
                    }
                }
                HSSFRow row=sheet.createRow(i+1);
                Field[] fields = clazz.getDeclaredFields();
                int tmp = 0;
                for (Field field :fields) {
                    ExcelField excelFiled = field.getAnnotation(ExcelField.class);
                    if (excelFiled == null||excelFiled.isOnlyImport()){continue;}
                    HSSFCell cell = row.createCell(tmp);
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
                    Method getMethod = pd.getReadMethod();
                    Object o = getMethod.invoke(obj);
                    if(!StrKit.isBlank(excelFiled.dateFormat())&& o instanceof Date) cell.setCellValue(DateUtils.format((Date)o,excelFiled.dateFormat()));
                    else cell.setCellValue(StringUtil.isNullOrEmpty(o)?excelFiled.isNullDefaultValue():String.valueOf(o));
                    tmp++;
                }
            }
            response.reset();
            response.setHeader("Content-disposition", "attachment;filename="+ new String(sheetName.getBytes("utf-8"), "ISO8859-1")+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导出excel失败,["+e.getMessage()+"]");
        }
    }

    public static void exports2007(String sheetName,List<?> list,HttpServletResponse response) {

        try {
            ServletOutputStream out = response.getOutputStream();
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet=wb.createSheet(sheetName);
            for (int i= 0;i<list.size();i++) {
                Object obj = list.get(i);
                Class clazz = obj.getClass();
                if(i== 0) {
                    XSSFRow row=sheet.createRow(i);
                    Field[] fields = clazz.getDeclaredFields();
                    int tmp = 0;
                    for (Field field :fields) {
                        XSSFCell cell = row.createCell(tmp);
                        ExcelField excelFiled = field.getAnnotation(ExcelField.class);
                        if (excelFiled == null||excelFiled.isOnlyImport()) continue;
                        cell.setCellValue(excelFiled.value());
                        tmp++;
                    }
                }
                XSSFRow row=sheet.createRow(i+1);
                Field[] fields = clazz.getDeclaredFields();
                int tmp = 0;
                for (Field field :fields) {
                    ExcelField excelFiled = field.getAnnotation(ExcelField.class);
                    if (excelFiled == null||excelFiled.isOnlyImport()){continue;}
                    XSSFCell cell = row.createCell(tmp);
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
                    Method getMethod = pd.getReadMethod();
                    Object o = getMethod.invoke(obj);
                    if(!StrKit.isBlank(excelFiled.dateFormat())&& o instanceof Date) cell.setCellValue(DateUtils.format((Date)o,excelFiled.dateFormat()));
                    else cell.setCellValue(StringUtil.isNullOrEmpty(o)?excelFiled.isNullDefaultValue():String.valueOf(o));
                    tmp++;
                }
            }
            response.reset();
            response.setHeader("Content-disposition", "attachment;filename="+ new String(sheetName.getBytes("utf-8"), "ISO8859-1")+".xlsx");
            response.setContentType("application/msexcel");
            wb.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导出excel失败,["+e.getMessage()+"]");
        }
    }


    public static <T> List<T> imports(FileInputStream is,Class<T> clazz){
        List<T> result = Lists.newArrayList();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            /*获取第0行标题*/
            Row row0 = sheet.getRow(0);
            /*遍历每一列*/
            for (int r = 1; r < sheet.getPhysicalNumberOfRows(); r++) {
                T obj = clazz.newInstance();
                Field[] fields = obj.getClass().getDeclaredFields();
                Row row = sheet.getRow(r);
                for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
                    for(Field field :fields) {
                        ExcelField excelFiled = field.getAnnotation(ExcelField.class);
                        String title = getCellValue(row0.getCell(c).getCellType(), row0.getCell(c));
                        if(excelFiled==null&&!title.equals(excelFiled.value())) continue;
                        String cellValue = getCellValue(row.getCell(c).getCellTypeEnum().getCode(), row.getCell(c));
                        PropertyDescriptor pd = new PropertyDescriptor(field.getName(),obj.getClass());
                        if("错误".equals(cellValue)||field.getType()==String.class) {
                            pd.getWriteMethod().invoke(obj, cellValue);
                            continue;
                        }
                        if(field.getType()==Integer.class){
                            pd.getWriteMethod().invoke(obj, Integer.parseInt(cellValue));
                            continue;
                        }
                        if(field.getType()==Long.class){
                            pd.getWriteMethod().invoke(obj, Long.parseLong(cellValue));
                            continue;
                        }
                        if(field.getType()==Float.class){
                            pd.getWriteMethod().invoke(obj, Float.parseFloat(cellValue));
                            continue;
                        }
                        if(field.getType()==Double.class){
                            pd.getWriteMethod().invoke(obj, Double.parseDouble(cellValue));
                            continue;
                        }
                        if(field.getType()==Date.class)
                            pd.getWriteMethod().invoke(obj, DateUtils.parseDate(cellValue));

                    }
                }
                result.add(obj);
            }
        }catch (Exception e) {
            throw new RuntimeException("导如excel失败,["+e.getMessage()+"]");
        }
        return result;
    }

    public static <T> List<T> imports2007(FileInputStream is,Class<T> clazz){
        List<T> result = Lists.newArrayList();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            /*获取第0行标题*/
            Row row0 = sheet.getRow(0);
            /*遍历每一列*/
            for (int r = 1; r < sheet.getPhysicalNumberOfRows(); r++) {
                T obj = clazz.newInstance();
                Field[] fields = obj.getClass().getDeclaredFields();
                Row row = sheet.getRow(r);
                for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
                    for(Field field :fields) {
                        ExcelField excelFiled = field.getAnnotation(ExcelField.class);
                        String title = getCellValue(row0.getCell(c).getCellType(), row0.getCell(c));
                        if(excelFiled==null||!title.equals(excelFiled.value())) continue;
                        String cellValue = getCellValue(row.getCell(c).getCellType(), row.getCell(c));
                        PropertyDescriptor pd = new PropertyDescriptor(field.getName(),obj.getClass());
                        if("错误".equals(cellValue)||field.getType()==String.class) {
                            pd.getWriteMethod().invoke(obj, cellValue);
                            continue;
                        }
                        if(field.getType()==Integer.class){
                            pd.getWriteMethod().invoke(obj, Integer.parseInt(cellValue));
                            continue;
                        }
                        if(field.getType()==Long.class){
                            pd.getWriteMethod().invoke(obj, Long.parseLong(cellValue));
                            continue;
                        }
                        if(field.getType()==Float.class){
                            pd.getWriteMethod().invoke(obj, Float.parseFloat(cellValue));
                            continue;
                        }
                        if(field.getType()==Double.class){
                            pd.getWriteMethod().invoke(obj, Double.parseDouble(cellValue));
                            continue;
                        }
                        if(field.getType()==Date.class)
                            pd.getWriteMethod().invoke(obj, DateUtils.parseDate(cellValue));

                    }
                }
                result.add(obj);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导如excel失败,["+e.getMessage()+"]");
        }
        return result;
    }

    public static String getCellValue(int cellType,Cell cell){
        switch(cellType) {
            case Cell.CELL_TYPE_STRING: /*文本*/
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC: /*数字、日期*/
                if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    return DateUtils.formatDate(cell.getDateCellValue()); /*日期型*/
                }
                else {
                    return String.valueOf(cell.getNumericCellValue()); /*数字*/
                }
            case Cell.CELL_TYPE_BOOLEAN: /*布尔型*/
                return  String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_BLANK: /*空白*/
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_ERROR: /*错误*/
                return "错误";
            case Cell.CELL_TYPE_FORMULA: /*公式*/
                return "错误";
            default:
                return "错误";
        }
    }

//    public static void main(String [] args){
//        File file = new File("E:\\123.xlsx");
//        try {
//            FileInputStream inputStream=  new FileInputStream(file);
//            List<TCmsContent> list= imports2007(inputStream, TCmsContent.class);
//            System.out.println(list.get(0).getTitle());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}
