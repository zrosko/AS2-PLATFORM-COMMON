package hr.as2.inf.common.reports.word;

import hr.as2.inf.common.data.AS2Record;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

public class AS2WordDoc extends AS2Word {
	HWPFDocument document = null;
	
	public byte[] generateWord(String srcPath, AS2Record map) {
		String[] sp = srcPath.split("\\.");
		if (sp.length > 0) {
			if (sp[sp.length - 1].equalsIgnoreCase("doc")) {
				try {
					document = new HWPFDocument(new FileInputStream(srcPath));
					Range range = document.getRange();
					for (Map.Entry<String, Object> entry : map.datas()) {
						range.replaceText(entry.getKey(), entry.getValue()
								.toString());
					}
					// range = document.getHeaderStoryRange();
					// for (Map.Entry<String, String> entry : map.entrySet()) {
					// range.replaceText(entry.getKey(), entry.getValue());
					// }
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					document.write(bos);
					byte[] fileByteArray = bos.toByteArray();
					bos.close();
					return fileByteArray;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public String getFieldNamex(String field_name) {
		StringBuffer sb = new StringBuffer();
		sb.append("${");
		sb.append(field_name);
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public void saveDocument(FileOutputStream outFile) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void createParagraph(String paragraph_name) throws Exception {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
    	try{
    		AS2WordDoc doc = new AS2WordDoc();
	        String filepathString = "C:\\AS2_ZR\\WS\\AS2 PLATFORM COMMON\\OVRHA.doc";
	        String destpathString = "C:\\AS2_ZR\\WS\\AS2 PLATFORM COMMON\\OVRHA_BUJAS.doc";
	        AS2Record map = new AS2Record();
	        map.set(doc.getFieldNamex("prima"), "JAVNI  BILJEŽNIK\nNEVENKA NAKIĆ\nANTE STARČEVIĆA 5 22 000 ŠIBENIK");
	        map.set(doc.getFieldNamex("ovrhovoditelj"), "JADRANSKA BANKA d.d. Šibenik, Ante Starčevića br. 4, 2200 Šibenik, OIB 02899494784, "
	        		+ "zastupana po Predsjedniku Uprave Ivica Džapo, dipl. ek. iz Šibenika");
	        map.set(doc.getFieldNamex("ovrsenik"), "MARKO MARKOVIĆ, Obala viteza 40, 22010 Brodarica, OIB71786243840");
	        map.set(doc.getFieldNamex("broj_partije_32"), "3201394343");
	        map.set(doc.getFieldNamex("broj_partije_74"), "7401002726");
	        
	        File file = new File(destpathString);
	        FileOutputStream fos = new FileOutputStream(file);
			fos.write(doc.generateWord(filepathString, map));
			fos.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        
    }

}
