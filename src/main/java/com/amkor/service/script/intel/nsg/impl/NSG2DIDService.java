package com.amkor.service.script.intel.nsg.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport;
import com.amkor.dao.DaoSupport5;
import com.amkor.entity.Page;
import com.amkor.service.script.intel.nsg.NSG2DIDManager;
import com.amkor.util.PageData;

/** 

 * @version
 */
@Service("nsg2didService")
public class NSG2DIDService implements NSG2DIDManager{

	@Resource(name = "daoSupport5")
	private DaoSupport5 dao;
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("NSG2DIDMapper.datalistPage", page);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("NSG2DIDMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("NSG2DIDMapper.delete", pd);
	}
	
	/**修改状态
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("NSG2DIDMapper.edit", pd);
	}
	
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NSG2DIDMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("NSG2DIDMapper.findById", pd);
	}
	
	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao.findForObject("NSG2DIDMapper.finBySimilar", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("NSG2DIDMapper.deleteAll", ArrayDATA_IDS);
	}
}

