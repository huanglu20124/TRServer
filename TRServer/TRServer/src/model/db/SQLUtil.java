package model.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;

/**
 * Ö´ÐÐSQLÓï¾ä
 *
 */

public class SQLUtil {
	
	/***
	 * Ö´ÐÐ¸üÐÂÓï¾ä
	 */
	public static boolean executeUpdateSQL(String SQL) {
		PreparedStatement preparedStatement = null;
		int result = 0;
		Connection connection = null;
		try {
			connection = DBUtil.getCon();
			preparedStatement = connection.prepareStatement(SQL);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			DBUtil.freeConnection(connection);
		}
		
		return result == 0 ? false : true;
	}
	

	/***
	 * Ö´ÐÐ²éÑ¯SQLÓï¾ä
	 */
	public static List<Map<String, Object>> executeQuerySQL(String SQL) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ResultSet rs = null;
		ResultSetMetaData resultSetMetaData = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		try {
			connection = DBUtil.getCon();
			preparedStatement = connection.prepareStatement(SQL);
			rs = preparedStatement.executeQuery();
			resultSetMetaData = rs.getMetaData();
			int colCount = resultSetMetaData.getColumnCount();
			
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				for (int i = 1; i <= colCount; i++) {
					String key = resultSetMetaData.getColumnName(i);
					String value = rs.getString(i);
					map.put(key, value);
				}
				
				list.add(map);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			DBUtil.freeConnection(connection);
		}
		
		return list;
	}
}
