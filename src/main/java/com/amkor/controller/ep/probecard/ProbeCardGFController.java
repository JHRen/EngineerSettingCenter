package com.amkor.controller.ep.probecard;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.SimpleHash;
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
import com.amkor.service.ep.probecard.ProbeCardManager;
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
//import com.fh.util.datasource.DataSourceContextHolder;
import com.amkor.util.mail.Sendmail;

@Controller
@RequestMapping(value="probecard")
public class ProbeCardGFController extends BaseController{
	String menuUrl = "probecard/gf.do";  // 菜单地址(权限用)
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="ProbeCardGFService")
	private  ProbeCardManager probecardGFService;
	
	@Resource(name="ApprovalService")
	private  ApprovalManager approvalService;
	
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
			pd.put("TABLE_NAME", "PC_GF");
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
			List<PageData> varList = probecardGFService.list(page); 
			mv.addObject("varList", varList);
		}
		
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		
		if("2".equals(type)){
			mv.setViewName("probecard/gf/gf_list_r");
		}else{
			mv.setViewName("probecard/gf/gf_list");
		}	
		
		return mv;	
	}
	
	@RequestMapping(value="/gf/delete")
	public void delete(PrintWriter out) throws Exception{
		
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		//添加操作日志-删除记录
		logBefore(logger, Jurisdiction.getUsername()+"DELETE gf");
		
		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd = probecardGFService.findById(pd);	
		//开启审核 删除保留
		probecardGFService.delete(pd);
		//添加进审批表			
		approvalService.save(Jurisdiction.getUsername(),"删除",JSON.toJSONString(pd),"PC_GF",Jurisdiction.getRoleParentID());
		
		//发送邮件
		Sendmail.sendToManager(Jurisdiction.getUsername(), "删除", pd, "PC_GF_A");
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
		mv.setViewName("probecard/gf/gf_edit");
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
			logBefore(logger, Jurisdiction.getUsername()+"add gf data");
			String cxlogid=UuidUtil.get32UUID();		
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			//String hostName=Jurisdiction.getHostName();
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			//增加审批功能 此处先注释
			//probecardGFService.save(pd); 
			//添加进审批表			
			approvalService.save(Jurisdiction.getUsername(),"新增",JSON.toJSONString(pd),"PC_GF",Jurisdiction.getRoleParentID());
			
			//发送邮件
			Sendmail.sendToManager(Jurisdiction.getUsername(), "新增", pd, "PC_GF_A");
			
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
			pd = probecardGFService.findById(pd);	
			mv.setViewName("probecard/gf/gf_edit");
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
			
			//probecardGFService.edit(pd);	//执行修改
			probecardGFService.delete(pd);
			//添加进审批表	
			approvalService.save(Jurisdiction.getUsername(),"修改",JSON.toJSONString(pd),"PC_GF",Jurisdiction.getRoleParentID());
			
			
			//发送邮件
			Sendmail.sendToManager(Jurisdiction.getUsername(), "修改", pd, "PC_GF_A");
			
			mv.addObject("msg","success");
			mv.setViewName("save_result");
			return mv;
		}
		
		/**导出ProbeCard GF 到EXCEL
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping(value="/gf/excel")
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
					titles.add("Cust Tool Name");  	   //1
					titles.add("Tool Name");		   //2
					titles.add("Customer");			   //3
					titles.add("Device Name");		   //4
					titles.add("Operation");		   //5
					titles.add("Touchdown Per Wafer"); //6
					titles.add("Touchdown Limit");	   //7
					titles.add("Touchdown Limit");	   //7
					titles.add("Gross Die Per Wafer"); //8
					dataMap.put("titles", titles);
					
					List<PageData> pcList= probecardGFService.listAllPc(pd);
					List<PageData> varList = new ArrayList<PageData>();
					for(int i=0;i<pcList.size();i++){
						PageData vpd = new PageData();
						vpd.put("var1", pcList.get(i).getString("Cust_Tool_Name"));		    //1
						vpd.put("var2", pcList.get(i).getString("Tool_Name"));			    //2
						vpd.put("var3", pcList.get(i).getString("Customer"));	            //3
						vpd.put("var4", pcList.get(i).getString("Device_Name"));		    //4
						vpd.put("var5", pcList.get(i).getString("Operation"));		        //5
						vpd.put("var6", pcList.get(i).get("Touchdown_Per_Wafer")+"");		//6
						vpd.put("var7", pcList.get(i).get("Touchdown_Limit")+"");	   		//7
						vpd.put("var8", pcList.get(i).get("Gross_Die_Per_Wafer")+"");
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
		@RequestMapping(value="/gf/goUploadExcel")
		public ModelAndView goUploadExcel()throws Exception{
			ModelAndView mv = this.getModelAndView();
			mv.setViewName("probecard/gf/uploadexcel");
			return mv;
		}
		
		/**下载模版
		 * @param response
		 * @throws Exception
		 */
		@RequestMapping(value="/gf/downExcel")
		public void downExcel(HttpServletResponse response)throws Exception{
			FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Procard.xls", "Procard.xls");
		}
		
		/**从EXCEL导入到数据库
		 * @param file
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/gf/readExcel")
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
				pd.put("Cust_Tool_Name", "");			
				pd.put("Tool_Name", "");				
				pd.put("Customer", "");						
				pd.put("Device_Name", "");			
				pd.put("Operation", "");			
				pd.put("Touchdown_Per_Wafer", "");				
				pd.put("Touchdown_Limit", "");
				pd.put("Gross_Die_Per_Wafer", "");
				
				for(int i=0;i<listPd.size();i++){
					
					pd.put("Cust_Tool_Name", listPd.get(i).getString("var0") );			
					pd.put("Tool_Name", listPd.get(i).getString("var1"));				
					pd.put("Customer", listPd.get(i).getString("var2"));						
					pd.put("Device_Name", listPd.get(i).getString("var3"));					
					pd.put("Operation", listPd.get(i).getString("var4"));					
					pd.put("Touchdown_Per_Wafer", Integer.parseInt(listPd.get(i).getString("var5")));				
					pd.put("Touchdown_Limit", Integer.parseInt(listPd.get(i).getString("var6")) );
					pd.put("Gross_Die_Per_Wafer", Integer.parseInt(listPd.get(i).getString("var7")) );
					//判断是否有重复
					int count=probecardGFService.findBySimilar(pd);
					if(count>0) {
						errorListPd.add(pd);
					}else {
						probecardGFService.save(pd); 
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
			pdd.put("Cust_Tool_Name", jsonObject.get("Cust_Tool_Name"));
			pdd.put("Tool_Name", jsonObject.get("Tool_Name"));
			pdd.put("Customer", jsonObject.get("Customer"));
			pdd.put("Device_Name", jsonObject.get("Device_Name"));
			pdd.put("Operation", jsonObject.get("Operation"));
			pdd.put("Touchdown_Per_Wafer", jsonObject.get("Touchdown_Per_Wafer"));
			pdd.put("Touchdown_Limit", jsonObject.get("Touchdown_Limit"));
			pdd.put("Gross_Die_Per_Wafer", jsonObject.get("Gross_Die_Per_Wafer"));
			
			if("新增".equals(pd.get("STATUS"))){
				pd.put("newZT", "1");
				probecardGFService.save(pdd); 	//将新增信息保存进去
				approvalService.edit(pd);		//修改状态为审核同意
				//发送邮件
				Sendmail.sendToManager(Jurisdiction.getUsername(), "新增", pdd, "PC_GF_T");
			}else if("删除".equals(pd.get("STATUS"))){
				pd.put("newZT", "1");
				approvalService.edit(pd);		//修改状态为审核同意
				//发送邮件
				Sendmail.sendToManager(Jurisdiction.getUsername(), "删除", pdd, "PC_GF_T");
			}else if("修改".equals(pd.get("STATUS"))){
				pd.put("newZT", "1");
				approvalService.edit(pd);		//修改状态为审核同意
				probecardGFService.save(pdd);	    //修改的信息同意
				//发送邮件
				Sendmail.sendToManager(Jurisdiction.getUsername(), "修改", pdd, "PC_GF_T");
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
			pdd.put("Cust_Tool_Name", jsonObject.get("Cust_Tool_Name"));
			pdd.put("Tool_Name", jsonObject.get("Tool_Name"));
			pdd.put("Customer", jsonObject.get("Customer"));
			pdd.put("Device_Name", jsonObject.get("Device_Name"));
			pdd.put("Operation", jsonObject.get("Operation"));
			pdd.put("Touchdown_Per_Wafer", jsonObject.get("Touchdown_Per_Wafer"));
			pdd.put("Touchdown_Limit", jsonObject.get("Touchdown_Limit"));	
			pdd.put("Gross_Die_Per_Wafer", jsonObject.get("Gross_Die_Per_Wafer"));	
			
			
			if("新增".equals(pd.get("STATUS"))){
				pd.put("newZT", "2");
				approvalService.edit(pd);
				//发送邮件
				Sendmail.sendToManager(Jurisdiction.getUsername(), "新增", pdd, "PC_GF_F");
			}else if("删除".equals(pd.get("STATUS"))){
				pd.put("newZT", "2");
				approvalService.edit(pd);		//修改状态为审核不同意
				probecardGFService.save(pdd);	//删除的信息还原
				//发送邮件
				Sendmail.sendToManager(Jurisdiction.getUsername(), "删除", pdd, "PC_GF_F");
			}else if("修改".equals(pd.get("STATUS"))){
				PageData pdb = new PageData();
				pdb.put("ID",  pd.get("ID"));
				pdb.put("Cust_Tool_Name", jsonObject.get("Cust_Tool_Name_check"));
				pdb.put("Tool_Name", jsonObject.get("Tool_Name_check"));
				pdb.put("Customer", jsonObject.get("Customer_check"));
				pdb.put("Device_Name", jsonObject.get("Device_Name_check"));
				pdb.put("Operation", jsonObject.get("Operation_check"));
				pdb.put("Touchdown_Per_Wafer", jsonObject.get("Touchdown_Per_Wafer_check"));
				pdb.put("Touchdown_Limit", jsonObject.get("Touchdown_Limit_check"));
				pdb.put("Gross_Die_Per_Wafer", jsonObject.get("Gross_Die_Per_Wafer"));
				
				pd.put("newZT", "2");
				approvalService.edit(pd);		//修改状态为审核同意				
				probecardGFService.save(pdb);	//修改的信息还原
				//发送邮件
				Sendmail.sendToManager(Jurisdiction.getUsername(), "修改", pdd, "PC_GF_F");
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
				int count=probecardGFService.findBySimilar(pd);
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

