package srmt.java.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srmt.java.dao.OrganDao;

@Service
public class OrganService {
	@Autowired
	private OrganDao organDao;

	/** 
	 * @method 查询单位列表
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:02
	 */
	public List<Map> queryOragnList(HttpServletRequest request) {
		List<Map> list = organDao.queryOragnList(request);
		return list;
	}

	/** 
	 * @method 注销单位
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:41
	 */
	public void layoutOrgan(String organId) {
		organDao.layoutOrgan(organId);
	}

	/** 
	 * @method 取消注销单位
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:29
	 */
	public void unLayoutOrgan(String organId) {
		organDao.unLayoutOrgan(organId);
	}

	/** 
	 * @method 删除单位
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:20
	 */
	public void delOrgan(HttpServletRequest request) {
		organDao.delOrgan(request);
	}

	/** 
	 * @method 获取修改单位信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:15:48
	 */
	public Map getOrganInfo(String organId) {
		return organDao.getOrganInfo(organId);
	}

	
	/** 
	 * @method 保存单位信息
	 * @author Instant
	 * @time 2016年4月30日 下午4:16:07
	 */
	public Map saveOrgan(HttpServletRequest request) {
		return organDao.saveOrgan(request);
	}
	/** 
	 * @method 查询单位用于下拉列表
	 * @author Instant
	 * @time 2016年4月30日 下午4:16:20
	 */
	public List<Map> queryOragn(HttpServletRequest request) {
		List<Map> list = organDao.queryOrgan(request);
		return list;
	}
}
