package com.amkor.controller.sorter.sortprogram;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.entity.system.Role;
import com.amkor.service.sorter.sortprogram.SortProgramManager;
import com.amkor.service.system.fhlog.FHlogManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Const;
import com.amkor.util.FileDownload;
import com.amkor.util.FileUpload;
import com.amkor.util.GetPinyin;
import com.amkor.util.Jurisdiction;
import com.amkor.util.ObjectExcelRead;
import com.amkor.util.PageData;
import com.amkor.util.PathUtil;
import com.amkor.util.Tools;

@Controller
@RequestMapping(value = "script")
public class SortProgramController extends BaseController {
	String menuUrl = "script/sortprogram.do"; // 菜单地址(权限用)
	@Resource(name = "fhlogService")
	private FHlogManager FHLOG;

	@Resource(name = "sortProgramService")
	private SortProgramManager sortProgramService;

	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sortprogram/list")
	public ModelAndView list(Page page) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		// 校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}

		page.setPd(pd);

		// 数据视图
		List<PageData> dataList = sortProgramService.list(page);

		mv.addObject("varList", dataList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限

		mv.setViewName("scriptdb/sorter/sort_list");

		return mv;
	}

	@RequestMapping(value = "/sortprogram/delete")
	public void delete(PrintWriter out) throws Exception {

		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();

		pd = sortProgramService.findById(pd);
		sortProgramService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 去新增界面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sortprogram/add")
	public ModelAndView goAdd() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("scriptdb/sorter/sort_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 保存修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/sortprogram/save")
	public ModelAndView save() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		sortProgramService.save(pd);

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 去修改界面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sortprogram/goEdit")
	public ModelAndView goEdit() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = sortProgramService.findById(pd);
		mv.setViewName("scriptdb/sorter/sort_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 修改
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sortprogram/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "  update sorter");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		sortProgramService.edit(pd); // 执行修改

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 打开上传EXCEL页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sortprogram/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("scriptdb/sorter/uploadexcel");
		return mv;
	}

	/**
	 * 下载模版
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sortprogram/downExcel")
	public void downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "SORTprogram对照表.xlsx", "SORTprogram对照表.xlsx");
	}

	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sortprogram/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file)
			throws Exception {
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE; // 文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "userexcel"); // 执行上传
			List<PageData> listPd = (List) ObjectExcelRead.readExcelXlsx(filePath, fileName, 1, 0, 0); // 执行读EXCEL操作,读出的数据导入List
																									// 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/* 存入数据库操作====================================== */
			for (int i = 0; i < listPd.size(); i++) {
				pd.put("GROUP", listPd.get(i).getString("var0"));
				pd.put("DEVICE", listPd.get(i).getString("var1"));
				pd.put("TARGET_DEVICE", listPd.get(i).getString("var2"));
				pd.put("TARGET_DEVICE_18", listPd.get(i).getString("var3"));
				pd.put("AMKORWWOPERNAME", listPd.get(i).getString("var4"));
				pd.put("PROCESS_CODE", listPd.get(i).getString("var5"));
				pd.put("PKG_SORT_HANDLER", listPd.get(i).getString("var6"));
				int count = sortProgramService.findBySimilar(pd);
				if (count > 0) {
					continue;
				}
				sortProgramService.save(pd);
			}
			/* 存入数据库操作====================================== */
			mv.addObject("msg", "success");
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 判断数据是否重复
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sortprogram/hasData")
	@ResponseBody
	public Object hasU() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			int count = sortProgramService.findBySimilar(pd);
			if (count > 0) {
				errInfo = "error";
			}
		} catch (Exception e) {
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
}
