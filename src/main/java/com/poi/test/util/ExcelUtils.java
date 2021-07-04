//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.poi.test.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    public static final String POINT = ".";

    public ExcelUtils() {
    }

    public static JSONArray getValues(String fileName) {
        File f = new File(fileName);
        return f.exists() ? getValues(f) : null;
    }

    public static JSONArray getValues(InputStream is) {
        JSONArray array = new JSONArray();

        try {
            //创建工作薄
            Workbook xssfWorkbook = WorkbookFactory.create(is);
            //获取工作列表，根据工作表的顺序获取
            Sheet sheet = xssfWorkbook.getSheetAt(0);
            List<String> head = new ArrayList();
            //获取表头信息
            Row xss_row = sheet.getRow(0);
            Iterator var7 = xss_row.iterator();

            while(var7.hasNext()) {
                Cell cell = (Cell)var7.next();
                String value = getXValue(cell);
                if (StringUtils.isEmpty(value)) {
                    break;
                }

                head.add(value);
            }

            //循环遍历所有的数据
            for(int row_num = 1; row_num < sheet.getLastRowNum() + 1; ++row_num) {
                //获取当前行
                Row row = sheet.getRow(row_num);
                if (row == null) {
                    System.out.println(row_num);
                } else {
                    JSONObject ob = new JSONObject();
                    //循环获取当前行中的单元格
                    for(int i = 0; i < head.size(); ++i) {
                        //获取有数据的单元格
                        Cell cell = row.getCell(i);
                        //将单元格的数据放入json对象中 key--表头 value--单元格数据
                        if (cell != null) {
                            ob.put((String)head.get(i), getXValue(cell));
                        }
                    }
                    //将每一行的数据存入到集合中
                    array.add(ob);
                }
            }
        } catch (FileNotFoundException var23) {
            var23.printStackTrace();
        } catch (IOException var24) {
            var24.printStackTrace();
        } catch (InvalidFormatException var25) {
            var25.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException var22) {
                }
            }

        }

        return array;
    }

    public static JSONArray getValues(File file) {
        FileInputStream is = null;

        try {
            is = new FileInputStream(file);
            JSONArray var4 = getValues((InputStream)is);
            return var4;
        } catch (FileNotFoundException var12) {
            var12.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException var11) {
                }
            }

        }

        return null;
    }

    public static JSONArray get2007Values(String fileName) {
        File f = new File(fileName);
        return f.exists() ? get2007Values(f) : null;
    }

    public static JSONArray get2007Values(File file) {
        InputStream is = null;
        JSONArray array = new JSONArray();

        try {
            is = new FileInputStream(file);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            List<String> head = new ArrayList();
            XSSFRow xss_row = sheet.getRow(0);
            Iterator var8 = xss_row.iterator();

            while(var8.hasNext()) {
                Cell cell = (Cell)var8.next();
                String value = getXValue(cell);
                if (StringUtils.isEmpty(value)) {
                    break;
                }

                head.add(value);
            }

            for(int row_num = 1; row_num < sheet.getLastRowNum() + 1; ++row_num) {
                XSSFRow row = sheet.getRow(row_num);
                JSONObject ob = new JSONObject();

                for(int i = 0; i < head.size(); ++i) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        ob.put((String)head.get(i), getXValue(cell));
                    }
                }

                array.add(ob);
            }
        } catch (FileNotFoundException var22) {
            var22.printStackTrace();
        } catch (IOException var23) {
            var23.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException var21) {
                }
            }

        }

        return array;
    }

    public static String getXValue(Cell xssfCell) {
        if (xssfCell.getCellType() == 4) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == 0) {
            String cellValue = "";
            if (XSSFDateUtil.isCellDateFormatted(xssfCell)) {
                Date date = XSSFDateUtil.getJavaDate(xssfCell.getNumericCellValue());
                cellValue = sdf.format(date);
            } else {
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(xssfCell.getNumericCellValue());
                String strArr = cellValue.substring(cellValue.lastIndexOf(".") + 1, cellValue.length());
                if (strArr.equals("00")) {
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf("."));
                }
            }

            return cellValue;
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }

//    public static void main(String[] args) throws Exception {
//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet sheet = wb.createSheet("new sheet");
//        HSSFRow row = sheet.createRow(1);
//        HSSFCell cell = row.createCell((short)1);
//        cell.setCellValue("This is a test of merging");
//        HSSFFont font = wb.createFont();
//        font.setFontHeightInPoints((short)10);
//        font.setFontName("新宋体");
//        font.setColor((short)12);
//        font.setBoldweight((short)0);
//        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment((short)2);
//        style.setVerticalAlignment((short)1);
//        style.setFont(font);
//        style.setWrapText(true);
//        style.setBorderTop((short)6);
//        style.setBorderLeft((short)6);
//        style.setTopBorderColor((short)51);
//        style.setLeftBorderColor((short)61);
//        cell.setCellStyle(style);
//        sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
//        HSSFRow row2 = sheet.createRow(3);
//        HSSFCell cell2 = row2.createCell((short)3);
//        cell2.setCellValue("this is a very very very long string , please check me out.");
//
//        try {
//            FileOutputStream fileOut = new FileOutputStream("E:/students.xls");
//            wb.write(fileOut);
//            fileOut.close();
//        } catch (FileNotFoundException var11) {
//            var11.printStackTrace();
//        } catch (IOException var12) {
//            var12.printStackTrace();
//        }
//
//    }
//
//    public static void ObjectToexcel(OutputStream out, ExcelEntity... entity) throws IOException {
//        HSSFWorkbook wb = new HSSFWorkbook();
//        ExcelEntity[] var6 = entity;
//        int var5 = entity.length;
//
//        label92:
//        for(int var4 = 0; var4 < var5; ++var4) {
//            ExcelEntity et = var6[var4];
//            HSSFSheet sheet = wb.createSheet(et.getSheetname());
//            int rownum = 0;
//            HSSFFont font;
//            if (et.getHead() != null && !et.getHead().isEmpty()) {
//                HSSFCellStyle setBorder = wb.createCellStyle();
//                setBorder.setWrapText(true);
//                HSSFRow row = sheet.createRow(rownum);
//                font = wb.createFont();
//                font.setFontName("宋体");
//                font.setFontHeightInPoints((short)14);
//                font.setBoldweight((short)700);
//                setBorder.setFont(font);
//                setBorder.setFillForegroundColor((short)22);
//                setBorder.setAlignment((short)2);
//                setBorder.setVerticalAlignment((short)1);
//
//                for(int i = 0; i < et.getHead().size(); ++i) {
//                    sheet.setColumnWidth(i, 13766);
//                    HSSFCell cell = row.createCell(i);
//                    cell.setCellStyle(setBorder);
//                    cell.setCellValue((String)et.getHead().get(i));
//                }
//
//                ++rownum;
//            }
//
//            if (et.getBody() != null && !et.getBody().isEmpty()) {
//                List<RowEntity> list = et.getBody();
//                HSSFCellStyle setBorder = wb.createCellStyle();
//                setBorder.setWrapText(true);
//                font = wb.createFont();
//                font.setFontName("宋体");
//                font.setFontHeightInPoints((short)11);
//                setBorder.setFont(font);
//                setBorder.setFillForegroundColor((short)8);
//                setBorder.setBorderTop((short)1);
//                setBorder.setBorderLeft((short)2);
//                setBorder.setBorderRight((short)2);
//                setBorder.setBorderBottom((short)2);
//                setBorder.setAlignment((short)2);
//                setBorder.setVerticalAlignment((short)1);
//                HSSFCellStyle setBorder1 = wb.createCellStyle();
//                setBorder1.setWrapText(true);
//                HSSFFont font1 = wb.createFont();
//                font1.setColor((short)10);
//                font1.setFontName("宋体");
//                font1.setFontHeightInPoints((short)11);
//                setBorder1.setFont(font1);
//                setBorder1.setFillForegroundColor((short)8);
//                setBorder1.setBorderTop((short)1);
//                setBorder1.setBorderLeft((short)2);
//                setBorder1.setBorderRight((short)2);
//                setBorder1.setBorderBottom((short)2);
//                setBorder1.setAlignment((short)2);
//                setBorder1.setVerticalAlignment((short)1);
//                Iterator var15 = list.iterator();
//
//                while(true) {
//                    RowEntity cells;
//                    do {
//                        do {
//                            if (!var15.hasNext()) {
//                                continue label92;
//                            }
//
//                            cells = (RowEntity)var15.next();
//                            sheet.createRow(rownum);
//                        } while(cells.getCells() == null);
//                    } while(cells.getCells().isEmpty());
//
//                    for(int i = 0; i < cells.getCells().size(); ++i) {
//                        CellEntity cell = (CellEntity)cells.getCells().get(i);
//                        HSSFRow row;
//                        if (cell.getSize() > 1) {
//                            for(int j = 0; j < cell.getSize(); ++j) {
//                                int nextrow = rownum + j;
//                                row = sheet.createRow(nextrow);
//                                HSSFCell cel = row.createCell(i);
//                                if (cell.isFlag()) {
//                                    cel.setCellStyle(setBorder1);
//                                } else {
//                                    cel.setCellStyle(setBorder);
//                                }
//
//                                cel.setCellValue(cell.getCellvalString(j));
//                            }
//                        } else {
//                            row = sheet.getRow(rownum);
//                            HSSFCell cel = row.createCell(i);
//                            if (cell.isFlag()) {
//                                cel.setCellStyle(setBorder1);
//                            } else {
//                                cel.setCellStyle(setBorder);
//                            }
//
//                            if (!StringUtils.isEmpty(cell.getCellvalString(0))) {
//                                System.out.println(rownum);
//                            }
//
//                            cel.setCellValue(cell.getCellvalString(0));
//                            if (cells.getRowcross() > 1) {
//                                sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + cells.getRowcross() - 1, i, i));
//                            }
//                        }
//                    }
//
//                    rownum += cells.getRowcross();
//                }
//            }
//        }
//
//        wb.write(out);
//    }
//
//    public static void ObjectToexcel07(InputStream is, OutputStream out, ExcelEntity... entity) throws InvalidFormatException, IOException {
//        Workbook wb = WorkbookFactory.create(is);
//
//        label74:
//        for(int index = 0; index < entity.length; ++index) {
//            Sheet sheet = wb.getSheetAt(index);
//            ExcelEntity et = entity[index];
//            int rownum = 1;
//            if (et.getBody() != null && !et.getBody().isEmpty()) {
//                List<RowEntity> list = et.getBody();
//                CellStyle setBorder = wb.createCellStyle();
//                setBorder.setWrapText(true);
//                Font font = wb.createFont();
//                font.setFontName("宋体");
//                font.setFontHeightInPoints((short)11);
//                setBorder.setFont(font);
//                setBorder.setFillForegroundColor((short)8);
//                setBorder.setBorderTop((short)1);
//                setBorder.setBorderLeft((short)2);
//                setBorder.setBorderRight((short)2);
//                setBorder.setBorderBottom((short)2);
//                setBorder.setAlignment((short)2);
//                setBorder.setVerticalAlignment((short)1);
//                CellStyle setBorder1 = wb.createCellStyle();
//                setBorder1.setWrapText(true);
//                Font font1 = wb.createFont();
//                font1.setColor((short)10);
//                font1.setFontName("宋体");
//                font1.setFontHeightInPoints((short)11);
//                setBorder1.setFont(font1);
//                setBorder1.setFillForegroundColor((short)8);
//                setBorder1.setBorderTop(BorderStyle.valueOf((short)1));
//                setBorder1.setBorderLeft(BorderStyle.valueOf((short)2));
//                setBorder1.setBorderRight(BorderStyle.valueOf((short)2));
//                setBorder1.setBorderBottom(BorderStyle.valueOf((short)2));
//                setBorder1.setAlignment(HorizontalAlignment.forInt((short)2));
//                setBorder1.setVerticalAlignment(VerticalAlignment.forInt((short)1));
//                Iterator var14 = list.iterator();
//
//                while(true) {
//                    RowEntity cells;
//                    do {
//                        do {
//                            if (!var14.hasNext()) {
//                                continue label74;
//                            }
//
//                            cells = (RowEntity)var14.next();
//                            sheet.createRow(rownum);
//                        } while(cells.getCells() == null);
//                    } while(cells.getCells().isEmpty());
//
//                    for(int i = 0; i < cells.getCells().size(); ++i) {
//                        CellEntity cell = (CellEntity)cells.getCells().get(i);
//                        Row row;
//                        if (cell.getSize() > 1) {
//                            for(int j = 0; j < cell.getSize(); ++j) {
//                                int nextrow = rownum + j;
//                                row = sheet.createRow(nextrow);
//                                Cell cel = row.createCell(i);
//                                if (cell.isFlag()) {
//                                    cel.setCellStyle(setBorder1);
//                                } else {
//                                    cel.setCellStyle(setBorder);
//                                }
//
//                                cel.setCellValue(cell.getCellvalString(j));
//                            }
//                        } else {
//                            row = sheet.getRow(rownum);
//                            Cell cel = row.createCell(i);
//                            if (cell.isFlag()) {
//                                cel.setCellStyle(setBorder1);
//                            } else {
//                                cel.setCellStyle(setBorder);
//                            }
//
//                            if (!StringUtils.isEmpty(cell.getCellvalString(0))) {
//                                System.out.println(rownum);
//                            }
//
//                            cel.setCellValue(cell.getCellvalString(0));
//                            if (cells.getRowcross() > 1) {
//                                sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + cells.getRowcross() - 1, i, i));
//                            }
//                        }
//                    }
//
//                    rownum += cells.getRowcross();
//                }
//            }
//        }
//
//        wb.write(out);
//    }
//
//    public static void ObjectToexcel07(OutputStream out, ExcelEntity... entity) throws IOException {
//        XSSFWorkbook wb = new XSSFWorkbook();
//        ExcelEntity[] var6 = entity;
//        int var5 = entity.length;
//
//        label92:
//        for(int var4 = 0; var4 < var5; ++var4) {
//            ExcelEntity et = var6[var4];
//            XSSFSheet sheet = wb.createSheet(et.getSheetname());
//            int rownum = 0;
//            XSSFFont font;
//            if (et.getHead() != null && !et.getHead().isEmpty()) {
//                XSSFCellStyle setBorder = wb.createCellStyle();
//                setBorder.setWrapText(true);
//                XSSFRow row = sheet.createRow(rownum);
//                font = wb.createFont();
//                font.setFontName("宋体");
//                font.setFontHeightInPoints((short)14);
//                font.setBoldweight((short)700);
//                setBorder.setFont(font);
//                setBorder.setFillForegroundColor((short)22);
//                setBorder.setAlignment((short)2);
//                setBorder.setVerticalAlignment((short)1);
//
//                for(int i = 0; i < et.getHead().size(); ++i) {
//                    sheet.setColumnWidth(i, 13766);
//                    XSSFCell cell = row.createCell(i);
//                    cell.setCellStyle(setBorder);
//                    cell.setCellValue((String)et.getHead().get(i));
//                }
//
//                ++rownum;
//            }
//
//            if (et.getBody() != null && !et.getBody().isEmpty()) {
//                List<RowEntity> list = et.getBody();
//                XSSFCellStyle setBorder = wb.createCellStyle();
//                setBorder.setWrapText(true);
//                font = wb.createFont();
//                font.setFontName("宋体");
//                font.setFontHeightInPoints((short)11);
//                setBorder.setFont(font);
//                setBorder.setFillForegroundColor((short)8);
//                setBorder.setBorderTop((short)1);
//                setBorder.setBorderLeft((short)2);
//                setBorder.setBorderRight((short)2);
//                setBorder.setBorderBottom((short)2);
//                setBorder.setAlignment((short)2);
//                setBorder.setVerticalAlignment((short)1);
//                XSSFCellStyle setBorder1 = wb.createCellStyle();
//                setBorder1.setWrapText(true);
//                XSSFFont font1 = wb.createFont();
//                font1.setColor((short)10);
//                font1.setFontName("宋体");
//                font1.setFontHeightInPoints((short)11);
//                setBorder1.setFont(font1);
//                setBorder1.setFillForegroundColor((short)8);
//                setBorder1.setBorderTop((short)1);
//                setBorder1.setBorderLeft((short)2);
//                setBorder1.setBorderRight((short)2);
//                setBorder1.setBorderBottom((short)2);
//                setBorder1.setAlignment((short)2);
//                setBorder1.setVerticalAlignment((short)1);
//                Iterator var15 = list.iterator();
//
//                while(true) {
//                    RowEntity cells;
//                    do {
//                        do {
//                            if (!var15.hasNext()) {
//                                continue label92;
//                            }
//
//                            cells = (RowEntity)var15.next();
//                            sheet.createRow(rownum);
//                        } while(cells.getCells() == null);
//                    } while(cells.getCells().isEmpty());
//
//                    for(int i = 0; i < cells.getCells().size(); ++i) {
//                        CellEntity cell = (CellEntity)cells.getCells().get(i);
//                        XSSFRow row;
//                        if (cell.getSize() > 1) {
//                            for(int j = 0; j < cell.getSize(); ++j) {
//                                int nextrow = rownum + j;
//                                row = sheet.createRow(nextrow);
//                                XSSFCell cel = row.createCell(i);
//                                if (cell.isFlag()) {
//                                    cel.setCellStyle(setBorder1);
//                                } else {
//                                    cel.setCellStyle(setBorder);
//                                }
//
//                                cel.setCellValue(cell.getCellvalString(j));
//                            }
//                        } else {
//                            row = sheet.getRow(rownum);
//                            XSSFCell cel = row.createCell(i);
//                            if (cell.isFlag()) {
//                                cel.setCellStyle(setBorder1);
//                            } else {
//                                cel.setCellStyle(setBorder);
//                            }
//
//                            if (!StringUtils.isEmpty(cell.getCellvalString(0))) {
//                                System.out.println(rownum);
//                            }
//
//                            cel.setCellValue(cell.getCellvalString(0));
//                            if (cells.getRowcross() > 1) {
//                                sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + cells.getRowcross() - 1, i, i));
//                            }
//                        }
//                    }
//
//                    rownum += cells.getRowcross();
//                }
//            }
//        }
//
//        wb.write(out);
//    }
}
