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
	 * @method ��ѯ�����Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:24:23
	 */
	public List<Map> queryOilStorage(HttpServletRequest request) {
		List<Map> list = oilDao.queryOilStorage(request);
		return list;
	}
	
	/** 
	 * @method ɾ�������Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:25:47
	 */
	public void delOliStirage(String oilStorageId){
		oilDao.delOliStirage(oilStorageId);
	}
	
	/**
	 * @method ���������Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:24:37
	 */
	public void saveOliStirage(HttpServletRequest request){
		 oilDao.saveOliStirage(request);
	}
	
	/**
	 * @method ��ȡ�޸������Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:25:17
	 */
	public Map getOliStirage(String oilStorageId){
		return oilDao.getOliStirage(oilStorageId);
	}
	
	/** 
	 * @method ��ѯ��Ʒ����
	 * @author Instant
	 * @time 2016��4��30�� ����4:25:58
	 */
	public List<Map> queryOilType() {
		List<Map> list = oilDao.queryOilType();
		return list;
	}
	
	/** 
	 * @method ��ѯ��Ʒ��¼
	 * @author Instant
	 * @time 2016��4��30�� ����4:26:14
	 */
	public List<Map> queryGasRecord(HttpServletRequest request) {
		List<Map> list = oilDao.queryGasRecord(request);
		return list;
	}
	
	/** 
	 * @method ɾ�����ͼ�¼
	 * @author Instant
	 * @time 2016��4��30�� ����4:27:00
	 */
	public void delGasRecord(String gasId){
		oilDao.delGasRecord(gasId);
	}
	
	/** 
	 * @method ������ͼ�¼��Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:27:16
	 */
	public Map saveGasRecord(HttpServletRequest request){
		 return oilDao.saveGasRecord(request);
	}
	
	/** 
	 * @method ��ȡ�޸ļ��ͼ�¼��Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:27:36
	 */
	public Map getGasRecord(String gasId){
		return oilDao.getGasRecord(gasId);
	}
	
	/** 
	 * @method ��ѯ��ǰ�û����ͼ�¼
	 * @author Instant
	 * @time 2016��4��30�� ����4:27:52
	 */
	public List<Map> queryMyGasRecord(HttpServletRequest request) {
		List<Map> list = oilDao.queryMyGasRecord(request);
		return list;
	}
	
	/** 
	 * @method ��ȡ��6���µ�������Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:39:22
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
		    categories[1]=year+"��"+month+"��";
		    categories[2]=year2+"��"+month2+"��";
		    categories[3]=year3+"��"+month3+"��";
		    categories[4]=year4+"��"+month4+"��";
		    categories[5]=year5+"��"+month5+"��";
		    categories[6]=year6+"��"+month6+"��";
		    Map mapInfo = new HashMap<>();
		    mapInfo.put("categories", categories);
		    mapInfo.put("qi97", qi97);
		    mapInfo.put("qi95", qi95);
		    mapInfo.put("chaiGas", chaiGas);
		    return mapInfo;
	}
	
	/** 
	 * @method ��ȡ��һ���µ���ݺ��·�
	 * @author Instant
	 * @time 2016��4��30�� ����4:40:57
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
	 * @method ��ȡ�����Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:39:51
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
