package com.amkor.service.script.intel;

import java.util.List;

import com.amkor.entity.Page;
import com.amkor.util.PageData;

/** 

 * @version
 */
public interface IntelManager{

	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	public void delete(PageData pd)throws Exception;
	
}

