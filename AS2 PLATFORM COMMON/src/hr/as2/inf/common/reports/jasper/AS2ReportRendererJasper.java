/**
 * (C) Copyright 2014, Adriacom Software d.o.o.
 *	   Report service class for Jasper reports.
 */
//TODO net.sf.jasperreports.awt.ignore.missing.font=true
//http://stackoverflow.com/questions/3987804/jasper-stops-finding-one-font
package hr.as2.inf.common.reports.jasper;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;
import hr.as2.inf.common.reports.AS2JasperDataSource;
import hr.as2.inf.common.reports.AS2ReportRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePptxReportConfiguration;
import net.sf.jasperreports.export.SimpleRtfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.export.WriterExporterOutput;

public final class  AS2ReportRendererJasper implements AS2ReportRenderer {	
	
	public byte[] renderReport(String sourceFileName, Map<String,Object> parameters,AS2RecordList rs, String to_format) {
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(sourceFileName,parameters, new AS2JasperDataSource(rs.getRows()));
			return exportReportTo(jasperPrint, to_format);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "AS2ReportRendererJasper.renderReport.ERROR".getBytes();//TODO
	}
	
	public byte[] renderReport(String reportFilePath, AS2Record as2_request,AS2RecordList recordList, String to_format) {
		JasperPrint jasperPrint = null;
		try {
			String reportFilename = as2_request.getAsString(REPORT_FILE_NAME, DEFAULT_REPORT_FILENAME);
			String resourceName = DEFAULT_REPORT_SERVICE_PATH + reportFilename + ".jasper";
			String sourceFileName = as2_request.get("war_path") + java.io.File.separator+resourceName;
			jasperPrint = JasperFillManager.fillReport(sourceFileName,as2_request.getProperties(), new AS2JasperDataSource(recordList.getRows()));
			return exportReportTo(jasperPrint, to_format);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "AS2ReportRendererJasper.renderReport.ERROR".getBytes();//TODO
	}
	
	
	private byte[] exportReportTo(JasperPrint jasperPrint, String to_format) throws Exception {
		if(jasperPrint ==null || to_format == null)
			return "AS2ReportRendererJasper.exportReportTo.ERROR".getBytes();//TODO
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(to_format.equalsIgnoreCase("pdf")){
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		}else if(to_format.equalsIgnoreCase("html")){
			//TODO je li radi?
			HtmlExporter exporter = new HtmlExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleHtmlExporterOutput(baos));
			SimpleHtmlExporterConfiguration configuration = new SimpleHtmlExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
//			JRHtmlExporter exporter = new JRHtmlExporter();
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//			exporter.exportReport();
		}else if(to_format.equalsIgnoreCase("rtf")){
			JRRtfExporter exporter = new JRRtfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			//TODO je li radi
			exporter.setExporterOutput((WriterExporterOutput) new SimpleOutputStreamExporterOutput(baos));
			SimpleRtfExporterConfiguration configuration = new SimpleRtfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		}else if(to_format.equalsIgnoreCase("docx")){
			JRDocxExporter exporter = new JRDocxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
			exporter.setConfiguration(configuration);
		}else if(to_format.equalsIgnoreCase("xls")){
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(true);
			configuration.setDetectCellType(true);
			configuration.setCollapseRowSpan(false);
			exporter.setConfiguration(configuration);
		}else if(to_format.equalsIgnoreCase("xlsx")){
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
			configuration.setOnePagePerSheet(true);
			configuration.setDetectCellType(true);
			configuration.setCollapseRowSpan(false);
			exporter.setConfiguration(configuration);
		}else if(to_format.equalsIgnoreCase("pptx")){
			JRPptxExporter exporter = new JRPptxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			SimplePptxReportConfiguration configuration = new SimplePptxReportConfiguration();
			exporter.setConfiguration(configuration);
		}else{
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		}
		return baos.toByteArray();
	}
	//Deprecated
//	private byte[] exportReportTo(JasperPrint jasperPrint, String to_format) throws Exception {
//		if(jasperPrint ==null || to_format == null)
//			return "AS2ReportRendererJasper.exportReportTo.ERROR".getBytes();//TODO
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		JRAbstractExporter exporter;
//		if(to_format.equalsIgnoreCase("pdf")){
//			 exporter = new JRPdfExporter();
//		}else if(to_format.equalsIgnoreCase("html"))
//			exporter = new JRHtmlExporter();
//		else if(to_format.equalsIgnoreCase("rtf"))
//			exporter = new JRRtfExporter();
//		else if(to_format.equalsIgnoreCase("docx"))
//			exporter = new JRDocxExporter();
//		else if(to_format.equalsIgnoreCase("xls"))
//			exporter = new JRXlsExporter();
//		else if(to_format.equalsIgnoreCase("xlsx"))
//			exporter = new JRXlsxExporter();
//		else if(to_format.equalsIgnoreCase("pptx"))
//			exporter = new JRPptxExporter();
//		else
//			exporter = new JRPdfExporter();
//		
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//		exporter.exportReport();
//		return baos.toByteArray();
//	}
}