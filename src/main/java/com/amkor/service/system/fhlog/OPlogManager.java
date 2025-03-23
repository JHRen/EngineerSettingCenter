package com.amkor.service.system.fhlog;

import java.util.List;

import com.amkor.entity.Page;
import com.amkor.util.PageData;

/** 
 * 说明： 操作日志记录
 * @version
 */
public interface OPlogManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(String USERNAME,String OPERATION,String MENU, String CONTENT)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

