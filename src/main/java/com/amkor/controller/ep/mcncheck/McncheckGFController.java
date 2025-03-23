package com.amkor.controller.ep.mcncheck;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.entity.system.User;
import com.amkor.service.ep.approval.ApprovalManager;
import com.amkor.service.ep.mcncheck.McncheckManager;
import com.amkor.service.fhoa.sendmail.SendmailManager;
import com.amkor.service.system.fhlog.FHlogManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Const;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.Tools;
import com.amkor.util.UuidUtil;
//import com.fh.util.datasource.DataSourceContextHolder;
import com.amkor.util.mail.Sendmail;

@Controller
@RequestMapping(value="mcncheck")
public class McncheckGFController extends BaseController{
	String menuUrl = "mcncheck/gf.do";  // 菜单地址(权限用)
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="McncheckGFService")
	private  McncheckManager mcncheckGFService;
	
	@Resource(name="ApprovalService")
	private  ApprovalManager approvalService;
	
	@Resource(name="sendmailService")
	private SendmailManager sendmailService;
	
	/**
	 * 显示GF 列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gf/list")
	public ModelAndView listCP(Page page) throws Exception{	
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
	    //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		
		ModelAndView mv = this.getModelAndView();		
		PageData pd = new PageData();
		pd = this.getPageData();
		String type = pd.getString("type");
		type = Tools.isEmpty(type)?"0":type;
		
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String LAST_LOGINTIME_END=pd.getString("LAST_LOGINTIME_END");//获取查询的结束时间
		if(null !=LAST_LOGINTIME_END && !"".equals(LAST_LOGINTIME_END)){
			pd.put("LAST_LOGINTIME_END", LAST_LOGINTIME_END);
		}
		page.setPd(pd);
		
		//审核视图
		if("2".equals(type)){
			pd.put("TABLE_NAME", "MCN_GF");
			pd.put("GROUP_ID", Jurisdiction.getRoleParentID());
			List<PageData> varList_r = approvalService.list(page);

				List<JSONObject> mcnList=new ArrayList<JSONObject>();
				if(!varList_r.isEmpty()){				
					for(PageData pda:varList_r){
						JSONObject jsonObject=JSONObject.parseObject((String) pda.get("TABLE_FIELD_ALL"));
						mcnList.add(jsonObject);
					}
				
			}
				mv.addObject("mcnList", mcnList);
				mv.addObject("varList_r", varList_r);
		}else{
			//数据视图
			List<PageData> varList = mcncheckGFService.list(page); 
			mv.addObject("varList", varList);
		}
		
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		
		if("2".equals(type)){
			mv.setViewName("mcncheck/gf/gf_list_r");
		}else{
			mv.setViewName("mcncheck/gf/gf_list");
		}		
		
		return mv;	
	}
	
	@RequestMapping(value="/gf/delete")
	public void delete(PrintWriter out) throws Exception{
		
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		//添加操作日志-删除记录
		logBefore(logger, Jurisdiction.getUsername()+"DELETE GF");
		
		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd = mcncheckGFService.findById(pd);	
		//开启审核 删除保留
		mcncheckGFService.delete(pd);
		//添加进审批表			
		approvalService.save(Jurisdiction.getUsername(),"删除",JSON.toJSONString(pd),"MCN_GF",Jurisdiction.getRoleParentID());
		
		//发送邮件
		sendmailService.sendToManager(Jurisdiction.getUsername(),Jurisdiction.getRecipient(), "删除", pd, "MCN_GF_A");
		out.write("success");
		out.close();
	}
	
	/**
	 * 去新增界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gf/add")
	public ModelAndView goAdd()throws Exception{	
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("mcncheck/gf/gf_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**保存修改
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/gf/save")
		public ModelAndView save()throws Exception{
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
			//添加操作日志-增加记录
			logBefore(logger, Jurisdiction.getUsername()+"add GF data");
			String cxlogid=UuidUtil.get32UUID();		
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			//String hostName=Jurisdiction.getHostName();
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			//增加审批功能 此处先注释
			//mcncheckGFService.save(pd); 
			//添加进审批表			
			approvalService.save(Jurisdiction.getUsername(),"新增",JSON.toJSONString(pd),"MCN_GF",Jurisdiction.getRoleParentID());
			
			//发送邮件
			sendmailService.sendToManager(Jurisdiction.getUsername(),Jurisdiction.getRecipient(), "新增", pd, "MCN_GF_A");
			
			mv.addObject("msg","success");
			mv.setViewName("save_result");
			return mv;
		}	
		
		/**
		 * 去修改界面
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/gf/goEdit")
		public ModelAndView goEdit()throws Exception{
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			pd = mcncheckGFService.findById(pd);	
			mv.setViewName("mcncheck/gf/gf_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			return mv;
		}	
		
		/**
		 * 修改
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/gf/edit")
		public ModelAndView edit()throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"  update GF");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
			//添加操作日志-修改记录
			String cxlogid=UuidUtil.get32UUID();		
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);	
			//String hostName=Jurisdiction.getHostName();
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			//mcncheckGFService.edit(pd);	//执行修改
			mcncheckGFService.delete(pd);
			//添加进审批表	
			PageData pdd = new PageData();
//			pdd.put("ID",  pd.get("ID"));
//			pdd.put("MCN_Name", pd.get("MCN_Name_check"));
//			pdd.put("GF_Part", pd.get("GF_Part_check"));
//			pdd.put("TEST_PGM1", pd.get("TEST_PGM1_check"));
//			pdd.put("JOB_NAME", pd.get("JOB_NAME_check"));
			approvalService.save(Jurisdiction.getUsername(),"修改",JSON.toJSONString(pd),"MCN_GF",Jurisdiction.getRoleParentID());
			
			
			//发送邮件
			sendmailService.sendToManager(Jurisdiction.getUsername(),Jurisdiction.getRecipient(), "修改", pd, "MCN_GF_A");
			
			mv.addObject("msg","success");
			mv.setViewName("save_result");
			return mv;
		}
		/**
		 * 审批通过
		 * @param out
		 * @throws Exception
		 */
		@RequestMapping(value="/gf/agree")
		public void agree(PrintWriter out) throws Exception{
			
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "apr")){return;} //校验权限
			//添加操作日志-审批记录
			logBefore(logger, Jurisdiction.getUsername()+"同意审批");
			
			PageData pd = new PageData();
			pd = this.getPageData();
			
			pd = approvalService.findById(pd);	
			JSONObject jsonObject=JSONObject.parseObject((String) pd.get("TABLE_FIELD_ALL"));
		
			PageData pdd = new PageData();
			pdd.put("ID",  pd.get("ID"));
			pdd.put("MCN_Name", jsonObject.get("MCN_Name"));
			pdd.put("GF_Part", jsonObject.get("GF_Part"));
			pdd.put("TEST_PGM1", jsonObject.get("TEST_PGM1"));
			pdd.put("JOB_NAME", jsonObject.get("JOB_NAME"));
			if("新增".equals(pd.get("STATUS"))){
				pd.put("newZT", "1");
				mcncheckGFService.save(pdd); 	//将新增mcn信息保存进去
				approvalService.edit(pd);		//修改状态为审核同意
				//发送邮件
				sendmailService.sendToManager(Jurisdiction.getUsername(),Jurisdiction.getRecipient(), "新增", pdd, "MCN_GF_T");
			}else if("删除".equals(pd.get("STATUS"))){
				pd.put("newZT", "1");
				approvalService.edit(pd);		//修改状态为审核同意
				//发送邮件
				sendmailService.sendToManager(Jurisdiction.getUsername(),Jurisdiction.getRecipient(), "删除", pdd, "MCN_GF_T");
			}else if("修改".equals(pd.get("STATUS"))){
				pd.put("newZT", "1");
				approvalService.edit(pd);		//修改状态为审核同意
				mcncheckGFService.save(pdd);	    //修改的信息同意
				//发送邮件
				sendmailService.sendToManager(Jurisdiction.getUsername(),Jurisdiction.getRecipient(), "修改", pdd, "MCN_GF_T");
			}
			

			
			out.write("success");
			out.close();
		}
		
		/**
		 * 审批不通过
		 * @param out
		 * @throws Exception
		 */
		@RequestMapping(value="/gf/deny")
		public void deny(PrintWriter out) throws Exception{
			
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "apr")){return;} //校验权限
			//添加操作日志
			logBefore(logger, Jurisdiction.getUsername()+"不同意审批");
			
			PageData pd = new PageData();
			pd = this.getPageData();			
			pd = approvalService.findById(pd);	
			JSONObject jsonObject=JSONObject.parseObject((String) pd.get("TABLE_FIELD_ALL"));
			
			PageData pdd = new PageData();
			pdd.put("ID",  pd.get("ID"));
			pdd.put("MCN_Name", jsonObject.get("MCN_Name"));
			pdd.put("GF_Part", jsonObject.get("GF_Part"));
			pdd.put("TEST_PGM1", jsonObject.get("TEST_PGM1"));
			pdd.put("JOB_NAME", jsonObject.get("JOB_NAME"));		
			
			
			if("新增".equals(pd.get("STATUS"))){
				pd.put("newZT", "2");
				approvalService.edit(pd);
				//发送邮件
				sendmailService.sendToManager(Jurisdiction.getUsername(),Jurisdiction.getRecipient(), "新增", pdd, "MCN_GF_F");
			}else if("删除".equals(pd.get("STATUS"))){
				pd.put("newZT", "2");
				approvalService.edit(pd);		//修改状态为审核不同意
				mcncheckGFService.save(pdd);	//删除的信息还原
				//发送邮件
				sendmailService.sendToManager(Jurisdiction.getUsername(),Jurisdiction.getRecipient(), "删除", pdd, "MCN_GF_F");
			}else if("修改".equals(pd.get("STATUS"))){
				PageData pdb = new PageData();
				pdb.put("ID",  pd.get("ID"));
				pdb.put("MCN_Name", jsonObject.get("MCN_Name_check"));
				pdb.put("GF_Part", jsonObject.get("GF_Part_check"));
				pdb.put("TEST_PGM1", jsonObject.get("TEST_PGM1_check"));
				pdb.put("JOB_NAME", jsonObject.get("JOB_NAME_check"));
				
				pd.put("newZT", "2");
				approvalService.edit(pd);		//修改状态为审核同意				
				mcncheckGFService.save(pdb);	//修改的信息还原
				//发送邮件
				sendmailService.sendToManager(Jurisdiction.getUsername(),Jurisdiction.getRecipient(), "修改", pdd, "MCN_GF_F");
			}
			
			out.write("success");
			out.close();
		}
		
		/**判断数据是否重复
		 * @return
		 */
		@RequestMapping(value="/gf/hasData")
		@ResponseBody
		public Object hasU(){
			Map<String,String> map = new HashMap<String,String>();
			String errInfo = "success";
			PageData pd = new PageData();
			try{
				pd = this.getPageData();
				int count=mcncheckGFService.findBySimilar(pd);
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

