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
	 * @method ��ѯ�û��б�
	 * @author Instant
	 * @time 2016��4��30�� ����4:01:06
	 */
	public List<Map> queryUserList(HttpServletRequest request){
		List<Map> list = userDao.queryUserList(request);
		return list;
	}
	

	/** 
	 * @method ɾ���û�
	 * @author Instant
	 * @time 2016��4��30�� ����4:01:27
	 */
	public void delUser(HttpServletRequest request){
		userDao.delUser(request);
	}
	
	/** 
	 * @method �����û�
	 * @author Instant
	 * @time 2016��4��30�� ����4:01:35
	 */
	public Map saveUser(HttpServletRequest request){
		return userDao.saveUser(request);
	}
	
	/** 
	 * @method ��ȡ�û���Ϣ�������޸Ľ����������
	 * @author Instant
	 * @time 2016��4��30�� ����4:01:53
	 */
	public Map getUserInfo(String userId){
		return userDao.getUserInfo(userId);
	}
	
	/** 
	 * @method ע���û�
	 * @author Instant
	 * @time 2016��4��30�� ����4:02:39
	 */
	public void layoutUser(String userId) {
		userDao.layoutUser(userId);
	}

	/** 
	 * @method ȡ��ע���û�
	 * @author Instant
	 * @time 2016��4��30�� ����4:02:23
	 */
	public void unLayoutUser(String userId) {
		userDao.unLayoutUser(userId);
	}
	
	/** 
	 * @method �޸�����
	 * @author Instant
	 * @time 2016��4��30�� ����4:03:24
	 */
	public Map savePassword(String password,String userId ,String newPassword) {
		return userDao.savePassword(password,userId,newPassword);
	}
	
	/** 
	 * @method ����Ϊϵͳ����Ա
	 * @author Instant
	 * @time 2016��4��30�� ����4:03:35
	 */
	public void isXTAdmin(String userId) {
		userDao.isXTAdmin(userId);
	}

	/** 
	 * @method ����Ϊ��ͨ����Ա
	 * @author Instant
	 * @time 2016��4��30�� ����4:04:01
	 */
	public void isAdmin(String userId) {
		userDao.isAdmin(userId);
	}
	
	/** 
	 * @method ����Ϊ��ͨ�û�
	 * @author Instant
	 * @time 2016��4��30�� ����4:04:17
	 */
	public void isNormalUser(String userId) {
		userDao.isNormalUser(userId);
	}
	
	/** 
	 * @method ��ѯ�û��˻����
	 * @author Instant
	 * @time 2016��4��30�� ����4:05:23
	 */
	public Map getMyMoney(HttpServletRequest request) {
		return userDao.getMyMoney(request);
	}
	
	/** 
	 * @method �û���ֵ
	 * @author Instant
	 * @time 2016��4��30�� ����4:05:46
	 */
	public double reMoney(HttpServletRequest request) {
		return userDao.reMoney(request);
	}
}