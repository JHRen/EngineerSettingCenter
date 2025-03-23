package com.amkor.controller.system.login;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amkor.controller.base.BaseController;
import com.amkor.entity.system.Menu;
import com.amkor.entity.system.Role;
import com.amkor.entity.system.User;
import com.amkor.service.fhoa.datajur.DatajurManager;
import com.amkor.service.system.appuser.AppuserManager;
import com.amkor.service.system.buttonrights.ButtonrightsManager;
import com.amkor.service.system.fhbutton.FhbuttonManager;
import com.amkor.service.system.fhlog.FHlogManager;
import com.amkor.service.system.loginimg.LogInImgManager;
import com.amkor.service.system.menu.MenuManager;
import com.amkor.service.system.role.RoleManager;
import com.amkor.service.system.user.UserManager;
import com.amkor.util.AppUtil;
import com.amkor.util.Const;
import com.amkor.util.DateUtil;
import com.amkor.util.Jurisdiction;
import com.amkor.util.PageData;
import com.amkor.util.RightsHelper;
import com.amkor.util.Tools;

/**
 * 总入口
 * @author fh QQ 3 1 3 5 9 6 7 9 0[青苔]
 * 修改日期：2015/11/2
 */
/**
 * @author Administrator
 *
 */
@Controller
public class LoginController extends BaseController {

	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "menuService")
	private MenuManager menuService;
	@Resource(name = "roleService")
	private RoleManager roleService;
	@Resource(name = "buttonrightsService")
	private ButtonrightsManager buttonrightsService;
	@Resource(name = "fhbuttonService")
	private FhbuttonManager fhbuttonService;
	@Resource(name = "appuserService")
	private AppuserManager appuserService;
	@Resource(name = "datajurService")
	private DatajurManager datajurService;
	@Resource(name = "fhlogService")
	private FHlogManager FHLOG;
	@Resource(name = "loginimgService")
	private LogInImgManager loginimgService;

	/**
	 * 访问登录页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login_toLogin")
	public ModelAndView toLogin() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = this.setLoginPd(pd); // 设置登录页面的配置参数
		mv.setViewName("system/index/login");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * AD域请求登录，验证用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login_login", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object login() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "";
		String KEYDATA[] = pd.getString("KEYDATA").replaceAll("qq313596790fh", "").replaceAll("QQ978336446fh", "")
				.split(",fh,");
		if (null != KEYDATA && KEYDATA.length == 3) {
			// ===========ad域登陆开始=======================
			Session session = Jurisdiction.getSession();
			String sessionCode = (String) session.getAttribute(Const.SESSION_SECURITY_CODE); // 获取session中的验证码
			String code = KEYDATA[2];
			String USERNAME = KEYDATA[0]; // 登录过来的用户名
			String PASSWORD = KEYDATA[1]; // 登录过来的密码
			pd.put("USERNAME", USERNAME);

			// AD域登陆方式验证
			boolean adCheck = AdCheck(USERNAME, PASSWORD);
			if (adCheck == true) {
				try {
					pd = userService.getUserByName(pd); // 根据用户名去读取用户信息
					if (pd != null) {
						pd.put("LAST_LOGIN", DateUtil.getTime().toString());
					    userService.updateLastLogin(pd);
						User user = new User();
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						// user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));
						user.setROLE_ID(pd.getString("ROLE_ID"));
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						user.setDEPARTMENT_ID(pd.getString("DEPARTMENT_ID"));
						session.setAttribute(Const.SESSION_USER, user); // 把用户信息放session中

						// shiro加入身份验证
						Subject subject = SecurityUtils.getSubject();
						UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
						try {
							subject.login(token);
						} catch (AuthenticationException e) {
							errInfo = "身份验证失败！";
						}
					} else {
						errInfo = "quanxianerror"; // 没有权限
					}
				} catch (Exception e) {
					errInfo = "身份验证失败！";
				}
			} else {
				errInfo = "usererror"; // 用户名或密码有误
				logBefore(logger, USERNAME + ":username or password error!");
			}
			if (Tools.isEmpty(errInfo)) {
				errInfo = "success"; // 验证成功
				logBefore(logger, USERNAME + "登录系统");
				FHLOG.save(USERNAME, "登录系统");
			}

		} else {
			errInfo = "error"; // 缺少参数
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 账户密码请求登录，验证用户
	 * 
	 * @return
	 * @throws Exception
	 */
	/*
	 * @RequestMapping(value="/login_login"
	 * ,produces="application/json;charset=UTF-8")
	 * 
	 * @ResponseBody public Object login()throws Exception{ Map<String,String> map =
	 * new HashMap<String,String>(); PageData pd = new PageData(); pd =
	 * this.getPageData(); String errInfo = ""; String KEYDATA[] =
	 * pd.getString("KEYDATA").replaceAll("qq313596790fh",
	 * "").replaceAll("QQ978336446fh", "").split(",fh,"); if(null != KEYDATA &&
	 * KEYDATA.length == 3){ Session session = Jurisdiction.getSession(); String
	 * sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);
	 * //获取session中的验证码 String code = KEYDATA[2]; if(null == code ||
	 * "".equals(code)){//判断效验码 errInfo = "nullcode"; //效验码为空 }else{ String USERNAME
	 * = KEYDATA[0]; //登录过来的用户名 String PASSWORD = KEYDATA[1]; //登录过来的密码
	 * pd.put("USERNAME", USERNAME); if(Tools.notEmpty(sessionCode) &&
	 * sessionCode.equalsIgnoreCase(code)){ //判断登录验证码 String passwd = new
	 * SimpleHash("SHA-1", USERNAME, PASSWORD).toString(); //密码加密 pd.put("PASSWORD",
	 * passwd); pd = userService.getUserByNameAndPwd(pd); //根据用户名和密码去读取用户信息 if(pd !=
	 * null){ this.removeSession(USERNAME);//请缓存
	 * pd.put("LAST_LOGIN",DateUtil.getTime().toString());
	 * userService.updateLastLogin(pd); User user = new User();
	 * user.setUSER_ID(pd.getString("USER_ID"));
	 * user.setUSERNAME(pd.getString("USERNAME"));
	 * user.setPASSWORD(pd.getString("PASSWORD"));
	 * user.setNAME(pd.getString("NAME")); user.setRIGHTS(pd.getString("RIGHTS"));
	 * user.setROLE_ID(pd.getString("ROLE_ID"));
	 * user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
	 * user.setIP(pd.getString("IP")); user.setSTATUS(pd.getString("STATUS"));
	 * session.setAttribute(Const.SESSION_USER, user); //把用户信息放session中
	 * session.removeAttribute(Const.SESSION_SECURITY_CODE); //清除登录验证码的session
	 * //shiro加入身份验证 Subject subject = SecurityUtils.getSubject();
	 * UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
	 * try { subject.login(token); } catch (AuthenticationException e) { errInfo =
	 * "身份验证失败！"; } }else{ errInfo = "usererror"; //用户名或密码有误 logBefore(logger,
	 * USERNAME+"登录系统密码或用户名错误"); FHLOG.save(USERNAME, "登录系统密码或用户名错误"); } }else{
	 * errInfo = "codeerror"; //验证码输入有误 } if(Tools.isEmpty(errInfo)){ errInfo =
	 * "success"; //验证成功 logBefore(logger, USERNAME+"登录系统"); FHLOG.save(USERNAME,
	 * "登录系统"); } } }else{ errInfo = "error"; //缺少参数 } map.put("result", errInfo);
	 * return AppUtil.returnObject(new PageData(), map); }
	 */

	/**
	 * 访问系统首页
	 * 
	 * @param changeMenu：切换菜单参数
	 * @return
	 */
	@RequestMapping(value = "/main/{changeMenu}")
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Session session = Jurisdiction.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER); // 读取session中的用户信息(单独用户信息)
			if (user != null) {
				User userr = (User) session.getAttribute(Const.SESSION_USERROL); // 读取session中的用户信息(含角色信息)
				if (null == userr) {
					user = userService.getUserAndRoleById(user.getUSER_ID()); // 通过用户ID读取用户信息和角色信息
					session.setAttribute(Const.SESSION_USERROL, user); // 存入session
				} else {
					user = userr;
				}
				String USERNAME = user.getUSERNAME();
				Role role = user.getRole(); // 获取用户角色
				String roleRights = role != null ? role.getRIGHTS() : ""; // 角色权限(菜单权限)
				String ROLE_IDS = user.getROLE_IDS();
				String roleParent = role != null ? role.getPARENT_ID() : ""; // 角色权限的父组
				String DEPARTMENT_ID = user.getDEPARTMENT_ID();
				
				session.setAttribute(Const.SESSION_ROLE_PARENTID, roleParent);
				session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); // 将角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, USERNAME); // 放入用户名到session
				session.setAttribute(Const.SESSION_U_NAME, user.getNAME()); // 放入用户姓名到session
				//this.setAttributeToAllDEPARTMENT_ID(session, USERNAME); // 把用户的组织机构权限放到session里面
				session.setAttribute(Const.DEPARTMENT_ID, DEPARTMENT_ID); //把用户的部门IP放到session里面
				session.setAttribute(USERNAME + Const.SESSION_FILE, "true"); //文件第一次打开的开关
				
				// 邮件发送列表
				List<String> allEmaliRecipient = userService.listEmail(roleParent);// 列出此组下admin邮箱
				// allEmaliRecipient.add("SHCN-TEST-IT@amkor.com");
				session.setAttribute(Const.EMAILRECIPIENT, allEmaliRecipient);
				List<Menu> allmenuList = new ArrayList<Menu>();
				allmenuList = this.getAttributeMenu(session, USERNAME, roleRights, getArrayRoleRights(ROLE_IDS)); // 菜单缓存
				List<Menu> menuList = new ArrayList<Menu>();
				menuList = this.changeMenuF(allmenuList, session, USERNAME, changeMenu); // 切换菜单
				if (null == session.getAttribute(USERNAME + Const.SESSION_QX)) {
					session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME)); // 主职角色按钮权限放到session中
					session.setAttribute(USERNAME + Const.SESSION_QX2, this.getUQX2(USERNAME)); // 副职角色按钮权限放到session中
				}
				this.getRemortIP(USERNAME); // 更新登录IP
				mv.setViewName("system/index/main");
				mv.addObject("user", user);
				mv.addObject("SKIN",
						null == session.getAttribute(Const.SKIN) ? user.getSKIN() : session.getAttribute(Const.SKIN)); // 用户皮肤
				mv.addObject("menuList", menuList);
			} else {
				mv.setViewName("system/index/login");// session失效后跳转登录页面
			}
		} catch (Exception e) {
			mv.setViewName("system/index/login");
			logger.error(e.getMessage(), e);
		}
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 获取副职角色权限List
	 * 
	 * @param ROLE_IDS
	 * @return
	 * @throws Exception
	 */
	public List<String> getArrayRoleRights(String ROLE_IDS) throws Exception {
		if (Tools.notEmpty(ROLE_IDS)) {
			List<String> list = new ArrayList<String>();
			String arryROLE_ID[] = ROLE_IDS.split(",fh,");
			for (int i = 0; i < arryROLE_ID.length; i++) {
				PageData pd = new PageData();
				pd.put("ROLE_ID", arryROLE_ID[i]);
				pd = roleService.findObjectById(pd);
				if (null != pd) {
					String RIGHTS = pd.getString("RIGHTS");
					if (Tools.notEmpty(RIGHTS)) {
						list.add(RIGHTS);
					}
				}
			}
			return list.size() == 0 ? null : list;
		} else {
			return null;
		}
	}

	/**
	 * 菜单缓存
	 * 
	 * @param session
	 * @param USERNAME
	 * @param roleRights
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getAttributeMenu(Session session, String USERNAME, String roleRights,
			List<String> arrayRoleRights) throws Exception {
		List<Menu> allmenuList = new ArrayList<Menu>();
		if (null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)) {
			allmenuList = menuService.listAllMenuQx("0"); // 获取所有菜单
			if (Tools.notEmpty(roleRights)) {
				allmenuList = this.readMenu(allmenuList, roleRights, arrayRoleRights); // 根据角色权限获取本权限的菜单列表
			}
			session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);// 菜单权限放入session中
		} else {
			allmenuList = (List<Menu>) session.getAttribute(USERNAME + Const.SESSION_allmenuList);
		}
		return allmenuList;
	}

	/**
	 * 根据角色权限获取本权限的菜单列表(递归处理)
	 * 
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList, String roleRights, List<String> arrayRoleRights) {
		for (int i = 0; i < menuList.size(); i++) {
			Boolean b1 = RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID());
			menuList.get(i).setHasMenu(b1); // 赋予主职角色菜单权限
			if (!b1 && null != arrayRoleRights) {
				for (int n = 0; n < arrayRoleRights.size(); n++) {
					if (RightsHelper.testRights(arrayRoleRights.get(n), menuList.get(i).getMENU_ID())) {
						menuList.get(i).setHasMenu(true);
						break;
					}
				}
			}
			if (menuList.get(i).isHasMenu()) { // 判断是否有此菜单权限
				this.readMenu(menuList.get(i).getSubMenu(), roleRights, arrayRoleRights);// 是：继续排查其子菜单
			}
		}
		return menuList;
	}

	/**
	 * 切换菜单处理
	 * 
	 * @param allmenuList
	 * @param session
	 * @param USERNAME
	 * @param changeMenu
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> changeMenuF(List<Menu> allmenuList, Session session, String USERNAME, String changeMenu) {
		List<Menu> menuList = new ArrayList<Menu>();
		/** 菜单缓存为空 或者 传入的菜单类型和当前不一样的时候，条件成立，重新拆分菜单，把选择的菜单类型放入缓存 */
		if (null == session.getAttribute(USERNAME + Const.SESSION_menuList)
				|| (!changeMenu.equals(session.getAttribute("changeMenu")))) {
			List<Menu> menuList1 = new ArrayList<Menu>();
			List<Menu> menuList2 = new ArrayList<Menu>();
			List<Menu> menuList3 = new ArrayList<Menu>();
			List<Menu> menuList4 = new ArrayList<Menu>();
			for (int i = 0; i < allmenuList.size(); i++) {// 拆分菜单
				Menu menu = allmenuList.get(i);
				if ("1".equals(menu.getMENU_TYPE())) {
					menuList1.add(menu); // 系统菜单
				} else if ("2".equals(menu.getMENU_TYPE())) {
					menuList2.add(menu); // 业务菜单
				} else if ("3".equals(menu.getMENU_TYPE())) {
					menuList3.add(menu); // 菜单类型三
				} else if ("4".equals(menu.getMENU_TYPE())) {
					menuList4.add(menu); // 菜单类型四
				}
			}
			session.removeAttribute(USERNAME + Const.SESSION_menuList);
			if ("index".equals(changeMenu)) {
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList2);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "index");
				menuList = menuList2;
			} else if ("2".equals(changeMenu)) {
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList1);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "2");
				menuList = menuList1;
			} else if ("3".equals(changeMenu)) {
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList3);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "3");
				menuList = menuList3;
			} else if ("4".equals(changeMenu)) {
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList4);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "4");
				menuList = menuList4;
			}
		} else {
			menuList = (List<Menu>) session.getAttribute(USERNAME + Const.SESSION_menuList);
		}
		return menuList;
	}

	/**
	 * 把用户的组织机构权限放到session里面
	 * 
	 * @param session
	 * @param USERNAME
	 * @return
	 * @throws Exception
	 */
	public void setAttributeToAllDEPARTMENT_ID(Session session, String USERNAME) throws Exception {
		String DEPARTMENT_IDS = "0", DEPARTMENT_ID = "0";
		if (!"40607".equals(USERNAME)) {
			PageData pd = datajurService.getDEPARTMENT_IDS(USERNAME);
			DEPARTMENT_IDS = null == pd ? "无权" : pd.getString("DEPARTMENT_IDS");
			DEPARTMENT_ID = null == pd ? "无权" : pd.getString("DEPARTMENT_ID");
		}
		session.setAttribute(Const.DEPARTMENT_IDS, DEPARTMENT_IDS); // 把用户的组织机构权限集合放到session里面
		session.setAttribute(Const.DEPARTMENT_ID, DEPARTMENT_ID); // 把用户的最高组织机构权限放到session里面
	}

	/**
	 * 进入tab标签
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tab")
	public String tab() {
		return "system/index/tab";
	}

	/**
	 * 进入首页后的默认页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login_default")
	public ModelAndView defaultPage() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("userCount", Integer.parseInt(userService.getUserCount("").get("userCount").toString()) - 1); // 系统用户数
		pd.put("appUserCount", Integer.parseInt(appuserService.getAppUserCount("").get("appUserCount").toString())); // 会员数
		mv.addObject("pd", pd);
		mv.setViewName("system/index/default");

		// 获取当前用户基本信息
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER); // 读取session中的用户信息(单独用户信息)
		User userr = (User) session.getAttribute(Const.SESSION_USERROL); // 读取session中的用户信息(含角色信息)
		mv.addObject("user", user);
		mv.addObject("userr", userr);

		// Map<String, String> map=Jurisdiction.getHC();

		return mv;
	}

	/**
	 * 用户注销
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout() throws Exception {
		String USERNAME = Jurisdiction.getUsername(); // 当前登录的用户名
		logBefore(logger, USERNAME + "退出系统");
		FHLOG.save(USERNAME, "退出");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		this.removeSession(USERNAME);// 请缓存
		// shiro销毁登录
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		pd = this.getPageData();
		pd.put("msg", pd.getString("msg"));
		pd = this.setLoginPd(pd); // 设置登录页面的配置参数
		mv.setViewName("system/index/login");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 清理session
	 */
	public void removeSession(String USERNAME) {
		Session session = Jurisdiction.getSession(); // 以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(USERNAME + Const.SESSION_QX2);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_U_NAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute(Const.SESSION_RNUMBERS);
		session.removeAttribute(Const.SESSION_ROLE_PARENTID);
		session.removeAttribute(Const.EMAILRECIPIENT);
		session.removeAttribute(USERNAME +Const.SESSION_FILE);
		session.removeAttribute("changeMenu");
		session.removeAttribute("DEPARTMENT_IDS");
		session.removeAttribute("DEPARTMENT_ID");
	}

	/**
	 * 设置登录页面的配置参数
	 * 
	 * @param pd
	 * @return
	 */
	public PageData setLoginPd(PageData pd) {
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		String strLOGINEDIT = Tools.readTxtFile(Const.LOGINEDIT); // 读取登录页面配置
		if (null != strLOGINEDIT && !"".equals(strLOGINEDIT)) {
			String strLo[] = strLOGINEDIT.split(",fh,");
			if (strLo.length == 2) {
				pd.put("isZhuce", strLo[0]);
				pd.put("isMusic", strLo[1]);
			}
		}
		try {
			List<PageData> listImg = loginimgService.listAll(pd); // 登录背景图片
			pd.put("listImg", listImg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	/**
	 * 获取用户权限
	 * 
	 * @param session
	 * @return
	 */
	public Map<String, String> getUQX(String USERNAME) {
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);

			PageData userpd = new PageData();
			userpd = userService.findByUsername(pd); // 通过用户名获取用户信息
			String ROLE_ID = userpd.get("ROLE_ID").toString();
			String ROLE_IDS = userpd.getString("ROLE_IDS");
			pd.put("ROLE_ID", ROLE_ID); // 获取角色ID
			pd = roleService.findObjectById(pd); // 获取角色信息
			map.put("adds", pd.getString("ADD_QX")); // 增
			map.put("dels", pd.getString("DEL_QX")); // 删
			map.put("edits", pd.getString("EDIT_QX")); // 改
			map.put("chas", pd.getString("CHA_QX")); // 查
			map.put("aprs", pd.getString("APR_QX")); // 审
			List<PageData> buttonQXnamelist = new ArrayList<PageData>();
			if ("40607".equals(USERNAME)) {
				buttonQXnamelist = fhbuttonService.listAll(pd); // 40607用户拥有所有按钮权限
			} else {
				if (Tools.notEmpty(ROLE_IDS)) {// (主副职角色综合按钮权限)
					ROLE_IDS = ROLE_IDS + ROLE_ID;
					String arryROLE_ID[] = ROLE_IDS.split(",fh,");
					buttonQXnamelist = buttonrightsService.listAllBrAndQxnameByZF(arryROLE_ID);
				} else { // (主职角色按钮权限)
					buttonQXnamelist = buttonrightsService.listAllBrAndQxname(pd); // 此角色拥有的按钮权限标识列表
				}
			}
			for (int i = 0; i < buttonQXnamelist.size(); i++) {
				map.put(buttonQXnamelist.get(i).getString("QX_NAME"), "1"); // 按钮权限
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return map;
	}

	/**
	 * 获取用户权限(处理副职角色)
	 * 
	 * @param session
	 * @return
	 */
	public Map<String, List<String>> getUQX2(String USERNAME) {
		PageData pd = new PageData();
		Map<String, List<String>> maps = new HashMap<String, List<String>>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			PageData userpd = new PageData();
			userpd = userService.findByUsername(pd); // 通过用户名获取用户信息
			String ROLE_IDS = userpd.getString("ROLE_IDS");
			if (Tools.notEmpty(ROLE_IDS)) {
				String arryROLE_ID[] = ROLE_IDS.split(",fh,");
				PageData rolePd = new PageData();
				List<String> addsList = new ArrayList<String>();
				List<String> delsList = new ArrayList<String>();
				List<String> editsList = new ArrayList<String>();
				List<String> chasList = new ArrayList<String>();
				List<String> aprsList = new ArrayList<String>();
				for (int i = 0; i < arryROLE_ID.length; i++) {
					rolePd.put("ROLE_ID", arryROLE_ID[i]);
					rolePd = roleService.findObjectById(rolePd);
					addsList.add(rolePd.getString("ADD_QX"));
					delsList.add(rolePd.getString("DEL_QX"));
					editsList.add(rolePd.getString("EDIT_QX"));
					chasList.add(rolePd.getString("CHA_QX"));
					aprsList.add(rolePd.getString("APR_QX"));
				}
				maps.put("addsList", addsList); // 增
				maps.put("delsList", delsList); // 删
				maps.put("editsList", editsList); // 改
				maps.put("chasList", chasList); // 查
				maps.put("aprsList", aprsList); // 审
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return maps;
	}

	/**
	 * 更新登录用户的IP
	 * 
	 * @param USERNAME
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}

	/**
	 * Ad域登陆验证
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static boolean AdCheck(String userName, String password) {
		String url = new String(
				"LDAP://10.86.71.25:389/OU=Office Users,OU=People,OU=C1-C3,DC=cn,DC=ds,DC=amkor,DC=com");
		String user = "CN=" + userName + ",OU=Office Users,OU=People,OU=C1-C3,DC=cn,DC=ds,DC=amkor,DC=com";
		Hashtable env = new Hashtable();// 实例化一个Env
		DirContext ctx = null;
		env.put(Context.SECURITY_AUTHENTICATION, "simple");// LDAP访问安全级别(none,simple,strong),一种模式，这么写就行
		env.put(Context.SECURITY_PRINCIPAL+"@amkor.com", user); // 用户名
		env.put(Context.SECURITY_CREDENTIALS, password);// 密码
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");// LDAP工厂类
		env.put(Context.PROVIDER_URL, url);// Url
		try {
			ctx = new InitialDirContext(env);// 初始化上下文
			return true;
		} catch (AuthenticationException e) {
			System.out.println("身份验证失败!");
			return false;
		} catch (javax.naming.CommunicationException e) {
			System.out.println("AD域连接失败!");
			return false;
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println("身份验证未知异常!");
			return false;
		} finally {
			if (null != ctx) {
				try {
					ctx.close();
					ctx = null;
				} catch (Exception e) {

					return false;
				}
			}
		}
	}

	@RequestMapping(value = "notice")
	public String Notice() {

		return "sucess";
	}

}
