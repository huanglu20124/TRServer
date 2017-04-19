package model.db;
/**
 * ��DBConnectionManager��һ���װ�����㵱ǰ����
 * 	�������ݿ������
 */

import java.sql.Connection;

public class DBUtil {
	static DBConnectionManager connectionManager = DBConnectionManager.getInstance();
	static String defaultDB = "mysql";
	
	public static Connection getCon() {
		Connection connection = null;
		connection = connectionManager.getConnection(defaultDB);
		return connection;
	}
	
	public static void freeConnection(Connection connection) {
		connectionManager.freeConnection(defaultDB, connection);
	}
}
