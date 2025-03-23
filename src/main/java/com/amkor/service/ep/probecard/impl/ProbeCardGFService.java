package com.amkor.service.ep.probecard.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport4;
import com.amkor.entity.Page;
import com.amkor.service.ep.probecard.ProbeCardManager;
import com.amkor.util.PageData;

@Service("ProbeCardGFService")
public class ProbeCardGFService implements ProbeCardManager {
	
	@Resource(name = "daoSupport4")
	private DaoSupport4 dao;

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ProbeCardGFMapper.datalistPage", page);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("ProbeCardGFMapper.delete", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("ProbeCardGFMapper.save", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ProbeCardGFMapper.findById", pd);
	}

	/**列表(全部)
	 * @param 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllPc(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("ProbeCardGFMapper.listAllPc", pd);
	}
	
	/**
	 * 修改
	 */
	public void edit(PageData pd) throws Exception{
		dao.update("ProbeCardGFMapper.edit", pd);
	}

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao.findForObject("ProbeCardGFMapper.finBySimilar", pd);
	}


}
