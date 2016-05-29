package srmt.java.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import srmt.java.common.Constants;
import srmt.java.common.MyUtil;
import srmt.java.entity.SysUser;

@Repository
@Transactional
public class UserDao {
	@Resource
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/** 
	 * @method ��ѯ�û��б�
	 * @author Instant
	 * @time 2016��4��30�� ����4:01:06
	 */
	public List<Map> queryUserList(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String organId = request.getParameter("organId");
		String userNum = request.getParameter("userNum");
		String isValid = request.getParameter("isValid");
		String userType = request.getParameter("userTyp");

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT                                               		");
		sb.append(" 		SU.USERNAME,                                        ");
		sb.append(" 		SU.USER_ID     USERID,                              ");
		sb.append(" 		SU.SEX,                                             ");
		sb.append(" 		SU.EMAIL,                                           ");
		sb.append(" 		SU.MOBILE,                                          ");
		sb.append(" 		SU.USER_NUM USERNUM,                                ");
		sb.append(" 		SO.ORGAN_NAME ORGANNAME,                            ");
		sb.append(" 		SU.IS_VALID       ISVALID,                          ");
		sb.append(" 		SU.is_admin       ISADMIN,                          ");
		sb.append(" 		SU.user_type       USERTYPE,                        ");
		sb.append("     SU.ADRESS,                                              ");
		sb.append("     DATE_FORMAT(SU.BIRTHDAY,'%Y-%m-%d')   BIRTHDAY          ");
		sb.append("	 FROM                                                       ");
		sb.append(" 		SYS_USER SU                                         ");
		sb.append(" 	LEFT JOIN SYS_ORGAN SO ON SU.ORGAN_ID = SO.ORGAN_ID  where 1=1 ");
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" and su.USERNAME like :userName   ");
		}
		if (StringUtils.isNotEmpty(email)) {
			sb.append(" and su.EMAIL like :email   ");
		}
		if (StringUtils.isNotEmpty(mobile)) {
			sb.append(" and su.mobile = :mobile   ");
		}
		if (StringUtils.isNotEmpty(organId)) {
			sb.append(" and su.ORGAN_ID = :organId   ");
		}
		if (StringUtils.isNotEmpty(isValid)) {
			if (isValid.equals(Constants.YES)) {
				sb.append("  and su.IS_VALID = :isValid       ");
			} else {
				isValid = Constants.YES;
				sb.append("  and su.IS_VALID != :isValid or su.IS_VALID is null     ");
			}
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" and su.USER_NUM = :userNum   ");
		}
		if (StringUtils.isNotEmpty(userType)) {
			sb.append(" and su.user_type = :userType   ");
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		if (StringUtils.isNotEmpty(userName)) {
			userName = "%" + userName + "%";
			query.setParameter("userName", userName);
		}
		if (StringUtils.isNotEmpty(email)) {
			email = "%" + email + "%";
			query.setParameter("email", email);
		}
		if (StringUtils.isNotEmpty(mobile)) {
			query.setParameter("mobile", mobile);
		}
		if (StringUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}
		if (StringUtils.isNotEmpty(userType)) {
			query.setParameter("userType", userType);
		}
		if (StringUtils.isNotEmpty(isValid)) {
			query.setParameter("isValid", isValid);
		}
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		return queryList;
	}

	/** 
	 * @method ɾ���û�
	 * @author Instant
	 * @time 2016��4��30�� ����4:01:27
	 */
	public void delUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		Transaction transaction = getSession().beginTransaction();
		SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
		getSession().delete(sysUser);
		transaction.commit();
	}

	/** 
	 * @method �����û�
	 * @author Instant
	 * @time 2016��4��30�� ����4:01:35
	 */
	public Map saveUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String organId = request.getParameter("organId");
		String birhtdayStr = request.getParameter("birhtday");
		String address = request.getParameter("address");
		String userType = request.getParameter("userType");

		Map result = new HashMap();
		if (isExitEmail(email, userId)) {
			result.put("errorMsg", true);
			result.put("msg", "�������䲻���ظ�");
			return result;
		}
		if (isExitMobile(mobile, userId)) {
			result.put("errorMsg", true);
			result.put("msg", "�ƶ��绰�����ظ�");
			return result;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = null;
		try {
			if (StringUtils.isNotEmpty(birhtdayStr)) {
				birthday = sdf.parse(birhtdayStr);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setUsername(userName);
			sysUser.setSex(sex);
			sysUser.setEmail(email);
			sysUser.setMobile(mobile);
			sysUser.setOrganId(organId);
			sysUser.setAdress(address);
			sysUser.setUserType(userType);
			sysUser.setBirthday(birthday);
			getSession().update(sysUser);
		} else {
			SysUser sysUser = new SysUser();
			sysUser.setUsername(userName);
			sysUser.setSex(sex);
			sysUser.setEmail(email);
			sysUser.setMobile(mobile);
			sysUser.setOrganId(organId);
			sysUser.setAdress(address);
			sysUser.setUserType(userType);
			sysUser.setUserNum(getUserNum());
			sysUser.setUserType(userType);
			sysUser.setIsValid(Constants.YES);
			sysUser.setBirthday(birthday);
			sysUser.setPassword(MyUtil.getMd5(Constants.DEFULT_PASSWORD));
			getSession().save(sysUser);
		}
		transaction.commit();
		getSession().close();
		result.put("success", true);
		return result;
	}

	/** 
	 * @method ��ȡ�û���Ϣ�������޸Ľ����������
	 * @author Instant
	 * @time 2016��4��30�� ����4:01:53
	 */
	public Map getUserInfo(String userId) {
		Transaction transaction = getSession().beginTransaction();
		Map userMap = new HashMap<>();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			userMap.put("userId", userId);
			userMap.put("userName", sysUser.getUsername());
			userMap.put("address", sysUser.getAdress());
			userMap.put("email", sysUser.getEmail());
			userMap.put("mobile", sysUser.getMobile());
			userMap.put("organId", sysUser.getOrganId());
			userMap.put("sex", sysUser.getSex());
			userMap.put("userType", sysUser.getUserType());
			userMap.put("birhtday", sysUser.getBirthday());
		}
		transaction.commit();
		getSession().close();
		return userMap;
	}

	/** 
	 * @method ȡ��ע���û�
	 * @author Instant
	 * @time 2016��4��30�� ����4:02:23
	 */
	public void unLayoutUser(String userId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setIsValid(Constants.YES);
			getSession().update(sysUser);
		}
		transaction.commit();
		getSession().close();
	}

	/** 
	 * @method ע���û�
	 * @author Instant
	 * @time 2016��4��30�� ����4:02:39
	 */
	public void layoutUser(String userId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setIsValid(Constants.NO);
			getSession().update(sysUser);
		}
		transaction.commit();
		getSession().close();
	}

	/** 
	 * @method �ж������Ƿ�Ψһ
	 * @author Instant
	 * @time 2016��4��30�� ����4:02:47
	 */
	public Boolean isExitEmail(String email, String userId) {
		Boolean flag = false;
		String sql = "select * from sys_user where email =:email  ";
		if (StringUtils.isNotEmpty(userId)) {
			sql = sql + "  and  user_id != :userId";
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("email", email);
		if (StringUtils.isNotEmpty(userId)) {
			query.setParameter("userId", userId);
		}
		List queryList = query.list();
		if (queryList != null && queryList.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/** 
	 * @method �ж��ֻ����Ƿ�Ψһ
	 * @author Instant
	 * @time 2016��4��30�� ����4:03:06
	 */
	public Boolean isExitMobile(String mobile, String userId) {
		Boolean flag = false;
		String sql = "select * from sys_user where mobile =:mobile  ";
		if (StringUtils.isNotEmpty(userId)) {
			sql = sql + "  and  user_id != :userId";
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("mobile", mobile);
		if (StringUtils.isNotEmpty(userId)) {
			query.setParameter("userId", userId);
		}
		List queryList = query.list();
		if (queryList != null && queryList.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/** 
	 * @method �޸�����
	 * @author Instant
	 * @time 2016��4��30�� ����4:03:24
	 */
	public Map savePassword(String password, String userId, String newPassword) {
		Map result = new HashMap();
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			if (MyUtil.getMd5(password).equals(sysUser.getPassword())) {
				sysUser.setPassword(MyUtil.getMd5(newPassword));
				getSession().update(sysUser);
			} else {

				result.put("errorMsg", true);
				result.put("msg", "ԭ������������");
				return result;
			}
		}
		transaction.commit();
		getSession().close();
		result.put("success", true);
		return result;
	}

	/** 
	 * @method ����Ϊϵͳ����Ա
	 * @author Instant
	 * @time 2016��4��30�� ����4:03:35
	 */
	public void isXTAdmin(String userId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setUserType(Constants.USER_TYPE_XT_ADMIN);
			sysUser.setIsAdmin(Constants.YES);
			getSession().update(sysUser);
		}
		transaction.commit();
		getSession().close();
	}

	/** 
	 * @method ����Ϊ��ͨ����Ա
	 * @author Instant
	 * @time 2016��4��30�� ����4:04:01
	 */
	public void isAdmin(String userId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setUserType(Constants.USER_TYPE_ADMIN);
			sysUser.setIsAdmin(Constants.YES);
			getSession().update(sysUser);
		}
		transaction.commit();
		getSession().close();
	}

	/** 
	 * @method ����Ϊ��ͨ�û�
	 * @author Instant
	 * @time 2016��4��30�� ����4:04:17
	 */
	public void isNormalUser(String userId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNoneEmpty(userId)) {
			SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
			sysUser.setUserType(Constants.USER_TYPE_NORMAL);
			sysUser.setIsAdmin(Constants.NO);
			getSession().update(sysUser);
		}
		transaction.commit();
		getSession().close();
	}

	/** 
	 * @method �H�����û����userNum
	 * @author Instant
	 * @time 2016��4��30�� ����4:04:43
	 */
	public Map queryUserNum(String sql) {
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		Map map = new HashMap<>();
		if (list != null && list.size() > 0) {
			map = list.get(0);
		}
		return map;
	}

	/** 
	 * @method �õ������û�userNun
	 * @author Instant
	 * @time 2016��4��30�� ����4:05:04
	 */
	public Long getUserNum() {
		String sql = "select  CONVERT(max(s.user_num),CHAR) maxNum from sys_user s ";
		Map map = queryUserNum(sql);
		String maxNum = (String) map.get("maxNum");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long userNum = 0;
		if (StringUtils.isNotEmpty(maxNum)) {
			String year = maxNum.substring(0, 4);
			if (String.valueOf(cal.get(Calendar.YEAR)).equals(year)) {
				userNum = Long.parseLong(maxNum.substring(4)) + 1;
			} else {
				userNum = Constants.USER_NUM_MIN;
			}
		} else {
			userNum = Constants.USER_NUM_MIN;
		}

		String userNumStr = String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf(userNum);
		userNum = Long.parseLong(userNumStr.trim());
		return userNum;

	}
	/** 
	 * @method ��ѯ�û��˻����
	 * @author Instant
	 * @time 2016��4��30�� ����4:05:23
	 */
	public Map getMyMoney(HttpServletRequest request){
		Transaction transaction = getSession().beginTransaction();
		HttpSession session =request.getSession();
		String userId =(String)session.getAttribute("userId");
		SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
		double userBalance = sysUser.getUserBalance();
		String money =new java.text.DecimalFormat("0.00").format(userBalance);  
		Map map =new HashMap<>();
		map.put("money", money);
		return map;
	}
	
	/** 
	 * @method �û���ֵ
	 * @author Instant
	 * @time 2016��4��30�� ����4:05:46
	 */
	public double reMoney(HttpServletRequest request){
		Transaction transaction = getSession().beginTransaction();
		HttpSession session =request.getSession();
		String userId =(String)session.getAttribute("userId");
		String reMoney=request.getParameter("money");
		SysUser sysUser = (SysUser) getSession().get(SysUser.class, userId);
		double userBalance = sysUser.getUserBalance();
		userBalance= userBalance+Double.parseDouble(reMoney);
		sysUser.setUserBalance(userBalance);
		getSession().update(sysUser);
		transaction.commit();
		getSession().close();
		return userBalance;
	}
}
