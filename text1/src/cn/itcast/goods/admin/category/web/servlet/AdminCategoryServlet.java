package cn.itcast.goods.admin.category.web.servlet;

import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.service.BookService;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.goods.category.service.CategoryService;
import java.util.List;
/**
 * Servlet implementation class AdminCategoryServlet
 */
@SuppressWarnings("serial")
@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	private BookService bookService = new BookService(); 
	
	/**
	 * ��ѯ���з���
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("parents", categoryService.findAll());
		return "f:/adminjsps/admin/category/list.jsp";
	}
	
	/**
	 * ���һ������
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. ��װ�����ݵ�Category��
		 * 2. ����service��add()����������
		 * 3. ����findAll()������list.jsp��ʾ���з���
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		parent.setCid(CommonUtils.uuid());//����cid
		categoryService.add(parent);
		return findAll(req, resp);
	}
	
	public String addChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. ��װ�����ݵ�Category��
		 * 2. ��Ҫ�ֶ��İѱ��е�pidӳ�䵽child������
		 * 2. ����service��add()����������
		 * 3. ����findAll()������list.jsp��ʾ���з���
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		child.setCid(CommonUtils.uuid());//����cid
		
		// �ֶ�ӳ��pid
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		
		categoryService.add(child);
		return findAll(req, resp);
	}
	
	/**
	 * ��ӵڶ����ࣺ��һ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pid = req.getParameter("pid");//��ǰ����ĸ�����id
		List<Category> parents = categoryService.findParents();
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);
		
		return "f:/adminjsps/admin/category/add2.jsp";
	}
	
	/**
	 * �޸�һ�����ࣺ��һ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParentPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. ��ȡ�����е�cid
		 * 2. ʹ��cid����Category
		 * 3. ����Category
		 * 4. ת����edit.jspҳ����ʾCategory
		 */
		String cid = req.getParameter("cid");
		Category parent = categoryService.load(cid);
		req.setAttribute("parent", parent);
		return "f:/adminjsps/admin/category/edit.jsp";
	}
	
	/**
	 * �޸�һ�����ࣺ�ڶ���
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. ��װ�����ݵ�Category��
		 * 2. ����service��������޸�
		 * 3. ת����list.jsp��ʾ���з��ࣨreturn findAll();��
		 */
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		categoryService.edit(parent);
		return findAll(req, resp);
	}
	
	/**
	 * �޸Ķ������ࣺ��һ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. ��ȡ���Ӳ���cid��ͨ��cid����Category������֮
		 * 2. ��ѯ������1�����࣬����֮
		 * 3. ת����edit2.jsp
		 */
		String cid = req.getParameter("cid");
		Category child = categoryService.load(cid);
		req.setAttribute("child", child);
		req.setAttribute("parents", categoryService.findParents());
		
		return "f:/adminjsps/admin/category/edit2.jsp";
	}
	
	/**
	 * �޸Ķ������ࣺ�ڶ���
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. ��װ��������Category child
		 * 2. �ѱ��е�pid��װ��child, ...
		 * 3. ����service.edit()����޸�
		 * 4. ���ص�list.jsp
		 */
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		
		categoryService.edit(child);
		return findAll(req, resp);
	}
	
	/**
	 * ɾ��һ������
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. ��ȡ���Ӳ���cid������һ��1�������id
		 * 2. ͨ��cid���鿴�ø��������ӷ���ĸ���
		 * 3. ��������㣬˵�������ӷ��࣬����ɾ�������������Ϣ��ת����msg.jsp
		 * 4. ��������㣬ɾ��֮�����ص�list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = categoryService.findChildrenCountByParent(cid);
		if(cnt > 0) {
			req.setAttribute("msg", "�÷����»����ӷ��࣬����ɾ����");
			return "f:/adminjsps/msg.jsp";
		} else {
			categoryService.delete(cid);
			return findAll(req, resp);
		}
	}
	
	/**
	 * ɾ��2������
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. ��ȡcid����2������id
		 * 2. ��ȡ�÷����µ�ͼ�����
		 * 3. ��������㣬���������Ϣ��ת����msg.jsp
		 * 4. ��������㣬ɾ��֮�����ص�list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = bookService.findBookCountByCategory(cid);
		if(cnt > 0) {
			req.setAttribute("msg", "�÷����»�����ͼ�飬����ɾ����");
			return "f:/adminjsps/msg.jsp";
		} else {
			categoryService.delete(cid);
			return findAll(req, resp);
		}
	}

}
