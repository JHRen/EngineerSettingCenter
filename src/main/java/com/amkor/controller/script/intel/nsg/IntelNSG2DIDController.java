package com.amkor.controller.script.intel.nsg;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.service.script.intel.nsg.NSG2DIDManager;
import com.amkor.service.system.fhlog.FHlogManager;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.Tools;
import com.amkor.util.mail.SendMailToAll;

@Controller
@RequestMapping(value = "intel")
public class IntelNSG2DIDController extends BaseController {
	String menuUrl = "intel/nsg2did.do"; // 菜单地址(权限用)
	@Resource(name = "fhlogService")
	private FHlogManager FHLOG;
	
	@Resource(name="nsg2didService")
    private NSG2DIDManager nsg2didService;
	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/nsg2did/list")
	public ModelAndView list(Page page) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		// 校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String TEST_STEP=pd.getString("TEST_STEP");
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (Tools.notEmpty(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		if (Tools.notEmpty(TEST_STEP)) {
			pd.put("TEST_STEP", TEST_STEP.trim());
		}
		
		page.setPd(pd);
		// 数据视图
	
		List<PageData> dataList= null ;
		if (Tools.notEmpty(keywords)&&Tools.notEmpty(TEST_STEP)) {
			dataList= nsg2didService.list(page);
		}
		
		
		mv.addObject("varList", dataList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限

		mv.setViewName("scriptdb/nsg2did/nsg2did_list");

		return mv;
	}

	@RequestMapping(value="/nsg2did/delete")
	public void delete(PrintWriter out) throws Exception{
		
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		String LOTNO=pd.getString("LOTNO");
		String TEST_STEP=pd.getString("TEST_STEP");
		if (Tools.isEmpty(LOTNO)||Tools.isEmpty(TEST_STEP)) {
			out.write("error");
			out.close();
		}else {
			nsg2didService.delete(pd);
			// 发送邮件

			String title ="Engineer Setting Center";
			String from = "SHCN-TEST-IT@amkor.com";// 发件人
			//List<String> list = Jurisdiction.getRecipient(); // 获取发送邮件的人
			 String sendTo[] = {"SHCN-TEST-INTEL@amkor.com"};
			
			String mailbody="NSG_2DID信息,删除成功,:<br>LOTNO:"+LOTNO+"  TEST_STEP："+TEST_STEP;
			SendMailToAll.sendmail(title, from, sendTo, mailbody, null, "text/html;charset=UTF-8");
		
			out.write("success");
			out.close();
		}
	}
	


}
