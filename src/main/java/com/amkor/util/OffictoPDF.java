package com.amkor.util;

import java.io.File;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.ExternalOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeConnectionProtocol;
import org.artofsolving.jodconverter.office.OfficeManager;

/**
 * ppt转换成PDF 使用open office 工具类
 *  
 * @author 40607
 *
 */
public class OffictoPDF {
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
}
