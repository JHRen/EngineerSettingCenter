package com.amkor.controller.ep.socketsetting;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.entity.system.User;
import com.amkor.service.ep.approval.ApprovalManager;
import com.amkor.service.ep.socketsetting.SocketSettingManager;
import com.amkor.service.system.fhlog.FHlogManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Const;
import com.amkor.util.FileDownload;
import com.amkor.util.FileUpload;
import com.amkor.util.Jurisdiction;
import com.amkor.util.ObjectExcelRead;
import com.amkor.util.ObjectExcelView;
import com.amkor.util.PageData;
import com.amkor.util.PathUtil;
import com.amkor.util.Tools;
import com.amkor.util.UuidUtil;
import com.amkor.util.mail.SendMailToAll;
//import com.fh.util.datasource.DataSourceContextHolder;
import com.amkor.util.mail.Sendmail;

@Controller
@RequestMapping(value="sockettdlimit")
public class SocketTDLimitController extends BaseController{
	String menuUrl = "sockettdlimit.do";  // 菜单地址(权限用)
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="SocketSettingService")
	private  SocketSettingManager socketSettingService;
	
	@Resource(name="ApprovalService")
	private  ApprovalManager approvalService;
	
	/**
	 * 显示GF 列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
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
			pd.put("TABLE_NAME", "SOCKET_TD_LIMIT");
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
			List<PageData> varList = socketSettingService.list(page); 
			mv.addObject("varList", varList);
		}
		
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		
		if("2".equals(type)){
			mv.setViewName("socketsetting/qca/list_r");
		}else{
			mv.setViewName("socketsetting/qca/list");
		}	
		
		return mv;	
	}
	
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		//添加操作日志-删除记录
		logBefore(logger, Jurisdiction.getUsername()+"DELETE gf");
		
		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd = socketSettingService.findById(pd);	
		//开启审核 删除保留
		socketSettingService.delete(pd);
		//添加进审批表			
		approvalService.save(Jurisdiction.getUsername(),"删除",JSON.toJSONString(pd),"SOCKET_TD_LIMIT",Jurisdiction.getRoleParentID());
		
		//发送邮件
		sendToMail(Jurisdiction.getUsername(), "删除", pd, "SOCKETSETTING_A");
		out.write("success");
		out.close();
	}
	
	/**
	 * 去新增界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	public ModelAndView goAdd()throws Exception{	
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("socketsetting/qca/edit");
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
			logBefore(logger, Jurisdiction.getUsername()+"add socketlimit data");
			String cxlogid=UuidUtil.get32UUID();		
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			//String hostName=Jurisdiction.getHostName();
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			//添加进审批表			
			approvalService.save(Jurisdiction.getUsername(),"新增",JSON.toJSONString(pd),"SOCKET_TD_LIMIT",Jurisdiction.getRoleParentID());
			
			//发送邮件
			sendToMail(Jurisdiction.getUsername(), "新增", pd, "SOCKETSETTING_A");
			
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
			pd = socketSettingService.findById(pd);	
			mv.setViewName("socketsetting/qca/edit");
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
			logBefore(logger, Jurisdiction.getUsername()+"  update gf");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
			//添加操作日志-修改记录
			String cxlogid=UuidUtil.get32UUID();		
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);	
			//String hostName=Jurisdiction.getHostName();
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			//socketSettingService.edit(pd);	//执行修改
			socketSettingService.delete(pd);
			//添加进审批表	
			approvalService.save(Jurisdiction.getUsername(),"修改",JSON.toJSONString(pd),"SOCKET_TD_LIMIT",Jurisdiction.getRoleParentID());
			
			
			//发送邮件
			sendToMail(Jurisdiction.getUsername(), "修改", pd, "SOCKETSETTING_A");
			
			mv.addObject("msg","success");
			mv.setViewName("save_result");
			return mv;
		}
		
		/**导出ProbeCard GF 到EXCEL
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping(value="/excel")
		public ModelAndView exportExcel() throws Exception{
			FHLOG.save(Jurisdiction.getUsername(), "导出用户信息到EXCEL");
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			try{
				if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
					String keywords = pd.getString("keywords");				//关键词检索条件
					if(null != keywords && !"".equals(keywords)){
						pd.put("keywords", keywords.trim());
					}
			
					Map<String,Object> dataMap = new HashMap<String,Object>();
					List<String> titles = new ArrayList<String>();
					titles.add("SOCKET_ID");  	   //1
					titles.add("CUSTOMER_CODE");		   //2
					titles.add("DEVICE_NAME");			   //3
					titles.add("OPERATION");		   //4
					titles.add("TD_LIMIT");		   //5
					
					dataMap.put("titles", titles);
					
					List<PageData> pcList= socketSettingService.listAll(pd);
					List<PageData> varList = new ArrayList<PageData>();
					for(int i=0;i<pcList.size();i++){
						PageData vpd = new PageData();
						vpd.put("var1", pcList.get(i).getString("SOCKET_ID"));		    //1
						vpd.put("var2", pcList.get(i).getString("CUSTOMER_CODE"));			    //2
						vpd.put("var3", pcList.get(i).getString("DEVICE_NAME"));	            //3
						vpd.put("var4", pcList.get(i).getString("OPERATION"));		    //4
						vpd.put("var5", pcList.get(i).getString("TD_LIMIT"));		        //5
						
						varList.add(vpd);
					}
					dataMap.put("varList", varList);
					ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
					mv = new ModelAndView(erv,dataMap);
				}
			} catch(Exception e){
				logger.error(e.toString(), e);
			}
			return mv;
		}
		
		/**打开上传EXCEL页面
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/goUploadExcel")
		public ModelAndView goUploadExcel()throws Exception{
			ModelAndView mv = this.getModelAndView();
			mv.setViewName("socketsetting/qca/uploadexcel");
			return mv;
		}
		
		/**下载模版
		 * @param response
		 * @throws Exception
		 */
		@RequestMapping(value="/downExcel")
		public void downExcel(HttpServletResponse response)throws Exception{
			FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "socket_setting.xls", "socket_setting.xls");
		}
		
		/**从EXCEL导入到数据库
		 * @param file
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/readExcel")
		public ModelAndView readExcel(@RequestParam(value="excel",required=false) MultipartFile file
				) throws Exception{
			FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入到数据库");
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
			if (null != file && !file.isEmpty()) {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
				String fileName =  FileUpload.fileUp(file, filePath, "userexcel");							//执行上传
				List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
				List<PageData> errorListPd = new ArrayList<PageData>();
				/*存入数据库操作======================================*/
				pd.put("SOCKET_ID", "");			
				pd.put("CUSTOMER_CODE", "");				
				pd.put("DEVICE_NAME", "");						
				pd.put("OPERATION", "");			
				pd.put("TD_LIMIT", "");			
				
				for(int i=0;i<listPd.size();i++){
					
					pd.put("SOCKET_ID", listPd.get(i).getString("var0") );			
					pd.put("CUSTOMER_CODE", listPd.get(i).getString("var1"));				
					pd.put("DEVICE_NAME", listPd.get(i).getString("var2"));						
					pd.put("OPERATION", listPd.get(i).getString("var3"));					
					pd.put("TD_LIMIT", listPd.get(i).getString("var4"));					
					
					//判断是否有重复
					int count=socketSettingService.findBySimilar(pd);
					if(count>0) {
						errorListPd.add(pd);
					}else {
						socketSettingService.save(pd); 
					}
				}
				/*存入数据库操作======================================*/
//				if (errorListPd.isEmpty()) {
					mv.addObject("msg","success");
//				}else {
//					mv.addObject("msg","error");
//				}
			}
			mv.setViewName("save_result");
			return mv;
		}
		
		/**
		 * 审批通过
		 * @param out
		 * @throws Exception
		 */
		@RequestMapping(value="/agree")
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
			pdd.put("SOCKET_ID", jsonObject.get("SOCKET_ID"));
			pdd.put("CUSTOMER_CODE", jsonObject.get("CUSTOMER_CODE"));
			pdd.put("DEVICE_NAME", jsonObject.get("DEVICE_NAME"));
			pdd.put("OPERATION", jsonObject.get("OPERATION"));
			pdd.put("TD_LIMIT", jsonObject.get("TD_LIMIT"));
			
			
			if("新增".equals(pd.get("STATUS"))){
				pd.put("newZT", "1");
				socketSettingService.save(pdd); 	//将新增信息保存进去
				approvalService.edit(pd);		//修改状态为审核同意
				//发送邮件
				sendToMail(Jurisdiction.getUsername(), "新增", pdd, "SOCKETSETTING_T");
			}else if("删除".equals(pd.get("STATUS"))){
				pd.put("newZT", "1");
				approvalService.edit(pd);		//修改状态为审核同意
				//发送邮件
				sendToMail(Jurisdiction.getUsername(), "删除", pdd, "SOCKETSETTING_T");
			}else if("修改".equals(pd.get("STATUS"))){
				pd.put("newZT", "1");
				approvalService.edit(pd);		//修改状态为审核同意
				socketSettingService.save(pdd);	    //修改的信息同意
				//发送邮件
				sendToMail(Jurisdiction.getUsername(), "修改", pdd, "SOCKETSETTING_T");
			}
			
			out.write("success");
			out.close();
		}
		
		/**
		 * 审批不通过
		 * @param out
		 * @throws Exception
		 */
		@RequestMapping(value="/deny")
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
			pdd.put("SOCKET_ID", jsonObject.get("SOCKET_ID"));
			pdd.put("CUSTOMER_CODE", jsonObject.get("CUSTOMER_CODE"));
			pdd.put("DEVICE_NAME", jsonObject.get("DEVICE_NAME"));
			pdd.put("OPERATION", jsonObject.get("OPERATION"));
			pdd.put("TD_LIMIT", jsonObject.get("TD_LIMIT"));
			
			
			if("新增".equals(pd.get("STATUS"))){
				pd.put("newZT", "2");
				approvalService.edit(pd);
				//发送邮件
				sendToMail(Jurisdiction.getUsername(), "新增", pdd, "SOCKETSETTING_F");
			}else if("删除".equals(pd.get("STATUS"))){
				pd.put("newZT", "2");
				approvalService.edit(pd);		//修改状态为审核不同意
				socketSettingService.save(pdd);	//删除的信息还原
				//发送邮件
				sendToMail(Jurisdiction.getUsername(), "删除", pdd, "SOCKETSETTING_F");
			}else if("修改".equals(pd.get("STATUS"))){
				PageData pdb = new PageData();
				pdb.put("ID",  pd.get("ID"));
				pdb.put("SOCKET_ID", jsonObject.get("SOCKET_ID"));
				pdb.put("CUSTOMER_CODE", jsonObject.get("CUSTOMER_CODE"));
				pdb.put("DEVICE_NAME", jsonObject.get("DEVICE_NAME"));
				pdb.put("OPERATION", jsonObject.get("OPERATION"));
				pdb.put("TD_LIMIT", jsonObject.get("TD_LIMIT"));
				
				pd.put("newZT", "2");
				approvalService.edit(pd);		//修改状态为审核同意				
				socketSettingService.save(pdb);	//修改的信息还原
				//发送邮件
				sendToMail(Jurisdiction.getUsername(), "修改", pdd, "SOCKETSETTING_F");
			}
			
			out.write("success");
			out.close();
		}
		
		/**判断数据是否重复
		 * @return
		 */
		@RequestMapping(value="/hasData")
		@ResponseBody
		public Object hasU(){
			Map<String,String> map = new HashMap<String,String>();
			String errInfo = "success";
			PageData pd = new PageData();
			try{
				pd = this.getPageData();
				int count=socketSettingService.findBySimilar(pd);
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
	
		
		/**
		 * 发送通知邮件
		 * 
		 * @throws Exception
		 *             EMAIL_FLAG: MCN_GF_A (MCN:1级大分类 GF:2级分类 A/T/F:
		 *             用作申请未处理时/审核通过/审核不通过）
		 */
		public static void sendToMail(String UserName, String Status, PageData pd, String EMAIL_FLAG) throws Exception {
			String title ="Engineer Setting Center";
			String from = "SHCN-TEST-IT@amkor.com";// 发件人
			String fileNames[] = null; // 附件

			List<String> list = Jurisdiction.getRecipient(); // 获取发送邮件的人
			 String sendTo[] =(String[])list.toArray(new String[0]);


			String mailbody = "";

			String[] EmailFlag = EMAIL_FLAG.split("_"); // 将发送的系统内容的识别标记 按下滑杠分割提取内容
			
				mailbody = "";
				mailbody = "<br>Hi All,</br>";
				if ("A".equals(EmailFlag[1])) {
					mailbody += "<p>Socket Setting 操作申请</p>";
				} else {
					mailbody += "<p>Socket Setting 审批通知</p>";
				}

				mailbody += "<br><table style='FONT-SIZE: 12px' borderColor=#d6dff5 cellSpacing=1 cellPadding=0 bgColor=#d6dff5>";
				mailbody += "<tr bgcolor='#cccccc'>";
				mailbody += "<td align='center' valign='middle'>操作人</td>";
				mailbody += "<td align='center' valign='middle'>操作状态</td>";
				mailbody += "<td align='center' valign='middle'>操作时间</td>";

				mailbody += "<td align='center' valign='middle'>SOCKET_ID</td>";
				mailbody += "<td align='center' valign='middle'>CUSTOMER_CODE</td>";
				mailbody += "<td align='center' valign='middle'>DEVICE_NAME</td>";
				mailbody += "<td align='center' valign='middle'>OPERATION</td>";
				mailbody += "<td align='center' valign='middle'>TD_LIMIT</td>";
				mailbody += "<td align='center' valign='middle'>审批状态</td>";
				mailbody += "</tr>";

				mailbody += "<tr bgcolor='#ffffff'>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + UserName + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + Status + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + Tools.date2Str(new Date()) + "&nbsp;</td>";

				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("SOCKET_ID") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("CUSTOMER_CODE") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("DEVICE_NAME") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("OPERATION") + "&nbsp;</td>";
				mailbody += "<td align='center' valign='middle'>&nbsp;" + pd.getString("TD_LIMIT") + "&nbsp;</td>";
				if ("T".equals(EmailFlag[1])) {
					mailbody += "<td align='center' valign='middle'>&nbsp; 批准 &nbsp;</td>";
				} else if ("F".equals(EmailFlag[1])) {
					mailbody += "<td align='center' valign='middle'>&nbsp; 拒绝  &nbsp;</td>";
				}

				mailbody += "</tr>";
				mailbody += "</table>";
				mailbody += "<p>http://atctest:8081/EngineerSettingCenter</p>";
				mailbody += "<br><br><br>";
				mailbody += "<p>如何你有任何问题，请邮件联系我们：SHCN-TEST-IT@amkor.com</p>";
				mailbody += "<p>Thanks and Best Regards</p>";
			 
			// 邮件发送

			SendMailToAll.sendmail(title, from, sendTo, mailbody, fileNames, "text/html;charset=UTF-8");
		}
}

