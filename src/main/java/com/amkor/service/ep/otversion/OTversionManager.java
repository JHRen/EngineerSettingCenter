package com.amkor.service.ep.otversion;

import java.util.List;

import com.amkor.entity.Page;
import com.amkor.util.PageData;

public interface OTversionManager {
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;

	/**
	 * 删除
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * 保存
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 通过ID查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 修改
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;
	
	/**
	 * 查询有无重复主键
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Integer findBySimilar(PageData pd)throws Exception; 
	
}
