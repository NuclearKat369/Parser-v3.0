package com.company;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelFile {

    static Workbook wb;
    static Sheet sheet;
    static int rownum;



    public static void createExcelFile() throws IOException {

        wb = new XSSFWorkbook();
        sheet = wb.createSheet("Фильмы");
        Font headerFont = wb.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = wb.createCellStyle();
        headerCellStyle.setFont(headerFont);

        String[] col = {"Название", "Рейтинг", "Канал", "Время"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < col.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(col[i]);
            cell.setCellStyle(headerCellStyle);
        }

        rownum = 1; //номер строки следующей, чтобы инфу про кино вставлять

        File data = new File("C:/Users/User/Documents/movies.xlsx");
        if (data.createNewFile()) {
            System.out.println("File created: " + data.getName());
        } else {
            System.out.println("File already exists.");
            data.delete();
            data.createNewFile();
            System.out.println("The existing file was rewritten.");
        }

        FileOutputStream fo = new FileOutputStream(data);
        wb.write(fo);
        fo.close();
    }

    public static void writeExcel(Movie m) throws IOException {
        FileOutputStream fo = new FileOutputStream("C:/Users/User/Documents/movies.xlsx");
//        int rows = sheet.getPhysicalNumberOfRows();
        Row row = sheet.createRow(rownum);
//        if(row == null) {
//            row = sheet.createRow(rows);
//        }
//        else row = sheet.createRow((rows+1));
        row.createCell(0).setCellValue(m.title);
        row.createCell(1).setCellValue(m.rate);
        row.createCell(2).setCellValue(m.chan);
        row.createCell(3).setCellValue(m.time);
        rownum++;

        wb.write(fo);
        fo.close();
    }
}