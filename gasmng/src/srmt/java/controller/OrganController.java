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

import srmt.java.service.OrganService;

@Controller
@RequestMapping("/organMng")
public class OrganController {
	@Autowired
	private OrganService organSevrvice;

	@RequestMapping("/enterOrganMng.do")
	public ModelAndView enterOrganMng() {

		return new ModelAndView("organMng/organMng");
	}

	/** 
	 * @method 查询单位列表
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:02
	 */
	@ResponseBody
	@RequestMapping("/queryOragnList.do")
	public List<Map> queryOragnList(HttpServletRequest request) {
		List<Map> list = organSevrvice.queryOragnList(request);
		return list;
	}

	/** 
	 * @method 查询单位用于下拉列表
	 * @author Instant
	 * @time 2016年4月30日 下午4:16:20
	 */
	@ResponseBody
	@RequestMapping("/queryOragn.do")
	public List<Map> queryOragn(HttpServletRequest request) {
		List<Map> list = organSevrvice.queryOragn(request);
		return list;
	}

	/** 
	 * @method 取消注销单位
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:29
	 */
	@ResponseBody
	@RequestMapping("/unLayoutOrgan.do")
	public Map unLayoutOrgan(HttpServletRequest request) {
		String organId = request.getParameter("organId");
		organSevrvice.unLayoutOrgan(organId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	/** 
	 * @method 注销单位
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:41
	 */
	@ResponseBody
	@RequestMapping("/layoutOrgan.do")
	public Map layoutOrgan(HttpServletRequest request) {
		String organId = request.getParameter("organId");
		organSevrvice.layoutOrgan(organId);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	/** 
	 * @method 删除单位
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:20
	 */
	@ResponseBody
	@RequestMapping("/delOrgan.do")
	public Map delOrgan(HttpServletRequest request) {
		organSevrvice.delOrgan(request);
		Map map = new HashMap();
		map.put("success", true);
		return map;
	}

	/** 
	 * @method 获取修改单位信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:48
	 */
	@ResponseBody
	@RequestMapping("/getOrganInfo.do")
	public Map getOrganInfo(HttpServletRequest request) {
		String organId = request.getParameter("organId");
		return organSevrvice.getOrganInfo(organId);
	}

	/** 
	 * @method 保存单位信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:16:07
	 */
	@ResponseBody
	@RequestMapping("/saveOrgan.do")
	public Map saveOrgan(HttpServletRequest request) {;
		Map map = organSevrvice.saveOrgan(request);
		return map;
	}
}
