package com.amkor.service.fhoa.otherwise.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport;
import com.amkor.entity.Page;
import com.amkor.service.fhoa.otherwise.FhfileOhterwiseManager;
import com.amkor.util.PageData;

/** 
 * 说明： 查看历史版本
 * @version
 */
@Service("fhfileOhterwiseService")
public class FhfileOhterwiseService implements FhfileOhterwiseManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("FhfileOhterwiseMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("FhfileOhterwiseMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("FhfileOhterwiseMapper.edit", pd);
	}
	
	/**修改状态
	 * @param pd
	 * @throws Exception
	 */
	public void editStatus(PageData pd)throws Exception{
		dao.update("FhfileOhterwiseMapper.editStatus", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FhfileOhterwiseMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FhfileOhterwiseMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FhfileOhterwiseMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("FhfileOhterwiseMapper.deleteAll", ArrayDATA_IDS);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listById(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  (List<PageData>) dao.findForList("FhfileOhterwiseMapper.listById", pd);
	}

	/**
	 * 通过状态获取数据 
	 */
	@SuppressWarnings("unchecked")
	public  List<PageData> listByStatus(PageData pd) throws Exception {
		return  (List<PageData>) dao.findForList("FhfileOhterwiseMapper.listByStatus", pd);
	}
	
}

