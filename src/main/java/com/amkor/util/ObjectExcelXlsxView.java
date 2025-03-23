package com.amkor.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.amkor.util.PageData;
import com.amkor.util.Tools;
/**
* 导入到EXCEL
* 类名称：ObjectExcelView.java
* @author Ren
* @version 1.0
 */
public class ObjectExcelXlsxView extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		String filename =model.get("filename")+"_"+ Tools.date2Str(date, "yyyyMMddHHmmss");
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
		HSSFSheet sheet1 = workbook.createSheet("hardBin");
		HSSFSheet sheet2 = workbook.createSheet("softBin");
		
		//设置sheet1内容
		HSSFCell cell1;
		List<String> titles1 = (List<String>) model.get("titles1");
		int len = titles1.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont();	//标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short)11);
		headerStyle.setFont(headerFont);
		short width = 20,height=25*20;
		sheet1.setDefaultColumnWidth(width);
		for(int i=0; i<len; i++){ //设置标题
			String title1 = titles1.get(i);
			cell1 = getCell(sheet1, 0, i);
			cell1.setCellStyle(headerStyle);
			setText(cell1,title1);
		}
		sheet1.getRow(0).setHeight(height);
		
		HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		List<PageData> varList1 = (List<PageData>) model.get("varList1");
		int varCount = varList1.size();
		for(int i=0; i<varCount; i++){
			PageData vpd = varList1.get(i);
			for(int j=0;j<len;j++){
				Object varstr =  vpd.get("var"+(j+1));
				cell1 = getCell(sheet1, i+1, j);
				cell1.setCellStyle(contentStyle);
				setText(cell1,varstr.toString());
			}
			
		}
		
		//设置sheet2内容
		HSSFCell cell2;
		List<String> titles2 = (List<String>) model.get("titles2");
		int len2 = titles2.size();
		headerStyle.setFont(headerFont);
		sheet2.setDefaultColumnWidth(width);
		for(int i=0; i<len2; i++){ //设置标题
			String title2 = titles2.get(i);
			cell2 = getCell(sheet2, 0, i);
			cell2.setCellStyle(headerStyle);
			setText(cell2,title2);
		}
		sheet2.getRow(0).setHeight(height);
		
		List<PageData> varList2 = (List<PageData>) model.get("varList2");
		int varCount2 = varList2.size();
		for(int i=0; i<varCount2; i++){
			PageData vpd = varList2.get(i);
			for(int j=0;j<len2;j++){
				Object varstr =  vpd.get("var"+(j+1));
				cell2 = getCell(sheet2, i+1, j);
				cell2.setCellStyle(contentStyle);
				setText(cell2,varstr.toString());
			}
			
		}
	}}
