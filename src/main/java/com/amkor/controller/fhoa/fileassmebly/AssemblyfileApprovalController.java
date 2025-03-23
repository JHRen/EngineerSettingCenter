package com.amkor.controller.fhoa.fileassmebly;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.service.fhoa.fhfile.FhfileManager;
import com.amkor.service.system.dictionaries.DictionariesManager;
import com.amkor.service.system.fhlog.OPlogManager;
import com.amkor.service.system.user.UserManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Const;
import com.amkor.util.DelAllFile;
import com.amkor.util.FileDownload;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.PathUtil;
import com.amkor.util.Tools;
import com.amkor.util.mail.picmail.Mail;

import cn.hutool.core.date.DateUtil;

/**
 * 说明：文件管理
 */
@Controller
@RequestMapping(value = "/assemblyfileapproval")
public class AssemblyfileApprovalController extends BaseController {

	String menuUrl = "assemblyfileapproval/list.do"; // 菜单地址(权限用)

	@Resource(name = "oplogService")
	private OPlogManager oplogService;

	@Resource(name = "assemblyfileService")
	private FhfileManager assemblyfileService;
	
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name = "userService")
	private UserManager userService;

	private static String fileUrl = "\\\\C3WJAVAP01\\EngCenterFile\\";

	
	/**
	 * 审批界面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView approvalList(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Fhfile");
		 if(!Jurisdiction.buttonJurisdiction(menuUrl, "apr")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		
		String item = Jurisdiction.getDEPARTMENT_ID();
		pd.put("item", item);
		pd.put("STATUS", 1); //显示没有审批的
		page.setPd(pd);
		
		List<PageData> nvarList = assemblyfileService.list(page);
		mv.setViewName("fhoa/assemblyfile/fhfile_list_r");
		
		mv.addObject("varList_r", nvarList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/agree")
	public void agree(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername() + "文件审批通过");
		PageData pd = new PageData();
		pd = this.getPageData();
		//修改状态为审批通过
		pd.put("STATUS", 2);
		pd.put("APPROVE_TIME", DateUtil.now());
		pd.put("APPROVE_NAME", Jurisdiction.getUsername());
		assemblyfileService.edit(pd);
		
		PageData pData = assemblyfileService.findById(pd);
		// 发送邮件
		String emails = pData.getString("EMAILS");
		SendMail(pData.getString("NAME"), pData.getString("USERNAME"), pData.getString("CTIME"),emails);
		out.write("success");
		out.close();
	}
	
	@RequestMapping(value = "/deny")
	public void deny(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername() + "文件审批拒绝");
		PageData pd = new PageData();
		pd = this.getPageData();
		//修改状态为审批拒绝
		pd.put("STATUS", 3);
		pd.put("APPROVE_TIME", DateUtil.now());
		pd.put("APPROVE_NAME", Jurisdiction.getUsername());
		assemblyfileService.edit(pd);
		out.write("success");
		out.close();
	}

	private void SendMail(String fileName, String userName, String fileTime,String emails) {

		String title = "Assembly File Sharing";// 所发送邮件的标题
		String item = Jurisdiction.getDEPARTMENT_ID();
		String	nameList =null;
		if (StringUtils.isNotEmpty(emails)) {
			nameList = emails;
		}else {
				nameList = Tools.readTxtFile(Const.EMAILTO_ASSY); // 收件人
		}

		if (fileName.toLowerCase().endsWith(".ppt") || fileName.toLowerCase().endsWith(".pptx")) {

		} else {
			fileName = fileName + ".ppt";
		}
		try {
			Mail.send(nameList, title, buildContent(fileName, userName, fileTime));
			// SendMailToAll.sendmail(nameList, title, buildContent(fileName, userName));
			// MailUtil.send(nameList, title, buildContent(fileName, userName), true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String buildContent(String fileName, String userName, String fileTime) throws IOException {

		// 加载邮件html模板
		String filehtmlName = "/templates.html";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filehtmlName);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = fileReader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
		} finally {
			inputStream.close();
			fileReader.close();
		}

		// 填充html模板中的五个参数
		String htmlText = MessageFormat.format(buffer.toString(), fileName, userName, fileTime, DateUtil.today());

		return htmlText;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Fhfile");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = assemblyfileService.findById(pd);
		assemblyfileService.delete(pd);

		// 删除web路径下的文件
		boolean webFalg = cn.hutool.core.io.FileUtil.del(new File(
				PathUtil.getClasspath() + Const.FILEPATHFILEOA + pd.getString("FILEPATH").substring(0, 20) + "pdf")); // 删除文件
		System.out.println(webFalg);
		// 删除服务器上的文件
		boolean pptFlag = cn.hutool.core.io.FileUtil.del(new File(fileUrl + pd.getString("FILEPATH"))); // 删除文件
		System.out.println(pptFlag);
		// 删除pdf文件
		boolean pdfFlag = cn.hutool.core.io.FileUtil.del(
				new File("\\\\C3WJAVAP01\\uploadFiles\\atctest\\" + pd.getString("FILEPATH").substring(0, 20) + "pdf"));
		System.out.println(pdfFlag);
		oplogService.save(Jurisdiction.getUsername(), "del", "assemblyfile", "删除文件:" + pd.getString("NAME") + ".pptx");

		out.write("success");
		out.close();
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 选择数据字典
		PageData zidian = new PageData();
		String item = Jurisdiction.getDEPARTMENT_ID();
		
		//assembly 部门数据字典
		zidian.put("level", "e9ae79fb51ad4db489982001ad79addf");
		zidian.put("customer", "0b430073e27d4cd594e49853bd801772");
		zidian.put("defect", "0f15ab60cbf54854ab9776286433cd69");
		zidian.put("process", "a0b7bcb5cf8e4d7c8b5bcd28f31a8ca6");
		
		mv.setViewName("fhoa/assemblyfile/fhfile_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("zidian", zidian);
		return mv;
	}

	

	/**
	 * 去预览txt,java,php,等文本文件页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goViewTxt")
	public ModelAndView goViewTxt() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String encoding = pd.getString("encoding");
		pd = assemblyfileService.findById(pd);
		// String code =
		// Tools.readTxtFileAll(Const.FILEPATHFILEOA+pd.getString("FILEPATH"),encoding);
		String code = Tools.readTxtFileAll(fileUrl + pd.getString("FILEPATH"), encoding);

		pd.put("code", code);
		mv.setViewName("fhoa/assemblyfile/fhfile_view_txt");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Fhfile");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			PageData fpd = new PageData();
			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				fpd.put("FHFILE_ID", ArrayDATA_IDS[i]);
				fpd = assemblyfileService.findById(fpd);
				DelAllFile.delFolder(PathUtil.getClasspath() + Const.FILEPATHFILEOA + fpd.getString("FILEPATH")); // 删除物理文件
			}
			assemblyfileService.deleteAll(ArrayDATA_IDS); // 删除数据库记录
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 下载
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/download")
	public void downExcel(HttpServletResponse response) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = assemblyfileService.findById(pd);
		String fileName = pd.getString("FILEPATH");

		// FileDownload.fileDownload(response, PathUtil.getClasspath() +
		// Const.FILEPATHFILEOA + fileName, pd.getString("NAME")+fileName.substring(19,
		// fileName.length()));
		FileDownload.fileDownload(response, fileUrl + fileName,
				pd.getString("NAME") + fileName.substring(19, fileName.length()));
	}

	

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
