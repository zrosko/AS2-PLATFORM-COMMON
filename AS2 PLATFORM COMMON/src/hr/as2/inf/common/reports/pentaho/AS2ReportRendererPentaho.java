/**
 * (C) Copyright 2013, Adriacom Software d.o.o.
 *	   Report service class for Jasper reports.
 */
//TODO net.sf.jasperreports.awt.ignore.missing.font=true
//http://stackoverflow.com/questions/3987804/jasper-stops-finding-one-font
package hr.as2.inf.common.reports.pentaho;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;
import hr.as2.inf.common.reports.AS2ReportRenderer;

import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;

public final class  AS2ReportRendererPentaho implements AS2ReportRenderer {
	@Override
	public byte[] renderReport(String sourceFileName, Map<String,Object> parameters, AS2RecordList rs, String to_format){
		return exportReportTo(null, to_format);
	}
	@Override
	public byte[] renderReport(String sourceFileName, AS2Record as2_request,
			AS2RecordList recordList, String to_format) {
		return exportReportTo(null, to_format);
	}
	//TODO
	public static byte[] exportReportTo(JasperPrint pentahoPrint, String to_format){
		if(pentahoPrint ==null || to_format == null)
			return "AS2ReportRendererPentaho.exportReportTo.ERROR".getBytes();//TODO
		if(to_format.equalsIgnoreCase("pdf"))
			return "pdf".getBytes();
		else if(to_format.equalsIgnoreCase("html"))
			return "pdf".getBytes();
		else if(to_format.equalsIgnoreCase("rtf"))
			return "pdf".getBytes();
		else if(to_format.equalsIgnoreCase("docx"))
			return "pdf".getBytes();
		else if(to_format.equalsIgnoreCase("xls"))
			return "pdf".getBytes();
		else if(to_format.equalsIgnoreCase("xlsx"))
			return "pdf".getBytes();
		else if(to_format.equalsIgnoreCase("pptx"))
			return "pdf".getBytes();
		else
			return "pdf".getBytes();
	}
}