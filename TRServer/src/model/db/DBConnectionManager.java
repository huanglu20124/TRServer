package model.db;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/***
 * ���ݿ������
 * 
 * ����ģʽʵ��
 */
public class DBConnectionManager {
	/**
	 * �ͻ�������
	 */
	static private int clients;
	
	/**
	 * ���ݿ�����������Ϣ
	 */
	private Vector<DSConfigBean> drivers = new Vector<DSConfigBean>();

	/**
	 * װ���ӳص�����
	 */
	 private Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>();

	/**
	 * ʵ����������
	 */
	private DBConnectionManager() {
		this.init();
	}
	
	/**
	 * ˽�о�̬�ڲ���ʵ�ֶ���ģʽ
	 */
	private static class DBConnectionManagerLoader{
		/**
		 * Ψһ���ݿ����ӳع���ʵ����
		 */
		static private DBConnectionManager instance = new DBConnectionManager();
		
	}
	
	/**
	 * �õ�Ψһ��ʵ����������
	 */
	static synchronized public DBConnectionManager getInstance() {
		return DBConnectionManagerLoader.instance;
	}
	
	/**
	 * �ͷ�����
	 * @param name ���ݿ����ӳص�����
	 * @param con ���ݿ�����
	 */
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = this.pools.get(name);
		if (pool != null) {
			pool.freeConnection(con);
		}
	}
	
	/**
	 * �����ݿ����ӳ��л�ȡ����
	 * @param name ���ݿ����ӳص�����
	 */
	public Connection getConnection(String name) {
		DBConnectionPool pool = this.pools.get(name);
		Connection con = pool.getConnection();
		return con;
	}
	
	
	/**
	 * �����ݿ����ӳ��л�ȡ����
	 * @param name ���ݿ����ӳص�����
	 * @param timeout ���ӳ�ʱ
	 */
	public Connection getConnection(String name, long timeout) {
		DBConnectionPool pool = this.pools.get(name);
		Connection con = pool.getConnection(timeout);
		return con;
	}
	
	/**
	 * �ͷ��������ӳ�
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
	 * �������ӳ�
	 */
	private void createPools(DSConfigBean dsb) {
		DBConnectionPool dbpool = new DBConnectionPool(dsb.getName(), 
				dsb.getDriver(), dsb.getUrl(), dsb.getUsername(), dsb.getPassword(), dsb.getMaxConn());
		this.pools.put(dsb.getName(), dbpool);
	}
	
	/**
	 * ��ʼ�����ӳصĲ���
	 */
	private void init() {
		// ������������
		this.loadDrivers();
		// �������ӳ�
		Iterator<DSConfigBean> allDrivers = this.drivers.iterator();
		while (allDrivers.hasNext()) {
			DSConfigBean dsConfigBean = (DSConfigBean) allDrivers.next();
			this.createPools(dsConfigBean);
		}
	}
	
	/**
	 * �����������ݿ���������
	 */
	private void loadDrivers() {
		ParseDSConfig pdConfig = new ParseDSConfig();
		// ��ȡ���ݿ������ļ�
		this.drivers = pdConfig.readConfigInfo("ds.config.xml");
	}
}
