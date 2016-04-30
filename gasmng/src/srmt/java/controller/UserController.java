package srmt.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import srmt.java.service.UserSevrvice;

@Controller
@RequestMapping("/userMng")
public class UserController {

	@Autowired
	private UserSevrvice userSevrvice;

	@RequestMapping("/enterUserMng.do")
	public ModelAndView enterUserMng() {

		return new ModelAndView("userMng/userMng");
	}

	/** 
	 * @method 查询用户列表
	 * @author Instant
	 * @time 2016年4月30日 下午4:01:06
	 */
	@ResponseBody
	@RequestMapping("/queryUserList.do")
	public List<Map> queryUserList(HttpServletRequest request) {
		List<Map> list = userSevrvice.queryUserList(request);
		return list;
	}


	/** 
	 * @method 删除用户
	 * @author Instant
	 * @time 2016年4月30日 下午4:01:27
	 */
	@ResponseBody
	@RequestMapping("/delUser.do")
	public Map delUser(HttpServletRequest request) {
		userSevrvice.delUser(request);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	/** 
	 * @method 保存用户
	 * @author Instant
	 * @time 2016年4月30日 下午4:01:35
	 */
	@ResponseBody
	@RequestMapping("/saveUser.do")
	public Map saveUser(HttpServletRequest request) {
		Map map = userSevrvice.saveUser(request);
		return map;
	}

	/** 
	 * @method 获取当前用户的个人信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:10:13
	 */
	@ResponseBody
	@RequestMapping("/getUserInfo.do")
	public Map getUserInfo(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		return userSevrvice.getUserInfo(userId);
	}

	/** 
	 * @method 获取用户信息，用于修改界面加载数据
	 * @author Instant
	 * @time 2016年4月30日 下午4:01:53
	 */
	@ResponseBody
	@RequestMapping("/getMyUserInfo.do")
	public Map getMyUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		return userSevrvice.getUserInfo(userId);
	}

	/** 
	 * @method 取消注销用户
	 * @author Instant
	 * @time 2016年4月30日 下午4:02:23
	 */
	@ResponseBody
	@RequestMapping("/unLayoutUser.do")
	public Map unLayoutUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		userSevrvice.unLayoutUser(userId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	/** 
	 * @method 注销用户
	 * @author Instant
	 * @time 2016年4月30日 下午4:02:39
	 */
	@ResponseBody
	@RequestMapping("/layoutUser.do")
	public Map layoutUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		userSevrvice.layoutUser(userId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	/** 
	 * @method 进入我的个人信息界面
	 * @author Instant
	 * @time 2016年4月30日 下午4:08:12
	 */
	@RequestMapping("/enterMyUser.do")
	public ModelAndView enterMyUser() {

		return new ModelAndView("userMng/myUserInfo");
	}

	/** 
	 * @method 修改密码
	 * @author Instant
	 * @time 2016年4月30日 下午4:03:24
	 */
	@ResponseBody
	@RequestMapping("/savePassword.do")
	public Map savePassword(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String newPassword = request.getParameter("newPassword");
		String password = request.getParameter("oldPassword");
		Map map = userSevrvice.savePassword(password, userId, newPassword);
		return map;
	}

	/** 
	 * @method 设置为系统管理员
	 * @author Instant
	 * @time 2016年4月30日 下午4:03:35
	 */
	@ResponseBody
	@RequestMapping("/isXTAdmin.do")
	public Map isXTAdmin(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		userSevrvice.isXTAdmin(userId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	/** 
	 * @method 设置为普通管理员
	 * @author Instant
	 * @time 2016年4月30日 下午4:04:01
	 */
	@ResponseBody
	@RequestMapping("/isAdmin.do")
	public Map isAdmin(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		userSevrvice.isAdmin(userId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	/** 
	 * @method 设置为普通用户
	 * @author Instant
	 * @time 2016年4月30日 下午4:04:17
	 */
	@ResponseBody
	@RequestMapping("/isNormalUser.do")
	public Map isNotXTAdmin(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		userSevrvice.isNormalUser(userId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	/** 
	 * @method 查询用户账户余额
	 * @author Instant
	 * @time 2016年4月30日 下午4:05:23
	 */
	@ResponseBody
	@RequestMapping("/getMyMoney.do")
	public Map getMyMoney(HttpServletRequest request) {
		Map map = userSevrvice.getMyMoney(request);
		return map;
	}

	/** 
	 * @method 用户充值
	 * @author Instant
	 * @time 2016年4月30日 下午4:05:46
	 */
	@ResponseBody
	@RequestMapping("/reMoney.do")
	public Map reMoney(HttpServletRequest request) {
		double money = userSevrvice.reMoney(request);
		Map map = new HashMap();
		map.put("money", money);
		return map;
	}
}
