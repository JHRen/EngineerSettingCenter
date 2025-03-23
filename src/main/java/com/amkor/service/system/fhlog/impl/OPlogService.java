package com.amkor.service.system.fhlog.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport;
import com.amkor.entity.Page;
import com.amkor.service.system.fhlog.OPlogManager;
import com.amkor.util.PageData;
import com.amkor.util.Tools;
import com.amkor.util.UuidUtil;

/**
 * 说明： 操作日志记录
 * 
 * @version
 */
@Service("oplogService")
public class OPlogService implements OPlogManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(String USERNAME, String OPERATION, String MENU, String CONTENT) throws Exception {
		PageData pd = new PageData();
		pd.put("USERNAME", USERNAME); // 用户名
		pd.put("OPERATION", OPERATION);
		pd.put("MENU", MENU); // 菜单
		pd.put("CONTENT", CONTENT); // 事件
		pd.put("ID", UuidUtil.get32UUID()); // 主键
		pd.put("CZTIME", Tools.date2Str(new Date())); // 操作时间
		dao.save("OPlogMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("OPlogMapper.delete", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OPlogMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OPlogMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OPlogMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("OPlogMapper.deleteAll", ArrayDATA_IDS);
	}

}
