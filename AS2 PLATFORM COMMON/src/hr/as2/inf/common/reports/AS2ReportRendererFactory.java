package hr.as2.inf.common.reports;

import hr.as2.inf.common.core.AS2Context;
import hr.as2.inf.common.reports.excel.AS2ReportRendererExcel;
import hr.as2.inf.common.reports.jasper.AS2ReportRendererJasper;
import hr.as2.inf.common.reports.pentaho.AS2ReportRendererPentaho;
/**
 * Factory for report renderer (eg. jasper, pentaho, crystal,...)
 * @author zrosko
 *
 */
public final class AS2ReportRendererFactory {
	private static final String REPORTS_TYPE_JASPER = ".jasper";
	private static final String REPORTS_TYPE_PENTAHO = ".pentaho";
	private static final String REPORTS_TYPE_EXCEL = ".xls";
	private static final String REPORTS_TYPE_EXCELX = ".xlsx";
	//private static final String REPORTS_TYPE_CRYSTAL = ".crystal";

	private static AS2ReportRendererFactory _instance = null;

	private AS2ReportRendererFactory() {
		AS2Context.setSingletonReference(this);
	}
	public static AS2ReportRendererFactory getInstance(){
		if(_instance == null)
			_instance = new AS2ReportRendererFactory();
		return _instance;
	}
	public  AS2ReportRenderer createRenderer(String reportType) {
		AS2ReportRenderer as2renderer = null;
		if(reportType.equals(REPORTS_TYPE_JASPER))
			as2renderer = new AS2ReportRendererJasper();
		else if(reportType.equals(REPORTS_TYPE_PENTAHO))
			as2renderer = new AS2ReportRendererPentaho();
		else if(reportType.equals(REPORTS_TYPE_EXCEL)||reportType.equals(REPORTS_TYPE_EXCELX))
			as2renderer = new AS2ReportRendererExcel();
		return as2renderer;
	}
}
