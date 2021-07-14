package com.jtelaa.da2.lib.files.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.jtelaa.da2.lib.log.Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * 
 */

 // TODO comment

public class ExcelFile {
    
    /** */
    private Workbook workbook;

    /** */
    private File file;

    /** */
    private FileInputStream fileInputStream;

    /** */
    private Sheet current_sheet;

    /** */
    private Row current_row;

    /**
     * 
     * @param file
     */

    public ExcelFile(File file) {
        this.file = file;
        
        try {
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
            getSheet(0);

        } catch (IOException e) {
            Log.sendMessage(e);

        }

    }

    /**
     * 
     */

    public synchronized void save() {
        try {
            close();
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();

        } catch (IOException e) {
            Log.sendMessage(e);

        }
        
    }

    /**
     * 
     */

    public synchronized void close() { 
        try {
            fileInputStream.close();

        } catch (IOException e) {
            Log.sendMessage(e);

        }   
    }

    /**
     * 
     * @param data
     */

    public void appendData(ArrayList<String> data) {
        newRow();

        for (int i = 0; i < data.size(); i++) {
            set(i, data.get(i));

        }

    }

    /**
     * 
     * @param name
     * @return
     */

    public synchronized Sheet getSheet(String name) { 
        current_sheet = workbook.getSheet(name); 
        return current_sheet;
    
    }

    /**
     * 
     * @param num
     * @return
     */

    public synchronized Sheet getSheet(int num) { 
        current_sheet = workbook.getSheetAt(num);
        return current_sheet;
    
    }

    /**
     * 
     * @return
     */

    public synchronized Sheet getSheet() { return getSheet(0); } 

    /**
     * 
     * @param sheet
     * @param index
     * @return
     */

    public synchronized Row getRow(Sheet sheet, int index) { 
        current_row = sheet.getRow(index);
        return current_row;

    }

    /**
     * 
     * @param index
     * @return
     */

    public synchronized Row getRow(int index) { return current_sheet.getRow(index); }

    /**
     * 
     * @param index
     * @return
     */

    public synchronized Row getTitleRow() { return getRow(0); }

    /**
     * 
     * @return
     */

    public synchronized Row newRow(Sheet sheet) {
        sheet.createRow(sheet.getLastRowNum() + 1);
        current_row = sheet.getRow(sheet.getLastRowNum());
        return current_row;

    }

    /**
     *  
     * @return
     */

    public synchronized Row newRow() { return newRow(current_sheet); }

    /**
     * 
     * @param row
     * @return
     */

    public synchronized Cell[] getCells(Row row) { 
        ArrayList<Cell> cells = new ArrayList<Cell>();

        int last_cell = row.getLastCellNum();
        for (int index = row.getFirstCellNum(); index < last_cell; index++) {
            cells.add(row.getCell(index));

        }

        return (Cell[]) cells.toArray();

    }

    /**
     * 
     * @return
     */

    public synchronized Cell[] getCells() { return getCells(current_row); }

    /**
     * 
     * @param cell
     * @return
     */

    public synchronized String get(Cell cell) { return cell.getStringCellValue(); }

    /**
     * 
     * @param cell
     * @return
     */

    public synchronized String get(int cell) { return get(current_row.getCell(cell)); }

    /**
     * 
     * @param cell
     * @param info
     */

    public synchronized void set(Cell cell, String info) { cell.setCellValue(info); }

    /**
     * 
     * @param cell
     * @param info
     */

    public synchronized void set(int cell_num, String info) { 
        Cell cell;

        if (cell_num > current_row.getLastCellNum()) {
            cell = newCell(cell_num);

        } else {
            cell = current_row.getCell(cell_num);

        }

        set(cell, info); 
    }

    /**
     * 
     * @param row
     * @param column
     * @return
     */
    
    public synchronized Cell newCell(Row row, int column) {
        Cell cell = row.createCell(column);
        return cell;
        
    }

    /**
     * 
     * @param column
     * @return
     */

    public synchronized Cell newCell(int column) { return newCell(current_row, column); } 

    /**
     * 
     * @return
     */

    public synchronized Cell newCell() { return newCell(current_row.getLastCellNum() + 1); }

    
    
}
