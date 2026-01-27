package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    // Original method - returns all columns
    public static Object[][] getData(String sheetName) {
        return getDataWithColumns(sheetName, -1);
    }

    // New method - returns only specified number of columns
    public static Object[][] getDataWithColumns(String sheetName, int columnCount) {
        XSSFWorkbook localWorkbook = null;
        try {
            String filePath = "TestData/loginData.xlsx";
            InputStream input = ExcelReader.class.getClassLoader()
                    .getResourceAsStream(filePath);

            if (input == null) {
                throw new RuntimeException("❌ FILE NOT FOUND at: src/main/resources/" + filePath);
            }

            localWorkbook = new XSSFWorkbook(input);
            XSSFSheet sheet = localWorkbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("❌ SHEET NOT FOUND: '" + sheetName + "'");
            }

            int totalRows = sheet.getLastRowNum();
            if (totalRows < 1) {
                throw new RuntimeException("❌ No data rows in sheet");
            }

            // Auto-detect or use specified column count
            XSSFRow headerRow = sheet.getRow(0);
            int totalColumns = (columnCount == -1) ? headerRow.getLastCellNum() : columnCount;

            System.out.println("📊 Sheet: " + sheetName + " | Rows: " + totalRows + " | Cols: " + totalColumns);

            List<Object[]> dataList = new ArrayList<>();

            // Skip headers - Start from Row 1
            for (int i = 1; i <= totalRows; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row == null || isRowEmpty(row, totalColumns)) continue;

                Object[] rowData = new Object[totalColumns];
                for (int j = 0; j < totalColumns; j++) {
                    rowData[j] = getCellValue(row.getCell(j));
                }
                dataList.add(rowData);

                // Debug: Print first row
                if (i == 1) {
                    System.out.print("First row: [");
                    for (int k = 0; k < totalColumns; k++) {
                        System.out.print(rowData[k] + (k < totalColumns - 1 ? ", " : ""));
                    }
                    System.out.println("]");
                }
            }

            if (dataList.isEmpty()) {
                return new Object[0][0];
            }

            Object[][] dataArray = new Object[dataList.size()][totalColumns];
            for (int i = 0; i < dataList.size(); i++) {
                dataArray[i] = dataList.get(i);
            }

            return dataArray;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read sheet: " + sheetName, e);
        } finally {
            if (localWorkbook != null) {
                try { localWorkbook.close(); } catch (Exception e) {}
            }
        }
    }

    private static boolean isRowEmpty(XSSFRow row, int totalColumns) {
        if (row == null) return true;

        for (int j = 0; j < totalColumns; j++) {
            String value = getCellValue(row.getCell(j));
            if (value != null && !value.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                String value = cell.getStringCellValue();
                return value != null ? value.trim() : "";
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}