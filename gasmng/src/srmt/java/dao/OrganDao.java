package srmt.java.dao;

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
import srmt.java.entity.SysOrgan;

@Repository
@Transactional
public class OrganDao {
	@Resource
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	/** 
	 * @method 查询单位列表
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:02
	 */
	public List<Map> queryOragnList(HttpServletRequest request) {
		String organName = request.getParameter("organName");
		String organCode = request.getParameter("organCode");
		String isValid = request.getParameter("isValid");
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT                                  ");
		sb.append("   SO.ORGAN_ID ORGANID,                  ");
		sb.append("   SO.ORGAN_NAME ORGANNAME,              ");
		sb.append("   (SELECT S.ORGAN_NAME FROM SYS_ORGAN S WHERE S.ORGAN_ID=SO.PARENT) PARENT,              ");
		sb.append("   SO.ORGAN_CODE ORGANCODE,              ");
		sb.append("   SO.ORGAN_ADDRESS ORGANADDRESS,        ");
		sb.append("   SO.IS_VALID ISVALID                   ");
		sb.append(" FROM                                    ");
		sb.append(" 	SYS_ORGAN SO    WHERE 1=1           ");
		if (StringUtils.isNotEmpty(organName)) {
			sb.append(" and  SO.ORGAN_NAME LIKE :organName       ");
		}
		if (StringUtils.isNotEmpty(organCode)) {
			sb.append("  and SO.ORGAN_CODE LIKE :organCode       ");
		}
		if (StringUtils.isNotEmpty(isValid)) {
			if(isValid.equals(Constants.YES)){
				sb.append("  and SO.IS_VALID = :isValid       ");
			}else {
				isValid = Constants.YES;
				sb.append("  and SO.IS_VALID != :isValid or SO.IS_VALID is null     ");
			}
		}
		getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		if (StringUtils.isNotEmpty(organName)) {
			organName = "%" + organName + "%";
			query.setParameter("organName", organName);
		}
		if (StringUtils.isNotEmpty(organCode)) {
			organCode = "%" + organCode + "%";
			query.setParameter("organCode", organCode);
		}
		if (StringUtils.isNotEmpty(isValid)) {
			query.setParameter("isValid", isValid);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> queryList = query.list();
		return queryList;
	}

	/** 
	 * @method 删除单位
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:20
	 */
	public void delOrgan(HttpServletRequest request) {
		String organId = request.getParameter("organId");
		Transaction transaction = getSession().beginTransaction();
		SysOrgan sysOrgan = (SysOrgan) getSession().get(SysOrgan.class, organId);
		getSession().delete(sysOrgan);
		transaction.commit();
	}

	/** 
	 * @method 取消注销单位
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:29
	 */
	public void unLayoutOrgan(String organId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(organId)) {
			SysOrgan sysOrgan = (SysOrgan) getSession().get(SysOrgan.class, organId);
			sysOrgan.setIsValid(Constants.YES);
			getSession().update(sysOrgan);
		}
		transaction.commit();
		getSession().close();
	}

	/** 
	 * @method 注销单位
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:41
	 */
	public void layoutOrgan(String organId) {
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(organId)) {
			SysOrgan sysOrgan = (SysOrgan) getSession().get(SysOrgan.class, organId);
			sysOrgan.setIsValid(Constants.NO);
			getSession().update(sysOrgan);
		}
		transaction.commit();
		getSession().close();
	}

	/** 
	 * @method 获取修改单位信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:48
	 */
	public Map getOrganInfo(String organId) {
		Map map = new HashMap();
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(organId)) {
			SysOrgan sysOrgan = (SysOrgan) getSession().get(SysOrgan.class, organId);
			map.put("organId", sysOrgan.getOrganId());
			map.put("organCode", sysOrgan.getOrganCode());
			map.put("organName", sysOrgan.getOrganName());
			map.put("isValid", sysOrgan.getIsValid());
		    map.put("address", sysOrgan.getOrganAddress());
		    map.put("parent", sysOrgan.getParent());
			}
		transaction.commit();
		getSession().close();
		return map;
	}
	
	/** 
	 * @method 保存单位信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:16:07
	 */
	public Map saveOrgan(HttpServletRequest request) {
		String organId = request.getParameter("organId");
		String organName = request.getParameter("organName");
		String organCode = request.getParameter("organCode");
		String address = request.getParameter("address");
		String isValid = request.getParameter("isValid");
		String parent = request.getParameter("parent");
		Map result = new HashMap();
		if(isExitOrganCode(organCode, organId)){
			result.put("errorMsg", true);
			result.put("msg", "单位代码不能重复");
			return result;
		}
		Transaction transaction = getSession().beginTransaction();
		if(StringUtils.isNotEmpty(organId)){
			SysOrgan sysOrgan = (SysOrgan) getSession().get(SysOrgan.class, organId);
			sysOrgan.setIsValid(isValid);
			sysOrgan.setOrganAddress(address);
			sysOrgan.setOrganCode(organCode);
			sysOrgan.setOrganName(organName);
			sysOrgan.setParent(parent);
			getSession().update(sysOrgan);
		}else {
			SysOrgan sysOrgan = new SysOrgan();
			sysOrgan.setIsValid(isValid);
			sysOrgan.setOrganAddress(address);
			sysOrgan.setOrganCode(organCode);
			sysOrgan.setOrganName(organName);
			sysOrgan.setParent(parent);
			getSession().save(sysOrgan);
		}
		transaction.commit();
		getSession().close();
		result.put("success", true);
		return result;
	}
	
	/** 
	 * @method 查询单位用于下拉列表
	 * @author Instant
	 * @time 2016年4月30日 下午4:16:20
	 */
	public List<Map> queryOrgan(HttpServletRequest request){
		String sql = "SELECT SO.ORGAN_ID  ORGANID ,SO.ORGAN_NAME ORGANNAME FROM SYS_ORGAN SO WHERE SO.IS_VALID=:isValid";
		HttpSession session = request.getSession();
		String userType = (String)session.getAttribute("userType");
		String organId = (String)session.getAttribute("organId");
		if(Constants.USER_TYPE_ADMIN.equals(userType)){
			sql = sql + "  AND (SO.ORGAN_ID =:oragnId OR SO.PARENT =:oragnId) ";
		}
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("isValid", Constants.YES);
		if(Constants.USER_TYPE_ADMIN.equals(userType)){
			query.setParameter("oragnId", organId);
		}
		List<Map> queryList = query.list();
		transaction.commit();
		getSession().close();
		return queryList;
	}
	
	/** 
	 * @method 单位代码是否重复
	 * @author Instant
	 * @time 2016年4月30日 下午4:16:38
	 */
	public Boolean isExitOrganCode(String organCode,String organId){
		Boolean flag = false;
		String sql ="select * from sys_organ where organ_code=:organCode ";
		if(StringUtils.isNotEmpty(organId)){
			sql = sql +"  and  organ_id != :organId";
		}
		getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("organCode", organCode);
		if(StringUtils.isNotEmpty(organId)){
			query.setParameter("organId", organId);
		}
		List queryList = query.list();
		if(queryList!=null&&queryList.size()>0){
			flag = true;
		}
		return flag;
	}
}
