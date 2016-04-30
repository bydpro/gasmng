package srmt.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import srmt.java.service.OilStorageService;

@Controller
@RequestMapping("/oilStorage")
public class OilStorageController {
	@Autowired
	private OilStorageService oilStorageService;

	/**
	 * @method ��ѯ�����Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:24:23
	 */
	@ResponseBody
	@RequestMapping("/queryOilStorage.do")
	public List<Map> queryOilStorage(HttpServletRequest request) {
		List<Map> list = oilStorageService.queryOilStorage(request);
		return list;
	}
	
	/** 
	 * @method �������������
	 * @author Instant
	 * @time 2016��4��30�� ����4:33:48
	 */
	@RequestMapping("/enterUserMng.do")
	public ModelAndView enterUserMng() {

		return new ModelAndView("oilStorageMng/oilStorageMng");
	}
	
	/** 
	 * @method ɾ�������Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:25:47
	 */
	@ResponseBody
	@RequestMapping("/delOliStirage.do")
	public Map delOliStirage(HttpServletRequest request){
		String oilStorageId = request.getParameter("oilStorageId");
		oilStorageService.delOliStirage(oilStorageId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}
	
	/**
	 * @method ���������Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:24:37
	 */
	@ResponseBody
	@RequestMapping("/saveOliStirage.do")
	public Map saveOliStirage(HttpServletRequest request){
		oilStorageService.saveOliStirage(request);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}
	
	/**
	 * @method ��ȡ�޸������Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:25:17
	 */
	@ResponseBody
	@RequestMapping("/getOliStirage.do")
	public Map getOliStirage(HttpServletRequest request){
		String oilStorageId = request.getParameter("oilStorageId");
		return oilStorageService.getOliStirage(oilStorageId);
	}
	
	/** 
	 * @method ��ѯ��Ʒ����
	 * @author Instant
	 * @time 2016��4��30�� ����4:25:58
	 */
	@ResponseBody
	@RequestMapping("/queryOilType.do")
	public List<Map> queryOilType() {
		List<Map> list = oilStorageService.queryOilType();
		return list;
	}
	
	/** 
	 * @method ��ѯ��Ʒ��¼
	 * @author Instant
	 * @time 2016��4��30�� ����4:26:14
	 */
	@ResponseBody
	@RequestMapping("/queryGasRecord.do")
	public List<Map> queryGasRecord(HttpServletRequest request) {
		List<Map> list = oilStorageService.queryGasRecord(request);
		return list;
	}
	
	/** 
	 * @method ������ͼ�¼�������
	 * @author Instant
	 * @time 2016��4��30�� ����4:36:03
	 */
	@RequestMapping("/enterGasRecord.do")
	public ModelAndView enterGasRecord() {

		return new ModelAndView("gas/gasRecordMng");
	}
	
	/** 
	 * @method ɾ�����ͼ�¼
	 * @author Instant
	 * @time 2016��4��30�� ����4:27:00
	 */
	@ResponseBody
	@RequestMapping("/delGasRecord.do")
	public Map delGasRecord(HttpServletRequest request){
		String gasId = request.getParameter("gasId");
		oilStorageService.delGasRecord(gasId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}
	
	/** 
	 * @method ������ͼ�¼��Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:27:16
	 */
	@ResponseBody
	@RequestMapping("/saveGasRecord.do")
	public Map saveGasRecord(HttpServletRequest request){
		Map map=oilStorageService.saveGasRecord(request);
		return map;
	}
	
	/** 
	 * @method ��ȡ�޸ļ��ͼ�¼��Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:27:36
	 */
	@ResponseBody
	@RequestMapping("/getGasRecord.do")
	public Map getGasRecord(HttpServletRequest request){
		String gasId = request.getParameter("gasId");
		return oilStorageService.getGasRecord(gasId);
	}
	
	/** 
	 * @method ���뵱ǰ�û����ͼ�¼�鿴����
	 * @author Instant
	 * @time 2016��4��30�� ����4:37:59
	 */
	@RequestMapping("/enterMyOilRecord.do")
	public ModelAndView enterMyOilRecord() {

		return new ModelAndView("gas/myGasRecord");
	}
	
	/** 
	 * @method ��ѯ��ǰ�û����ͼ�¼
	 * @author Instant
	 * @time 2016��4��30�� ����4:27:52
	 */
	@ResponseBody
	@RequestMapping("/queryMyGasRecord.do")
	public List<Map> queryMyGasRecord(HttpServletRequest request) {
		List<Map> list = oilStorageService.queryMyGasRecord(request);
		return list;
	}
	
	/** 
	 * @method �����ֵ����
	 * @author Instant
	 * @time 2016��4��30�� ����4:38:31
	 */
	@RequestMapping("/enterRecharge.do")
	public ModelAndView enterRecharge() {

		return new ModelAndView("gas/recharge");
	}
	
	/** 
	 * @method ����������Ϣͳ�ƽ���
	 * @author Instant
	 * @time 2016��4��30�� ����4:38:48
	 */
	@RequestMapping("/enterGasSaleMng.do")
	public ModelAndView enterGasSaleMng() {

		return new ModelAndView("gas/gasSaleMng");
	}
	
	/** 
	 * @method ��ȡ��6���µ�������Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:39:22
	 */
	@ResponseBody
	@RequestMapping("/getOilSalInfo.do")
	public Map getOilSalInfo(HttpServletRequest request) {
		Map  map= oilStorageService.getOilSalInfo();
		return map;
	}
	
	/** 
	 * @method ��ȡ�����Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:39:51
	 */
	@ResponseBody
	@RequestMapping("/queryOilStorage4All.do")
	public Map queryOilStorage4All(HttpServletRequest request) {
		Map  map= oilStorageService.queryOilStorage4All();
		return map;
	}
}
