package com.amkor.controller.fhoa.fhfile;

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
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.jdbc.support.nativejdbc.WebLogicNativeJdbcExtractor;
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
import com.amkor.service.fhoa.fhfile.viewhistory.impl.FhfileViewHistoryService;
import com.amkor.service.fhoa.otherwise.FhfileOhterwiseManager;
import com.amkor.service.fhoa.sendmail.SendmailManager;
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
import com.amkor.util.PageData;
import com.amkor.util.PathUtil;
import com.amkor.util.Tools;
import com.amkor.util.mail.picmail.Mail;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 说明：文件管理
 */
@Controller
@RequestMapping(value = "/fhfile")
public class FhfileController extends BaseController {

	String menuUrl = "fhfile/list.do"; // 菜单地址(权限用)

	@Resource(name = "oplogService")
	private OPlogManager oplogService;

	@Resource(name = "fhfileService")
	private FhfileManager fhfileService;
	@Resource(name = "fhfileViewHistoryService")
	private FhfileManager fhfileViewHistoryService;
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "sendmailService")
	private SendmailManager sendmailService;
	@Resource(name = "fhfileOhterwiseService")
	private FhfileOhterwiseManager fhfileOhterwiseService;

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

		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();
		String USERNAME = user.getUSERNAME();

		String item = Jurisdiction.getDEPARTMENT_ID();
		// if ("0".equals(item) || "无权".equals(item)) {
		// pd.put("item", ""); // 根据部门ID过滤
		// } else {
		// pd.put("item", item.replaceFirst("\\(", "\\('" +
		// Jurisdiction.getDEPARTMENT_ID() + "',"));
		// }
		pd.put("item", item);

		page.setPd(pd);

		// 选择数据字典
		PageData zidian = new PageData();
		if ("00101".equals(item)) {// test部门
			zidian.put("level", "60fa4043cbe642ad8098a35ab9be6321");
			zidian.put("customer", "e7721f55f53349459539b90e0971ba6e");
			zidian.put("defect", "df3978fa144e409fbd3f84bb8f105f72");
			zidian.put("process", "a3d460736d3a4cf99aa1352a4adff564");
		}

		List<PageData> varList = fhfileService.list(page); // 列出Fhfile列表
		List<PageData> nvarList = new ArrayList<PageData>();
		String fileStatus = null; // 存放文件状态
		int expired = 0;
		PageData npd = null;
		int length = varList.size();
		for (int i = 0; i < length; i++) {
			npd = new PageData();

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
			npd.put("FHFILE_ID", varList.get(i).getString("FHFILE_ID")); // 唯一ID
			npd.put("NAME", varList.get(i).getString("NAME")); // 文件名
			npd.put("FILEPATH", FILEPATH); // 文件名+扩展名
			npd.put("CTIME", varList.get(i).getString("CTIME")); // 上传时间
			npd.put("UPDATE_TIME", varList.get(i).getString("UPDATE_TIME")); // 修改时间
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
			Integer status = (Integer) varList.get(i).get("STATUS"); // 文件是否看完状态
			if (Objects.isNull(status)) {
				status = 0;
			}
			Integer stopUpdate = (Integer) varList.get(i).get("STOP_UPDATE"); // 是否end
			if (Objects.isNull(stopUpdate)) {
				stopUpdate = 1;
			}
			// 判断当前文件是否到期
			String dueDate = varList.get(i).getString("DUE_DATE");
			boolean expiredFile = true;
			if (StrUtil.isNotEmpty(dueDate)) {
				expiredFile = com.amkor.util.DateUtil.compareDate(dueDate, DateUtil.today());
			}

			if (expiredFile) {
				// 没到期
				expired = 0;
			} else {
				// 到期
				expired = 1;
			}
			// System.out.println(status+","+expired+","+stopUpdate);
			if (0 == status && 0 == expired && 0 == stopUpdate) {
				fileStatus = "0";
			} else if (0 == status && 0 == expired && 1 == stopUpdate) {
				fileStatus = "0";
			} else if (0 == status && 1 == expired && 1 == stopUpdate) {
				fileStatus = "0";
			} else if (1 == status && 0 == expired && 1 == stopUpdate) {
				fileStatus = "1";
			} else if (1 == status && 1 == expired && 1 == stopUpdate) {
				fileStatus = "1";
			} else if (0 == status && 1 == expired && 0 == stopUpdate) {
				fileStatus = "2";
			} else if (1 == status && 1 == expired && 0 == stopUpdate) {
				fileStatus = "2";
			} else if (1 == status && 0 == expired && 0 == stopUpdate) {
				fileStatus = "3";
			}

			npd.put("FILE_STATUS", fileStatus);
			nvarList.add(npd);

		}

		Object fileFlag = session.getAttribute(USERNAME + Const.SESSION_FILE);
		List<PageData> fileList = fhfileService.listAll(pd); // 列出Fhfile列表
		int fileSize = fileList.size();
		PageData filePd = new PageData();
		String fileOutDate = null;
		for (int i = 0; i < fileSize; i++) {
			// 当前文件为过期状态并且为文件上传者为当前User
			String dueDate = fileList.get(i).getString("DUE_DATE");
			boolean expiredFile = true;
			if (StrUtil.isNotEmpty(dueDate)) {
				expiredFile = com.amkor.util.DateUtil.compareDate(dueDate, DateUtil.today());
			}
			if ((false == expiredFile) && userId.equals(fileList.get(i).get("USER_ID"))) {
				fileOutDate = "1";
				session.removeAttribute(USERNAME + Const.SESSION_FILE);
				break;
			}
		}
		filePd.put("fileOutDate", fileOutDate);// 文件状态
		filePd.put("fileFlag", fileFlag); // 文件session开关

		mv.setViewName("fhoa/fhfile/fhfile_list");
		mv.addObject("varList", nvarList);
		mv.addObject("filePd", filePd); // 存放文件状态
		mv.addObject("pd", pd);
		mv.addObject("zidian", zidian);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	@RequestMapping(value = "/listExpiredFiles")
	public ModelAndView listExpiredFiles(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}

		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userId = user.getUSER_ID();

		page.setPd(pd);

		// 选择数据字典
		PageData zidian = new PageData();
		zidian.put("level", "60fa4043cbe642ad8098a35ab9be6321");
		zidian.put("customer", "e7721f55f53349459539b90e0971ba6e");
		zidian.put("defect", "df3978fa144e409fbd3f84bb8f1045f72");
		zidian.put("process", "a3d460736d3a4cf99aa1352a4adff564");

		List<PageData> varList = fhfileService.listAll(pd); // 列出Fhfile列表
		List<PageData> expiredVarList = new ArrayList<PageData>(); // 存放过期文件
		PageData npd = null;
		String fileStatus = null; // 存放文件状态
		int expired = 0;
		for (int i = 0; i < varList.size(); i++) {
			npd = new PageData();

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
			npd.put("FHFILE_ID", varList.get(i).getString("FHFILE_ID")); // 唯一ID
			npd.put("NAME", varList.get(i).getString("NAME")); // 文件名
			npd.put("FILEPATH", FILEPATH); // 文件名+扩展名
			npd.put("CTIME", varList.get(i).getString("CTIME")); // 上传时间
			npd.put("UPDATE_TIME", varList.get(i).getString("UPDATE_TIME")); // 修改时间
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
			Integer status = (Integer) varList.get(i).get("STATUS"); // 文件是否看完状态
			if (Objects.isNull(status)) {
				status = 0;
			}
			Integer stopUpdate = (Integer) varList.get(i).get("STOP_UPDATE"); // 是否end
			if (Objects.isNull(stopUpdate)) {
				stopUpdate = 1;
			}
			String dueDate = varList.get(i).getString("DUE_DATE");

			boolean expiredFile = true;
			if (StrUtil.isNotEmpty(dueDate)) {
				expiredFile = com.amkor.util.DateUtil.compareDate(dueDate, DateUtil.today());
			}

			if (expiredFile) {
				// 没到期
				expired = 0;
			} else {
				// 到期
				expired = 1;
			}

			if (0 == status && 0 == expired && 0 == stopUpdate) {
				fileStatus = "0";
			} else if (0 == status && 0 == expired && 1 == stopUpdate) {
				fileStatus = "0";
			} else if (0 == status && 1 == expired && 1 == stopUpdate) {
				fileStatus = "0";
			} else if (1 == status && 0 == expired && 1 == stopUpdate) {
				fileStatus = "1";
			} else if (1 == status && 1 == expired && 1 == stopUpdate) {
				fileStatus = "1";
			} else if (0 == status && 1 == expired && 0 == stopUpdate) {
				fileStatus = "2";
			} else if (1 == status && 1 == expired && 0 == stopUpdate) {
				fileStatus = "2";
			} else if (1 == status && 0 == expired && 0 == stopUpdate) {
				fileStatus = "3";
			}
			npd.put("FILE_STATUS", fileStatus);// 文件状态
			// 当前文件为过期状态并且为文件上传者为当前User
			if ((1 == expired) && userId.equals(varList.get(i).get("USER_ID"))) {
				expiredVarList.add(npd);
			}
		}
		mv.setViewName("fhoa/fhfile/fhfile_list");
		mv.addObject("varList", expiredVarList); // 存放过期文件信息
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
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);

		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("FHFILE_ID", this.get32UUID()); // 主键
		pd.put("CTIME", Tools.date2Str(new Date())); // 上传时间
		pd.put("USER_ID", user.getUSER_ID());
		pd.put("USERNAME", Jurisdiction.getU_name()); // 上传者
		pd.put("DEPARTMENT_ID", Jurisdiction.getDEPARTMENT_ID()); // 部门ID
		pd.put("FILESIZE",
				FileUtil.getFilesize(PathUtil.getClasspath() + Const.FILEPATHFILEOA + pd.getString("FILEPATH"))); // 文件大小

		pd.put("STATUS", "0");
		fhfileService.save(pd);

		PageData vh = new PageData();

		vh.put("FHFILE_ID", pd.getString("FHFILE_ID"));
		vh.put("USER_ID", user.getUSER_ID());
		vh.put("USER_NAME", user.getNAME());
		vh.put("FIRST_VIEW_TIME", DateUtil.now()); // 第一次查看时间
		vh.put("VIEW_TIME", DateUtil.now());
		fhfileViewHistoryService.save(vh); // 将当前上传人放到查看历史记录里

		// 发送邮件
		// SendMail(pd.getString("NAME"), pd.getString("USERNAME"),
		// pd.getString("CTIME"));
		
		sendmailService.sendmail(pd.getString("NAME"), pd.getString("USERNAME"), Jurisdiction.getDEPARTMENT_ID(),
				pd.getString("CTIME"));
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
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
		pd = fhfileService.findById(pd);
		fhfileService.delete(pd);
		fhfileViewHistoryService.delete(pd);

		// 删除web路径下的文件
		boolean webFalg = cn.hutool.core.io.FileUtil.del(new File(
				PathUtil.getClasspath() + Const.FILEPATHFILEOA + pd.getString("FILEPATH").substring(0, 20) + "pptx")); // 删除文件
		System.out.println(webFalg);
		oplogService.save(Jurisdiction.getUsername(), "del", "fhfile", "删除文件:" + pd.getString("NAME") + ".pptx");

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
		zidian.put("level", "60fa4043cbe642ad8098a35ab9be6321");
		zidian.put("customer", "e7721f55f53349459539b90e0971ba6e");
		zidian.put("defect", "df3978fa144e409fbd3f84bb8f105f72");
		zidian.put("process", "a3d460736d3a4cf99aa1352a4adff564");

		mv.setViewName("fhoa/fhfile/fhfile_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("zidian", zidian);
		return mv;
	}

	/**
	 * 去修改界面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		// 无法修改非本人的文件
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = fhfileService.findById(pd);
		if (!user.getUSER_ID().equals(pd.getString("USER_ID"))) {
			mv.setViewName("fhoa/fhfile/error");
			return mv;
		}

		PageData zidian = new PageData();
		zidian.put("level", "60fa4043cbe642ad8098a35ab9be6321");
		zidian.put("customer", "e7721f55f53349459539b90e0971ba6e");
		zidian.put("defect", "df3978fa144e409fbd3f84bb8f105f72");
		zidian.put("process", "a3d460736d3a4cf99aa1352a4adff564");

		mv.setViewName("fhoa/fhfile/fhfile_edit");
		mv.addObject("msg", "edit");
		mv.addObject("zidian", zidian);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 查看历史版本
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goOtherwise")
	public ModelAndView goViewOther() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = fhfileOhterwiseService.listById(pd);
		List<PageData> nvarList = new ArrayList<PageData>();
		for (int i = 0; i < varList.size(); i++) {
			PageData npd = new PageData();
			String FILEPATH = varList.get(i).getString("FILEPATH").trim();
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
			npd.put("NAME", varList.get(i).getString("NAME")); // 文件名
			npd.put("CTIME", varList.get(i).get("CTIME")); // 上传时间
			npd.put("FHFILE_ID", varList.get(i).getString("FHFILE_ID")); // 唯一ID
			npd.put("FILEPATH", FILEPATH); // 文件名+扩展名
			nvarList.add(npd);
		}

		mv.setViewName("fhoa/fhfile/fhfile_otherwise");
		mv.addObject("varList", nvarList);
		return mv;
	}

	/**
	 * 保存修改
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		System.out.println(pd.getString("FHFILE_ID"));
		// 保存老版本文件信息
		PageData oldFile = fhfileService.findById(pd);

		if (StrUtil.isNotEmpty(oldFile.getString("UPDATE_TIME"))) {
			oldFile.put("CTIME", oldFile.getString("UPDATE_TIME"));
		}
		fhfileOhterwiseService.save(oldFile);

		// 更新文件信息
		String nowTime = DateUtil.now();
		pd.put("UPDATE_TIME", nowTime);
		pd.put("FILESIZE",
				FileUtil.getFilesize(PathUtil.getClasspath() + Const.FILEPATHFILEOA + pd.getString("FILEPATH"))); // 文件大小

		fhfileService.edit(pd);

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 在线预览office
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/goViewOffice")
	public void goViewOffice(PrintWriter out) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = fhfileService.findById(pd);

		PageData vh = new PageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		vh.put("FHFILE_ID", pd.getString("FHFILE_ID"));
		vh.put("USER_ID", user.getUSER_ID()); // 用户ID
		vh.put("USER_NAME", user.getNAME()); // 用户名
		vh.put("VIEW_TIME", DateUtil.now()); // 查看时间

		PageData pdResult = fhfileViewHistoryService.findById(vh);
		if (pdResult != null) {
			fhfileViewHistoryService.edit(vh); // 修改查看时间
		} else {
			vh.put("FIRST_VIEW_TIME", DateUtil.now()); // 第一次查看时间
			fhfileViewHistoryService.save(vh);
		}

		out.write("success");
		out.close();
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
				fpd = fhfileService.findById(fpd);
				DelAllFile.delFolder(PathUtil.getClasspath() + Const.FILEPATHFILEOA + fpd.getString("FILEPATH")); // 删除物理文件
			}
			fhfileService.deleteAll(ArrayDATA_IDS); // 删除数据库记录
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
	@RequestMapping(value = "/onlineDownload")
	public void onlineDownload(HttpServletResponse response) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = fhfileService.findById(pd);
		// 下载完同步到查看历史记录里
		PageData vh = new PageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		vh.put("FHFILE_ID", pd.getString("FHFILE_ID"));
		vh.put("USER_ID", user.getUSER_ID()); // 用户ID
		vh.put("USER_NAME", user.getNAME()); // 用户名
		vh.put("VIEW_TIME", DateUtil.now()); // 查看时间
		vh.put("STATUS", 0); // 状态

		PageData pdResult = fhfileViewHistoryService.findById(vh);
		if (pdResult != null) {
			fhfileViewHistoryService.edit(vh); // 修改查看时间
		} else {
			vh.put("FIRST_VIEW_TIME", DateUtil.now()); // 第一次查看时间
			fhfileViewHistoryService.save(vh);
		}
		// 下载
		String fileName = pd.getString("FILEPATH");
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILEOA + fileName,
				pd.getString("NAME") + fileName.substring(19, fileName.length()));
	}

	@RequestMapping(value = "/download")
	public void download(HttpServletResponse response) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = fhfileService.findById(pd);
		// 下载
		String fileName = pd.getString("FILEPATH");
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILEOA + fileName,
				pd.getString("NAME") + fileName.substring(19, fileName.length()));
	}

	/**
	 * 自动提醒邮件
	 * 
	 * @throws Exception
	 */
	// @Scheduled(cron="0/10 * * * * ? ")
	@Scheduled(cron = "0 0 10 ? * *") // 每天的10点
	public void autoMail() throws Exception {
		// 普通类从spring容器中拿出service
		FhfileService fhfileService = (FhfileService) ApplicationContextUtil.getBean("fhfileService");
		FhfileViewHistoryService fhfileViewHistoryService = (FhfileViewHistoryService) ApplicationContextUtil
				.getBean("fhfileViewHistoryService");
		UserService userService = (UserService) ApplicationContextUtil.getBean("userService");

		PageData pd = new PageData();
		pd.put("STATUS", "0");
		// 获取未阅览的文件列表ID
		List<PageData> unCheckFileList = fhfileService.listByStatus(pd);
		for (int i = 0; i < unCheckFileList.size(); i++) {
			ArrayList<String> userIdList = new ArrayList<>();

			String fileID = unCheckFileList.get(i).getString("FHFILE_ID");
			String fileName = unCheckFileList.get(i).getString("NAME");
			String userName = unCheckFileList.get(i).getString("USERNAME");
			String fileTime = unCheckFileList.get(i).getString("CTIME");
			String fileDepartMentId = unCheckFileList.get(i).getString("DEPARTMENT_ID");

			PageData pda = new PageData();
			pda.put("FHFILE_ID", fileID);
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
			if (emailTo.length() > 0 && emailTo != null) {
				Mail.send(emailTo.toString(), "Test Internal OPS/OPL Sharing",
						buildContent("您有一份新的文档需要查看", fileName, userName, fileTime));
			} else {
				PageData vh = new PageData();
				vh.put("STATUS", 1);
				vh.put("FHFILE_ID", fileID);
				fhfileService.editStatus(vh);
				System.out.println(fileName + ",文件都已阅览");
			}
			
		}

		// 对过期文件进行邮件提醒
		PageData overDuePd = new PageData();
		overDuePd.put("STOP_UPDATE", "0");
		List<PageData> overDueList = fhfileService.listByStatus(overDuePd);
		int fileSize = overDueList.size();

		boolean expiredFile;
		PageData uPd = null;
		for (int i = 0; i < fileSize; i++) {
			String dueDate = overDueList.get(i).getString("DUE_DATE");
			String fileName = overDueList.get(i).getString("NAME");
			if (fileName.toLowerCase().endsWith(".ppt") || fileName.toLowerCase().endsWith(".pptx")) {

			} else {
				fileName = fileName + ".ppt";
			}
			String userName = overDueList.get(i).getString("USERNAME");
			String fileTime = overDueList.get(i).getString("CTIME");
			if (StrUtil.isNotEmpty(dueDate)) {
				expiredFile = com.amkor.util.DateUtil.compareDate(dueDate, DateUtil.today());
				// 到期
				if (false == expiredFile) {
					String userId = overDueList.get(i).getString("USER_ID");
					uPd = new PageData();
					uPd.put("USER_ID", userId);
					PageData userInfo = userService.findById(uPd);
					String email = userInfo.getString("EMAIL");

					// 发送邮件
					Mail.send(email, "Test Internal OPS/OPL Sharing",
							buildContent("您的文件已经过期,请尽快更新", fileName, userName, fileTime));
				}
			}

		}

	}

	/**
	 * 设置文件end 不需要再更新
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/end")
	public void end(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "停止更新Fhfile");

		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("STOP_UPDATE", '1');
		fhfileService.editStatus(pd);

		out.write("success");
		out.close();
	}

	/**
	 * 邮件方法
	 * 
	 * @param fileName
	 * @param userName
	 * @param fileTime
	 */
	public void SendMail(String subject, String fileName, String userName, String fileTime) {

		String title = "Test Internal OPS/OPL Sharing";// 所发送邮件的标题
		// String sendTo[] = { "SHCN-TEST-MFG-MANAGER" ,"SHCN-Test-ENG-MGR" };
		String item = Jurisdiction.getDEPARTMENT_ID();
		String nameList = null;
		if ("00101".equals(item)) {
			nameList = Tools.readTxtFile(Const.EMAILTO_TEST); // 收件人
		} else if ("00102".equals(item)) {
			nameList = Tools.readTxtFile(Const.EMAILTO_ASSY); // 收件人
		}

		if (fileName.toLowerCase().endsWith(".ppt") || fileName.toLowerCase().endsWith(".pptx")) {

		} else {
			fileName = fileName + ".ppt";
		}
		try {
			Mail.send(nameList, title, buildContent(subject, fileName, userName, fileTime));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 加载邮件模板
	 * 
	 * @param fileName
	 * @param userName
	 * @param fileTime
	 * @return
	 * @throws IOException
	 */
	private String buildContent(String subject, String fileName, String userName, String fileTime) throws IOException {

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
		String htmlText = MessageFormat.format(buffer.toString(), subject, fileName, userName, fileTime,
				DateUtil.today());

		return htmlText;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

	/**
	 * 预览ppt
	 * 
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/goViewPpt")
	@Deprecated
	public ModelAndView goViewPpt() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = fhfileService.findById(pd);

		PageData vh = new PageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		vh.put("FHFILE_ID", pd.getString("FHFILE_ID"));
		vh.put("USER_ID", user.getUSER_ID()); // 用户ID
		vh.put("USER_NAME", user.getNAME()); // 用户名
		vh.put("VIEW_TIME", DateUtil.now()); // 查看时间
		vh.put("STATUS", 0); // 状态

		PageData pdResult = fhfileViewHistoryService.findById(vh);
		if (pdResult != null) {
			fhfileViewHistoryService.edit(vh); // 修改查看时间
		} else {
			vh.put("FIRST_VIEW_TIME", DateUtil.now()); // 第一次查看时间
			fhfileViewHistoryService.save(vh);
		}
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
	@Deprecated
	public ModelAndView goViewPdf() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = fhfileService.findById(pd);

		PageData vh = new PageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		vh.put("FHFILE_ID", pd.getString("FHFILE_ID"));
		vh.put("USER_ID", user.getUSER_ID()); // 用户ID
		vh.put("USER_NAME", user.getNAME()); // 用户名
		vh.put("VIEW_TIME", DateUtil.now()); // 查看时间
		vh.put("STATUS", 0); // 状态

		PageData pdResult = fhfileViewHistoryService.findById(vh);
		if (pdResult != null) {
			fhfileViewHistoryService.edit(vh); // 修改查看时间
		} else {
			vh.put("FIRST_VIEW_TIME", DateUtil.now()); // 第一次查看时间
			fhfileViewHistoryService.save(vh);
		}

		mv.setViewName("fhoa/fhfile/fhfile_view_pdf");
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
	@Deprecated
	public ModelAndView goViewTxt() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String encoding = pd.getString("encoding");
		pd = fhfileService.findById(pd);
		String code = Tools.readTxtFileAll(Const.FILEPATHFILEOA + pd.getString("FILEPATH"), encoding);

		pd.put("code", code);
		mv.setViewName("fhoa/fhfile/fhfile_view_txt");
		mv.addObject("pd", pd);
		return mv;
	}

}
