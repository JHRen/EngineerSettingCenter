package com.amkor.service.ep.esummary.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport2;
import com.amkor.dao.DaoSupport5;
import com.amkor.entity.Page;
import com.amkor.service.ep.esummary.EsummaryManager;
import com.amkor.service.ep.mcncheck.McncheckManager;
import com.amkor.util.PageData;

@Service("esummaryService")
public class EsummaryService implements EsummaryManager {

	@Resource(name = "daoSupport5")
	private DaoSupport5 dao;

	@SuppressWarnings("unchecked")
	public List<PageData> listEsumLotMir(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("EsummaryMapper.listEsumLotMir", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listHbin(PageData pd) throws Exception {
		if(pd==null) {
			return null;
		}
		return (List<PageData>) dao.findForList("EsummaryMapper.listHardBin", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listSbin(PageData pd) throws Exception {
		if (pd==null) {
			return null;
		}
		return (List<PageData>) dao.findForList("EsummaryMapper.listSoftBin", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> findFtlotnoByCustlotno(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("EsummaryMapper.findFtlotnoByCustlotno", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> findTestcodeByCustlotno(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("EsummaryMapper.findTestcodeByCustlotno", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> findReTestcodeByCustlotno(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("EsummaryMapper.findReTestcodeByCustlotno", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> findFilenameByCustlotno(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("EsummaryMapper.findFilenameByCustlotno", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listEsumLotMirForExcel(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("EsummaryMapper.listEsumLotMirForExcel", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listHbinForExcel(PageData pd) throws Exception {
		if(pd==null) {
			return null;
		}
		return (List<PageData>) dao.findForList("EsummaryMapper.listHardBinForExcel", pd);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listSbinForExcel(PageData pd) throws Exception {
		if (pd==null) {
			return null;
		}
		return (List<PageData>) dao.findForList("EsummaryMapper.listSoftBinForExcel", pd);
	}
	
	
}
