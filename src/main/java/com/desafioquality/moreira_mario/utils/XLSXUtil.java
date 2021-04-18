package com.desafioquality.moreira_mario.utils;


import com.desafioquality.moreira_mario.exceptions.ApiException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class XLSXUtil {

    private static Long objectId = 0L;
    private static String fileName = "";
    private static String filePath = "";
    private static Integer sheetNum = 0;
    private static Properties properties = new Properties();
    private static XSSFWorkbook book = null;
    private static FileInputStream file = null;
    private static boolean isOk = false;
    private static Row row = null;
    private static File newFile = new File("");
    private static Cell cell;

    /**
     * Read the xlsx
     *
     * @param propertiePath gives the property to read
     * @param sheetName     is the name of the xlsx to read
     * @return a map of list of cells read from the xlsx
     */
    public static Map<Long, ArrayList<Cell>> readXLSX(String propertiePath, String sheetName) throws ApiException {
        try {
            properties.load(new FileInputStream(ResourceUtils.getFile(propertiePath)));
            fileName = properties.get("file").toString();
            filePath = newFile.getAbsolutePath() + properties.get("filePath").toString() + fileName;
            sheetNum = Integer.parseInt(properties.get(sheetName).toString());
            file = new FileInputStream(ResourceUtils.getFile(filePath));
            book = new XSSFWorkbook(file);
            XSSFSheet sheet = book.getSheetAt(sheetNum);
            Iterator<Row> rowIterator = sheet.rowIterator();
            Map<Long, ArrayList<Cell>> data = new HashMap<>();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                if (row.getRowNum() > 0 && row.getCell(0) != null) {
                    ArrayList<Cell> line = new ArrayList<>();
                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();
                        line.add(cell);
                    }
                    data.put((long) row.getRowNum(), line);
                }
            }
            return data;
        } catch (IOException e) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Error: No se encontro el siguiente Archivo: " + e.getMessage() + ".");
        }
    }

    /**
     * Executes the xlsx update
     *
     * @param registry      is a xlsx row with the updated hotel hto
     * @param propertiePath gives the property to read
     * @param sheetName     is the name of the xlsx sheet 22to read
     *                      This method goes through the xlsx validating the filters to identify which is the row to replace
     */
    public static void updateXLSX(Row registry, Map<String, String> filters, String propertiePath, String sheetName) throws ApiException {
        try {
            properties.load(new FileInputStream(ResourceUtils.getFile(propertiePath)));
            fileName = properties.get("file").toString();
            filePath = newFile.getAbsolutePath() + properties.get("filePath").toString() + fileName;
            sheetNum = Integer.parseInt(properties.get(sheetName).toString());
            file = new FileInputStream(ResourceUtils.getFile(filePath));
            book = new XSSFWorkbook(file);
            XSSFSheet sheet = book.getSheetAt(sheetNum);
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                if (row.getRowNum() > 0 && row.getCell(0) != null) {
                    boolean isOk = true;
                    for (Map.Entry<String, String> entry : filters.entrySet()) {
                        Integer index = Integer.parseInt(entry.getKey());
                        if (!entry.getValue().equals(row.getCell(index).toString())) {
                            isOk = false;
                            break;
                        }
                    }
                    if (isOk) {
                        for (int i = 0; i < registry.getLastCellNum(); i++) {
                            switch (registry.getCell(i).getCellType()) {
                                case STRING:
                                    String string = registry.getCell(i).getStringCellValue();
                                    row.getCell(i).setCellValue(string);
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(registry.getCell(i))) {
                                        Date date = registry.getCell(i).getDateCellValue();
                                        row.getCell(i).setCellValue(date);
                                    } else {
                                        double cellValue = registry.getCell(i).getNumericCellValue();
                                        row.getCell(i).setCellValue(cellValue);
                                    }
                                    break;
                                case FORMULA:
                                    String formula = registry.getCell(i).getCellFormula();
                                    row.getCell(i).setCellFormula(formula);
                                    break;
                            }
                        }
                        break;
                    }
                }
            }
            file.close();
            FileOutputStream outputStream = new FileOutputStream(filePath);
            book.write(outputStream);
            book.close();
            outputStream.close();
        } catch (IOException | EncryptedDocumentException e) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Error: No se encontro el siguiente Archivo: " + e.getMessage() + ".");
        }
    }

    /**
     * Gets row by filters
     *
     * @param filters       is map with the filters to specify a row
     * @param propertiePath gives the property to read
     * @param sheetName     is the name of the xlsx sheet to read
     *                      This method goes through the xlsx validating the filters to identify which is the row to return
     */
    public static Row getRow(Map<String, String> filters, String propertiePath, String sheetName) throws ApiException {
        try {
            File newFile = new File("");
            properties.load(new FileInputStream(ResourceUtils.getFile(propertiePath)));
            fileName = properties.get("file").toString();
            filePath = newFile.getAbsolutePath() + properties.get("filePath").toString() + fileName;
            sheetNum = Integer.parseInt(properties.get(sheetName).toString());
            file = new FileInputStream(ResourceUtils.getFile(filePath));
            book = new XSSFWorkbook(file);
            XSSFSheet sheet = book.getSheetAt(sheetNum);
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                isOk = true;
                if (row.getRowNum() > 0 && row.getCell(0) != null) {
                    for (Map.Entry<String, String> entry : filters.entrySet()) {
                        Integer index = Integer.parseInt(entry.getKey());
                        if (!entry.getValue().equals(row.getCell(index).toString())) {
                            isOk = false;
                            break;
                        }
                    }
                }
                if (isOk) {
                    break;
                }
            }
            file.close();
            FileOutputStream outputStream = new FileOutputStream(filePath);
            book.write(outputStream);
            book.close();
            outputStream.close();
        } catch (IOException | EncryptedDocumentException e) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Error: No se encontro el siguiente Archivo: " + e.getMessage() + ".");
        }
        return row;
    }
}
