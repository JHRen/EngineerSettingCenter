package com.amkor.service.activiti.procdef.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.amkor.service.activiti.procdef.ProcdefManager;
import com.amkor.dao.DaoSupport;
import com.amkor.entity.Page;
import com.amkor.util.PageData;

/** 
 * 说明： 流程管理
 * 创建人：FH Q313596790
 * 创建时间：2018-01-06
 * @version
 */
@Service("procdefService")
public class ProcdefService implements ProcdefManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProcdefMapper.datalistPage", page);
	}
	
}

