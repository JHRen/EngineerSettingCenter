package com.amkor.service.ep.atc.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport2;
import com.amkor.entity.Page;
import com.amkor.service.ep.atc.AtccpManager;
import com.amkor.util.PageData;

@Service("atccpService")
public class AtccpService implements AtccpManager {
	
	@Resource(name = "daoSupport2")
	private DaoSupport2 dao2;

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao2.findForList("AtccpMapper.datalistPage", page);
	}

	public void delete(PageData pd) throws Exception {
			dao2.delete("AtccpMapper.delete", pd);
	}

	public void save(PageData pd) throws Exception {
		dao2.save("AtccpMapper.save", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao2.findForObject("AtccpMapper.findById", pd);
	}

	public void editCP(PageData pd) throws Exception{
		dao2.update("AtccpMapper.edit", pd);
		
	}

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao2.findForObject("AtccpMapper.finBySimilar", pd);
	}


}
