package com.amkor.service.ep.probecard.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport4;
import com.amkor.entity.Page;
import com.amkor.service.ep.probecard.ProbeCardManager;
import com.amkor.util.PageData;

@Service("ProbeCardTSMCService")
public class ProbeCardTSMCService implements ProbeCardManager {
	
	@Resource(name = "daoSupport4")
	private DaoSupport4 dao;

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ProbeCardTSMCMapper.datalistPage", page);
	}

	public void delete(PageData pd) throws Exception {
			dao.delete("ProbeCardTSMCMapper.delete", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("ProbeCardTSMCMapper.save", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ProbeCardTSMCMapper.findById", pd);
	}
	
	/**列表(全部)
	 * @param 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllPc(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("ProbeCardTSMCMapper.listAllPc", pd);
	}

	public void edit(PageData pd) throws Exception{
		dao.update("ProbeCardTSMCMapper.edit", pd);
		
	}

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao.findForObject("ProbeCardTSMCMapper.finBySimilar", pd);
	}


}
