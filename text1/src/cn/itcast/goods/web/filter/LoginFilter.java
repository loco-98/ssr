package cn.itcast.goods.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LoginFilter implements Filter {
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		/*
		 * 1. ��ȡsession�е�user
		 * 2. �ж��Ƿ�Ϊnull
		 *   > ���Ϊnull�����������Ϣ��ת����msg.jsp
		 *   > �����Ϊnull������
		 */
		HttpServletRequest req = (HttpServletRequest) request;
		Object user = req.getSession().getAttribute("sessionUser");
		if(user == null) {
			req.setAttribute("code", "error");//Ϊ����ʾXͼƬ
			req.setAttribute("msg", "����û�е�¼�����ܷ��ʱ���Դ");
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, response);
		} else {
			chain.doFilter(request, response);//����
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}
}
