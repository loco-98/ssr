package cn.itcast.mail;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.junit.Test;

public class MailUtilsDemo {
	@Test
	public void fun1() throws MessagingException, IOException {
		// ����һ��Mail��Ķ��󣬲���ָ�������˺��ռ���
		Mail mail = new Mail("itcast_cxf@163.com", "itcast_cxf@126.com");
		mail.setSubject("���ǲ����ʼ�");//��������
		mail.setContent("�������ģ����������ʼ���");//��������
		
		// ����Session������Ҫ�������������������û���������
		Session session = MailUtils.createSession("smtp.163.com", "itcast_cxf", "itcast");
		// ���ʼ�����Ҫsession��Mail����
		MailUtils.send(session, mail);
	}
}
