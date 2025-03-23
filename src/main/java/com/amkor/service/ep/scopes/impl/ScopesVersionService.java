package com.amkor.service.ep.scopes.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport2;
import com.amkor.entity.Page;
import com.amkor.service.ep.qts.QtsqcaManager;
import com.amkor.service.ep.scopes.ScopesVersionManager;
import com.amkor.util.PageData;

@Service("scopsVersionService")
public class ScopesVersionService implements ScopesVersionManager {
	
	@Resource(name = "daoSupport2")
	private DaoSupport2 dao;

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("scopsVersionMapper.datalistPage", page);
	}

	public void delete(PageData pd) throws Exception {
			dao.delete("scopsVersionMapper.delete", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("scopsVersionMapper.save", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("scopsVersionMapper.findById", pd);
	}

	public void edit(PageData pd) throws Exception{
		dao.update("scopsVersionMapper.edit", pd);
		
	}
	
	public List<PageData> getVersion(PageData pd) throws Exception{
	return (List<PageData>) dao.findForList("scopsVersionMapper.getVersion", pd);
		
	}

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao.findForObject("scopsVersionMapper.finBySimilar", pd);
	}



}
