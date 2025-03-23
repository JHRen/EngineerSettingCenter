package com.amkor.service.ep.mcncheck.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport2;
import com.amkor.entity.Page;
import com.amkor.service.ep.mcncheck.McncheckManager;
import com.amkor.util.PageData;

@Service("McncheckTSMCService")
public class McncheckTSMCService implements McncheckManager {
	
	@Resource(name = "daoSupport2")
	private DaoSupport2 dao2;

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao2.findForList("McncheckTSMCMapper.datalistPage", page);
	}

	public void delete(PageData pd) throws Exception {
			dao2.delete("McncheckTSMCMapper.delete", pd);
	}

	public void save(PageData pd) throws Exception {
		dao2.save("McncheckTSMCMapper.save", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao2.findForObject("McncheckTSMCMapper.findById", pd);
	}

	public void edit(PageData pd) throws Exception{
		dao2.update("McncheckTSMCMapper.edit", pd);
		
	}

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao2.findForObject("McncheckTSMCMapper.finBySimilar", pd);
	}


}
