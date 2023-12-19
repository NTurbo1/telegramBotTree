package com.nturbo1.telegramBot.service.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;

public class ExcelComparator {

    public static boolean compareExcelFiles(File file1, File file2) throws IOException {
        try (Workbook workbook1 = WorkbookFactory.create(file1);
             Workbook workbook2 = WorkbookFactory.create(file2)) {

            if (workbook1.getNumberOfSheets() != workbook2.getNumberOfSheets()) {
                return false;
            }

            for (int i = 0; i < workbook1.getNumberOfSheets(); i++) {
                Sheet sheet1 = workbook1.getSheetAt(i);
                Sheet sheet2 = workbook2.getSheetAt(i);

                if (!compareSheets(sheet1, sheet2)) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean compareSheets(Sheet sheet1, Sheet sheet2) {
        if (sheet1.getPhysicalNumberOfRows() != sheet2.getPhysicalNumberOfRows()) {
            return false;
        }

        for (int i = 0; i < sheet1.getPhysicalNumberOfRows(); i++) {
            Row row1 = sheet1.getRow(i);
            Row row2 = sheet2.getRow(i);

            if (!compareRows(row1, row2)) {
                return false;
            }
        }

        return true;
    }

    private static boolean compareRows(Row row1, Row row2) {
        if (row1.getPhysicalNumberOfCells() != row2.getPhysicalNumberOfCells()) {
            return false;
        }

        for (int i = 0; i < row1.getPhysicalNumberOfCells(); i++) {
            Cell cell1 = row1.getCell(i);
            Cell cell2 = row2.getCell(i);

            if (!compareCells(cell1, cell2)) {
                return false;
            }
        }

        return true;
    }

    private static boolean compareCells(Cell cell1, Cell cell2) {
        if (cell1 == null && cell2 == null) {
            return true;
        } else if (cell1 == null || cell2 == null) {
            return false;
        }

        if (cell1.getCellType() != cell2.getCellType()) {
            return false;
        }

        return switch (cell1.getCellType()) {
            case STRING -> cell1.getStringCellValue().equals(cell2.getStringCellValue());
            case BOOLEAN -> cell1.getBooleanCellValue() == cell2.getBooleanCellValue();
            case NUMERIC ->
                    Double.compare(cell1.getNumericCellValue(), cell2.getNumericCellValue()) == 0;
            case FORMULA ->
                    cell1.getCellFormula().equals(cell2.getCellFormula());
            default ->
                    false;
        };
    }
}
