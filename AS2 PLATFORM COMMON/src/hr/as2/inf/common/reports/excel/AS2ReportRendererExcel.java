/**
 * (C) Copyright 2013, Adriacom Software d.o.o.
 *	   Report service class for Jasper reports.
 */
//TODO net.sf.jasperreports.awt.ignore.missing.font=true
//http://stackoverflow.com/questions/3987804/jasper-stops-finding-one-font
package hr.as2.inf.common.reports.excel;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;
import hr.as2.inf.common.reports.AS2ReportRenderer;

import java.util.Map;

public final class  AS2ReportRendererExcel implements AS2ReportRenderer {

	@Override
	public byte[] renderReport(String sourceFileName, AS2Record as2_request,
			AS2RecordList recordList, String to_format) {
		try {
			AS2ExcelWorkbook wb = null;
			if(to_format.equals("xls"))
				wb = AS2ExcelWorkbookXls.convertRecordListToExcel(recordList);
			else
				wb=AS2ExcelWorkbookXlsx.convertRecordListToExcelXlsx(recordList);
			return wb.saveWorkbookAsBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "AS2ReportRendererExcel.renderReport.ERROR".getBytes();//TODO
	}

	@Override
	public byte[] renderReport(String sourceFileName,
			Map<String, Object> parameters, AS2RecordList rs, String to_format) {
		// TODO Auto-generated method stub
		return null;
	}
}