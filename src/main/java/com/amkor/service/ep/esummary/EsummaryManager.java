package com.amkor.service.ep.esummary;

import java.util.List;

import com.amkor.entity.Page;
import com.amkor.util.PageData;

public interface EsummaryManager {

	/**
	 * 查询bin信息总表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listEsumLotMir(PageData pd) throws Exception;
	
	/**
	 * 查询 hard bin
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listHbin(PageData pd) throws Exception;

	/**
	 * 查询 soft bin
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listSbin(PageData pd) throws Exception;
	
	/**
	 * 查询 ftlotno
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findFtlotnoByCustlotno(PageData pd) throws Exception;

	/**
	 * 查询 TESTCODE
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findTestcodeByCustlotno(PageData pd) throws Exception;
	
	/**
	 * 查询 RETESTCODE
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findReTestcodeByCustlotno(PageData pd) throws Exception;
	
	/**
	 * 查询FILENAME
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findFilenameByCustlotno(PageData pd) throws Exception;
	
	/**
	 * excel 导出  bin信息总表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listEsumLotMirForExcel(PageData pd) throws Exception;
	/**
	 * excel 导出  hard bin
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listHbinForExcel(PageData pd) throws Exception;

	/**
	 * excel 导出  soft bin
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listSbinForExcel(PageData pd) throws Exception;
}
