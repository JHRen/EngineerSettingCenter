package com.amkor.service.fhoa.fhfile.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport;
import com.amkor.entity.Page;
import com.amkor.service.fhoa.fhfile.FhfileManager;
import com.amkor.util.PageData;

/** 
 * 说明： Assembly文件管理
 * @version
 */
@Service("assemblyfileService")
public class AssemblyfileService implements FhfileManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AssemblyfileService.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("AssemblyfileService.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AssemblyfileService.edit", pd);
	}
	
	/**修改状态
	 * @param pd
	 * @throws Exception
	 */
	public void editStatus(PageData pd)throws Exception{
		dao.update("AssemblyfileService.editStatus", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AssemblyfileService.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AssemblyfileService.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AssemblyfileService.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("AssemblyfileService.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> listById(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 通过状态获取数据 
	 */
	@SuppressWarnings("unchecked")
	public  List<PageData> listByStatus(PageData pd) throws Exception {
		return  (List<PageData>) dao.findForList("AssemblyfileService.listByStatus", pd);
	}
	
}

