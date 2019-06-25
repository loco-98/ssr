package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

public class Dao {
	/**
	 * ����֪���Ƿ��������У�
	 * ����������У����ܹرգ���Ϊ�����п��ܴ��ں����������ã�������������Ҫʹ��ͬһ��con
	 * ������������У���ͱ���Ҫ�رգ�
	 * @param id
	 * @param balance
	 * @throws SQLException
	 */
	public void update(int id, double balance) throws SQLException {
		String sql = "update account set balance=balance+? where id=?";
		QueryRunner qr = new TxQueryRunner();
		qr.update(sql, balance, id);
	}
}
