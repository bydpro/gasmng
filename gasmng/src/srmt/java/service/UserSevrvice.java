package srmt.java.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srmt.java.dao.UserDao;

@Service
public class UserSevrvice {
	
	@Autowired
	private UserDao userDao;
	
	/** 
	 * @method 查询用户列表
	 * @author Instant
	 * @time 2016年4月30日 下午4:01:06
	 */
	public List<Map> queryUserList(HttpServletRequest request){
		List<Map> list = userDao.queryUserList(request);
		return list;
	}
	

	/** 
	 * @method 删除用户
	 * @author Instant
	 * @time 2016年4月30日 下午4:01:27
	 */
	public void delUser(HttpServletRequest request){
		userDao.delUser(request);
	}
	
	/** 
	 * @method 保存用户
	 * @author Instant
	 * @time 2016年4月30日 下午4:01:35
	 */
	public Map saveUser(HttpServletRequest request){
		return userDao.saveUser(request);
	}
	
	/** 
	 * @method 获取用户信息，用于修改界面加载数据
	 * @author Instant
	 * @time 2016年4月30日 下午4:01:53
	 */
	public Map getUserInfo(String userId){
		return userDao.getUserInfo(userId);
	}
	
	/** 
	 * @method 注销用户
	 * @author Instant
	 * @time 2016年4月30日 下午4:02:39
	 */
	public void layoutUser(String userId) {
		userDao.layoutUser(userId);
	}

	/** 
	 * @method 取消注销用户
	 * @author Instant
	 * @time 2016年4月30日 下午4:02:23
	 */
	public void unLayoutUser(String userId) {
		userDao.unLayoutUser(userId);
	}
	
	/** 
	 * @method 修改密码
	 * @author Instant
	 * @time 2016年4月30日 下午4:03:24
	 */
	public Map savePassword(String password,String userId ,String newPassword) {
		return userDao.savePassword(password,userId,newPassword);
	}
	
	/** 
	 * @method 设置为系统管理员
	 * @author Instant
	 * @time 2016年4月30日 下午4:03:35
	 */
	public void isXTAdmin(String userId) {
		userDao.isXTAdmin(userId);
	}

	/** 
	 * @method 设置为普通管理员
	 * @author Instant
	 * @time 2016年4月30日 下午4:04:01
	 */
	public void isAdmin(String userId) {
		userDao.isAdmin(userId);
	}
	
	/** 
	 * @method 设置为普通用户
	 * @author Instant
	 * @time 2016年4月30日 下午4:04:17
	 */
	public void isNormalUser(String userId) {
		userDao.isNormalUser(userId);
	}
	
	/** 
	 * @method 查询用户账户余额
	 * @author Instant
	 * @time 2016年4月30日 下午4:05:23
	 */
	public Map getMyMoney(HttpServletRequest request) {
		return userDao.getMyMoney(request);
	}
	
	/** 
	 * @method 用户充值
	 * @author Instant
	 * @time 2016年4月30日 下午4:05:46
	 */
	public double reMoney(HttpServletRequest request) {
		return userDao.reMoney(request);
	}
}