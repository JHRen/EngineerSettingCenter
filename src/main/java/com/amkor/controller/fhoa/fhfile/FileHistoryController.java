package com.amkor.controller.fhoa.fhfile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.amkor.controller.base.BaseController;
import com.amkor.service.fhoa.fhfile.FhfileManager;
import com.amkor.service.system.dictionaries.DictionariesManager;
import com.amkor.service.system.user.UserManager;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;

/**
 * 说明：文件管理
 */
@Controller
@RequestMapping(value = "/file")
public class FileHistoryController extends BaseController {

	String menuUrl = "fhfile/list.do"; // 菜单地址(权限用)
	@Resource(name = "fhfileService")
	private FhfileManager fhfileService;
	@Resource(name = "fhfileViewHistoryService")
	private FhfileManager fhfileViewHistoryService;
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name = "userService")
	private UserManager userService;


	/**
	 * 查看历史记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/viewHistory")
	public ModelAndView viewHistory() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 获取admin列表
		String DEPARTMENT_ID=Jurisdiction.getDEPARTMENT_ID();
		List<PageData> adminList = userService.listAdmin(DEPARTMENT_ID);

		List<PageData> varList = fhfileViewHistoryService.listById(pd);
		
		//查看过的amdinlist
		List<String> viewFileAdminList=new ArrayList<>();
		int adminViewCount=0;
		// 判断查看人是否是admin,如果是则计数
		for (int i = 0; i < varList.size(); i++) {
			String userId = varList.get(i).getString("USER_ID");
			PageData uPData = new PageData();
			uPData.put("USER_ID", userId);
			// 根据user id 查看是否amdin，如果是则观看数量加一
			PageData userInfo = userService.findById(uPData);
			Integer isAdmin = Integer.parseInt(userInfo.get("ADMIN").toString());
			if (isAdmin==1) {
				adminViewCount++;
				viewFileAdminList.add(varList.get(i).getString("USER_ID"));
			}
		}
		
		//所有没有查看文件的admin名单
		List<String> unViewFileAdminList=new ArrayList<>();
		List<PageData> unViewFileAdmin=new ArrayList<>();
		for (int i = 0; i < adminList.size(); i++) {
			unViewFileAdminList.add(adminList.get(i).getString("USER_ID"));
		}
		unViewFileAdminList.removeAll(viewFileAdminList);
		
	
		//根据USER_ID 获取 用户名
		for(int i=0;i<unViewFileAdminList.size();i++) {
			PageData upd = new PageData();
			upd.put("USER_ID", unViewFileAdminList.get(i).toString());
			PageData userInfo = userService.findById(upd);
			unViewFileAdmin.add(userInfo);
	 	}
		//unViewFileAdmin.forEach(System.out::println);
		
		mv.setViewName("fhoa/fhfile/fhfile_view_history");
		mv.addObject("varList", varList);
		mv.addObject("adminList", unViewFileAdmin);
		mv.addObject("admintNum", adminList.size());
		mv.addObject("adminViewCount", adminViewCount);
		return mv;
	}
	


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
