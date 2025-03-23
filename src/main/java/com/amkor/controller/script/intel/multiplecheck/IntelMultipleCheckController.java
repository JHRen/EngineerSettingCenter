package com.amkor.controller.script.intel.multiplecheck;

import java.io.PrintWriter;
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
import com.amkor.entity.system.User;
import com.amkor.service.script.intel.multiple.MultipleManager;
import com.amkor.service.system.fhlog.FHlogManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Const;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.UuidUtil;

@Controller
@RequestMapping(value = "intel")
public class IntelMultipleCheckController extends BaseController {
	String menuUrl = "intel/multiple.do"; // 菜单地址(权限用)
	@Resource(name = "fhlogService")
	private FHlogManager FHLOG;
	
	@Resource(name="multipleService")
    private MultipleManager multipleService;
	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/multiple/list")
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
		List<PageData> dataList = multipleService.list(page);
		
		mv.addObject("varList", dataList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限

		mv.setViewName("scriptdb/intel/multiple_config");

		return mv;
	}

	@RequestMapping(value="/multiple/delete")
	public void delete(PrintWriter out) throws Exception{
		
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd = multipleService.findById(pd);	
		multipleService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**
	 * 去新增界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/multiple/add")
	public ModelAndView goAdd()throws Exception{	
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("scriptdb/intel/multiple_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**保存修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/multiple/save")
	public ModelAndView save()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		multipleService.save(pd); 
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}	
	
	/**
	 * 去修改界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/multiple/goEdit")
	public ModelAndView goEdit()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = multipleService.findById(pd);	
		mv.setViewName("scriptdb/intel/multiple_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/multiple/edit")
	public ModelAndView edit()throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"  update gf");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		multipleService.edit(pd);	//执行修改
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**判断数据是否重复
	 * @return
	 */
	@RequestMapping(value="/multiple/hasData")
	@ResponseBody
	public Object hasU(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			int count=multipleService.findBySimilar(pd);
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
