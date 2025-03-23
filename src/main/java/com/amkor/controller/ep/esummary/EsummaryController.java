package com.amkor.controller.ep.esummary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.service.ep.esummary.EsummaryManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Const;
import com.amkor.util.FileDownload;
import com.amkor.util.Jurisdiction;
import com.amkor.util.ObjectExcelListView;
import com.amkor.util.ObjectExcelView;
import com.amkor.util.ObjectExcelXlsxView;
import com.amkor.util.PageData;
import com.amkor.util.PathUtil;
import com.amkor.util.StringUtil;
import com.amkor.util.Tools;
import com.amkor.util.mail.SendMailToAll;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

/**
 * 
 * @author 40607
 *
 */
@Controller
public class EsummaryController extends BaseController {
	@Resource(name = "esummaryService")
	private EsummaryManager esummaryService;

	/**
	 * 显示主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/esummary")
	public ModelAndView toLogin() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("esummary/index/esummary_list");
		mv.addObject("ftCss", "none");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/esummary/listAll")
	@ResponseBody
	public ModelAndView listlistAll() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.getString("custlotno") == null || pd.getString("custlotno") == "") {
			mv.setViewName("esummary/index/esummary_list");
			return mv;
		}

		pd.put("LOTID", pd.getString("custlotno"));
		pd.put("isExcel", 1);

		try {
			logger.info("当前custlotno: " + pd.getString("custlotno"));
			// 下拉框回显
			List<PageData> listFtLotno = esummaryService.findFtlotnoByCustlotno(pd);
			List<PageData> listTestcode = esummaryService.findTestcodeByCustlotno(pd);
			List<PageData> listReTestcode = esummaryService.findReTestcodeByCustlotno(pd);
			List<PageData> listFilename = esummaryService.findFilenameByCustlotno(pd);

			List<PageData> listHbin = esummaryService.listHbin(pd);
			List<PageData> listHbinData = getBinData(listHbin, "H");

			List<PageData> listSbin = esummaryService.listSbin(pd);
			List<PageData> listSbinData = getBinData(listSbin, "S");

			mv.addObject("listHbin", listHbinData);
			mv.addObject("listSbin", listSbinData);
			mv.addObject("listTestcode", listTestcode);
			mv.addObject("listReTestcode", listReTestcode);
			mv.addObject("listFilename", listFilename);
			if (Objects.nonNull(pd.getString("ftlotno"))) {
				mv.addObject("ftCss", "");
				mv.addObject("listFtlotno", listFtLotno);
			} else {
				mv.addObject("ftCss", "none");
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			mv.addObject("result", "error");
			mv.setViewName("esummary/index/esummary_list");
		}
		mv.addObject("pd", pd);
		mv.setViewName("esummary/index/esummary_list");
		return mv;
	}
	

	/**
	 * @param listBin
	 * @param binFlag
	 * @return
	 */
	public static List<PageData> getBinData(List<PageData> listBin, String binFlag) {
		boolean emailFlag = false;
		StringBuffer emailContent = new StringBuffer();

		// PageData hpd = new PageData();
		// PageData hsitePd = new PageData();

		LinkedHashMap<Object, Object> hpd = new LinkedHashMap<>();
		LinkedHashMap<Object, Object> hsitePd = new LinkedHashMap<>();

		// 存放每个site总数
		PageData siteTotalPd = new PageData();
		List<PageData> listBinData = new ArrayList<>();
		List<PageData> listStrBinData = new ArrayList<>();
		String BIN_INFO = binFlag + "BIN";
		String BIN_CNT = binFlag + "BIN_CNT";
		String BIN_TOTAL = binFlag + "B_TOTAL";

		double allTotal = 0;
		int listBinSize = listBin.size();
		if (listBinSize > 5000) {
			System.out.println(" error , ListBinSize :" + listBinSize + " <br>");
		}
		emailContent.append(BIN_INFO + " error , ListBinSize :" + listBinSize + " <br>");

		for (int i = 0; i < listBinSize; i++) {
			String site = listBin.get(i).getString("SITE");
			String bin = listBin.get(i).getString(BIN_INFO);
			String binCount = listBin.get(i).getString(BIN_CNT);

			// 判断bin 和binCount 是否是数字
			if (StringUtil.isInteger(bin) && StringUtil.isInteger(binCount) && StrUtil.isNotEmpty(bin)
					&& StrUtil.isNotEmpty(binCount)) {

				allTotal += Double.parseDouble(binCount);
				// 获取每个Hbin的总数量 (先获获取当前key的value,然后累加再覆盖)
				Integer perHbinCount = (Integer) hpd.get(bin);
				if (Objects.isNull(perHbinCount)) {
					perHbinCount = 0;
				}

				Integer hinCount = perHbinCount + Integer.parseInt(binCount);
				hpd.put(bin, hinCount);
				// System.out.println("当前hbin:"+bin+" 。当前bin总数:"+hinCount);

				// 获取每个 site的总数
				String siteNo = "SITE" + site;
				Integer perSiteCount = (Integer) siteTotalPd.get(siteNo);
				if (Objects.isNull(perSiteCount)) {
					perSiteCount = 0;
				}
				Integer siteCount = perSiteCount + Integer.parseInt(binCount);
				siteTotalPd.put(siteNo, siteCount);

				// 获取每个Hbin 对应site 的总数量
				Integer perHbinSiteCount = (Integer) hsitePd.get(bin + "|" + site);
				if (Objects.isNull(perHbinSiteCount)) {
					perHbinSiteCount = 0;
				}
				Integer hbinSiteCount = perHbinSiteCount + Integer.parseInt(binCount);
				hsitePd.put(bin + "|" + site, hbinSiteCount);
				// System.out.println("当前hbin site:"+bin+"|"+site+" 。当前bin总数:"+hbinSiteCount);
			} else {
				emailFlag = true;
				emailContent.append(" lotid:" + listBin.get(i).getString("LOTID"));
				emailContent.append(", bin:" + bin);
				emailContent.append(", site:" + site);
				emailContent.append(", binCount:" + binCount);
				emailContent.append("<br>");
			}
		}

		// 如果有错误数据发送报警邮件
		if (emailFlag) {
			String sendTo[] = { "junhuan.ren@amkor.com" };
			try {
				SendMailToAll.sendmail("Esummary Data Error Alert", "SHCN-TEST-IT@amkor.com", sendTo,
						emailContent.toString(), null, "text/html;charset=UTF-8");
			} catch (Exception e1) {

			}
		}

		PageData npd = null;
		Object binTotal = null;
		for (Object key : hpd.keySet()) {
			npd = new PageData();
			binTotal = hpd.get(key);
			npd.put(BIN_INFO, key); // HBIN
			npd.put(BIN_TOTAL, binTotal); // HB_TOTAL
			npd.put("YIELD", NumberUtil.decimalFormat("#.##%",
					NumberUtil.div(Double.parseDouble(binTotal.toString()), allTotal))); // YIELD

			for (Object siteKey : hsitePd.keySet()) {
				String[] siteKeySplit = siteKey.toString().split("\\|");
				if (siteKeySplit[0].equals(key)) {// 相同的 HBIN下保存 SITE的数量
					Object siteNum = hsitePd.get(siteKey);
					//System.out.println(siteNum+","+siteKeySplit[1]);
					Object siteTotal = siteTotalPd.get("SITE" + siteKeySplit[1]);
					if ("0".equals(siteTotal.toString())) {
						continue;
					}
					npd.put("SITE" + siteKeySplit[1], siteNum); // SITE
					//计算site的yield
					npd.put("S" + siteKeySplit[1] + "_Y", NumberUtil.decimalFormat("#.##%", NumberUtil
							.div(Double.parseDouble(siteNum.toString()), Double.parseDouble(siteTotal.toString())))); // SITE
																														// TOTAL:
																														// siteTotalPd
				}

			}
			if (StringUtil.isInteger(key.toString())) {
				listBinData.add(npd);
			} else {
				listStrBinData.add(npd);
			}
		}

		// 按bin排序

		Collections.sort(listBinData, new Comparator<PageData>() {
			@Override
			public int compare(PageData pd1, PageData pd2) {

				Integer bin1 = Integer.valueOf(pd1.get(BIN_INFO).toString());
				Integer bin2 = Integer.valueOf(pd2.get(BIN_INFO).toString());
				return bin1.compareTo(bin2);
			}
		});

		listBinData.addAll(listStrBinData); // 添加保存为string 的list
		// 添加最后total
		siteTotalPd.put(BIN_INFO, "Total");
		siteTotalPd.put(BIN_TOTAL, (int) allTotal);
		listBinData.add(siteTotalPd);

		return listBinData;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/esummary_lotno", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object searchLot() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		logger.info("开始查询当前lot：" + pd.toString());
		List<PageData> listEsumLotMir = esummaryService.listEsumLotMir(pd);
		List<PageData> ftlotnoList = new ArrayList<PageData>();
		List<PageData> testcodeList = new ArrayList<PageData>();

		PageData fpd = null;
		PageData tpd = null;
		for (int i = 0; i < listEsumLotMir.size(); i++) {
			fpd = new PageData();
			tpd = new PageData();

			String ftLotno = listEsumLotMir.get(i).getString("FTLOTNO");
			String testCode = listEsumLotMir.get(i).getString("TESTCODE");

			if (Objects.nonNull(ftLotno)) {
				fpd.put("ftlotno", ftLotno);
				ftlotnoList.add(fpd);
			}
			tpd.put("testcode", testCode);
			testcodeList.add(tpd);
		}

		Map<String, Object> map = new HashMap<String, Object>();

		if (ftlotnoList.size() > 0) {
			// map.put("ftlotnoList", ftlotnoList);
			map.put("result", "ft_sucess");
			map.put("ftlotnoList", ftlotnoList.stream().distinct().collect(Collectors.toList())); // list 去重复
		} else {
			map.put("result", "ft_null");
			map.put("testcodeList", testcodeList.stream().distinct().collect(Collectors.toList())); // list 去重复
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/esummary/getLevel", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object getLevel() throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("KEYWORDS");
		logger.info("查询当前keywords：" + keywords);

		List<PageData> listEsumLotMir = esummaryService.listEsumLotMir(pd);

		ArrayList<LinkedHashMap<String, Object>> levList = new ArrayList<>();
		for (int i = 0; i < listEsumLotMir.size(); i++) {
			LinkedHashMap<String, Object> levMap = new LinkedHashMap<String, Object>();
			String lev = listEsumLotMir.get(i).getString(keywords);
			levMap.put(keywords, lev);
			levList.add(levMap);
		}

		map.put("listLevel", levList.stream().distinct().collect(Collectors.toList()));
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 导出信息到EXCEL
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/esummary/excel")
	@ResponseBody
	public ModelAndView exportExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd != null) {

			try {
				List<PageData> listHbin = esummaryService.listHbin(pd);
				List<PageData> listHbinData = getBinData(listHbin, "H");

				List<PageData> listSbin = esummaryService.listSbin(pd);
				List<PageData> listSbinData = getBinData(listSbin, "S");

				Map<String, Object> dataMap = new HashMap<String, Object>();
				// hard bin
				List<String> titles1 = new ArrayList<String>();
				titles1.add("HBIN");
				titles1.add("HB_TOTAL");
				titles1.add("YIELD");
				titles1.add("SITE0");
				titles1.add("S0_Y");
				titles1.add("SITE1");
				titles1.add("S1_Y");
				titles1.add("SITE2");
				titles1.add("S2_Y");
				titles1.add("SITE3");
				titles1.add("S3_Y");
				titles1.add("SITE4");
				titles1.add("S4_Y");
				titles1.add("SITE5");
				titles1.add("S5_Y");
				titles1.add("SITE6");
				titles1.add("S6_Y");
				titles1.add("SITE7");
				titles1.add("S7_Y");
				titles1.add("SITE8");
				titles1.add("S8_Y");
				titles1.add("SITE9");
				titles1.add("S9_Y");
				titles1.add("SITE10");
				titles1.add("S10_Y");
				titles1.add("SITE11");
				titles1.add("S11_Y");
				titles1.add("SITE12");
				titles1.add("S12_Y");
				dataMap.put("titles1", titles1);

				List<PageData> hardBinList = new ArrayList<PageData>();
				for (int i = 0; i < listHbinData.size(); i++) {
					PageData vpd = new PageData();
					vpd.put("var1",Tools.isNotEmpty(listHbinData.get(i).get("HBIN")));
					vpd.put("var2",Tools.isNotEmpty( listHbinData.get(i).get("HB_TOTAL")));
					vpd.put("var3",Tools.isNotEmpty( listHbinData.get(i).get("YIELD")));
					vpd.put("var4",Tools.isNotEmpty( listHbinData.get(i).get("SITE0")));
					vpd.put("var5",Tools.isNotEmpty( listHbinData.get(i).get("S0_Y")));
					vpd.put("var6",Tools.isNotEmpty( listHbinData.get(i).get("SITE1")));
					vpd.put("var7",Tools.isNotEmpty( listHbinData.get(i).get("S1_Y")));
					vpd.put("var8",Tools.isNotEmpty( listHbinData.get(i).get("SITE2")));
					vpd.put("var9",Tools.isNotEmpty( listHbinData.get(i).get("S2_Y")));
					vpd.put("var10",Tools.isNotEmpty( listHbinData.get(i).get("SITE3")));
					vpd.put("var11",Tools.isNotEmpty( listHbinData.get(i).get("S3_Y")));
					vpd.put("var12",Tools.isNotEmpty( listHbinData.get(i).get("SITE4")));
					vpd.put("var13",Tools.isNotEmpty( listHbinData.get(i).get("S4_Y")));
					vpd.put("var14",Tools.isNotEmpty( listHbinData.get(i).get("SITE5")));
					vpd.put("var15",Tools.isNotEmpty( listHbinData.get(i).get("S5_Y")));
					vpd.put("var16",Tools.isNotEmpty( listHbinData.get(i).get("SITE6")));
					vpd.put("var17",Tools.isNotEmpty( listHbinData.get(i).get("S6_Y")));
					vpd.put("var18",Tools.isNotEmpty( listHbinData.get(i).get("SITE7")));
					vpd.put("var19",Tools.isNotEmpty( listHbinData.get(i).get("S7_Y")));
					vpd.put("var20",Tools.isNotEmpty( listHbinData.get(i).get("SITE8")));
					vpd.put("var21",Tools.isNotEmpty( listHbinData.get(i).get("S8_Y")));
					vpd.put("var22",Tools.isNotEmpty( listHbinData.get(i).get("SITE9")));
					vpd.put("var23",Tools.isNotEmpty( listHbinData.get(i).get("S9_Y")));
					vpd.put("var24",Tools.isNotEmpty( listHbinData.get(i).get("SITE10")));
					vpd.put("var25",Tools.isNotEmpty( listHbinData.get(i).get("S10_Y")));
					vpd.put("var26",Tools.isNotEmpty( listHbinData.get(i).get("SITE11")));
					vpd.put("var27",Tools.isNotEmpty( listHbinData.get(i).get("S11_Y")));
					vpd.put("var28",Tools.isNotEmpty( listHbinData.get(i).get("SITE12")));
					vpd.put("var29",Tools.isNotEmpty( listHbinData.get(i).get("S12_Y")));
					hardBinList.add(vpd);
				}
				// soft bin
				List<String> titles2 = new ArrayList<String>();
				titles2.add("SBIN");
				titles2.add("SB_TOTAL");
				titles2.add("YIELD");
				titles2.add("SITE0");
				titles2.add("S0_Y");
				titles2.add("SITE1");
				titles2.add("S1_Y");
				titles2.add("SITE2");
				titles2.add("S2_Y");
				titles2.add("SITE3");
				titles2.add("S3_Y");
				titles2.add("SITE4");
				titles2.add("S4_Y");
				titles2.add("SITE5");
				titles2.add("S5_Y");
				titles2.add("SITE6");
				titles2.add("S6_Y");
				titles2.add("SITE7");
				titles2.add("S7_Y");
				titles2.add("SITE8");
				titles2.add("S8_Y");
				titles2.add("SITE9");
				titles2.add("S9_Y");
				titles2.add("SITE10");
				titles2.add("S10_Y");
				titles2.add("SITE11");
				titles2.add("S11_Y");
				titles2.add("SITE12");
				titles2.add("S12_Y");
				dataMap.put("titles2", titles2);

				List<PageData> softBinList = new ArrayList<PageData>();
				for (int i = 0; i < listSbinData.size(); i++) {
					PageData vpd = new PageData();
					vpd.put("var1",Tools.isNotEmpty( listSbinData.get(i).get("SBIN")));
					vpd.put("var2",Tools.isNotEmpty( listSbinData.get(i).get("SB_TOTAL")));
					vpd.put("var3",Tools.isNotEmpty( listSbinData.get(i).get("YIELD")));
					vpd.put("var4",Tools.isNotEmpty( listSbinData.get(i).get("SITE0")));
					vpd.put("var5",Tools.isNotEmpty( listSbinData.get(i).get("S0_Y")));
					vpd.put("var6",Tools.isNotEmpty( listSbinData.get(i).get("SITE1")));
					vpd.put("var7",Tools.isNotEmpty( listSbinData.get(i).get("S1_Y")));
					vpd.put("var8",Tools.isNotEmpty( listSbinData.get(i).get("SITE2")));
					vpd.put("var9",Tools.isNotEmpty( listSbinData.get(i).get("S2_Y")));
					vpd.put("var10",Tools.isNotEmpty( listSbinData.get(i).get("SITE3")));
					vpd.put("var11",Tools.isNotEmpty( listSbinData.get(i).get("S3_Y")));
					vpd.put("var12",Tools.isNotEmpty( listSbinData.get(i).get("SITE4")));
					vpd.put("var13",Tools.isNotEmpty( listSbinData.get(i).get("S4_Y")));
					vpd.put("var14",Tools.isNotEmpty( listSbinData.get(i).get("SITE5")));
					vpd.put("var15",Tools.isNotEmpty( listSbinData.get(i).get("S5_Y")));
					vpd.put("var16",Tools.isNotEmpty( listSbinData.get(i).get("SITE6")));
					vpd.put("var17",Tools.isNotEmpty( listSbinData.get(i).get("S6_Y")));
					vpd.put("var18",Tools.isNotEmpty( listSbinData.get(i).get("SITE7")));
					vpd.put("var19",Tools.isNotEmpty( listSbinData.get(i).get("S7_Y")));
					vpd.put("var20",Tools.isNotEmpty( listSbinData.get(i).get("SITE8")));
					vpd.put("var21",Tools.isNotEmpty( listSbinData.get(i).get("S8_Y")));
					vpd.put("var22",Tools.isNotEmpty( listSbinData.get(i).get("SITE9")));
					vpd.put("var23",Tools.isNotEmpty( listSbinData.get(i).get("S9_Y")));
					vpd.put("var24",Tools.isNotEmpty( listSbinData.get(i).get("SITE10")));
					vpd.put("var25",Tools.isNotEmpty( listSbinData.get(i).get("S10_Y")));
					vpd.put("var26",Tools.isNotEmpty( listSbinData.get(i).get("SITE11")));
					vpd.put("var27",Tools.isNotEmpty( listSbinData.get(i).get("S11_Y")));
					vpd.put("var28",Tools.isNotEmpty( listSbinData.get(i).get("SITE12")));
					vpd.put("var29",Tools.isNotEmpty( listSbinData.get(i).get("S12_Y")));
					softBinList.add(vpd);
				}

				dataMap.put("varList1", hardBinList);
				dataMap.put("varList2", softBinList);
				dataMap.put("filename", pd.getString("LOTID"));
				ObjectExcelXlsxView erv = new ObjectExcelXlsxView(); // 执行excel操作
				mv = new ModelAndView(erv, dataMap);

			} catch (Exception e) {
				logger.error(e.toString(), e);
			}
		}else {
			mv.addObject("error","none");
		}
		
		return mv;
	}
	
	 /**去excel编辑页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/esummary/goExport")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("esummary/index/esummary_exportAll");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	
	/**excel按条件导出
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/esummary/exportAll")
	public ModelAndView exportAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String STARTTIME=pd.getString("STARTTIME").trim();
		String ENDTIME=pd.getString("ENDTIME").trim();
		String lotno_all=pd.getString("LOTNO").trim();
		String[] lotnoArray = lotno_all.split("\\R");
		pd.put("STARTTIME", STARTTIME);
		pd.put("ENDTIME", ENDTIME);
		List<String> custLotNos =new ArrayList<>();
		for(String arr:lotnoArray) {
			custLotNos.add(arr.trim());
		}
		pd.put("CUSTLOTNOS", custLotNos);
		
		List<PageData> listEsumLotMir = esummaryService.listEsumLotMirForExcel(pd);
		
		//esummaryService.listSbin(pd);
		 // 创建模型
        Map<String, Object> dataMap = new HashMap<>();

        // 创建多个sheet的数据
        List<Map<String, Object>> sheetsData = new ArrayList<>();

        // 第一个sheet的数据
        Map<String, Object> sheet1Data = new HashMap<>();
        String[] arrayTitles1= {"FILENAME", "CUSTLOTNO", "FTLOTNO","TESTCODE","RETESTCODE","STARTTIME","ENDTIME","PROGRAM","TESTER","HANDLER","PARTTYPE","LOADBOARDID","MCN","DEVICE"};
        List<String> titles1 =Arrays.asList(arrayTitles1);
        List<PageData> varList1 = new ArrayList<>();
        for (int i = 0; i < listEsumLotMir.size(); i++) {
        	PageData vpd = new PageData();
        	vpd.put("var1", listEsumLotMir.get(i).get("FILENAME"));
        	vpd.put("var2", listEsumLotMir.get(i).get("CUSTLOTNO"));
        	vpd.put("var3", listEsumLotMir.get(i).get("FTLOTNO"));
        	vpd.put("var4", listEsumLotMir.get(i).get("TESTCODE"));
        	vpd.put("var5", listEsumLotMir.get(i).get("RETESTCODE"));
        	vpd.put("var6", listEsumLotMir.get(i).get("STARTTIME"));
        	vpd.put("var7", listEsumLotMir.get(i).get("ENDTIME"));
        	vpd.put("var8", listEsumLotMir.get(i).get("PROGRAM"));
        	vpd.put("var9", listEsumLotMir.get(i).get("TESTER"));
        	vpd.put("var10", listEsumLotMir.get(i).get("HANDLER"));
        	vpd.put("var11", listEsumLotMir.get(i).get("PARTTYPE"));
        	vpd.put("var12", listEsumLotMir.get(i).get("LOADBOARDID"));
        	vpd.put("var13", listEsumLotMir.get(i).get("MCN"));
        	vpd.put("var14", listEsumLotMir.get(i).get("DEVICE"));
        	varList1.add(vpd);
        }
        sheet1Data.put("sheetName", "ESUM_LOT_MIR");
        sheet1Data.put("titles", titles1);
        sheet1Data.put("varList", varList1);
        sheetsData.add(sheet1Data);

        // 第二个sheet的数据
        Map<String, Object> sheet2Data = new HashMap<>();
        pd.put("LOTIDS", custLotNos);
		List<PageData> listHbin = esummaryService.listHbinForExcel(pd);
		
        String[] arrayTitles2= {"FILENAME", "LOTID", "TESTCODE", "RETESTCODE", "SITE", "HBIN", "HBIN_CNT", "FTLOTNO"};
        List<String> titles2 =Arrays.asList(arrayTitles2);
        List<PageData> varList2 = new ArrayList<>();
        for (int i = 0; i < listHbin.size(); i++) {
        	PageData vpd = new PageData();
        	vpd.put("var1", listHbin.get(i).get("FILENAME"));
        	vpd.put("var2", listHbin.get(i).get("LOTID"));
        	vpd.put("var3", listHbin.get(i).get("TESTCODE"));
        	vpd.put("var4", listHbin.get(i).get("RETESTCODE"));
        	vpd.put("var5", listHbin.get(i).get("SITE"));
        	vpd.put("var6", listHbin.get(i).get("HBIN"));
        	vpd.put("var7", listHbin.get(i).get("HBIN_CNT"));
        	vpd.put("var8", listHbin.get(i).get("FTLOTNO"));
        	varList2.add(vpd);
        }
        sheet2Data.put("sheetName", "ESUM_FILE_HBIN_YIELD");
        sheet2Data.put("titles", titles2);
        sheet2Data.put("varList", varList2);
        sheetsData.add(sheet2Data);

        // 第三个sheet的数据
        Map<String, Object> sheet3Data = new HashMap<>();
        pd.put("LOTIDS", custLotNos);
		List<PageData> listSbin = esummaryService.listSbinForExcel(pd);
		
        String[] arrayTitles3= {"FILENAME", "LOTID", "TESTCODE", "RETESTCODE", "SITE", "SBIN", "SBIN_CNT", "FTLOTNO"};
        List<String> titles3 =Arrays.asList(arrayTitles3);
        List<PageData> varList3 = new ArrayList<>();
        for (int i = 0; i < listSbin.size(); i++) {
        	PageData vpd = new PageData();
        	vpd.put("var1", listSbin.get(i).get("FILENAME"));
        	vpd.put("var2", listSbin.get(i).get("LOTID"));
        	vpd.put("var3", listSbin.get(i).get("TESTCODE"));
        	vpd.put("var4", listSbin.get(i).get("RETESTCODE"));
        	vpd.put("var5", listSbin.get(i).get("SITE"));
        	vpd.put("var6", listSbin.get(i).get("SBIN"));
        	vpd.put("var7", listSbin.get(i).get("SBIN_CNT"));
        	vpd.put("var8", listSbin.get(i).get("FTLOTNO"));
        	varList3.add(vpd);
        }
        sheet3Data.put("sheetName", "ESUM_FILE_SBIN_YIELD");
        sheet3Data.put("titles", titles3);
        sheet3Data.put("varList", varList3);
        sheetsData.add(sheet3Data);
        
        // 将sheetsData添加到模型中
        dataMap.put("sheetsData", sheetsData);
		
		ObjectExcelListView erv = new ObjectExcelListView(); // 执行excel操作
	
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

}
