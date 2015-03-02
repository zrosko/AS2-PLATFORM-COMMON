package hr.as2.inf.common.reports.excel;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;
import hr.as2.inf.common.file.AS2FileUtility;
import hr.as2.inf.common.types.AS2Date;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

public abstract class AS2ExcelWorkbook {
	public final static String DEFAULT_EXCEL_SERVER_DIRECTORY = AS2FileUtility.DEFAULT_SERVER_DIRECTORY + "EXCEL" + java.io.File.separator;
	Workbook workbook_=null;
//	XSSFWorkbook workbook_xlsx_=null;
	File file_;
	FileInputStream file_input_stream_;
	int sheet_counter_=10;
	Row row_; 
	Cell cell_;

	public AS2ExcelWorkbook(){
	}
	public AS2ExcelWorkbook(String file_name) throws Exception{
		String old_file_name = DEFAULT_EXCEL_SERVER_DIRECTORY+file_name;
		String new_file_name = AS2FileUtility.DEFAULT_SERVER_DIRECTORY_TEMP + file_name;
		new_file_name = AS2FileUtility.appendTimestampToFileName(new_file_name);
		file_ = AS2FileUtility.copy(old_file_name, new_file_name);
		file_input_stream_ = new FileInputStream(file_);
		file_.deleteOnExit();
	}
	public AS2ExcelWorkbook(String file_path,String file_name) throws Exception{
		String old_file_name = file_path+java.io.File.separator+file_name;
		String new_file_name = AS2FileUtility.DEFAULT_SERVER_DIRECTORY_TEMP + file_name;
		new_file_name = AS2FileUtility.appendTimestampToFileName(new_file_name);
		file_ = AS2FileUtility.copy(old_file_name, new_file_name);
		file_input_stream_ = new FileInputStream(file_);
		file_.deleteOnExit();
	}
	
	/* WORKBOOK metode */
	public Workbook getWorkbook(){
		return workbook_;
	}
	public void saveWorkbook() throws Exception {
		file_input_stream_.close();
		FileOutputStream outFile = new FileOutputStream(file_);
		saveWorkbook(outFile);
	    outFile.close();
	}
	public byte[] saveWorkbookAsBytes() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook_.write(bos);
		byte[] fileByteArray = bos.toByteArray();
		bos.close();
		return fileByteArray;
		
	}
	public byte[] readWorkbookAsBytes()throws Exception {
		return AS2FileUtility.readFileToBytes(file_);
	}
	/* CELL metode  */
	public void createCell(int cell_count){
		row_.createCell(cell_count-1);
	}
	public Cell createRowCell(int row_count, int cell_count){
		Cell _cell = row_.createCell(cell_count-1);
		return _cell;
	}	
	public void createCellValue(int cell_count, String value){
		row_.createCell(cell_count-1).setCellValue(value);
	}
	public void createCellFormula(int cell_count, String formula){
		row_.createCell(cell_count-1).setCellFormula(formula);
	}
	public void setCellValue(int cell_count, String value){
		cell_ = row_.getCell(cell_count-1);
		cell_.setCellValue(value);
	}
	public void setCellFormula(int row_count, int cell_count, String value)throws Exception {
		row_ = getRow(row_count);
		cell_ = row_.getCell(cell_count-1);
		cell_.setCellFormula(value);
	}
	public void setCellValue(int row_count, int cell_count, String value)throws Exception{
		row_ = getRow(row_count);
		cell_ = row_.getCell(cell_count-1);
		cell_.setCellValue(value);
	}
	public void setCellValue(int cell_count, Row dataRow, String cellValue, int type) throws Exception{
		row_ = dataRow;
		cell_ = row_.getCell(cell_count);
		if(cell_==null){
			row_.createCell(cell_count);
		}
		cell_ = row_.getCell(cell_count);
		if (type==java.sql.Types.DATE ){
			CreationHelper createHelper = workbook_.getCreationHelper();
			CellStyle cellStyle =  workbook_.createCellStyle();
			cellStyle.setDataFormat(
			    createHelper.createDataFormat().getFormat("d.m.yyyy"));//TODO testirati
			cell_.setCellStyle(cellStyle);
			if(cellValue.length()<=0) 
				cell_.setCellValue("");
			else
				cell_.setCellValue(AS2Date.convert(cellValue,AS2Date.DEFAULT_DATE_FORMAT_EN));
		}else if(type==java.sql.Types.TIMESTAMP){
			CreationHelper createHelper = workbook_.getCreationHelper();
			CellStyle cellStyle =  workbook_.createCellStyle();
			cellStyle.setDataFormat(
			    createHelper.createDataFormat().getFormat("d.m.yyyy hh:mm:ss"));
			cell_.setCellStyle(cellStyle);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AS2Date.getDefaultTimestampFormat());
			if(cellValue.length()<=0) 
				cell_.setCellValue("");
			else
				cell_.setCellValue(simpleDateFormat.parse(cellValue));
		}else if(type==java.sql.Types.DECIMAL || type==java.sql.Types.NUMERIC){
			cell_.setCellType(Cell.CELL_TYPE_NUMERIC);
			if(cellValue.length()<=0) 
				cellValue="0";
			cell_.setCellValue(Double.parseDouble(new BigDecimal(cellValue).toString()));
		}else if(type==java.sql.Types.INTEGER || type==java.sql.Types.SMALLINT ){
			cell_.setCellType(Cell.CELL_TYPE_NUMERIC);
			if(cellValue.length()<=0) 
				cellValue="0";
			cell_.setCellValue(Integer.parseInt(cellValue));
		}else if(type==java.sql.Types.BIGINT){
			cell_.setCellType(Cell.CELL_TYPE_NUMERIC);
			if(cellValue.length()<=0) 
				cellValue="0";
			cell_.setCellValue(Integer.parseInt(new BigInteger(cellValue).toString()));
		}else if(type==java.sql.Types.FLOAT){
			cell_.setCellType(Cell.CELL_TYPE_NUMERIC);
			if(cellValue.length()<=0) 
				cellValue="0";
			cell_.setCellValue(Float.parseFloat(cellValue));
		} else{
			cell_.setCellType(Cell.CELL_TYPE_STRING);
			cell_.setCellValue(cellValue.toString());
		}
	}
	public void setCellValue(String sheetName,String cellName, String cellValue, int type){
		if(workbook_.getNameIndex(cellName)!=-1){
			Name name = workbook_.getNameAt(workbook_.getNameIndex(cellName));
			AreaReference aref = new AreaReference(name.getRefersToFormula());
			CellReference[] crefs = aref.getAllReferencedCells();
		    for (int i=0; i<crefs.length; i++) {
			        Sheet s = workbook_.getSheet(sheetName);
			        Row r = s.getRow(crefs[i].getRow());
			        //Ako ima trazene celije u definiranome sheet-u
			        if(r.getCell(crefs[i].getCol())!=null){
				        Cell c = r.getCell(crefs[i].getCol());
				        if (type==java.sql.Types.DATE){
				        	 if(cellValue.length()<=0) 
				        		 c.setCellValue("");
				        	 else
				        		 c.setCellValue(AS2Date.parseStringDateToDate(cellValue));
				        }else if(type==java.sql.Types.DECIMAL || type==java.sql.Types.NUMERIC){
				        	 if(cellValue.length()<=0) 
				        		 cellValue="0";
				        	 c.setCellValue(Double.parseDouble(new BigDecimal(cellValue).toString()));
				        }else if(type==java.sql.Types.INTEGER || type==java.sql.Types.SMALLINT ){
				        	 if(cellValue.length()<=0) 
				        		 cellValue="0";
				        	 c.setCellValue(Integer.parseInt(cellValue));
				        }else if(type==java.sql.Types.BIGINT){
				        	if(cellValue.length()<=0) 
				        		cellValue="0";
				        	c.setCellValue(Integer.parseInt(new BigInteger(cellValue).toString()));
				        }else if(type==java.sql.Types.FLOAT){
				        	if(cellValue.length()<=0) 
				        		cellValue="0";
				        	c.setCellValue(Float.parseFloat(cellValue));
				        } else{
				        	c.setCellValue(cellValue.toString());
				        }
			    	}
		    }
		}
	}
	public void fillSheetWithData(String sheetName,AS2RecordList data, String column_sufix){
		if(column_sufix==null)
			fillSheetWithData(sheetName,data);
		else {
			for(AS2Record row:data.getRows()){
				String sufix = row.get(column_sufix);
				for(String key: data.getColumnNames()){
					setCellValue(sheetName,key+"_"+sufix, row.get(key),data.getMetaDataForName(key).getColumnType());
				}
			}
			workbook_.setForceFormulaRecalculation(true);
		}
    }
	
	public void fillSheetWithData(String sheetName,AS2RecordList data){
		int count=1;
		for(AS2Record row:data.getRows()){
			for(String key: data.getColumnNames()){
				setCellValue(sheetName,key+"_"+count, row.get(key),data.getMetaDataForName(key).getColumnType());
			}
			count++;
		}
		workbook_.setForceFormulaRecalculation(true);    	
    }
	public void fillSheetWithDataDefault(String sheetName,AS2RecordList data){
		for(AS2Record row:data.getRows()){
			for(String key: data.getColumnNames()){
				setCellValue(sheetName,key, row.get(key),data.getMetaDataForName(key).getColumnType());
			}
		}
		workbook_.setForceFormulaRecalculation(true);    	
    }
	public void fillSheetWithParameters(String sheetName, AS2Record parameters){
		Set<String> E = parameters.keys();
		for(String key: E){
			Object value = parameters.get(key);
			if (value instanceof java.lang.String){
				setCellValue(sheetName,key+"_param", parameters.get(key),parameters.getMetaDataForName(key).getColumnType());
			}
		}
		workbook_.setForceFormulaRecalculation(true);    	
    }
	public void printSheet() throws Exception {
		//iterate through each row from first sheet
        Iterator<Row> rowIterator = getSheetIterator();
        while(rowIterator.hasNext()){
            Row row = rowIterator.next(); 
            System.out.print("\n");
            //Fore each row iterate through each column
            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();             
                 
                switch (cell.getCellType()){
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t\t");
                        break;
                         
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t\t");
                        break;
                         
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue() + "\t\t");
                        break; 
                         
                }
	        }
        }
	}
	public byte[] saveWorkbookAsBytesCSV(String sheet_name) throws Exception {
		String CSV = ";";
		String str_;
		StringBuffer data = new StringBuffer();
		try {
			this.setCurrentSheet(sheet_name);			
			Cell cell;
			Row row;
			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = getRowIterator();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						data.append(cell.getBooleanCellValue() + CSV);
						break;
					case Cell.CELL_TYPE_NUMERIC:
						data.append(cell.getNumericCellValue() + CSV);
						break;
					case Cell.CELL_TYPE_STRING:
						str_ = cell.getStringCellValue();
						if(str_.contains(";"))
							str_="\""+str_+"\""; //in case there is a ; put " around the string
						data.append(str_ + CSV);
						break;
					case Cell.CELL_TYPE_BLANK:
						data.append("" + CSV);
						break;
					default:
						data.append(cell + CSV);
					}//data.append('\n');
				}// cell iterator
				data.append('\n');
			}// row iterator			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data.toString().getBytes();
	}
	
	public void saveWorkbookToFileAsCSV(String sheet_name, String csv_file) throws Exception {		
		try {
			File toFile = new File(csv_file);
			FileOutputStream fos = new FileOutputStream(toFile);
			fos.write(saveWorkbookAsBytesCSV(sheet_name));
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void saveWorkbook(FileOutputStream outFile) throws Exception {
		workbook_.write(outFile);
	}
	public abstract Iterator<Row> getRowIterator() throws Exception;
	public abstract void autoSizeColumn(int column)  throws Exception;
	public abstract void autoSizeAllColumns()  throws Exception;
	//public abstract void saveWorkbook(FileOutputStream outFile) throws Exception;
	public abstract void createSheet(String sheet_name) throws Exception;
	public abstract void deleteSheet(String sheet_name)throws Exception;
	public abstract void copySheet(String sheet_name, String new_name)throws Exception;
	public abstract void setCurrentSheet(String sheet_name) throws Exception;
	public abstract void createRow(int row_count) throws Exception;
	public abstract void setCurrentRow(int row_count) throws Exception;
	public abstract Row getRow(int row_count) throws Exception;
	public abstract Cell getRowCell(int row_count, int cell_count)throws Exception;
	public abstract Iterator<Row> getSheetIterator() throws Exception;
	public abstract void formatSheetHeader(AS2RecordList data);	
}
