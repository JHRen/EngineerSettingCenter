package com.amkor.controller.script.cypress;

import java.io.PrintWriter;
import java.math.BigDecimal;
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
import com.amkor.service.script.cypress.CypressManager;
import com.amkor.service.system.fhlog.FHlogManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Const;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.UuidUtil;
//import com.fh.util.datasource.DataSourceContextHolder;
import com.amkor.util.mail.Sendmail;

import cn.hutool.core.util.NumberUtil;

@Controller
@RequestMapping(value = "cypress")
public class CypressController extends BaseController {
	String menuUrl = "cypress/list.do"; // 菜单地址(权限用)
	@Resource(name = "fhlogService")
	private FHlogManager FHLOG;
	@Resource(name = "McncheckGFService")
	private McncheckManager mcncheckGFService;

	@Resource(name = "ApprovalService")
	private ApprovalManager approvalService;
	
	@Resource(name = "cypressService")
	private CypressManager cypressService;

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
		String LAST_LOGINTIME_END = pd.getString("LAST_LOGINTIME_END");// 获取查询的结束时间
		if (null != LAST_LOGINTIME_END && !"".equals(LAST_LOGINTIME_END)) {
			pd.put("LAST_LOGINTIME_END", LAST_LOGINTIME_END);
		}
		page.setPd(pd);

		// 数据视图
		List<PageData> dataList = cypressService.list(page);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<dataList.size();i++) {
			PageData vpd = new PageData();
			vpd.put("CUSTOMER_LOTNO", dataList.get(i).getString("CUSTOMER_LOTNO"));
			vpd.put("DEVICE_NAME", dataList.get(i).getString("DEVICE_NAME"));
			vpd.put("OPERATION_CODE", dataList.get(i).getString("OPERATION_CODE"));
			vpd.put("QTY_IN", dataList.get(i).get("UNIT"));
			vpd.put("BIN1", dataList.get(i).get("PASS"));
			vpd.put("BIN5", dataList.get(i).get("CAT21"));
			vpd.put("BIN6", dataList.get(i).get("CAT38"));
			vpd.put("BIN5_BIN6_BIN7", dataList.get(i).get("BIN7"));
			
			int bin7= Integer.parseInt(dataList.get(i).get("BIN7").toString());
			int cat21 =Integer.parseInt(dataList.get(i).get("CAT21").toString()); 
			int cat38 =Integer.parseInt(dataList.get(i).get("CAT38").toString()); 
			
			
			BigDecimal result=NumberUtil.sub(bin7,cat21,cat38);
			vpd.put("BIN7", result);
			vpd.put("BIN8", dataList.get(i).get("BIN8"));
			varList.add(vpd);
		}
		
		mv.addObject("varList", varList);

		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限

		mv.setViewName("scriptdb/cypress/cypress_list");

		return mv;
	}

	@RequestMapping(value = "/gf/delete")
	public void delete(PrintWriter out) throws Exception {

		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		// 添加操作日志-删除记录
		logBefore(logger, Jurisdiction.getUsername() + "DELETE GF");

		PageData pd = new PageData();
		pd = this.getPageData();

		pd = mcncheckGFService.findById(pd);
		// 开启审核 删除保留
		mcncheckGFService.delete(pd);
		// 添加进审批表
		approvalService.save(Jurisdiction.getUsername(), "删除", JSON.toJSONString(pd), "MCN_GF",
				Jurisdiction.getRoleParentID());

		// 发送邮件
		Sendmail.sendToManager(Jurisdiction.getUsername(), "删除", pd, "MCN_GF_A");
		out.write("success");
		out.close();
	}

	/**
	 * 去新增界面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gf/add")
	public ModelAndView goAdd() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("mcncheck/gf/gf_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 保存修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/gf/save")
	public ModelAndView save() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		// 添加操作日志-增加记录
		logBefore(logger, Jurisdiction.getUsername() + "add GF data");
		String cxlogid = UuidUtil.get32UUID();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		// String hostName=Jurisdiction.getHostName();

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 增加审批功能 此处先注释
		// mcncheckGFService.save(pd);
		// 添加进审批表
		approvalService.save(Jurisdiction.getUsername(), "新增", JSON.toJSONString(pd), "MCN_GF",
				Jurisdiction.getRoleParentID());

		// 发送邮件
		Sendmail.sendToManager(Jurisdiction.getUsername(), "新增", pd, "MCN_GF_A");

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 去修改界面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gf/goEdit")
	public ModelAndView goEdit() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gf/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "  update GF");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		// 添加操作日志-修改记录
		String cxlogid = UuidUtil.get32UUID();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		// String hostName=Jurisdiction.getHostName();

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// mcncheckGFService.edit(pd); //执行修改
		mcncheckGFService.delete(pd);
		// 添加进审批表
		PageData pdd = new PageData();
		// pdd.put("ID", pd.get("ID"));
		// pdd.put("MCN_Name", pd.get("MCN_Name_check"));
		// pdd.put("GF_Part", pd.get("GF_Part_check"));
		// pdd.put("TEST_PGM1", pd.get("TEST_PGM1_check"));
		// pdd.put("JOB_NAME", pd.get("JOB_NAME_check"));
		approvalService.save(Jurisdiction.getUsername(), "修改", JSON.toJSONString(pd), "MCN_GF",
				Jurisdiction.getRoleParentID());

		// 发送邮件
		Sendmail.sendToManager(Jurisdiction.getUsername(), "修改", pd, "MCN_GF_A");

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 审批通过
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/gf/agree")
	public void agree(PrintWriter out) throws Exception {

		if (!Jurisdiction.buttonJurisdiction(menuUrl, "apr")) {
			return;
		} // 校验权限
		// 添加操作日志-审批记录
		logBefore(logger, Jurisdiction.getUsername() + "同意审批");

		PageData pd = new PageData();
		pd = this.getPageData();

		pd = approvalService.findById(pd);
		JSONObject jsonObject = JSONObject.parseObject((String) pd.get("TABLE_FIELD_ALL"));

		PageData pdd = new PageData();
		pdd.put("ID", pd.get("ID"));
		pdd.put("MCN_Name", jsonObject.get("MCN_Name"));
		pdd.put("GF_Part", jsonObject.get("GF_Part"));
		pdd.put("TEST_PGM1", jsonObject.get("TEST_PGM1"));
		pdd.put("JOB_NAME", jsonObject.get("JOB_NAME"));
		if ("新增".equals(pd.get("STATUS"))) {
			pd.put("newZT", "1");
			mcncheckGFService.save(pdd); // 将新增mcn信息保存进去
			approvalService.edit(pd); // 修改状态为审核同意
			// 发送邮件
			Sendmail.sendToManager(Jurisdiction.getUsername(), "新增", pdd, "MCN_GF_T");
		} else if ("删除".equals(pd.get("STATUS"))) {
			pd.put("newZT", "1");
			approvalService.edit(pd); // 修改状态为审核同意
			// 发送邮件
			Sendmail.sendToManager(Jurisdiction.getUsername(), "删除", pdd, "MCN_GF_T");
		} else if ("修改".equals(pd.get("STATUS"))) {
			pd.put("newZT", "1");
			approvalService.edit(pd); // 修改状态为审核同意
			mcncheckGFService.save(pdd); // 修改的信息同意
			// 发送邮件
			Sendmail.sendToManager(Jurisdiction.getUsername(), "修改", pdd, "MCN_GF_T");
		}

		out.write("success");
		out.close();
	}

	/**
	 * 审批不通过
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/gf/deny")
	public void deny(PrintWriter out) throws Exception {

		if (!Jurisdiction.buttonJurisdiction(menuUrl, "apr")) {
			return;
		} // 校验权限
		// 添加操作日志
		logBefore(logger, Jurisdiction.getUsername() + "不同意审批");

		PageData pd = new PageData();
		pd = this.getPageData();
		pd = approvalService.findById(pd);
		JSONObject jsonObject = JSONObject.parseObject((String) pd.get("TABLE_FIELD_ALL"));

		PageData pdd = new PageData();
		pdd.put("ID", pd.get("ID"));
		pdd.put("MCN_Name", jsonObject.get("MCN_Name"));
		pdd.put("GF_Part", jsonObject.get("GF_Part"));
		pdd.put("TEST_PGM1", jsonObject.get("TEST_PGM1"));
		pdd.put("JOB_NAME", jsonObject.get("JOB_NAME"));

		if ("新增".equals(pd.get("STATUS"))) {
			pd.put("newZT", "2");
			approvalService.edit(pd);
			// 发送邮件
			Sendmail.sendToManager(Jurisdiction.getUsername(), "新增", pdd, "MCN_GF_F");
		} else if ("删除".equals(pd.get("STATUS"))) {
			pd.put("newZT", "2");
			approvalService.edit(pd); // 修改状态为审核不同意
			mcncheckGFService.save(pdd); // 删除的信息还原
			// 发送邮件
			Sendmail.sendToManager(Jurisdiction.getUsername(), "删除", pdd, "MCN_GF_F");
		} else if ("修改".equals(pd.get("STATUS"))) {
			PageData pdb = new PageData();
			pdb.put("ID", pd.get("ID"));
			pdb.put("MCN_Name", jsonObject.get("MCN_Name_check"));
			pdb.put("GF_Part", jsonObject.get("GF_Part_check"));
			pdb.put("TEST_PGM1", jsonObject.get("TEST_PGM1_check"));
			pdb.put("JOB_NAME", jsonObject.get("JOB_NAME_check"));

			pd.put("newZT", "2");
			approvalService.edit(pd); // 修改状态为审核同意
			mcncheckGFService.save(pdb); // 修改的信息还原
			// 发送邮件
			Sendmail.sendToManager(Jurisdiction.getUsername(), "修改", pdd, "MCN_GF_F");
		}

		out.write("success");
		out.close();
	}

	/**
	 * 判断数据是否重复
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gf/hasData")
	@ResponseBody
	public Object hasU() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			int count = mcncheckGFService.findBySimilar(pd);
			if (count > 0) {
				errInfo = "error";
			}
		} catch (Exception e) {
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

}
