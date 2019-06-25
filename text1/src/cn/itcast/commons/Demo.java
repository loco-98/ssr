package cn.itcast.commons;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class Demo {
	/**
	 * дһ��Map��map�е�StringҪ��װ��User��age��
	 * BeanUtils���԰�Stringת����int
	 */
	@Test
	public void fun1(){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("username", "����");
		map.put("password", "123");
		map.put("age", "55");
		
		User user = CommonUtils.toBean(map, User.class);
		System.out.println(user);
	}
	
	@Test
	public void fun2(){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("username", "����");
		map.put("password", "123");
		map.put("age", "55");
		map.put("birthday", "2013-01-29");
		
		User user = CommonUtils.toBean(map, User.class);
		System.out.println(user);
	}
}
