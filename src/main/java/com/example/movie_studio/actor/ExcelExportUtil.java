package com.example.movie_studio.actor;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelExportUtil {
    XSSFWorkbook workbook;
    @Getter
    XSSFSheet sheet;
    int arrLength = 0;

    public ExcelExportUtil(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }


    public void writeHeaderLine(String sheetName, List<String> headers) {
        sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        int columnCount = 0;
        for (String header : headers) {
            createCell(row, columnCount++, header, style);
        }

        arrLength = headers.size();
    }

    public void writeHeaderLineWithStyle(String sheetName, List<String> headers, boolean isBold, int fontHeight) {
        sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(isBold);
        font.setFontHeight(fontHeight);
        style.setFont(font);

        int columnCount = 0;
        for (String header : headers) {
            createCell(row, columnCount++, header, style);
        }

        arrLength = headers.size();
    }


    public void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }


    public void createCell(Row row, int columnCount, Object value) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
    }


    public void export(HttpServletResponse response, String fileName) {
        for (int i = 0; i < arrLength; i++) {
            sheet.autoSizeColumn(i);
        }

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + fileName + "_" + currentDateTime + ".xlsx\"");
        response.setContentType("application/octet-stream");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void exportToExcel(HttpServletResponse httpServletResponse, String safety_meeting_list) {

    }
}
