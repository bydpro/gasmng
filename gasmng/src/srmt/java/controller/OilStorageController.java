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
	 * @method 查询入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:24:23
	 */
	@ResponseBody
	@RequestMapping("/queryOilStorage.do")
	public List<Map> queryOilStorage(HttpServletRequest request) {
		List<Map> list = oilStorageService.queryOilStorage(request);
		return list;
	}
	
	/** 
	 * @method 进入入库管理界面
	 * @author Instant
	 * @time 2016年4月30日 下午4:33:48
	 */
	@RequestMapping("/enterUserMng.do")
	public ModelAndView enterUserMng() {

		return new ModelAndView("oilStorageMng/oilStorageMng");
	}
	
	/** 
	 * @method 删除入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:25:47
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
	 * @method 保存入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:24:37
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
	 * @method 获取修改入库信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:25:17
	 */
	@ResponseBody
	@RequestMapping("/getOliStirage.do")
	public Map getOliStirage(HttpServletRequest request){
		String oilStorageId = request.getParameter("oilStorageId");
		return oilStorageService.getOliStirage(oilStorageId);
	}
	
	/** 
	 * @method 查询油品类型
	 * @author Instant
	 * @time 2016年4月30日 下午4:25:58
	 */
	@ResponseBody
	@RequestMapping("/queryOilType.do")
	public List<Map> queryOilType() {
		List<Map> list = oilStorageService.queryOilType();
		return list;
	}
	
	/** 
	 * @method 查询油品记录
	 * @author Instant
	 * @time 2016年4月30日 下午4:26:14
	 */
	@ResponseBody
	@RequestMapping("/queryGasRecord.do")
	public List<Map> queryGasRecord(HttpServletRequest request) {
		List<Map> list = oilStorageService.queryGasRecord(request);
		return list;
	}
	
	/** 
	 * @method 进入加油记录管理界面
	 * @author Instant
	 * @time 2016年4月30日 下午4:36:03
	 */
	@RequestMapping("/enterGasRecord.do")
	public ModelAndView enterGasRecord() {

		return new ModelAndView("gas/gasRecordMng");
	}
	
	/** 
	 * @method 删除加油记录
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:00
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
	 * @method 保存加油记录信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:16
	 */
	@ResponseBody
	@RequestMapping("/saveGasRecord.do")
	public Map saveGasRecord(HttpServletRequest request){
		Map map=oilStorageService.saveGasRecord(request);
		return map;
	}
	
	/** 
	 * @method 获取修改加油记录信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:36
	 */
	@ResponseBody
	@RequestMapping("/getGasRecord.do")
	public Map getGasRecord(HttpServletRequest request){
		String gasId = request.getParameter("gasId");
		return oilStorageService.getGasRecord(gasId);
	}
	
	/** 
	 * @method 进入当前用户加油记录查看界面
	 * @author Instant
	 * @time 2016年4月30日 下午4:37:59
	 */
	@RequestMapping("/enterMyOilRecord.do")
	public ModelAndView enterMyOilRecord() {

		return new ModelAndView("gas/myGasRecord");
	}
	
	/** 
	 * @method 查询当前用户加油记录
	 * @author Instant
	 * @time 2016年4月30日 下午4:27:52
	 */
	@ResponseBody
	@RequestMapping("/queryMyGasRecord.do")
	public List<Map> queryMyGasRecord(HttpServletRequest request) {
		List<Map> list = oilStorageService.queryMyGasRecord(request);
		return list;
	}
	
	/** 
	 * @method 进入充值界面
	 * @author Instant
	 * @time 2016年4月30日 下午4:38:31
	 */
	@RequestMapping("/enterRecharge.do")
	public ModelAndView enterRecharge() {

		return new ModelAndView("gas/recharge");
	}
	
	/** 
	 * @method 进入销售信息统计界面
	 * @author Instant
	 * @time 2016年4月30日 下午4:38:48
	 */
	@RequestMapping("/enterGasSaleMng.do")
	public ModelAndView enterGasSaleMng() {

		return new ModelAndView("gas/gasSaleMng");
	}
	
	/** 
	 * @method 获取近6个月的销售信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:39:22
	 */
	@ResponseBody
	@RequestMapping("/getOilSalInfo.do")
	public Map getOilSalInfo(HttpServletRequest request) {
		Map  map= oilStorageService.getOilSalInfo();
		return map;
	}
	
	/** 
	 * @method 获取库存信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:39:51
	 */
	@ResponseBody
	@RequestMapping("/queryOilStorage4All.do")
	public Map queryOilStorage4All(HttpServletRequest request) {
		Map  map= oilStorageService.queryOilStorage4All();
		return map;
	}
}
