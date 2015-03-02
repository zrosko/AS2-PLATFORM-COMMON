package hr.as2.inf.common.reports.word;

import hr.as2.inf.common.file.AS2FileUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.POIXMLDocument;

public abstract class AS2Word {
	public final static String DEFAULT_EXCEL_SERVER_DIRECTORY = AS2FileUtility.DEFAULT_SERVER_DIRECTORY + "WORD" + java.io.File.separator;
	protected POIXMLDocument document_ = null;
	File file_;
	FileInputStream file_input_stream_;
	
	public AS2Word(){
	}
	public AS2Word(String file_name) throws Exception{
		String old_file_name = DEFAULT_EXCEL_SERVER_DIRECTORY+file_name;
		String new_file_name = AS2FileUtility.DEFAULT_SERVER_DIRECTORY_TEMP + file_name;
		new_file_name = AS2FileUtility.appendTimestampToFileName(new_file_name);
		file_ = AS2FileUtility.copy(old_file_name, new_file_name);
		file_input_stream_ = new FileInputStream(file_);
		file_.deleteOnExit();
	}
	public AS2Word(String file_path,String file_name) throws Exception{
		String old_file_name = file_path+java.io.File.separator+file_name;
		String new_file_name = AS2FileUtility.DEFAULT_SERVER_DIRECTORY_TEMP + file_name;
		new_file_name = AS2FileUtility.appendTimestampToFileName(new_file_name);
		file_ = AS2FileUtility.copy(old_file_name, new_file_name);
		file_input_stream_ = new FileInputStream(file_);
		file_.deleteOnExit();
	}
	
	/* WORKBOOK metode */
	public POIXMLDocument getDocument(){
		return document_;
	}
	public void saveDocument() throws Exception {
		file_input_stream_.close();
		FileOutputStream outFile = new FileOutputStream(file_);
		saveDocument(outFile);
	    outFile.close();
	}
	public byte[] saveDocumentAsBytes() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		document_.write(bos);
		byte[] fileByteArray = bos.toByteArray();
		bos.close();
		return fileByteArray;
		
	}
	public byte[] readDocumentBytes()throws Exception {
		return AS2FileUtility.readFileToBytes(file_);
	}
	public abstract void saveDocument(FileOutputStream outFile) throws Exception;
	public abstract void createParagraph(String paragraph_name) throws Exception;
}
