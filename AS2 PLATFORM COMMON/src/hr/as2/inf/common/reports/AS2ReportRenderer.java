package hr.as2.inf.common.reports;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;

import java.util.Map;
/**
 * Generic report render.
 * To be implemented by Jasper, Pentaho, Crystal,etc.
 * @author zrosko
 *
 */
public interface AS2ReportRenderer {
	//Defaults
	static final String DEFAULT_REPORT_FILENAME = "TODO";//TODO Error.jsp or ?
	static final String DEFAULT_REPORT_FORMAT = "pdf";
	static final String DEFAULT_REPORT_TYPE = ".jasper";
	static final String DEFAULT_REPORT_DISPOSITION = "inline";
	static final String DEFAULT_REPORT_SERVICE_PATH = "module/reports/"; /* war...*/
	static final String DEFAULT_CLIENT_TYPE = "smartgwt"; /* java ... */
	
	static final String REPORT_DISPOSITION = "report_disposition";
	static final String REPORT_FILE_NAME = "report_file_name";
	static final String REPORT_FORMAT = "report_format";
	static final String AS2_REPORT_FORMAT = "as2_report_format";
	static final String AS2_CLIENT_TYPE = "as2_client_type";
	
	public byte[] renderReport(
			String sourceFileName,
			Map<String,Object> parameters,
			AS2RecordList rs,
			String to_format);
	
	public byte[] renderReport(
			String sourceFileName,
			AS2Record as2_request,
			AS2RecordList recordList,
			String to_format);
}
