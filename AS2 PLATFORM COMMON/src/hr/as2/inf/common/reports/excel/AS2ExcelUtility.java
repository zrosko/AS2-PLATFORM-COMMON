package hr.as2.inf.common.reports.excel;

import hr.as2.inf.common.data.AS2RecordList;
import hr.as2.inf.common.file.AS2FileUtility;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class AS2ExcelUtility {
	
	public static AS2ExcelWorkbookXls transformRecordListToExcel(AS2RecordList recordList, String outputDirPath) throws Exception{
		AS2ExcelWorkbookXls wb = AS2ExcelWorkbookXls.convertRecordListToExcel(recordList);
		FileOutputStream fos = new FileOutputStream(outputDirPath);
		wb.saveWorkbook(fos);
		return wb;
	}
	
	public static AS2ExcelWorkbookXls transformRecordListToExcel(AS2RecordList recordList) throws Exception{
		AS2ExcelWorkbookXls wb = AS2ExcelWorkbookXls.convertRecordListToExcel(recordList);
		String tempPath = AS2FileUtility.DEFAULT_SERVER_DIRECTORY_TEMP + AS2FileUtility.appendTimestampToFileName("excel.xls");
		FileOutputStream fos = new FileOutputStream(tempPath);
		wb.saveWorkbook(fos);
		return wb;
	}
	
	public static byte[] transformRecordListToExcelByteArray(AS2RecordList recordList) throws Exception{
		AS2ExcelWorkbookXls wb = AS2ExcelWorkbookXls.convertRecordListToExcel(recordList);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		wb.getWorkbook().write(bos);
		return bos.toByteArray();
	}
	

}
