import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NikhilExcelReader {

	public static void main(String[] args) throws IOException {
		ArrayList<String> rowData = getSelectedData(0,1);
		System.out.println(rowData);
	}

	public static ArrayList<String> getSelectedData(int sheetNumber, int rownum) throws IOException {
		String excelFilePath = "data\\Books.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet firstSheet = workbook.getSheetAt(0);
		Row nextRow = firstSheet.getRow(rownum);
		Iterator<Cell> cellIterator = nextRow.cellIterator();
		ArrayList<String> data = new ArrayList<>();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			switch (cell.getCellType()) {
			case STRING:
				data.add(cell.getStringCellValue());
				break;
			case BOOLEAN:
				data.add(Boolean.toString(cell.getBooleanCellValue()));
				break;
			case NUMERIC:
				data.add(String.valueOf(cell.getNumericCellValue()));
				break;
			}
		}
		workbook.close();
		inputStream.close();
		return data;
	}

}
