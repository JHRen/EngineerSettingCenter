package com.amkor.service.system.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.amkor.dao.DaoSupport;
import com.amkor.entity.Page;
import com.amkor.entity.system.User;
import com.amkor.service.system.user.UserManager;
import com.amkor.util.PageData;


/** 系统用户
 * @author ren
 */
@Service("userService")
public class UserService implements UserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**登录判断
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByNameAndPwd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.getUserInfo", pd);
	}
	
	/**根据用户名登录判断，作用AD域
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.getUserInfoByName", pd);
	}
	
	/**更新登录时间
	 * @param pd
	 * @throws Exception
	 */
	public void updateLastLogin(PageData pd)throws Exception{
		dao.update("UserMapper.updateLastLogin", pd);
	}
	
	/**保存用户皮肤
	 * @param pd
	 * @throws Exception
	 */
	public void saveSkin(PageData pd)throws Exception{
		dao.update("UserMapper.saveSkin", pd);
	}
	
	/**通过用户ID获取用户信息和角色信息
	 * @param USER_ID
	 * @return
	 * @throws Exception
	 */
	public User getUserAndRoleById(String USER_ID) throws Exception {
		return (User) dao.findForObject("UserMapper.getUserAndRoleById", USER_ID);
	}
	
	/**通过USERNAEME获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.findByUsername", pd);
	}
	
	/**列出某角色下的所有用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUserByRoldId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.listAllUserByRoldId", pd);
	}
	
	/**保存用户IP
	 * @param pd
	 * @throws Exception
	 */
	public void saveIP(PageData pd)throws Exception{
		dao.update("UserMapper.saveIP", pd);
	}
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listUsers(Page page)throws Exception{
		return (List<PageData>) dao.findForList("UserMapper.userlistPage", page);
	}
	
	/**用户列表(弹窗选择用)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listUsersBystaff(Page page)throws Exception{
		return (List<PageData>) dao.findForList("UserMapper.userBystafflistPage", page);
	}
	
	/**通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUE(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.findByUE", pd);
	}
	
	/**通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUN(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.findByUN", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserMapper.findById", pd);
	}
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("UserMapper.saveU", pd);
	}
	 
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception{
		dao.update("UserMapper.editU", pd);
	}
	
	/**删除用户
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception{
		dao.delete("UserMapper.deleteU", pd);
	}
	
	/**批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS)throws Exception{
		dao.delete("UserMapper.deleteAllU", USER_IDS);
	}
	
	/**用户列表(全部)
	 * @param USER_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUser(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("UserMapper.listAllUser", pd);
	}
	
	/**获取总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData getUserCount(String value)throws Exception{
		return (PageData)dao.findForObject("UserMapper.getUserCount", value);
	}
	
	/**邮箱列表
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> listEmail(String roleParenID) throws Exception {
		// TODO Auto-generated method stub
		return (List<String>) dao.findForList("UserMapper.listEmail", roleParenID);
	}

	/**
	 * 获取Admin 列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAdmin(String DEPARTMENT_ID) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("UserMapper.listAdmin", DEPARTMENT_ID);
	}
}
