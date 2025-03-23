package com.amkor.service.ep.qts.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport3;
import com.amkor.entity.Page;
import com.amkor.service.ep.qts.QtscpManager;
import com.amkor.util.PageData;

@Service("qtscpService")
public class QtscpService implements QtscpManager {
	
	@Resource(name = "daoSupport3")
	private DaoSupport3 dao3;

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao3.findForList("QtscpMapper.datalistPage", page);
	}

	public void delete(PageData pd) throws Exception {
			dao3.delete("QtscpMapper.delete", pd);
	}

	public void save(PageData pd) throws Exception {
		dao3.save("QtscpMapper.save", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao3.findForObject("QtscpMapper.findById", pd);
	}

	public void editCP(PageData pd) throws Exception{
		dao3.update("QtscpMapper.edit", pd);
		
	}

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao3.findForObject("QtscpMapper.finBySimilar", pd);
	}


}
