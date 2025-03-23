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
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.entity.system.User;
import com.amkor.service.fhoa.fhfile.FhfileManager;
import com.amkor.service.fhoa.fhfile.impl.FhfileService;
import com.amkor.service.system.dictionaries.DictionariesManager;
import com.amkor.service.system.fhlog.OPlogManager;
import com.amkor.service.system.user.UserManager;
import com.amkor.service.system.user.impl.UserService;
import com.amkor.util.AppUtil;
import com.amkor.util.ApplicationContextUtil;
import com.amkor.util.Const;
import com.amkor.util.DelAllFile;
import com.amkor.util.FileDownload;
import com.amkor.util.FileUtil;
import com.amkor.util.Jurisdiction;
import com.amkor.util.OffictoPDF;
import com.amkor.util.PageData;
import com.amkor.util.PathUtil;
import com.amkor.util.Tools;
import com.amkor.util.mail.picmail.Mail;

import cn.hutool.core.date.DateUtil;

/**
 * 说明：文件管理
 */
@Controller
@RequestMapping(value = "/assemblyfile")
public class AssemblyfileController extends BaseController {

	String menuUrl = "assemblyfile/list.do"; // 菜单地址(权限用)

	@Resource(name = "oplogService")
	private OPlogManager oplogService;

	@Resource(name = "assemblyfileService")
	private FhfileManager assemblyfileService;
	
	@Resource(name = "fhfileViewHistoryService")
	private FhfileManager fhfileViewHistoryService;
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name = "userService")
	private UserManager userService;

	private static String fileUrl = "\\\\C3WJAVAP01\\EngCenterFile\\";

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Fhfile");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		
		String type = pd.getString("type");
		type = Tools.isEmpty(type)?"0":type;
		
		String item = Jurisdiction.getDEPARTMENT_ID();
		// if ("0".equals(item) || "无权".equals(item)) {
		// pd.put("item", ""); // 根据部门ID过滤
		// } else {
		// pd.put("item", item.replaceFirst("\\(", "\\('" +
		// Jurisdiction.getDEPARTMENT_ID() + "',"));
		// }
		pd.put("item", item);
		pd.put("STATUS", 2); //显示已经审批的
		page.setPd(pd);

		// 选择数据字典
		PageData zidian = new PageData();
		zidian.put("level", "e9ae79fb51ad4db489982001ad79addf");
		zidian.put("customer", "0b430073e27d4cd594e49853bd801772");
		zidian.put("defect", "0f15ab60cbf54854ab9776286433cd69");
		zidian.put("process", "a0b7bcb5cf8e4d7c8b5bcd28f31a8ca6");

		List<PageData> varList = assemblyfileService.list(page); // 列出Fhfile列表
		List<PageData> nvarList = new ArrayList<PageData>();
		for (int i = 0; i < varList.size(); i++) {
			PageData npd = new PageData();

			String FILEPATH = varList.get(i).getString("FILEPATH");
			String Extension_name = FILEPATH.substring(FILEPATH.length() - 3, FILEPATH.length()).toLowerCase();// 文件拓展名
			String fileType = "file";
			int zindex1 = "java,php,jsp,html,css,txt,asp".indexOf(Extension_name);
			if (zindex1 != -1) {
				fileType = "wenben"; // 文本类型
			}
			int zindex2 = "jpg,gif,bmp,png".indexOf(Extension_name);
			if (zindex2 != -1) {
				fileType = "tupian"; // 图片文件类型
			}
			int zindex3 = "rar,zip,rar5".indexOf(Extension_name);
			if (zindex3 != -1) {
				fileType = "yasuo"; // 压缩文件类型
			}
			int zindex4 = "doc,docx".indexOf(Extension_name);
			if (zindex4 != -1) {
				fileType = "doc"; // doc文件类型
			}
			int zindex5 = "xls,xlsx".indexOf(Extension_name);
			if (zindex5 != -1) {
				fileType = "xls"; // xls文件类型
			}
			int zindex6 = "ppt,pptx".indexOf(Extension_name);
			if (zindex6 != -1) {
				fileType = "ppt"; // ppt文件类型
			}
			int zindex7 = "pdf".indexOf(Extension_name);
			if (zindex7 != -1) {
				fileType = "pdf"; // ppt文件类型
			}
			int zindex8 = "fly,f4v,mp4,m3u8,webm,ogg,avi".indexOf(Extension_name);
			if (zindex8 != -1) {
				fileType = "video"; // 视频文件类型
			}
			npd.put("fileType", fileType); // 用于文件图标
			npd.put("FILE_ID", varList.get(i).getString("FILE_ID")); // 唯一ID
			npd.put("NAME", varList.get(i).getString("NAME")); // 文件名
			npd.put("FILEPATH", FILEPATH); // 文件名+扩展名
			npd.put("CTIME", varList.get(i).getString("CTIME")); // 上传时间
			npd.put("USERNAME", varList.get(i).getString("USERNAME")); // 用户名

			PageData bmpd = new PageData();
			bmpd.put("BIANMA", varList.get(i).getString("CUSTOMER"));
			PageData custPd = dictionariesService.findByBianma(bmpd);
			if (custPd != null) {
				npd.put("CUSTOMER", custPd.get("NAME")); // 客户
			}

			bmpd.put("BIANMA", varList.get(i).getString("LEVEL"));
			PageData levPd = dictionariesService.findByBianma(bmpd);
			if (levPd != null) {
				npd.put("LEVEL", levPd.get("NAME")); // 级别
			}

			bmpd.put("BIANMA", varList.get(i).getString("DEFECT_MODE"));
			PageData dModPd = dictionariesService.findByBianma(bmpd);
			if (dModPd != null) {
				npd.put("DEFECT_MODE", dModPd.get("NAME")); // 级别
			}

			bmpd.put("BIANMA", varList.get(i).getString("PROCESS"));
			PageData proPd = dictionariesService.findByBianma(bmpd);
			if (proPd != null) {
				npd.put("PROCESS", proPd.get("NAME")); // 级别
			}

			npd.put("DEPARTMENT_ID", varList.get(i).getString("DEPARTMENT_ID"));// 机构级别
			npd.put("FILESIZE", varList.get(i).getString("FILESIZE")); // 文件大小
			npd.put("BZ", varList.get(i).getString("BZ")); // 备注
			npd.put("STATUS", varList.get(i).get("STATUS"));
			nvarList.add(npd);
		}
		mv.setViewName("fhoa/assemblyfile/fhfile_list");
		
		mv.addObject("varList", nvarList);
		mv.addObject("pd", pd);
		mv.addObject("zidian", zidian);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(HttpServletResponse response) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Fhfile");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("FILE_ID", this.get32UUID()); // 主键
		pd.put("CTIME", Tools.date2Str(new Date())); // 上传时间
		pd.put("USERNAME", Jurisdiction.getU_name()); // 上传者
		pd.put("DEPARTMENT_ID", Jurisdiction.getDEPARTMENT_ID()); // 部门ID
		// pd.put("FILESIZE", FileUtil.getFilesize(PathUtil.getClasspath() +
		// Const.FILEPATHFILEOA + pd.getString("FILEPATH"))); //文件大小
		pd.put("FILESIZE", FileUtil.getFilesize(fileUrl + pd.getString("FILEPATH"))); // 文件大小
		pd.put("STATUS", 1); //文件审批状态 1为未审批 不显示
		String emails = pd.getString("EMAILS");
		assemblyfileService.save(pd);

		// 发送邮件
		SendMail(pd.getString("NAME"), pd.getString("USERNAME"), pd.getString("CTIME"),emails);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
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
		fhfileViewHistoryService.delete(pd);

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
	/*	ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("fhoa/assemblyfile/send_email");
		mv.addObject("pd", pd);
		return mv;*/
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 选择数据字典
		PageData zidian = new PageData();
		
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
	 * 去看ppt
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goViewPpt")
	public ModelAndView goViewPpt() throws Exception {
		ModelAndView mv=this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = assemblyfileService.findById(pd);
		
		mv.setViewName("fhoa/assemblyfile/fhfile_view_ppt");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去预览pdf文件页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goViewPdf")
	public ModelAndView goViewPdf() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = assemblyfileService.findById(pd);
		// 判断是否生成了PDF，若没有先生成pdf
		File inputFile = new File(fileUrl + File.separator + pd.getString("FILEPATH"));

		String ouputFileName = PathUtil.getClasspath() + Const.FILEPATHFILEOA
				+ pd.getString("FILEPATH").substring(0, 20) + "pdf";
		File outputFile = new File(ouputFileName);

		if (pd.getString("FILEPATH").contains(".ppt")) {
			if (!outputFile.exists()) {
				OffictoPDF.ppt2PDF(inputFile, outputFile);
			}
			pd.put("FILEPATH", pd.getString("FILEPATH").substring(0, 20) + "pdf");
		}

		PageData vh = new PageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		vh.put("FILE_ID", pd.getString("FILE_ID"));
		vh.put("FHFILE_ID", pd.getString("FILE_ID"));
		vh.put("USER_ID", user.getUSER_ID()); // 用户ID
		vh.put("USER_NAME", user.getNAME()); // 用户名
		vh.put("VIEW_TIME", DateUtil.now()); // 查看时间
		vh.put("STATUS", 0); // 状态

		PageData pdResult = fhfileViewHistoryService.findById(vh);
		if (pdResult != null) {
			fhfileViewHistoryService.edit(vh); // 修改查看时间

			// 如果所有人都已经看过，则将文件复制到atctest预览路径
			List<PageData> varList = fhfileViewHistoryService.listById(vh);// 获取 user_id
			int adminViewHistory = 0;
			for (int i = 0; i < varList.size(); i++) {
				String userId = varList.get(i).getString("USER_ID");
				PageData uPData = new PageData();
				uPData.put("USER_ID", userId);
				// 根据user id 查看是否amdin，如果是则观看数量加一
				PageData userInfo = userService.findById(uPData);
				Integer isAdmin = Integer.parseInt(userInfo.get("ADMIN").toString());
				if (isAdmin != null && isAdmin == 1) {
					adminViewHistory++;
				}
			}
			String DEPARTMENT_ID = Jurisdiction.getDEPARTMENT_ID();
			List<PageData> adminList = userService.listAdmin(DEPARTMENT_ID); // 查看admin 用户人数
			if (adminViewHistory == adminList.size()) {
				vh.put("STATUS", 1);
				String pptName = pd.getString("FILEPATH").substring(0, 20) + "pdf";
				File pptPath = new File("\\\\C3WJAVAP01\\uploadFiles\\atctest" + File.separator + pptName);
				if (!pptPath.exists()) {
					cn.hutool.core.io.FileUtil.copy(outputFile, new File("\\\\C3WJAVAP01\\uploadFiles\\atctest"), true);
				}
				assemblyfileService.edit(vh); // 修改状态
			}
		} else {
			vh.put("FIRST_VIEW_TIME", DateUtil.now()); // 第一次查看时间
			fhfileViewHistoryService.save(vh);
		}

		mv.setViewName("fhoa/assemblyfile/fhfile_view_pdf");
		mv.addObject("pd", pd);
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
				fpd.put("FILE_ID", ArrayDATA_IDS[i]);
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

	/**
	 * 自动提醒邮件
	 * 
	 * @throws Exception
	 */
//	 @Scheduled(cron="0/10 * * * * ? ")
	public void autoMail() throws Exception {
		// 普通类从spring容器中拿出service
		FhfileService fhfileService = (FhfileService) ApplicationContextUtil.getBean("fhfileService");

		UserService userService = (UserService) ApplicationContextUtil.getBean("userService");

		PageData pd = new PageData();
		pd.put("STATUS", 0);
		// 获取未阅览的文件列表ID
		List<PageData> unCheckFileList = fhfileService.listByStatus(pd);
		for (int i = 0; i < unCheckFileList.size(); i++) {
			ArrayList<String> userIdList = new ArrayList<>();

			String fileID = unCheckFileList.get(i).getString("FILE_ID");
			String fileName = unCheckFileList.get(i).getString("NAME");
			String userName = unCheckFileList.get(i).getString("USERNAME");
			String fileTime = unCheckFileList.get(i).getString("CTIME");
			String fileDepartMentId = unCheckFileList.get(i).getString("DEPARTMENT_ID");

			PageData pda = new PageData();
			pda.put("FILE_ID", fileID);
			// 通过文件列表ID 获取 已经阅览的用户ID
			List<PageData> userList = fhfileViewHistoryService.listById(pda);
			for (int j = 0; j < userList.size(); j++) {

				String userId = userList.get(j).getString("USER_ID");
				userIdList.add(userId); // 保存当前文件阅览的用户ID
			}

			// 查询当前文件未阅览的用户

			// 获取admin列表
			List<PageData> adminList = userService.listAdmin(fileDepartMentId);

			ArrayList<String> adminUserIdList = new ArrayList<>();
			for (int k = 0; k < adminList.size(); k++) {
				adminUserIdList.add(adminList.get(k).getString("USER_ID"));
			}

			// 去除掉已经阅览的用户
			adminUserIdList.removeAll(userIdList);

			// 根据用户ID获取对应的邮箱
			// ArrayList<String> emailTo = new ArrayList<>();
			StringBuffer emailTo = new StringBuffer();
			for (int m = 0; m < adminUserIdList.size(); m++) {
				PageData pdb = new PageData();
				pdb.put("USER_ID", adminUserIdList.get(m).toString());
				PageData userInfo = userService.findById(pdb);
				// emailTo.add(userInfo.getString("EMAIL"));
				emailTo.append(userInfo.getString("EMAIL") + ",");
			}
			// String mailContent="您有一份新的 issue case 还未查看.<br><br>文件名 : "+fileName+".ppt
			// ,<br> 上传者: "+userName;

			if (fileName.toLowerCase().endsWith(".ppt") || fileName.toLowerCase().endsWith(".pptx")) {

			} else {
				fileName = fileName + ".ppt";
			}
			// System.out.println(emailTo.toString());
			 if (emailTo.length()>0&&emailTo!=null) {
				 Mail.send(emailTo.toString(), "Test Internal OPS/OPL Sharing", buildContent(fileName, userName, fileTime));
			}else {
				System.out.println(fileName+",文件都已阅览");
			}
			// SendMailToAll.sendmail(emailTo.toString(), "Test Internal OPS/OPL Sharing",
			// buildContent(fileName, userName));
		}

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
