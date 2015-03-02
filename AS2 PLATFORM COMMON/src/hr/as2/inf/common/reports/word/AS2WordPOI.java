package hr.as2.inf.common.reports.word;

import hr.as2.inf.common.data.AS2Record;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class AS2WordPOI {

    public ArrayList<String> getReplaceElementsInWord(String filePath,
            String regex) {
        String[] p = filePath.split("\\.");
        if (p.length > 0) {
            if (p[p.length - 1].equalsIgnoreCase("doc")) {
                ArrayList<String> al = new ArrayList<>();
                File file = new File(filePath);
                HWPFDocument document = null;
                try {
                    InputStream is = new FileInputStream(file);
                    document = new HWPFDocument(is);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Range range = document.getRange();
                String rangeText = range.text();
                CharSequence cs = rangeText.subSequence(0, rangeText.length());
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(cs);
                int startPosition = 0;
                while (matcher.find(startPosition)) {
                    if (!al.contains(matcher.group())) {
                        al.add(matcher.group());
                    }
                    startPosition = matcher.end();
                }
                return al;
            } else if (p[p.length - 1].equalsIgnoreCase("docx")) {
                ArrayList<String> al = new ArrayList<>();
                XWPFDocument document = null;
                try {
                    document = new XWPFDocument(
                            POIXMLDocument.openPackage(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Iterator<XWPFParagraph> itPara = document
                        .getParagraphsIterator();
                while (itPara.hasNext()) {
                    XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                    String paragraphString = paragraph.getText();
                    CharSequence cs = paragraphString.subSequence(0,
                            paragraphString.length());
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(cs);
                    int startPosition = 0;
                    while (matcher.find(startPosition)) {
                        if (!al.contains(matcher.group())) {
                            al.add(matcher.group());
                        }
                        startPosition = matcher.end();
                    }
                }
                Iterator<XWPFTable> itTable = document.getTablesIterator();
                while (itTable.hasNext()) {
                    XWPFTable table = (XWPFTable) itTable.next();
                    int rcount = table.getNumberOfRows();
                    for (int i = 0; i < rcount; i++) {
                        XWPFTableRow row = table.getRow(i);
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            String cellText = "";
                            cellText = cell.getText();
                            CharSequence cs = cellText.subSequence(0,
                                    cellText.length());
                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(cs);
                            int startPosition = 0;
                            while (matcher.find(startPosition)) {
                                if (!al.contains(matcher.group())) {
                                    al.add(matcher.group());
                                }
                                startPosition = matcher.end();
                            }
                        }
                    }
                }
                return al;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean replaceAndGenerateWord(String srcPath,
            String destPath, Map<String, String> map) {
        String[] sp = srcPath.split("\\.");
        String[] dp = destPath.split("\\.");
        if ((sp.length > 0) && (dp.length > 0)) {         
            if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
                try {
                    XWPFDocument document = new XWPFDocument(
                            POIXMLDocument.openPackage(srcPath));
                    Iterator<XWPFParagraph> itPara = document
                            .getParagraphsIterator();
                    while (itPara.hasNext()) {
                        XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (int i = 0; i < runs.size(); i++) {
                            String oneparaString = runs.get(i).getText(
                                    runs.get(i).getTextPosition());
                            for (Map.Entry<String, String> entry : map
                                    .entrySet()) {
                                oneparaString = oneparaString.replace(
                                        entry.getKey(), entry.getValue());
                                System.out.println( "oneparaString: "+oneparaString);
                                System.out.println( ""+entry.getKey()+"  "+entry.getValue());
                            }
                            runs.get(i).setText(oneparaString, 0);
                        }
                    }

                    Iterator<XWPFTable> itTable = document.getTablesIterator();
                    while (itTable.hasNext()) {
                        XWPFTable table = (XWPFTable) itTable.next();
                        int rcount = table.getNumberOfRows();
                        for (int i = 0; i < rcount; i++) {
                            XWPFTableRow row = table.getRow(i);
                            List<XWPFTableCell> cells = row.getTableCells();
                            for (XWPFTableCell cell : cells) {
                                String cellTextString = cell.getText();
                                for (Entry<String, String> e : map.entrySet()) {
                                    if (cellTextString.contains(e.getKey()))
                                        cellTextString = cellTextString
                                                .replace(e.getKey(),
                                                        e.getValue());
                                }
                                cell.removeParagraph(0);
                                cell.setText(cellTextString);
                            }
                        }
                    }
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(destPath);
                    document.write(outStream);
                    outStream.close();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            } else

            if ((sp[sp.length - 1].equalsIgnoreCase("doc"))
                    && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {
                HWPFDocument document = null;
                try {
                    document = new HWPFDocument(new FileInputStream(srcPath));
                    Range range = document.getRange();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        range.replaceText(entry.getKey(), entry.getValue());
                    }
//                    range = document.getHeaderStoryRange();
//                    for (Map.Entry<String, String> entry : map.entrySet()) {
//                        range.replaceText(entry.getKey(), entry.getValue());
//                    }
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(destPath);
                    document.write(outStream);
                    outStream.close();
                    return true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static byte[] generateWord(String srcPath,AS2Record map) {
        String[] sp = srcPath.split("\\.");        
        if (sp.length > 0) {         
             if (sp[sp.length - 1].equalsIgnoreCase("doc")) {
                HWPFDocument document = null;
                try {
                    document = new HWPFDocument(new FileInputStream(srcPath));
                    Range range = document.getRange();
                    for (Map.Entry<String, Object> entry : map.datas()) {
                        range.replaceText(entry.getKey(), entry.getValue().toString());
                    }
//                    range = document.getHeaderStoryRange();
//                    for (Map.Entry<String, String> entry : map.entrySet()) {
//                        range.replaceText(entry.getKey(), entry.getValue());
//                  }
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
    private static String getFieldName(String field_name){
 		StringBuffer sb = new StringBuffer();
 		sb.append("<<");
 		sb.append(field_name);
 		sb.append(">>");
 		return sb.toString();
 	}
    private static String getFieldNamex(String field_name){
  		StringBuffer sb = new StringBuffer();
  		sb.append("${");
  		sb.append(field_name);
  		sb.append("}");
  		return sb.toString();
  	}
    public static void main(String[] args) {
    	try{
	        String filepathString = "C:\\AS2_ZR\\WS\\AS2 PLATFORM COMMON\\OVRHA.doc";
	        String destpathString = "C:\\AS2_ZR\\WS\\AS2 PLATFORM COMMON\\OVRHA_FOI_1.doc";
	        AS2Record map = new AS2Record();
	        map.set(getFieldNamex("prima"), "JAVNI  BILJEŽNIK\nNEVENKA NAKIĆ\nANTE STARČEVIĆA 5 22 000 ŠIBENIK");
	        map.set(getFieldNamex("ovrhovoditelj"), "JADRANSKA BANKA d.d. Šibenik, Ante Starčevića br. 4, 2200 Šibenik, OIB 02899494784, "
	        		+ "zastupana po Predsjedniku Uprave Ivica Džapo, dipl. ek. iz Šibenika");
	        map.set(getFieldNamex("ovrsenik"), "MARKO MARKOVIĆ, Obala viteza 40, 22010 Brodarica, OIB71786243840");
	        map.set(getFieldNamex("broj_partije_32"), "3201394343");
	        map.set(getFieldNamex("broj_partije_74"), "7401002726");
	        
	        File file = new File(destpathString);
	        FileOutputStream fos = new FileOutputStream(file);
			fos.write(generateWord(filepathString, map));
			fos.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        
    }

    public static void main2(String[] args) {
        String filepathString = "C:\\AS2_ZR\\WS\\AS2 PLATFORM COMMON\\Template.doc";
        String destpathString = "C:\\AS2_ZR\\WS\\AS2 PLATFORM COMMON\\Template_2021.doc";
        Map<String, String> map = new HashMap<String, String>();
        map.put(getFieldName("TextData"), "Jadranska banka d.d.");
        map.put(getFieldName("DataForFooter"), "Direkcija Informatike");
        map.put(getFieldName("InLineData"), "1200,00 kn");
        map.put(getFieldName("Date"), "12.11.2014.");
        System.out.println(replaceAndGenerateWord(filepathString,
                destpathString, map));
    }
}