package com.amkor.controller.ep.equipdmanage;

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
import com.amkor.service.ep.equipment.EquipmentManager;
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
@RequestMapping(value="equipment")
public class EquipmentController extends BaseController{
	String menuUrl = "equipment/list.do";  // 菜单地址(权限用)
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="equipmentService")
	private  EquipmentManager equipmentService;
	@Resource(name="userService")
	private UserManager userService;

	/*@ModelAttribute
	public void setDataSource(){
		logBefore(logger, "Switch to dataSource1");
		DataSourceContextHolder.setDBType("dataSource1"); 
	}*/
	
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

		List<PageData> varList = equipmentService.list(page); 
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		
		mv.setViewName("equipment/equpwd_list");
		return mv;	
	}
	
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		//添加操作日志-删除记录
		logBefore(logger, Jurisdiction.getUsername()+"删除设备密码管理");
		String cxlogid=UuidUtil.get32UUID();		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	
		//String hostName=Jurisdiction.getHostName();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		//FHLOG.save(cxlogid,Jurisdiction.getUsername(),"删除设备密码管理 失败"+pd,"1","2",user.getUSER_ID(),hostName);
		
		equipmentService.delete(pd);

		//FHLOG.edit(cxlogid, "0", "成功设备密码管理数据："+pd,"equipment");
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
		mv.setViewName("equipment/equpwd_edit");
		pd.put("Status", "0");//状态 启用
		pd.put("IsRemindByEmail", "1");//默认不发送邮件
		
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
			logBefore(logger, Jurisdiction.getUsername()+"add equipment password manager");
			String cxlogid=UuidUtil.get32UUID();		
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			//String hostName=Jurisdiction.getHostName();
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			//String GroupID=Jurisdiction.getRoleParentID();
		//	pd.put("GroupID", GroupID);
		//	FHLOG.save(cxlogid,Jurisdiction.getUsername(),"新增设备密码管理 失败"+pd,"1","2",user.getUSER_ID(),hostName);
			
			equipmentService.save(pd); 
			
			//FHLOG.edit(cxlogid, "0", "新增设备密码管理新增成功："+pd,"equipment");
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
			pd = equipmentService.findById(pd);	
			mv.setViewName("equipment/equpwd_edit");
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
			logBefore(logger, Jurisdiction.getUsername()+"  修改设备密码管理");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
			//添加操作日志-修改记录
			String cxlogid=UuidUtil.get32UUID();		
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);	
			//String hostName=Jurisdiction.getHostName();
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			//FHLOG.save(cxlogid,Jurisdiction.getUsername(),"修改设备密码管理失败"+pd,"1","2",user.getUSER_ID(),hostName);
			equipmentService.edit(pd);	//执行修改
			//FHLOG.edit(cxlogid, "0", "成功修改修改设备密码管理:"+pd,"equipment");
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
				int count=equipmentService.findBySimilar(pd);
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
		
		/**点击按钮处理关联表
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/upRb")
		@ResponseBody
		public Object updateIsSendEmail()throws Exception{
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
			logBefore(logger, Jurisdiction.getUsername()+"修改是否发送邮件");
			Map<String,String> map = new HashMap<String,String>();
			PageData pd = new PageData();
			pd = this.getPageData();
			String cxlogid=UuidUtil.get32UUID();
			Session session = Jurisdiction.getSession();
			//String hostName=Jurisdiction.getHostName();
			User user = (User)session.getAttribute(Const.SESSION_USER);		//读取session中的用户信息(单独用户信息)
		//	FHLOG.save(cxlogid,Jurisdiction.getUsername(), "修改邮件发送失败","1","2",user.getUSER_ID(),hostName);
			String errInfo = "success";
	
			equipmentService.editEmail(pd);		//新增
		//	FHLOG.edit(cxlogid, "0", "修改邮件发送成功"+pd,"equipment");
			
			map.put("result", errInfo);
			return AppUtil.returnObject(new PageData(), map);
		}
		
		 // 间隔10秒执行 0/10 * * * * ?
		//每天上午 8点触发 (cron = "0 0 8 * * ?")
		@Scheduled(cron = "0 0 8 * * ?")
	    public void taskCycle() {			
			try {
				String curtime=DateUtil.getTime();
				//String curtime="2018-07-01 08:57:00";   //测试时间
				List<PageData> list=equipmentService.sendEmail(curtime);
				if(list!=null){		
					String title = Tools.readTxtFile(Const.SYSNAME);// 所发送邮件的标题  
					String from = "SHCN-TEST-IT@amkor.com";// 发件人
					String fileNames[] = { "","" };   //附件
			
					String mailbody="";
					
					for(PageData email:list){
						String group=(String) email.getString("GroupID");
						//获取发送邮件的人
						List<String> allEmaliRecipient = userService.listEmail(group);//列出此组下admin邮箱
					    allEmaliRecipient.add("SHCN-TEST-IT@amkor.com");
						String sendTo[] =(String[])allEmaliRecipient.toArray(new String[0]);
						 
						 mailbody="<br>Hi,</br>";
						 mailbody +="<br>设备密码到期，请及时更新</br>";
						 mailbody +="<br>账户类型："+email.getString("AccountType")+"</br>";
						 mailbody +="<br>机器类型："+email.getString("MachineType")+"</br>";
						 mailbody +="<br>用户名："+email.getString("UserID")+"</br>";
						 mailbody +="<br>密码失效时间："+email.getString("ExpiredDate")+"</br>";
						 
						//邮件发送
						 SendMailToAll.sendmail(title, from, sendTo, mailbody, fileNames,"text/html;charset=UTF-8");  
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println(DateUtil.getTime());
	    }
	
}

