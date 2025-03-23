package com.amkor.service.ep.oiversion.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport6;
import com.amkor.entity.Page;
import com.amkor.service.ep.oiversion.OIVersionManager;
import com.amkor.util.PageData;

@Service("OIVersionService")
public class OIVersionService implements OIVersionManager {
	
	@Resource(name = "daoSupport6")
	private DaoSupport6 dao;

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("teradyneOIMapper.datalistPage", page);
	}

	public void delete(PageData pd) throws Exception {
			dao.delete("teradyneOIMapper.delete", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("teradyneOIMapper.save", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("teradyneOIMapper.findById", pd);
	}

	public void edit(PageData pd) throws Exception{
		dao.update("teradyneOIMapper.edit", pd);
		
	}
	

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao.findForObject("teradyneOIMapper.finBySimilar", pd);
	}



}
