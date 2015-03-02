package hr.as2.inf.common.file;

import hr.as2.inf.common.data.as400.AS2AS400SpoolFileVo;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class AS2SpoolFile {
	
    public void showSpoolFile(String file_name){
		try {
            AS2FileUtility.deleteFileOnExit(file_name);
            AS2FileUtility.openFileWithReader(file_name);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
    }
    public String writeSpoolFile(AS2AS400SpoolFileVo vo){
        String naziv_datoteke = AS2FileUtility.prepareFileNameDefault(vo.getAsString("spfile_name")+".txt");
		BufferedWriter writer = null;
		try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(naziv_datoteke.trim()), "UTF16"));
			writer.write(vo.getSpfileTekst());
			return naziv_datoteke.trim();
		} catch (IOException e) {
			e.printStackTrace(System.out);
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}
		return "";
    }
}