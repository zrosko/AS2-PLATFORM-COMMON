package hr.as2.inf.common.reports.excel;

import hr.as2.inf.common.data.AS2MetaData;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;
import hr.as2.inf.common.logging.AS2Trace;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AS2ExcelWorkbookXlsx extends AS2ExcelWorkbook {
	XSSFSheet sheet_xlsx_;
	XSSFFont font_;
	XSSFCellStyle style_;
	
	/* WORKBOOK metode */
	public AS2ExcelWorkbookXlsx() throws Exception{
		workbook_ = new XSSFWorkbook();
		sheet_xlsx_ = ((XSSFWorkbook)workbook_).createSheet("1");
	}
	public AS2ExcelWorkbookXlsx(String file_name) throws Exception{
		super(file_name);
		workbook_ = new XSSFWorkbook(file_input_stream_);	
//		font_ = workbook_.createFont();
//		style_.setFont(font_);
	}
	public AS2ExcelWorkbookXlsx(String file_path,String file_name) throws Exception{
		super(file_path,file_name);
		workbook_ = new XSSFWorkbook(file_input_stream_);	
	}
	/* SHEET metode */
	public XSSFSheet getCurrentSheet(){
		return sheet_xlsx_;
	}
	public void deleteSheet(String sheet_name){		
		workbook_.removeSheetAt(workbook_.getSheetIndex(sheet_name));
	}
	public void copySheet(String sheet_name, String new_name){		
		XSSFSheet new_sheet = ((XSSFWorkbook)workbook_).cloneSheet(workbook_.getSheetIndex(sheet_name));
		workbook_.setSheetName(workbook_.getSheetIndex(new_sheet.getSheetName()),new_name);
	}
	public void createSheet(String sheet_name){
		if(sheet_name==null){
			sheet_counter_=sheet_counter_++;
			sheet_name = sheet_counter_+"";
		}
		sheet_xlsx_ = ((XSSFWorkbook)workbook_).createSheet(sheet_name);
	}
	public void setCurrentSheet(String sheet_name){
		sheet_xlsx_ = ((XSSFWorkbook)workbook_).getSheet(sheet_name);
	}	
	/* ROW metode */
	public void createRow(int row_count){
		row_ = sheet_xlsx_.createRow(row_count-1);
	}
	public void setCurrentRow(int row_count){
		row_ = sheet_xlsx_.getRow(row_count-1);
	}
	public Row getRow(int row_count){
		return sheet_xlsx_.getRow(row_count-1);
	}	
	/* CELL metode */
	public Cell createRowCell(int row_count, int cell_count){
		row_ = sheet_xlsx_.createRow(row_count-1);
		Cell _cell = row_.createCell(cell_count-1);
		return _cell;
	}
	public Cell getRowCell(int row_count, int cell_count){
		Cell _cell;
		_cell = sheet_xlsx_.getRow(row_count-1).getCell(cell_count-1);
		return _cell;
	}
	public void setCellValue(int row_count, int cell_count, String value){
		row_ = getRow(row_count);
		cell_ = row_.getCell(cell_count-1);
		cell_.setCellValue(value);
	}
	public void autoSizeColumn(int column)  throws Exception{
		sheet_xlsx_.autoSizeColumn(column-1);
	}
	public  Iterator<Row> getSheetIterator() throws Exception {
		return sheet_xlsx_.iterator();
 	}
	@Override
	public void autoSizeAllColumns() throws Exception {
	}
    public void formatSheetHeader(AS2RecordList data){
        try {
            String name;
            XSSFFont fontType = ((XSSFWorkbook)workbook_).createFont(); 
            fontType.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            fontType.setItalic(false);
            fontType.setFontName(XSSFFont.DEFAULT_FONT_NAME);
            XSSFCellStyle cellFormat = ((XSSFWorkbook)workbook_).createCellStyle();
            cellFormat.setFont(fontType);
            Row row_ = sheet_xlsx_.createRow(0);
            int cell_counter_ = 0;
            Iterator <String> E = data.getMetaData().keySet().iterator();
           	while (E.hasNext()){
           		AS2MetaData metaData = data.getMetaData().get(E.next());
        	    name = metaData.getColumnLabel();        	    
        	    Cell cell = row_.createCell(cell_counter_);
        	    cell_counter_=cell_counter_+1;
        	    cell.setCellValue(name);
        	    cell.setCellStyle(cellFormat);
            }               
        }catch(Exception e){
            AS2Trace.trace(AS2Trace.E, e);
        }
    }
	public void expandAllColumns() {
		//Expand to fit all columns
		for(int columnPosition = 0; columnPosition< 20; columnPosition++) {
			getCurrentSheet().autoSizeColumn((short) (columnPosition));
		}		
	}
	public static AS2ExcelWorkbookXlsx convertRecordListToExcelXlsx(AS2RecordList recordList) throws Exception {
		AS2ExcelWorkbookXlsx wb = new AS2ExcelWorkbookXlsx();
		ArrayList<String> columns = recordList.getColumnNames();
		Row headerRow = wb.getCurrentSheet().createRow(0);
		//Freeze row
		wb.getCurrentSheet().createFreezePane(0,1,0,1);
		//Bold
		XSSFCellStyle headerCellStyle = (XSSFCellStyle) wb.getWorkbook().createCellStyle();
		headerCellStyle = (XSSFCellStyle) wb.getWorkbook().createCellStyle();
		XSSFFont headerFont =  (XSSFFont) wb.getWorkbook().createFont();
		headerFont.setBold(true);
		headerCellStyle.setFont(headerFont);
		//Put header values in sheet
		int header_cell=0;
		for(String column:columns){
			column=column.replace("_", " ");
			String firstChar = column.substring(0,1).toUpperCase();
			column=column.replace(column.charAt(0), firstChar.charAt(0));
			Cell cell = headerRow.createCell(header_cell);
			cell.setCellValue(column);
			cell.setCellStyle(headerCellStyle);
			header_cell++;
		}
		//Put data values in sheet
		int row_count=1;
		for(int i=0; i<recordList.getRows().size();i++){
			AS2Record row = recordList.getRowAt(i);
			Row dataRow = wb.getCurrentSheet().createRow(row_count);
			int cell_count=0;
			for(String column:columns){
				String value = row.get(column);
				wb.setCellValue(cell_count, dataRow,value,recordList.getMetaDataForName(column).getColumnType());
				cell_count++;
			}
			row_count++;
		}
		//Expand to fit all columns
		wb.expandAllColumns();
		return wb;
	}
	@Override
	public Iterator<Row> getRowIterator() throws Exception {
		XSSFSheet sheet = getCurrentSheet();
		return sheet.iterator();
	}
}