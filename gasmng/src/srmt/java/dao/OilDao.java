package srmt.java.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import srmt.java.entity.GasPrice;
import srmt.java.entity.GasRecord;
import srmt.java.entity.OilStorage;
import srmt.java.entity.SysUser;

@Repository
@Transactional
public class OilDao {
	@Resource
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * @method 查询入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:24:23
	 */
	public List<Map> queryOilStorage(HttpServletRequest request) {
		String oilType = request.getParameter("oilType");
		String oilTankId = request.getParameter("oilTankId");
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT o.oil_tank_id,o.oil_storage_id,o.oil_price, "
				+ "(select s.dict_name from sys_dict s where s.dict_value= o.oil_type) oil_type,o.olil_num,"
				+ "DATE_FORMAT(o.oil_receive_time,'%Y-%m-%d %H:%i')  "
				+ "oil_receive_time ,(select so.organ_name from sys_organ so where so.organ_id =o.oil_ru_place) organname"
				+ " from oil_storage    o       where 1=1        						");
		if (StringUtils.isNotEmpty(oilType)) {
			sb.append(" and o.oil_type = :oilType   ");
		}
		if (StringUtils.isNotEmpty(oilTankId)) {
			sb.append("  and o.oil_tank_id = :oilTankId   ");
		}
		getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		if (StringUtils.isNotEmpty(oilType)) {
			query.setParameter("oilType", oilType);
		}
		if (StringUtils.isNotEmpty(oilTankId)) {
			query.setParameter("oilTankId", oilTankId);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		return queryList;
	}

	/**
	 * @method 保存入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:24:37
	 */
	public void saveOliStirage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String oilStorageId = request.getParameter("oilStorageId");
		String oilType = request.getParameter("oilType");
		String olilNum = request.getParameter("olilNum");
		String oilTankId = request.getParameter("oilTankId");
		String oilReceiveTime = request.getParameter("oilReceiveTime");
		String oilPlace = request.getParameter("oilPlace");
		String oilPrice = request.getParameter("oliPrice");
		String userId = (String) session.getAttribute("userId");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date oilReceiveDate = null;
		try {
			if (StringUtils.isNotEmpty(oilReceiveTime)) {
				oilReceiveDate = sdf.parse(oilReceiveTime);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		double oilPriceDou = Double.parseDouble(oilPrice);
		Transaction transaction = getSession().beginTransaction();
		if (StringUtils.isNotEmpty(oilStorageId)) {
			OilStorage oilStorage = getSession().get(OilStorage.class, oilStorageId);
			oilStorage.setOilReceiveTime(oilReceiveDate);
			oilStorage.setOlilNum(Integer.parseInt(olilNum));
			oilStorage.setOilType(oilType);
			oilStorage.setOilTankId(oilTankId);
			oilStorage.setCeraterDate(new Date());
			oilStorage.setCreater(userId);
			oilStorage.setOilRuPlace(oilPlace);
			oilStorage.setOilPrice(oilPriceDou);
			getSession().update(oilStorage);
		} else {
			OilStorage oilStorage = new OilStorage();
			oilStorage.setOilReceiveTime(oilReceiveDate);
			oilStorage.setOlilNum(Integer.parseInt(olilNum));
			oilStorage.setOilType(oilType);
			oilStorage.setOilTankId(oilTankId);
			oilStorage.setOilRuPlace(oilPlace);
			oilStorage.setOilPrice(oilPriceDou);
			getSession().save(oilStorage);
		}
		transaction.commit();
		getSession().close();
	}

	/**
	 * @method 获取修改入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:25:17
	 */
	public Map getOliStirage(String oilStorageId) {
		Transaction transaction = getSession().beginTransaction();
		Map oilMap = new HashMap<>();
		if (StringUtils.isNoneEmpty(oilStorageId)) {
			OilStorage oilStorage = getSession().get(OilStorage.class, oilStorageId);
			oilMap.put("oilStorageId", oilStorageId);
			oilMap.put("oilType", oilStorage.getOilType());
			oilMap.put("olilNum", oilStorage.getOlilNum());
			oilMap.put("oilTankId", oilStorage.getOilTankId());
			oilMap.put("oilReceiveTime", oilStorage.getOilReceiveTime().toString());
			oilMap.put("oilPlace", oilStorage.getOilRuPlace());
			oilMap.put("oliPrice", oilStorage.getOilPrice());
		}
		transaction.commit();
		getSession().close();
		return oilMap;
	}

	/** 
	 * @method 删除入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:25:47
	 */
	public void delOliStirage(String oilStorageId) {
		Transaction transaction = getSession().beginTransaction();
		OilStorage oilStorage = getSession().get(OilStorage.class, oilStorageId);
		getSession().delete(oilStorage);
		transaction.commit();
	}

	/** 
	 * @method 查询油品类型
	 * @author Instant
	 * @time 2016年4月30日 下午4:25:58
	 */
	public List<Map> queryOilType() {
		getSession().beginTransaction();
		String sql = "select s.dict_name dictname,s.dict_value dictvalue from sys_dict s where s.dict_is_valid=:isValid"
				+ " and s.dict_type=:dictType ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("isValid", Constants.YES);
		query.setParameter("dictType", Constants.DICT_TYPE_GAS);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> queryList = query.list();
		return queryList;

	}

	/** 
	 * @method 查询油品记录
	 * @author Instant
	 * @time 2016年4月30日 下午4:26:14
	 */
	public List<Map> queryGasRecord(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String userNum = request.getParameter("userNum");
		String gasType = request.getParameter("gasType");
		String organId = request.getParameter("organId");
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT                                                 ");
		sb.append("  	g.gas_id gasid,                                     ");
		sb.append("    g.gas_price gasprice,                                ");
		sb.append("    (select s.organ_name from sys_organ s where g.gas_place = s.organ_id) organName,  ");
		sb.append("    g.gas_volume gasvolume,                              ");
		sb.append("    DATE_FORMAT(g.gas_time,'%Y-%m-%d %H:%i')  gastime,   ");
		sb.append("    g.gas_user_num gasusernum,                           ");
		sb.append("      s.username username,                               ");
		sb.append("      s.mobile,                                          ");
		sb.append("      s.email ,                                          ");
		sb.append(" (select sd.dict_name from sys_dict sd where sd.dict_value= g.gas_type) gastype  ");
		sb.append("  FROM                                                   ");
		sb.append("  	gas_record g                                        ");
		sb.append("  LEFT JOIN sys_user s ON g.gas_user_num = s.user_num    where 1=1 ");
		if (StringUtils.isNotEmpty(userName)) {
			sb.append(" and s.USERNAME like :userName   ");
		}
		if (StringUtils.isNotEmpty(email)) {
			sb.append(" and s.EMAIL like :email   ");
		}
		if (StringUtils.isNotEmpty(mobile)) {
			sb.append(" and s.mobile = :mobile   ");
		}
		if (StringUtils.isNotEmpty(gasType)) {
			sb.append(" and g.gas_type= :gasType   ");
		}
		if (StringUtils.isNotEmpty(userNum)) {
			sb.append(" and g.gas_user_num = :userNum   ");
		}
		if (StringUtils.isNotEmpty(organId)) {
			sb.append(" and s.organ_id= :organId   ");
		}
		getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
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
		if (StringUtils.isNotEmpty(userNum)) {
			query.setParameter("userNum", userNum);
		}
		if (StringUtils.isNotEmpty(gasType)) {
			query.setParameter("gasType", gasType);
		}
		if (StringUtils.isNotEmpty(organId)) {
			query.setParameter("organId", organId);
		}
		List<Map> queryList = query.list();
		return queryList;
	}

	/** 
	 * @method 删除加油记录
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:00
	 */
	public void delGasRecord(String gasId) {
		Transaction transaction = getSession().beginTransaction();
		GasRecord gasRecord = getSession().get(GasRecord.class, gasId);
		getSession().delete(gasRecord);
		transaction.commit();
	}

	/** 
	 * @method 保存加油记录信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:16
	 */
	public Map saveGasRecord(HttpServletRequest request) {
		Map map = new HashMap<>();
		String gasId = request.getParameter("gasId");
		String gasUserNum = request.getParameter("gasUserNum");
		String gasType = request.getParameter("gasType");
		String gasPrice = request.getParameter("gasPrice");
		String gasVolume = request.getParameter("gasVolume");
		String organId = request.getParameter("organId");
		String sql = "select * from sys_user su where su.user_num=:userNum";
		Transaction transaction = getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("userNum", gasUserNum);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		String userId = "";
		double money = 0;
		List<Map> list = query.list();
		if (list != null && list.size() > 0) {
			Map loginInfo = list.get(0);
			userId = (String) loginInfo.get("user_id");
			money = (double) loginInfo.get("user_balance");
		} else {
			map.put("error", "当前输入卡号不存在!");
			return map;
		}
		SysUser sysUser = getSession().get(SysUser.class, userId);
		if (StringUtils.isNotEmpty(gasId)) {
			GasRecord gasRecord = getSession().get(GasRecord.class, gasId);
			double userMoney = Double.valueOf(gasPrice) * Double.valueOf(gasVolume);
			double myMoney = sysUser.getUserBalance() + gasRecord.getGasPrice() * gasRecord.getGasVolume() - userMoney;
			if (myMoney < 0) {
				map.put("error", "用户余额不足,请充值!");
				return map;
			}
			gasRecord.setGasPrice(Double.valueOf(gasPrice));
			gasRecord.setGasUserNum(Long.valueOf(gasUserNum));
			gasRecord.setGasVolume(Double.valueOf(gasVolume));
			gasRecord.setGasType(gasType);
			gasRecord.setGasPlace(organId);
			getSession().update(gasRecord);
			sysUser.setUserBalance(myMoney);
			getSession().update(sysUser);
			map.put("sucesss", true);
		} else {
			double userMoney = Double.valueOf(gasPrice) * Double.valueOf(gasVolume);
			if ((sysUser.getUserBalance() - userMoney) < 0) {
				map.put("error", "用户余额不足,请充值!");
				return map;
			}
			GasRecord gasRecord = new GasRecord();
			gasRecord.setGasPrice(Double.valueOf(gasPrice));
			gasRecord.setGasUserNum(Long.valueOf(gasUserNum));
			gasRecord.setGasVolume(Double.valueOf(gasVolume));
			gasRecord.setGasType(gasType);
			gasRecord.setGasPlace(organId);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			gasRecord.setGasTime(ts);
			gasRecord.setCreaterTime(ts);
			getSession().save(gasRecord);
			sysUser.setUserBalance(sysUser.getUserBalance() - userMoney);
			getSession().update(sysUser);
			map.put("sucesss", true);
		}
		transaction.commit();
		return map;
	}

	/** 
	 * @method 获取修改加油记录信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:36
	 */
	public Map getGasRecord(String gasId) {
		Transaction transaction = getSession().beginTransaction();
		Map gasMap = new HashMap<>();
		if (StringUtils.isNoneEmpty(gasId)) {
			GasRecord gasRecord = getSession().get(GasRecord.class, gasId);
			gasMap.put("gasId", gasId);
			gasMap.put("gasType", gasRecord.getGasType());
			gasMap.put("gasUserNum", gasRecord.getGasUserNum());
			gasMap.put("gasPrice", gasRecord.getGasPrice());
			gasMap.put("gasVolume", gasRecord.getGasVolume());
			gasMap.put("organId", gasRecord.getGasPlace());
		}
		transaction.commit();
		getSession().close();
		return gasMap;
	}

	/** 
	 * @method 查询当前用户加油记录
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:52
	 */
	public List<Map> queryMyGasRecord(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT                                                 ");
		sb.append("  	g.gas_id gasid,                                     ");
		sb.append("    g.gas_price gasprice,                                ");
		sb.append("    g.gas_volume gasvolume,                              ");
		sb.append("    (select s.organ_name from sys_organ s where g.gas_place = s.organ_id) organName,  ");
		sb.append("    DATE_FORMAT(g.gas_time,'%Y-%m-%d %H:%i')  gastime,   ");
		sb.append("   round(g.gas_volume* g.gas_price, 2) userMoney,        ");
		sb.append("    g.gas_user_num gasusernum,                           ");
		sb.append("      s.username username,                               ");
		sb.append(" (select sd.dict_name from sys_dict sd where sd.dict_value= g.gas_type) gastype  ");
		sb.append("  FROM                                                   ");
		sb.append("  	gas_record g                                        ");
		sb.append("  LEFT JOIN sys_user s ON g.gas_user_num = s.user_num    where 1=1 ");
		sb.append("  and g.gas_user_num=:userNum ");
		HttpSession session = request.getSession();
		BigInteger userNum = (BigInteger) session.getAttribute("userNum");
		getSession().beginTransaction();
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("userNum", userNum);
		List<Map> queryList = query.list();
		return queryList;
	}

	/** 
	 * @method 查询某年某月的销售量
	 * @author Instant
	 * @time 2016年4月30日 下午4:28:31
	 */
	public Double queryOilSalVolume(int year, int month, String gasType,String userType,String organId) {
		String monthStr = Integer.toString(month);
		int length = monthStr.length();
		if (length == 1) {
			monthStr = "0" + monthStr;
		}
		String dateStr = Integer.toString(year) + monthStr;
		getSession().beginTransaction();
		StringBuffer sb = new StringBuffer();
		sb.append("  select SUM(gr.gas_volume) gasvolume from gas_record gr  ");
		sb.append("  where DATE_FORMAT(gr.gas_time,'%Y%m') = :dateStr       ");
		sb.append("  and gr.gas_type= :gasType                              ");
		if(Constants.USER_TYPE_ADMIN.equals(userType)){
			sb.append("   AND (gr.gas_place in (select so.organ_id from sys_organ ");
			sb.append("  so where so.organ_id=:organId or so.parent=:organId ))") ;
		}
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("dateStr", dateStr);
		query.setParameter("gasType", gasType);
		if(Constants.USER_TYPE_ADMIN.equals(userType)){
			query.setParameter("organId", organId);
		}
		List<Map> queryList = query.list();
		Map map = new HashMap<>();
		Double gasVolume = 0.0;
		if (queryList != null && queryList.size() > 0) {
			map = queryList.get(0);
			gasVolume = (Double) map.get("gasvolume");
		}
		return gasVolume;
	}

	/** 
	 * @method 查询某种油品的进库总量
	 * @author Instant
	 * @time 2016年4月30日 下午4:29:56
	 */
	public double queryGasTotal(String gasType,String userType,String organId) {
		getSession().beginTransaction();
		String sql = "select SUM(os.olil_num)  olilnum from oil_storage os where os.oil_type=:gasType ";
		if(Constants.USER_TYPE_ADMIN.equals(userType)){
			sql = sql + "  AND (os.oil_ru_place in (select so.organ_id from sys_organ "
					  + " so where so.organ_id=:organId or so.parent=:organId ))" ;
		}
		SQLQuery totalQuery = getSession().createSQLQuery(sql);
		totalQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		totalQuery.setParameter("gasType", gasType);
		if(Constants.USER_TYPE_ADMIN.equals(userType)){
			totalQuery.setParameter("organId", organId);
		}
		List<Map> totalList = totalQuery.list();
		double oilTotal = 0.00;
		Map totalMap = new HashMap<>();
		if (totalList != null && totalList.size() > 0) {
			totalMap = totalList.get(0);
			BigDecimal oilnum = (BigDecimal) totalMap.get("olilnum");
			oilTotal = oilnum.doubleValue();
		}
		return oilTotal;
	}

	/** 
	 * @method 查询某个油品的销售量
	 * @author Instant
	 * @time 2016年4月30日 下午4:29:04
	 */
	public double queryOilSalVolume(String gasType,String userType,String organId) {
		getSession().beginTransaction();
		String sql = "select sum(gr.gas_volume)  gasvolume  from gas_record gr where gr.gas_type=:gasType  ";
		if(Constants.USER_TYPE_ADMIN.equals(userType)){
			sql = sql + "  AND (gr.gas_place in (select so.organ_id from sys_organ "
					  + " so where so.organ_id=:organId or so.parent=:organId ))" ;
		}
		SQLQuery totalQuery = getSession().createSQLQuery(sql);
		totalQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		totalQuery.setParameter("gasType", gasType);
		if(Constants.USER_TYPE_ADMIN.equals(userType)){
			totalQuery.setParameter("organId", organId);
		}
		List<Map> totalList = totalQuery.list();
		double oilTotal = 0;
		Map totalMap = new HashMap<>();
		if (totalList != null && totalList.size() > 0) {
			totalMap = totalList.get(0);
			oilTotal = (Double) totalMap.get("gasvolume");
		}
		
		
		return oilTotal;
	}
	
	public List<Map> queryGasPriceList(HttpServletRequest request) {
		getSession().beginTransaction();
		String gasType = request.getParameter("gasType");
		StringBuilder sb = new StringBuilder();
		sb.append("select g.gas_price_id gaspriceid,(select s.dict_name from sys_dict s where s.dict_value= g.gas_type) gas_type, ");
		sb.append(" g.gas_price,DATE_FORMAT(g.gas_price_time,'%Y-%m-%d %H:%i') gas_price_time from gas_price g  where 1=1 ");
		if(StringUtils.isNotEmpty(gasType)){
			sb.append("  and g.gas_type=:gasType ");
		}
		sb.append(" order by gas_type,g.gas_price_time asc  ");
		SQLQuery query = getSession().createSQLQuery(sb.toString());
		if(StringUtils.isNotEmpty(gasType)){
			query.setParameter("gasType", gasType);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		return list;
	}
	public void saveOliPrice(HttpServletRequest request) {
		Transaction transaction = getSession().beginTransaction();
		String gasPriceId = request.getParameter("gasPriceId");
		String gasType = request.getParameter("gasType");
		String gasPrice = request.getParameter("gasPrice");
		double price = Double.parseDouble(gasPrice);
		if(StringUtils.isEmpty(gasPriceId)){
			GasPrice gasPrices = new GasPrice();
			gasPrices.setGasPrice(price);
			gasPrices.setGasType(gasType);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			gasPrices.setGasPriceTime(ts);
			getSession().save(gasPrices);
		}else{
			GasPrice gasPrices = getSession().get(GasPrice.class, gasPriceId);
			gasPrices.setGasPrice(price);
			gasPrices.setGasType(gasType);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			gasPrices.setGasPriceTime(ts);
			getSession().update(gasPrices);
		}
		transaction.commit();
	}
	
	public void delGasPrice(String gasPriceId){
		Transaction transaction = getSession().beginTransaction();
		GasPrice gasPrice = getSession().get(GasPrice.class, gasPriceId);
		getSession().delete(gasPrice);
		transaction.commit();
	}
	
	
	
	public Map getGasPriceInfo(String gasPriceId) {
		Transaction transaction = getSession().beginTransaction();
		Map oilMap = new HashMap<>();
		if (StringUtils.isNoneEmpty(gasPriceId)) {
			GasPrice gasPrice = getSession().get(GasPrice.class, gasPriceId);
			oilMap.put("gasPriceId", gasPriceId);
			oilMap.put("gasPrice",gasPrice.getGasPrice());
			oilMap.put("gasType", gasPrice.getGasType());
		}
		transaction.commit();
		getSession().close();
		return oilMap;
	}
	
	public Map getGasPrice4gasType(String gasType){
		getSession().beginTransaction();
		String sql = "select g.gas_price  from gas_price g where g.gas_type = :gasType order by g.gas_price_time desc ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("gasType", gasType);
		List<Double> list = query.list();
		double price = 0;
		if(!list.isEmpty()){
			price = list.get(0);
		}
		Map priceMap = new HashMap<>();
		priceMap.put("price", price);
		return priceMap;
	}
}