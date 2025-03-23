package com.amkor.service.ep.equipment.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport;
import com.amkor.entity.Page;
import com.amkor.service.ep.equipment.EquipmentManager;
import com.amkor.util.PageData;

@Service("equipmentService")
public class EquipmentService implements EquipmentManager {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("EquipmentMapper.datalistPage", page);
	}

	public void delete(PageData pd) throws Exception {
			dao.delete("EquipmentMapper.delete", pd);
	}

	public void save(PageData pd) throws Exception {
		dao.save("EquipmentMapper.save", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("EquipmentMapper.findById", pd);
	}

	public void edit(PageData pd) throws Exception{
		dao.update("EquipmentMapper.edit", pd);
		
	}

	@Override
	public Integer findBySimilar(PageData pd) throws Exception {
		return  (Integer) dao.findForObject("EquipmentMapper.finBySimilar", pd);
	}

	public void editEmail(PageData pd) throws Exception{
		dao.update("EquipmentMapper.editEmail", pd);
		
	}


	@SuppressWarnings("unchecked")
	public List<PageData> sendEmail(String curtime) throws Exception {
		return (List<PageData>)dao.findForList("EquipmentMapper.listOfEmail", curtime);
	}
	


}
