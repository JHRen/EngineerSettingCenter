package com.amkor.service.fhoa.filetransfer.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport;
import com.amkor.entity.Page;
import com.amkor.service.fhoa.filetransfer.FiletransferFTNonManager;
import com.amkor.util.PageData;

/** 
 * 说明： 文件转移 FT-Non
 * @version
 */
@Service("filetransferFTNonService")
public class FiletransferFTNonService implements FiletransferFTNonManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("FileTransferFTNonMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("FileTransferFTNonMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("FileTransferFTNonMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FileTransferFTNonMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FileTransferFTNonMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FileTransferFTNonMapper.findById", pd);
	}
	
	/**通过title获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByTitle(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FileTransferFTNonMapper.findByTitle", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("FileTransferFTNonMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

