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
	 * @method ��ѯ��λ�б�
	 * @author Instant
	 * @time 2016��4��30�� ����4:15:02
	 */
	public List<Map> queryOragnList(HttpServletRequest request) {
		List<Map> list = organDao.queryOragnList(request);
		return list;
	}

	/** 
	 * @method ע����λ
	 * @author Instant
	 * @time 2016��4��30�� ����4:15:41
	 */
	public void layoutOrgan(String organId) {
		organDao.layoutOrgan(organId);
	}

	/** 
	 * @method ȡ��ע����λ
	 * @author Instant
	 * @time 2016��4��30�� ����4:15:29
	 */
	public void unLayoutOrgan(String organId) {
		organDao.unLayoutOrgan(organId);
	}

	/** 
	 * @method ɾ����λ
	 * @author Instant
	 * @time 2016��4��30�� ����4:15:20
	 */
	public void delOrgan(HttpServletRequest request) {
		organDao.delOrgan(request);
	}

	/** 
	 * @method ��ȡ�޸ĵ�λ��Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:15:48
	 */
	public Map getOrganInfo(String organId) {
		return organDao.getOrganInfo(organId);
	}

	
	/** 
	 * @method ���浥λ��Ϣ
	 * @author Instant
	 * @time 2016��4��30�� ����4:16:07
	 */
	public Map saveOrgan(HttpServletRequest request) {
		return organDao.saveOrgan(request);
	}
	/** 
	 * @method ��ѯ��λ���������б�
	 * @author Instant
	 * @time 2016��4��30�� ����4:16:20
	 */
	public List<Map> queryOragn(HttpServletRequest request) {
		List<Map> list = organDao.queryOrgan(request);
		return list;
	}
}
