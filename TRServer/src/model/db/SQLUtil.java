package model.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;

/**
 * ִ��SQL���
 *
 */

public class SQLUtil {
	
	/**
	 * ִ����������
	 */
	public static void executeUpdataBatch(List list) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DBUtil.getCon();
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				String sql = CreateSqlByReflect(obj);
				preparedStatement = connection.prepareStatement(sql);  
				preparedStatement.executeUpdate();
			}
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			DBUtil.freeConnection(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}
	
	/**
	 * ���䣬��ȡ��������ԣ�������ֵ�����һ��sql���
	 * @param obj
	 * @return
	 */
	private static String CreateSqlByReflect(Object obj) {
		String sql = "insert into ";
		Class c = obj.getClass();
		// ��ȡ���з���
		Method[] methods = c.getMethods();
		// �õ���������������
		Field[] fields = c.getFields();
		// �õ���������֣����ڱ��������һ��õ��ľ��Ǳ���
		String tableName = c.getName();
		tableName = tableName.substring(tableName.lastIndexOf('.')+1, tableName.length());
		sql += tableName + "(";
		// �ֶ�������
		List<String> mList = new ArrayList<String>();
		// �ֶ�ֵ�б�
		List vList = new ArrayList();
		
		for(Method method : methods) {
			String mName = method.getName();
			if(mName.startsWith("get") && !mName.startsWith("getClass")) {
				String fieldName = mName.substring(3, mName.length());
				mList.add(fieldName);
				
				try {
					Object value = method.invoke(obj, null);
					if (value instanceof String) {
						vList.add("'" + value + "'");
					} else {
						vList.add(value);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
		// ����ȡ���ֶ�����ֶ�ֵ���ӳ�Ϊsql���
		for (int i = 0; i < mList.size(); i++) {
			if (i<mList.size()-1) {
				sql += mList.get(i) + ",";
			} else {
				sql += mList.get(i) + ") values(";
			}
		}
		
		for (int i = 0; i < vList.size(); i++) {
			if (i < vList.size()-1) {
				sql += vList.get(i)+",";
			} else {
				sql += vList.get(i)+")";
			}
		}
		
		
		return sql;
	}
	
	
	
	/***
	 * ִ�и������
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
	

	/**
	 * 根据sql语句返回记录的数量
	 */
	public static Integer getCountFromSql(String SQL) {
		Integer count = 0;
		
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		try {
			connection = DBUtil.getCon();
			preparedStatement = connection.prepareStatement(SQL);
			rs = preparedStatement.executeQuery();
		
			while(rs.next()) {
				count++;
			}
			
		}	catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return count;
	}
	
	
	/***
	 * ִ�в�ѯSQL���
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
