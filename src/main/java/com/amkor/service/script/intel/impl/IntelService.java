package com.amkor.service.script.intel.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport5;
import com.amkor.entity.Page;
import com.amkor.service.script.intel.IntelManager;
import com.amkor.util.PageData;

/** 

 * @version
 */
@Service("intelService")
public class IntelService implements IntelManager{

	@Resource(name = "daoSupport5")
	private DaoSupport5 dao;
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("IntelMapper.datalistPage", page);
	}
	
	public void delete(PageData pd) throws Exception {
		dao.delete("IntelMapper.delete", pd);
}
}

