package com.amkor.service.ep.approval.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport;
import com.amkor.entity.Page;
import com.amkor.service.ep.approval.ApprovalManager;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.Tools;


/** 系统用户
 * @author ren
 */
@Service("ApprovalService")
public class ApprovalService implements ApprovalManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ApprovalMapper.datalistPage", page);
	}

	public void save(String UserName,String Status,Object pdd,String TableName,String roleParentID) throws Exception {
		PageData pd = new PageData();
		pd.put("USERNAME", UserName);				//用户名
		pd.put("STATUS",Status);					//操作状态（CURD）
		pd.put("CZTIME",Tools.date2Str(new Date()));//操作时间	
		
		pd.put("TABLE_FIELD_ALL",pdd.toString());				//需要审批的表的字段
		pd.put("TABLE_NAME",TableName);				//需要审批的表名
		pd.put("ZT", "0");						//状态  0 未审批  1审批通过 2审批不通过
		pd.put("GROUP_ID", roleParentID);		//用户组
		dao.save("ApprovalMapper.save", pd);
	}
	
	public void delete(PageData pd) throws Exception {
			dao.delete("ApprovalMapper.delete", pd);
	}


	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ApprovalMapper.findById", pd);
	}

	public void edit(PageData pd) throws Exception{
		pd.put("ApproverName", Jurisdiction.getUsername());
		pd.put("ApproveTime", Tools.date2Str(new Date()));
		dao.update("ApprovalMapper.edit", pd);
		
	}

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao.findForObject("McncheckGFMapper.finBySimilar", pd);
	}
}
