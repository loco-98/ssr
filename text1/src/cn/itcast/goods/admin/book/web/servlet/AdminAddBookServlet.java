package cn.itcast.goods.admin.book.web.servlet;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.book.service.BookService;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.goods.category.service.CategoryService;
/**
 * Servlet implementation class AdminAddBookServlet
 */
@SuppressWarnings("serial")
@WebServlet("/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		/*
		 * 1. commons-fileupload���ϴ�����
		 */
		// ��������
		FileItemFactory factory = new DiskFileItemFactory();
		/*
		 * 2. ��������������
		 */
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(80 * 1024);//���õ����ϴ����ļ�����Ϊ80KB
		/*
		 * 3. ����request�õ�List<FileItem>
		 */
		List<FileItem> fileItemList = null;
		try {
			fileItemList = sfu.parseRequest(request);
		} catch (FileUploadException e) {
			// �����������첽��˵�������ļ�������80KB
			error("�ϴ����ļ�������80KB", request, response);
			return;
		}
		
		/*
		 * 4. ��List<FileItem>��װ��Book������
		 * 4.1 ���Ȱѡ���ͨ���ֶΡ��ŵ�һ��Map�У��ٰ�Mapת����Book��Category�����ٽ������ߵĹ�ϵ
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		for(FileItem fileItem : fileItemList) {
			if(fileItem.isFormField()) {//�������ͨ���ֶ�
				map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
			}
		}
		Book book = CommonUtils.toBean(map, Book.class);//��Map�д󲿷����ݷ�װ��Book������
		Category category = CommonUtils.toBean(map, Category.class);//��Map��cid��װ��Category��
		book.setCategory(category);
		
		/*
		 * 4.2 ���ϴ���ͼƬ��������
		 *   > ��ȡ�ļ�������ȡ֮
		 *   > ���ļ����ǰ׺��ʹ��uuidǰ׺��ΪҲ�����ļ�ͬ������
		 *   > У���ļ�����չ����ֻ����jpg
		 *   > У��ͼƬ�ĳߴ�
		 *   > ָ��ͼƬ�ı���·��������Ҫʹ��ServletContext#getRealPath()
		 *   > ����֮
		 *   > ��ͼƬ��·�����ø�Book����
		 */
		// ��ȡ�ļ���
		FileItem fileItem = fileItemList.get(1);//��ȡ��ͼ
		String filename = fileItem.getName();
		// ��ȡ�ļ�������Ϊ����������ϴ��ľ���·��
		int index = filename.lastIndexOf("\\");
		if(index != -1) {
			filename = filename.substring(index + 1);
		}
		// ���ļ������uuidǰ׺�������ļ�ͬ������
		filename = CommonUtils.uuid() + "_" + filename;
		// У���ļ����Ƶ���չ��
		if(!filename.toLowerCase().endsWith(".jpg")) {
			error("�ϴ���ͼƬ��չ��������JPG", request, response);
			return;
		}
		// У��ͼƬ�ĳߴ�
		// �����ϴ���ͼƬ����ͼƬnew��ͼƬ����Image��Icon��ImageIcon��BufferedImage��ImageIO
		/*
		 * ����ͼƬ��
		 * 1. ��ȡ��ʵ·��
		 */
		String savepath = this.getServletContext().getRealPath("/book_img");
		/*
		 * 2. ����Ŀ���ļ�
		 */
		File destFile = new File(savepath, filename);
		/*
		 * 3. �����ļ�
		 */
		try {
			fileItem.write(destFile);//�������ʱ�ļ��ض���ָ����·������ɾ����ʱ�ļ�
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// У��ߴ�
		// 1. ʹ���ļ�·������ImageIcon
		ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
		// 2. ͨ��ImageIcon�õ�Image����
		Image image = icon.getImage();
		// 3. ��ȡ���������У��
		if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
			error("���ϴ���ͼƬ�ߴ糬����350*350��", request, response);
			destFile.delete();//ɾ��ͼƬ
			return;
		}
		
		// ��ͼƬ��·�����ø�book����
		book.setImage_w("book_img/" + filename);
		
		


		// ��ȡ�ļ���
		fileItem = fileItemList.get(2);//��ȡСͼ
		filename = fileItem.getName();
		// ��ȡ�ļ�������Ϊ����������ϴ��ľ���·��
		index = filename.lastIndexOf("\\");
		if(index != -1) {
			filename = filename.substring(index + 1);
		}
		// ���ļ������uuidǰ׺�������ļ�ͬ������
		filename = CommonUtils.uuid() + "_" + filename;
		// У���ļ����Ƶ���չ��
		if(!filename.toLowerCase().endsWith(".jpg")) {
			error("�ϴ���ͼƬ��չ��������JPG", request, response);
			return;
		}
		// У��ͼƬ�ĳߴ�
		// �����ϴ���ͼƬ����ͼƬnew��ͼƬ����Image��Icon��ImageIcon��BufferedImage��ImageIO
		/*
		 * ����ͼƬ��
		 * 1. ��ȡ��ʵ·��
		 */
		savepath = this.getServletContext().getRealPath("/book_img");
		/*
		 * 2. ����Ŀ���ļ�
		 */
		destFile = new File(savepath, filename);
		/*
		 * 3. �����ļ�
		 */
		try {
			fileItem.write(destFile);//�������ʱ�ļ��ض���ָ����·������ɾ����ʱ�ļ�
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// У��ߴ�
		// 1. ʹ���ļ�·������ImageIcon
		icon = new ImageIcon(destFile.getAbsolutePath());
		// 2. ͨ��ImageIcon�õ�Image����
		image = icon.getImage();
		// 3. ��ȡ���������У��
		if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
			error("���ϴ���ͼƬ�ߴ糬����350*350��", request, response);
			destFile.delete();//ɾ��ͼƬ
			return;
		}
		
		// ��ͼƬ��·�����ø�book����
		book.setImage_b("book_img/" + filename);
		
		
		
		
		// ����service��ɱ���
		book.setBid(CommonUtils.uuid());
		BookService bookService = new BookService();
		bookService.add(book);
		
		// ����ɹ���Ϣת����msg.jsp
		request.setAttribute("msg", "���ͼ��ɹ���");
		request.getRequestDispatcher("/adminjsps/msg.jsp").forward(request, response);
	}
	
	/*
	 * ���������Ϣ��ת����add.jsp
	 */
	private void error(String msg, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("msg", msg);
		request.setAttribute("parents", new CategoryService().findParents());//����һ������
		request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").
				forward(request, response);
	}

}
