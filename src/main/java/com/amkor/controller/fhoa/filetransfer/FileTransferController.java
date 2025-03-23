package com.amkor.controller.fhoa.filetransfer;


import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.service.fhoa.filetransfer.FiletransferFTNonManager;
import com.amkor.service.fhoa.filetransfer.FiletransferManager;
import com.amkor.service.fhoa.myleave.MyleaveManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Jurisdiction;
import com.amkor.util.ObjectExcelView;
import com.amkor.util.PageData;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;

/** 
 * 文件传输
 */
@Controller
@RequestMapping(value="/filetransfer")
public class FileTransferController extends BaseController {
	
	String menuUrl = "filetransfer/list.do"; //菜单地址(权限用)
	@Resource(name="filetransferService")
	private FiletransferManager filetransferService;
	@Resource(name="filetransferFTNonService")
	private FiletransferFTNonManager filetransferFTNonService;
	
	/**审批列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表FileTransfer");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("status", pd.getString("status"));
		page.setPd(pd);
		List<PageData> varList = filetransferService.list(page);
		mv.addObject("varList", varList);
		mv.setViewName("fhoa/filetransfer/filetransfer_approval");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());				//按钮权限
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/ft/list")
	public ModelAndView listFT(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表FileTransfer");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("status", pd.getString("pd"));
		page.setPd(pd);
		filetransferService.list(page);
		mv.setViewName("fhoa/filetransfer/ft-memory/filetransfer_list");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());				//按钮权限
		return mv;
	}
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/ft-non/list")
	public ModelAndView listFTNon(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表FileTransfer");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("status", pd.getString("status"));
		page.setPd(pd);
		List<PageData> varList = filetransferService.list(page);
		mv.addObject("varList", varList);
		mv.setViewName("fhoa/filetransfer/ft-non-memory/filetransfer_list");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());				//按钮权限
		return mv;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/ft-non/save")
	public ModelAndView save(){
		logBefore(logger, Jurisdiction.getUsername()+"保存申请单");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("userID", Jurisdiction.getUsername());         //工号
		
		PageData formPd = new PageData();
		formPd.put("title", pd.getString("title"));
		formPd.put("requestID",  pd.getString("requestID"));
		formPd.put("userID", Jurisdiction.getUsername());  
		formPd.put("userGroup", Jurisdiction.getRoleParentID());  //群组
		formPd.put("sourcePath", pd.getString("programSourcePath"));
		formPd.put("destinationPath", pd.getString("programDestinationPath"));
		formPd.put("createTime", DateUtil.now()); //创建时间
		formPd.put("status", "1"); // 1：等待leader审批，2：等待manager审批，3：完结，0：拒绝
		try {
			filetransferService.save(formPd);
			
			filetransferFTNonService.save(pd);
			mv.addObject("msg","success");
		} catch (Exception e) {
			mv.addObject("errer","errer");
			mv.addObject("msgContent","申请单错误请联系管理员!");
		}								
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Myleave");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		filetransferService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Myleave");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		filetransferService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String cuString = pd.getString("CUST");
		pd.put("isReadProgram", "1");
		//Request ID
		pd.put("requestID", IdUtil.objectId());
		//username
		pd.put("USERNAME", Jurisdiction.getU_name()); 
		
		if ("ft".equals(cuString)) {
			//title
			pd.put("title","FT-Memory_"+DateUtil.format(   DateUtil.date(System.currentTimeMillis()),"yyyyMMddHHmmss"));
			mv.setViewName("fhoa/filetransfer/ft-memory/FT_list");
		}else if ("ft-non".equals(cuString)) {
			//title
			pd.put("title","FT-Non-Memory_"+DateUtil.format(   DateUtil.date(System.currentTimeMillis()),"yyyyMMddHHmmss"));
			mv.setViewName("fhoa/filetransfer/ft-non-memory/FT-Non_list");
		}
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = filetransferService.findById(pd);	//根据ID读取
		mv.setViewName("fhoa/folder/myleave_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**判断路径是否合法
	 * @return
	 */
	@RequestMapping(value="/checkData")
	@ResponseBody
	public Object checkData(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			Path path=Paths.get(pd.getString("programSourcePath"));
			boolean exists = Files.exists(path);

			if( !exists){
				errInfo = "error";
			}
		} catch(Exception e){
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Myleave");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			filetransferService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Myleave到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户名");	//1
		titles.add("类型");	//2
		titles.add("开始时间");	//3
		titles.add("结束时间");	//4
		titles.add("时长");	//5
		titles.add("事由");	//6
		titles.add("审批意见");	//7
		dataMap.put("titles", titles);
		List<PageData> varOList = filetransferService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("USERNAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("TYPE"));	        //2
			vpd.put("var3", varOList.get(i).getString("STARTTIME"));	//3
			vpd.put("var4", varOList.get(i).getString("ENDTIME"));	    //4
			vpd.put("var5", varOList.get(i).getString("WHENLONG"));	    //5
			vpd.put("var6", varOList.get(i).getString("REASON"));	    //6
			vpd.put("var7", varOList.get(i).getString("OPINION"));	    //7
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}

	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list/cp")
	public ModelAndView list3(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表FileTransfer");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("USERNAME", "admin".equals(Jurisdiction.getUsername())?"":Jurisdiction.getUsername()); //除admin用户外，只能查看自己的数据
		page.setPd(pd);
		List<PageData>	varList = filetransferService.list(page);	//列出列表
		mv.setViewName("fhoa/filetransfer/filetransfer_CP_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());				//按钮权限
		return mv;
	}
	
}


