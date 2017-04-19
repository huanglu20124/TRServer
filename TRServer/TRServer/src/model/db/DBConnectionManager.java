package model.db;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/***
 * 数据库管理类
 * 
 * 单例模式实现
 */
public class DBConnectionManager {
	/**
	 * 客户连接数
	 */
	static private int clients;
	
	/**
	 * 数据库连接驱动信息
	 */
	private Vector<DSConfigBean> drivers = new Vector<DSConfigBean>();

	/**
	 * 装连接池的容器
	 */
	 private Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>();

	/**
	 * 实例化管理类
	 */
	private DBConnectionManager() {
		this.init();
	}
	
	/**
	 * 私有静态内部类实现饿汉模式
	 */
	private static class DBConnectionManagerLoader{
		/**
		 * 唯一数据库连接池管理实例类
		 */
		static private DBConnectionManager instance = new DBConnectionManager();
		
	}
	
	/**
	 * 得到唯一的实例化管理类
	 */
	static synchronized public DBConnectionManager getInstance() {
		return DBConnectionManagerLoader.instance;
	}
	
	/**
	 * 释放连接
	 * @param name 数据库连接池的名字
	 * @param con 数据库连接
	 */
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = this.pools.get(name);
		if (pool != null) {
			pool.freeConnection(con);
		}
	}
	
	/**
	 * 从数据库连接池中获取连接
	 * @param name 数据库连接池的名字
	 */
	public Connection getConnection(String name) {
		DBConnectionPool pool = this.pools.get(name);
		Connection con = pool.getConnection();
		return con;
	}
	
	
	/**
	 * 从数据库连接池中获取连接
	 * @param name 数据库连接池的名字
	 * @param timeout 连接超时
	 */
	public Connection getConnection(String name, long timeout) {
		DBConnectionPool pool = this.pools.get(name);
		Connection con = pool.getConnection(timeout);
		return con;
	}
	
	/**
	 * 释放所有连接池
	 */
	public synchronized void release() {
		Enumeration<DBConnectionPool> allPools = this.pools.elements();
		while (allPools.hasMoreElements()) {
			DBConnectionPool dbConnectionPool = (DBConnectionPool) allPools
					.nextElement();
			dbConnectionPool.release();
		}
		
		this.pools.clear();
	}
	
	/**
	 * 创建连接池
	 */
	private void createPools(DSConfigBean dsb) {
		DBConnectionPool dbpool = new DBConnectionPool(dsb.getName(), 
				dsb.getDriver(), dsb.getUrl(), dsb.getUsername(), dsb.getPassword(), dsb.getMaxConn());
		this.pools.put(dsb.getName(), dbpool);
	}
	
	/**
	 * 初始化连接池的参数
	 */
	private void init() {
		// 加载驱动程序
		this.loadDrivers();
		// 创建连接池
		Iterator<DSConfigBean> allDrivers = this.drivers.iterator();
		while (allDrivers.hasNext()) {
			DSConfigBean dsConfigBean = (DSConfigBean) allDrivers.next();
			this.createPools(dsConfigBean);
		}
	}
	
	/**
	 * 加载所有数据库驱动程序
	 */
	private void loadDrivers() {
		ParseDSConfig pdConfig = new ParseDSConfig();
		// 读取数据库配置文件
		this.drivers = pdConfig.readConfigInfo("ds.config.xml");
	}
}
