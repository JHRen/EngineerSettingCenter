package com.amkor.test.office2PDF;

import java.io.File;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.ExternalOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeConnectionProtocol;
import org.artofsolving.jodconverter.office.OfficeManager;

/**
 * 
 * @author 40607
 *
 */
public class PPT_2_PDF_Util {
	public static void ppt2PDF(File inputFile, File outputFile) {
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}

		// convert
		ExternalOfficeManagerConfiguration configuration = new ExternalOfficeManagerConfiguration();
		configuration.setConnectionProtocol(OfficeConnectionProtocol.SOCKET);
		configuration.setPortNumber(8100);
		OfficeManager officeManager = configuration.buildOfficeManager();
		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
		converter.convert(inputFile, outputFile);
		if (officeManager!=null) {
			officeManager.stop();
			System.out.println("关闭office转换服务");
		}
	}

	public static void main(String[] args) {
		File inputFile=new File("D:\\aaa\\OPL_Eng'r+hand+carry+units+out+of+company+without+approval.pptx");
		File outputFile = new File("D:\\aaa\\abc.pdf");
		ppt2PDF(inputFile, outputFile);

	}
	
	
	
	
}
