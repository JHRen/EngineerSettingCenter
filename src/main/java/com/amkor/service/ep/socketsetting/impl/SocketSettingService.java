package com.amkor.service.ep.socketsetting.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport4;
import com.amkor.entity.Page;
import com.amkor.service.ep.socketsetting.SocketSettingManager;
import com.amkor.util.PageData;

@Service("SocketSettingService")
public class SocketSettingService implements SocketSettingManager {
	
	@Resource(name = "daoSupport4")
	private DaoSupport4 dao;

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("SocketTDLimitMapper.datalistPage", page);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("SocketTDLimitMapper.delete", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("SocketTDLimitMapper.save", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("SocketTDLimitMapper.findById", pd);
	}

	/**列表(全部)
	 * @param 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("SocketTDLimitMapper.listAll", pd);
	}
	
	/**
	 * 修改
	 */
	public void edit(PageData pd) throws Exception{
		dao.update("SocketTDLimitMapper.edit", pd);
	}

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao.findForObject("SocketTDLimitMapper.finBySimilar", pd);
	}


}
