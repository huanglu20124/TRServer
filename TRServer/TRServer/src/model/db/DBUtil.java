package model.db;
/**
 * 对DBConnectionManager做一层封装，方便当前开发
 * 	进行数据库的连接
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
