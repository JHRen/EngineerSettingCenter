package com.amkor.service.ep.otversion.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport;
import com.amkor.entity.Page;
import com.amkor.service.ep.otversion.OTversionManager;
import com.amkor.util.PageData;

@Service("otversionService")
public class OTversionService implements OTversionManager {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("OTversionMapper.datalistPage", page);
	}

	public void delete(PageData pd) throws Exception {
			dao.delete("OTversionMapper.delete", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("OTversionMapper.save", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OTversionMapper.findById", pd);
	}

	public void edit(PageData pd) throws Exception{
		dao.update("OTversionMapper.edit", pd);
		
	}

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao.findForObject("OTversionMapper.finBySimilar", pd);
	}


}
