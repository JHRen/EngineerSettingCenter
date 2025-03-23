package com.amkor.service.sorter.sortprogram.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport;
import com.amkor.dao.DaoSupport5;
import com.amkor.entity.Page;
import com.amkor.service.script.intel.IntelManager;
import com.amkor.service.sorter.sortprogram.SortProgramManager;
import com.amkor.util.PageData;

/** 

 * @version
 */
@Service("sortProgramService")
public class SortProgramService implements SortProgramManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SortProgramMapper.datalistPage", page);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SortProgramMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SortProgramMapper.delete", pd);
	}
	
	/**修改状态
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SortProgramMapper.edit", pd);
	}
	
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SortProgramMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SortProgramMapper.findById", pd);
	}
	
	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao.findForObject("SortProgramMapper.finBySimilar", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SortProgramMapper.deleteAll", ArrayDATA_IDS);
	}
}

