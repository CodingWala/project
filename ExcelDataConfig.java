package com.agoda.hotelSearch;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelDataConfig {

	static XSSFWorkbook wb;
	static XSSFSheet sheet;
	static String[] excelData = null;
	
	public static void excelDataConfig(String excelPath) {

		File src = new File(excelPath);
		FileInputStream fis;
		try {
			fis = new FileInputStream(src);
			wb = new XSSFWorkbook(fis);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}

	public static String getData(int sheetNumber, int row, int column) {
		System.out.println("inside get data");
		sheet = wb.getSheetAt(sheetNumber);
		System.out.println(sheet);//Name: /xl/worksheets/sheet1.xml - Content Type: application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml
		Row row1 = sheet.getRow(row);
		
		Cell data = row1.getCell(column);
		System.out.println(data); //mumbai
		return "data hello";
	}
	
	public static String [] readExcelData() {
		
		ExcelDataConfig.excelDataConfig("C:\\Users\\Nikhil\\eclipse-workspace\\Mini Project Hotel Availabilty\\test\\resources\\InputData\\TravelData.xlsx");
		
		
		for(int i=0;i<5;i++)
			if(ExcelDataConfig.getData(0,1,i) != null)
				excelData[i] = ExcelDataConfig.getData(0,1,i);
				//System.out.println(excelData[i]);		
			else 
				System.out.println("Outside if");
				
			
		
		return excelData;
	}


}