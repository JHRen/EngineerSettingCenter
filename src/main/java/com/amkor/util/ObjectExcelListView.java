package com.amkor.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.amkor.util.PageData;
import com.amkor.util.Tools;

/**
 * 导入到EXCEL
 * 类名称：ObjectExcelView.java
 * @author Ren
 * @version 1.0
 */
public class ObjectExcelListView extends AbstractExcelView {   @Override
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Date date = new Date();
        String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");

        List<Map<String, Object>> sheetsData = (List<Map<String, Object>>) model.get("sheetsData");
        for (int sheetIndex = 0; sheetIndex < sheetsData.size(); sheetIndex++) {
            Map<String, Object> sheetData = sheetsData.get(sheetIndex);
            //sheet名字
            //HSSFSheet sheet = workbook.createSheet("sheet" + (sheetIndex + 1));
            HSSFSheet sheet = workbook.createSheet(sheetData.get("sheetName").toString());

            List<String> titles = (List<String>) sheetData.get("titles");
            List<PageData> varList = (List<PageData>) sheetData.get("varList");

            // 创建标题行
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            HSSFFont headerFont = workbook.createFont();
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headerFont.setFontHeightInPoints((short) 11);
            headerStyle.setFont(headerFont);

            HSSFCell cell;
            HSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < titles.size(); i++) {
                cell = headerRow.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(titles.get(i));
            }
            headerRow.setHeight((short) (25 * 20));

            // 创建内容行
            HSSFCellStyle contentStyle = workbook.createCellStyle();
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            for (int i = 0; i < varList.size(); i++) {
                PageData vpd = varList.get(i);
                HSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < titles.size(); j++) {
                    cell = row.createCell(j);
                    cell.setCellStyle(contentStyle);
                    cell.setCellValue(vpd.getString("var" + (j + 1)));
                }
            }
        }
    }}