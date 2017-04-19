package model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

/***
 * 数据库连接池
 */
public class DBConnectionPool {
	/***
	 * 数据库连接
	 */
	private Connection con = null;
	
	/***
	 * 使用中的连接数
	 */
	private int inUsed = 0;

	/***
	 * 装 空闲连接的容器
	 */
	private ArrayList<Connection> freeConnections = new ArrayList<Connection>();

	/***
	 * 最小连接数
	 */
	private int minConn; 

	/**
	 * 最大连接数
	 */
	private int maxConn;
	
	/**
	 * 连接池名字
	 */
	private String name;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 数据库连接地址
	 */
	private String url;
	
	/**
	 * 数据库驱动
	 */
	private String driver;
	
	/**
	 * 用户名
	 */
	private String user;
	
	/**
	 * 定时
	 */
	public Timer timer;
	
	/**
	 * 创建连接池
	 * @param name 连接池名字
	 * @param driver 数据库驱动
	 * @param URL 数据库连接地址
	 * @param user 数据库连接用户名
	 * @param password 密码
	 * @param maxConn 最大连接数
	 */
	public DBConnectionPool(String name, String driver, String URL, String user, String password, int maxConn) {
		this.name = name;
		this.driver = driver;
		this.url = URL;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
	}
	
	/**
	 * 释放连接
	 * @param con 数据库连接
	 */
	public synchronized void freeConnection(Connection con) {
		this.freeConnections.add(con);
		this.inUsed--;
	}
	
	/**
	 * 获取数据库连接
	 * @param timeout 根据timeout来选择什么时候给予数据库连接
	 */
	public synchronized Connection getConnection(long timeout) {
		Connection con = null;
		// 首先看目前是否有空闲的数据库连接，有的话，用空闲的数据库连接，没有的话，新建数据库连接
		if (this.freeConnections.size() > 0) {
			con = this.freeConnections.get(0);
			if (con == null) { 
				con = getConnection(timeout);	// 是空的，这次没有获取成功，继续递归获取连接
			} else { // 不为空，获取成功，从空闲连接中删除
				this.freeConnections.remove(0);
			}
		} else {
			con = newConnection();
		}
		
		this.isBeyond(con);
		
		return con;
	}
	
	/**
	 * 获取数据库连接
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		if (this.freeConnections.size() > 0) {
			con = this.freeConnections.get(0);
			if (con == null) { 
				con = getConnection();	// 是空的，这次没有获取成功，继续递归获取连接
			} else { // 不为空，获取成功，从空闲连接中删除
				this.freeConnections.remove(0);
			}
		} else {
			con = newConnection(); // 新建连接
		}
		
		this.isBeyond(con);
		
		return con;
	}
	
	/**
	 * 释放全部连接
	 */
	public synchronized void release() {
		Iterator<Connection> allConns = this.freeConnections.iterator();
		while (allConns.hasNext()) {
			Connection conn = (Connection) allConns.next();
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		this.freeConnections.clear();
	}
	
	/**
	 * 创建新的连接
	 */
	private Connection newConnection() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Can't find database driver");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Can't create Connection");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("链接数据库出错");
		}
		
		return this.con;
	}
	
	/**
	 * 判断是否超过最大连接数
	 * @param con 数据库连接
	 */
	void isBeyond(Connection con) {
		if (this.maxConn < this.inUsed) { // 达到最大连接数，暂时不能获取连接了
			con = null; 
		}
		
		if (con != null) {
			this.inUsed++;
		}
	}
	
	/**
	 * 定时处理函数
	 */
	public synchronized void TimerEvent() {
		
	}

	public int getInUsed() {
		return inUsed;
	}

	public void setInUsed(int inUsed) {
		this.inUsed = inUsed;
	}

	public ArrayList<Connection> getFreeConnections() {
		return freeConnections;
	}

	public void setFreeConnections(ArrayList<Connection> freeConnections) {
		this.freeConnections = freeConnections;
	}

	public int getMinConn() {
		return minConn;
	}

	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
