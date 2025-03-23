package com.amkor.controller.ep.scopes;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.entity.system.Dictionaries;
import com.amkor.entity.system.User;
import com.amkor.service.ep.scopes.ScopesVersionManager;
import com.amkor.service.fhoa.sendmail.SendmailManager;
import com.amkor.service.system.fhlog.FHlogManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Const;
import com.amkor.util.DateUtil;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.Tools;
import com.sun.star.util.Date;

@Controller
@RequestMapping(value = "scopes")
public class ScopesController extends BaseController {
	String menuUrl = "scopes/list.do"; // 菜单地址(权限用)
	@Resource(name = "fhlogService")
	private FHlogManager FHLOG;
	@Resource(name = "scopsVersionService")
	private ScopesVersionManager scopesVersionService;
	@Resource(name="sendmailService")
	private SendmailManager sendmailService;
	/**
	 * 显示数据 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView listCP(Page page) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}

		page.setPd(pd);

		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		List<PageData> varList = scopesVersionService.list(page);

		mv.addObject("varList", varList);
		mv.setViewName("scopes/scopesversion_list");
		return mv;
	}

	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {

		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {return;} // 校验权限
		
		// 添加操作日志-删除记录
		logBefore(logger, Jurisdiction.getUsername() + "删除socpes version");
		Session session = Jurisdiction.getSession();

		PageData pd = new PageData();
		pd = this.getPageData();
		pd = scopesVersionService.findById(pd);
		scopesVersionService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 去新增界面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {return null;} // 校验权限

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("scopes/scopesversion_edit");
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
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增scopes version");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String Version=pd.getString("Version");
		String[] versionSplit = Version.split(",");
		if (!Tools.isEmpty(Version)) {
			pd.put("VersionNo",versionSplit[0]);
			pd.put("VersionName", versionSplit[1]);
		}
		pd.put("UpdateTime", DateUtil.getTime());
		String mails=pd.getString("Mails");
		pd.put("MailTo", mails);
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String USERNAME = user.getUSERNAME();
		pd.put("UserName", USERNAME);
		
		if (!Tools.isEmpty(mails)) {
			//发送邮件
			String subject="Scops Setup Request";
			String  content="";
			content +="新增内容："+"<br>";
			content +="Hostname: "+pd.getString("Hostname")+"<br>";
			content +="CustomerCode: "+pd.getString("CustomerCode")+"<br>";
			content +="Factory : "+pd.getString("Factory")+"<br>";
			content +="Process : "+pd.getString("Process")+"<br>";
			content +="Platform : "+pd.getString("Platform")+"<br>";
			content +="Devicename : "+pd.getString("Devicename")+"<br>";
			content +="VersionName : "+pd.getString("VersionName")+"<br>";
			content +="UserName : "+ USERNAME+"<br>";
			
			sendmailService.sendToMail(mails, subject, content);			
		}
		
		scopesVersionService.save(pd); 
		mv.addObject("msg","success");
		mv.setViewName("save_result");
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
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = scopesVersionService.findById(pd);	
		pd.put("versionAll", pd.getString("VersionNo")+","+pd.getString("VersionName"));
		mv.setViewName("scopes/scopesversion_edit");
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
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"  update scope version");
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String Version=pd.getString("Version");
		String[] versionSplit = Version.split(",");
		if (!Tools.isEmpty(Version)) {
			pd.put("VersionNo",versionSplit[0]);
			pd.put("VersionName", versionSplit[1]);
		}
		pd.put("UpdateTime", DateUtil.getTime());
		scopesVersionService.edit(pd);	//执行修改

		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="/getVersion")
	@ResponseBody
	public Object getVersion(){
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			List<PageData> varList = scopesVersionService.getVersion(pd); //用传过来的ID获取此ID下的子列表数据
			List<PageData> pdList = new ArrayList<PageData>();
			for(PageData pda :varList){
				PageData pdf = new PageData();
				pdf.put("VersionNo", pda.getString("VersionNo"));
				pdf.put("VersionName", pda.getString("VersionName"));
				pdf.put("verNoAndName", pda.getString("VersionNo")+","+pda.getString("VersionName"));
				pdList.add(pdf);
			}
			map.put("list", pdList);	
		} catch(Exception e){
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	/**判断数据是否重复
	 * @return
	 */
	@RequestMapping(value="/hasData")
	@ResponseBody
	public Object hasData(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd.put("UpdateTime", DateUtil.getTime());
		
		try{
			pd = this.getPageData();
			int count=scopesVersionService.findBySimilar(pd);
			if( count>0){
				errInfo = "error";
			}
		} catch(Exception e){
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
}
