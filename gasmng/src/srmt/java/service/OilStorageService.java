package srmt.java.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srmt.java.common.Constants;
import srmt.java.dao.OilDao;

@Service
public class OilStorageService {

	@Autowired
	private OilDao oilDao;

	/**
	 * @method 查询入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:24:23
	 */
	public List<Map> queryOilStorage(HttpServletRequest request) {
		List<Map> list = oilDao.queryOilStorage(request);
		return list;
	}
	
	/** 
	 * @method 删除入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:25:47
	 */
	public void delOliStirage(String oilStorageId){
		oilDao.delOliStirage(oilStorageId);
	}
	
	/**
	 * @method 保存入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:24:37
	 */
	public void saveOliStirage(HttpServletRequest request){
		 oilDao.saveOliStirage(request);
	}
	
	/**
	 * @method 获取修改入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:25:17
	 */
	public Map getOliStirage(String oilStorageId){
		return oilDao.getOliStirage(oilStorageId);
	}
	
	/** 
	 * @method 查询油品类型
	 * @author Instant
	 * @time 2016年4月30日 下午4:25:58
	 */
	public List<Map> queryOilType() {
		List<Map> list = oilDao.queryOilType();
		return list;
	}
	
	/** 
	 * @method 查询油品记录
	 * @author Instant
	 * @time 2016年4月30日 下午4:26:14
	 */
	public List<Map> queryGasRecord(HttpServletRequest request) {
		List<Map> list = oilDao.queryGasRecord(request);
		return list;
	}
	
	/** 
	 * @method 删除加油记录
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:00
	 */
	public void delGasRecord(String gasId){
		oilDao.delGasRecord(gasId);
	}
	
	/** 
	 * @method 保存加油记录信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:16
	 */
	public Map saveGasRecord(HttpServletRequest request){
		 return oilDao.saveGasRecord(request);
	}
	
	/** 
	 * @method 获取修改加油记录信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:36
	 */
	public Map getGasRecord(String gasId){
		return oilDao.getGasRecord(gasId);
	}
	
	/** 
	 * @method 查询当前用户加油记录
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:52
	 */
	public List<Map> queryMyGasRecord(HttpServletRequest request) {
		List<Map> list = oilDao.queryMyGasRecord(request);
		return list;
	}
	
	/** 
	 * @method 获取近6个月的销售信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:39:22
	 */
	public Map getOilSalInfo(){
		   Calendar cal = Calendar.getInstance();
		    int month = cal.get(Calendar.MONTH) + 1;
		    int year = cal.get(Calendar.YEAR);
		    Map map =getSubMonth(year,month);
		    int month2 =(Integer) map.get("month");
		    int year2 =(Integer) map.get("year");
		    Map map3 =getSubMonth(year2,month2);
		    int month3 =(Integer) map3.get("month");
		    int year3 =(Integer) map3.get("year");
		    Map map4 =getSubMonth(year3,month3);
		    int month4 =(Integer) map4.get("month");
		    int year4 =(Integer) map4.get("year");
		    Map map5 =getSubMonth(year4,month4);
		    int month5 =(Integer) map5.get("month");
		    int year5 =(Integer) map5.get("year");
		    Map map6 =getSubMonth(year5,month5);
		    int month6 =(Integer) map6.get("month");
		    int year6 =(Integer) map6.get("year");
		    Double[] chaiGas = new Double[10] ;
		    chaiGas[1]=oilDao.queryOilSalVolume(year,month,Constants.OIL_TYPE_CHAI);
		    chaiGas[2]=oilDao.queryOilSalVolume(year2,month2,Constants.OIL_TYPE_CHAI);
		    chaiGas[3]=oilDao.queryOilSalVolume(year3,month3,Constants.OIL_TYPE_CHAI);
		    chaiGas[4]=oilDao.queryOilSalVolume(year4,month4,Constants.OIL_TYPE_CHAI);
		    chaiGas[5]=oilDao.queryOilSalVolume(year5,month5,Constants.OIL_TYPE_CHAI);
		    chaiGas[6]=oilDao.queryOilSalVolume(year6,month6,Constants.OIL_TYPE_CHAI);
		    Double[] qi95 = new Double[10] ;
		    qi95[1]=oilDao.queryOilSalVolume(year,month,Constants.OIL_TYPE_QI95);
		    qi95[2]=oilDao.queryOilSalVolume(year2,month2,Constants.OIL_TYPE_QI95);
		    qi95[3]=oilDao.queryOilSalVolume(year3,month3,Constants.OIL_TYPE_QI95);
		    qi95[4]=oilDao.queryOilSalVolume(year4,month4,Constants.OIL_TYPE_QI95);
		    qi95[5]=oilDao.queryOilSalVolume(year5,month5,Constants.OIL_TYPE_QI95);
		    qi95[6]=oilDao.queryOilSalVolume(year6,month6,Constants.OIL_TYPE_QI95);
		    Double[] qi97 = new Double[10] ;
		    qi97[1]=oilDao.queryOilSalVolume(year,month,Constants.OIL_TYPE_QI97);
		    qi97[2]=oilDao.queryOilSalVolume(year2,month2,Constants.OIL_TYPE_QI97);
		    qi97[3]=oilDao.queryOilSalVolume(year3,month3,Constants.OIL_TYPE_QI97);
		    qi97[4]=oilDao.queryOilSalVolume(year4,month4,Constants.OIL_TYPE_QI97);
		    qi97[5]=oilDao.queryOilSalVolume(year5,month5,Constants.OIL_TYPE_QI97);
		    qi97[6]=oilDao.queryOilSalVolume(year6,month6,Constants.OIL_TYPE_QI97);
		    String [] categories= new String[10];
		    categories[1]=year+"年"+month+"月";
		    categories[2]=year2+"年"+month2+"月";
		    categories[3]=year3+"年"+month3+"月";
		    categories[4]=year4+"年"+month4+"月";
		    categories[5]=year5+"年"+month5+"月";
		    categories[6]=year6+"年"+month6+"月";
		    Map mapInfo = new HashMap<>();
		    mapInfo.put("categories", categories);
		    mapInfo.put("qi97", qi97);
		    mapInfo.put("qi95", qi95);
		    mapInfo.put("chaiGas", chaiGas);
		    return mapInfo;
	}
	
	/** 
	 * @method 获取上一个月的年份和月份
	 * @author Instant
	 * @time 2016年4月30日 下午4:40:57
	 */
	public Map getSubMonth(int year,int month){
		if(month<=1){
			year = year-1;
			month=12;
		}else{
			month=	month-1;;
		}
		Map map= new HashMap<>();
		map.put("year", year);
		map.put("month", month);
		return map;
	}

	
	/** 
	 * @method 获取库存信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:39:51
	 */
	public Map queryOilStorage4All() {
		double totalChai = oilDao.queryGasTotal(Constants.OIL_TYPE_CHAI);
		double totalQI95 = oilDao.queryGasTotal(Constants.OIL_TYPE_QI95);
		double totalQI97 = oilDao.queryGasTotal(Constants.OIL_TYPE_QI97);
		double totalNum = totalChai + totalQI95 + totalQI97;
		double useChai = oilDao.queryOilSalVolume(Constants.OIL_TYPE_CHAI);
		double useQI95 = oilDao.queryOilSalVolume(Constants.OIL_TYPE_QI95);
		double useQI97 = oilDao.queryOilSalVolume(Constants.OIL_TYPE_QI97);
		double unChai = totalChai - useChai;
		double unQI95 = totalQI95 - useQI95;
		double unQI97 = totalQI97 - useQI97;
		double leaveTotal = unChai + unQI95 + unQI97;
		Map map = new HashMap<>();
		map.put("useChai", useChai/totalNum);
		map.put("useQI95", useQI95/totalNum);
		map.put("useQI97", useQI97/totalNum);
		map.put("unChai", unChai/totalNum);
		map.put("unQI95", unQI95/totalNum);
		map.put("unQI97", unQI97/totalNum);
		map.put("totalNum", totalNum);
		map.put("leaveTotal", leaveTotal);
		map.put("useTotal", totalNum-leaveTotal);
		map.put("leaveTotalPre", leaveTotal/totalNum);
		map.put("useTotalPre", (totalNum-leaveTotal)/totalNum);
		return map;
	}
}
