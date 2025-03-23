package com.amkor.controller.ep.wpd;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.entity.system.Role;
import com.amkor.entity.system.User;
import com.amkor.service.ep.approval.ApprovalManager;
import com.amkor.service.ep.qts.impl.QtscpService;
import com.amkor.service.system.fhlog.FHlogManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Const;
import com.amkor.util.DateUtil;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.Tools;
import com.amkor.util.UuidUtil;
//import com.fh.util.datasource.DataSourceContextHolder;
import com.amkor.util.mail.Sendmail;

@Controller
@RequestMapping(value="wpd")
public class GFwpdController extends BaseController{
	String menuUrl = "wpd/gf/list.do";  // 菜单地址(权限用)
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="qtscpService")
	private  QtscpService qtscpService;
	@Resource(name="ApprovalService")
	private  ApprovalManager approvalService;
	
	/**
	 * 显示WPD GF 列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gf/list")
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

		List<PageData> varList = qtscpService.list(page); 
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		
		mv.setViewName("lodfile/qts_cp/qtscp_list");
		return mv;	
	}
	
	@RequestMapping(value="/cp/delete")
	public void delete(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除QTSCP");
		String cxlogid=UuidUtil.get32UUID();		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	

		PageData pd = new PageData();
		pd = this.getPageData();
		pd = qtscpService.findById(pd);	
		qtscpService.delete(pd);
		//发送邮件
		Sendmail.sendToManager(Jurisdiction.getUsername(), "删除", pd, "lodfile_qtscp_A");

		out.write("success");
		out.close();
	}
	
	/**
	 * 去新增界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cp/add")
	public ModelAndView goAdd()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("lodfile/qts_cp/qtscp_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**保存修改
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/cp/save")
		public ModelAndView save()throws Exception{
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
			logBefore(logger, Jurisdiction.getUsername()+"新增QTS CP数据");
			String cxlogid=UuidUtil.get32UUID();		
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			qtscpService.save(pd); 
			//发送邮件
			Sendmail.sendToManager(Jurisdiction.getUsername(), "新增", pd, "lodfile_qtscp_A");
						
			mv.addObject("msg","success");
			mv.setViewName("save_result");
			return mv;
		}	
		
		/**
		 * 去修改界面
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/cp/goEdit")
		public ModelAndView goEdit()throws Exception{
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			pd = qtscpService.findById(pd);	
			mv.setViewName("lodfile/qts_cp/qtscp_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			return mv;
		}	
		
		/**
		 * 修改QTS CP
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/cp/edit")
		public ModelAndView edit()throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"  update QTS CP");
			String cxlogid=UuidUtil.get32UUID();		
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			qtscpService.editCP(pd);	//执行修改
			//发送邮件
			Sendmail.sendToManager(Jurisdiction.getUsername(), "修改", pd, "lodfile_qtscp_A");
			

			mv.addObject("msg","success");
			mv.setViewName("save_result");
			return mv;
		}
		
		
		/**判断数据是否重复
		 * @return
		 */
		@RequestMapping(value="/cp/hasCP")
		@ResponseBody
		public Object hasU(){
			Map<String,String> map = new HashMap<String,String>();
			String errInfo = "success";
			PageData pd = new PageData();
			try{
				pd = this.getPageData();
				int count=qtscpService.findBySimilar(pd);
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

