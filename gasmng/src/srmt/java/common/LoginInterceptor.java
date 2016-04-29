package srmt.java.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
	private static final String LOGIN_URL = "/gasmng/enterLoginPage.do";
	private static final String URL = "/gasmng/enterLoginPage.do , /gasmng/randValidateCode.do ,/gasmng/loginIn.do";

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		Boolean flag=true;
		String requestUrl = req.getRequestURI();
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute(Constants.USER_ID);
		// �ж����û��ȡ���û���Ϣ������ת����½ҳ�棬��ʾ�û����е�½
		if (URL.contains(requestUrl)) {
			flag= true;
		} else if (StringUtils.isEmpty(userId)) {
			res.sendRedirect(LOGIN_URL);
			flag =false;
		}
		return flag;
	}

}
