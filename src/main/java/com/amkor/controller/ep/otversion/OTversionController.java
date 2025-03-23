package com.amkor.controller.ep.otversion;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.entity.system.Role;
import com.amkor.entity.system.User;
import com.amkor.service.ep.otversion.OTversionManager;
import com.amkor.service.system.fhlog.FHlogManager;
import com.amkor.service.system.user.UserManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Const;
import com.amkor.util.DateUtil;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.Tools;
import com.amkor.util.UuidUtil;
import com.amkor.util.mail.SendMailToAll;
import com.amkor.util.mail.Sendmail;

@Controller
@RequestMapping(value="otversion")
public class OTversionController extends BaseController{
	String menuUrl = "otversion/list.do";  // 菜单地址(权限用)
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="otversionService")
	private  OTversionManager otversionService;
	@Resource(name="userService")
	private UserManager userService;
	
	/**
	 * 显示数据 列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView listCP(Page page) throws Exception{	
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		
		ModelAndView mv = this.getModelAndView();		
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String LAST_LOGINTIME_END=pd.getString("LAST_LOGINTIME_END");//获取查询的结束时间
		if(null !=LAST_LOGINTIME_END && !"".equals(LAST_LOGINTIME_END)){
			pd.put("LAST_LOGINTIME_END", LAST_LOGINTIME_END);
		}
		page.setPd(pd);

		List<PageData> varList = otversionService.list(page); 
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		
		mv.setViewName("otversion/otversion_list");
		return mv;	
	}
	
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		//添加操作日志-删除记录
		logBefore(logger, Jurisdiction.getUsername()+"删除otversion");
		String cxlogid=UuidUtil.get32UUID();		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	
		
		PageData pd = new PageData();
		pd = this.getPageData();
		otversionService.delete(pd);

		out.write("success");
		out.close();
	}
	
	/**
	 * 去新增界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{	
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("otversion/otversion_edit");
		
		String roleParentID=Jurisdiction.getRoleParentID();
		pd.put("GroupID", roleParentID);
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**保存修改
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/save")
		public ModelAndView save()throws Exception{
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
			//添加操作日志-增加记录
			logBefore(logger, Jurisdiction.getUsername()+"add otversion ");
			String cxlogid=UuidUtil.get32UUID();		
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			otversionService.save(pd); 
			mv.addObject("msg","success");
			mv.setViewName("save_result");
			return mv;
		}	
		
		/**
		 * 去修改界面
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/goEdit")
		public ModelAndView goEdit()throws Exception{
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			pd = otversionService.findById(pd);	
			mv.setViewName("otversion/otversion_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			return mv;
		}	
		
		/**
		 * 修改
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/edit")
		public ModelAndView edit()throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"  update otversion");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
			//添加操作日志-修改记录
			String cxlogid=UuidUtil.get32UUID();		
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);	
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			otversionService.edit(pd);	//执行修改
			mv.addObject("msg","success");
			mv.setViewName("save_result");
			return mv;
		}
		
		/**判断数据是否重复
		 * @return
		 */
		@RequestMapping(value="/hasCP")
		@ResponseBody
		public Object hasU(){
			Map<String,String> map = new HashMap<String,String>();
			String errInfo = "success";
			PageData pd = new PageData();
			try{
				pd = this.getPageData();
				int count=otversionService.findBySimilar(pd);
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

