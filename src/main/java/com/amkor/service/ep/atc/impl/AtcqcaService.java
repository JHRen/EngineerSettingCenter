package com.amkor.service.ep.atc.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport2;
import com.amkor.entity.Page;
import com.amkor.service.ep.atc.AtcqcaManager;
import com.amkor.util.PageData;

@Service("atcqcaService")
public class AtcqcaService implements AtcqcaManager {
	
	@Resource(name = "daoSupport2")
	private DaoSupport2 dao2;
	
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao2.findForList("AtcqcaMapper.datalistPage", page);
	}

	public void delete(PageData pd) throws Exception {
			dao2.delete("AtcqcaMapper.delete", pd);
	}

	public void save(PageData pd) throws Exception {
		dao2.save("AtcqcaMapper.save", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao2.findForObject("AtcqcaMapper.findById", pd);
	}

	@Override
	public void editQCA(PageData pd) throws Exception {
		dao2.update("AtcqcaMapper.edit", pd);
		
	}

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao2.findForObject("AtcqcaMapper.finBySimilar", pd);
	}



	
	




}
