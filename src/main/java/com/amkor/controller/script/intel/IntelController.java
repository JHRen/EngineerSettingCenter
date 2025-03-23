package com.amkor.controller.script.intel;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.amkor.controller.base.BaseController;
import com.amkor.entity.Page;
import com.amkor.service.script.intel.IntelManager;
import com.amkor.service.system.fhlog.FHlogManager;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.mail.SendMailToAll;
import com.amkor.util.mail.Sendmail;

@Controller
@RequestMapping(value = "intel")
public class IntelController extends BaseController {
	String menuUrl = "intel/list.do"; // 菜单地址(权限用)
	@Resource(name = "fhlogService")
	private FHlogManager FHLOG;

	@Resource(name = "intelService")
	private IntelManager intelService;

	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
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
		String opcode = pd.getString("operationCode");// 获取operation code
		if (null != opcode && !"".equals(opcode)) {
			pd.put("operationCode", opcode);
		}

		page.setPd(pd);

		// 数据视图
		List<PageData> dataList = intelService.list(page);

		mv.addObject("varList", dataList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限

		mv.setViewName("scriptdb/intel/intel_list");

		return mv;
	}

	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {

		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
			// 添加操作日志-删除记录
		logBefore(logger, Jurisdiction.getUsername() + "DELETE sbl");

		PageData pd = new PageData();
		pd = this.getPageData();
		String LOTNO=pd.getString("LOTNO");
		String OPERATION_CODE=pd.getString("OPERATION_CODE");
		if (LOTNO==""&&OPERATION_CODE=="") {
			out.write("error");
			out.close();
		}else {
			intelService.delete(pd);
			// 发送邮件

			String title ="Engineer Setting Center";
			String from = "SHCN-TEST-IT@amkor.com";// 发件人
			List<String> list = Jurisdiction.getRecipient(); // 获取发送邮件的人
			 String sendTo[] =(String[])list.toArray(new String[0]);
			
			String mailbody="删除成功,SBL信息:<br>LOTNO:"+LOTNO+"  OPERATION_CODE："+OPERATION_CODE;
			SendMailToAll.sendmail(title, from, sendTo, mailbody, null, "text/html;charset=UTF-8");
		
			out.write("success");
			out.close();
		}
		
	
	}

}
