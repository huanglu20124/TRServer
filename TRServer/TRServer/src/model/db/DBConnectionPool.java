package model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

/***
 * ���ݿ����ӳ�
 */
public class DBConnectionPool {
	/***
	 * ���ݿ�����
	 */
	private Connection con = null;
	
	/***
	 * ʹ���е�������
	 */
	private int inUsed = 0;

	/***
	 * װ �������ӵ�����
	 */
	private ArrayList<Connection> freeConnections = new ArrayList<Connection>();

	/***
	 * ��С������
	 */
	private int minConn; 

	/**
	 * ���������
	 */
	private int maxConn;
	
	/**
	 * ���ӳ�����
	 */
	private String name;

	/**
	 * ����
	 */
	private String password;

	/**
	 * ���ݿ����ӵ�ַ
	 */
	private String url;
	
	/**
	 * ���ݿ�����
	 */
	private String driver;
	
	/**
	 * �û���
	 */
	private String user;
	
	/**
	 * ��ʱ
	 */
	public Timer timer;
	
	/**
	 * �������ӳ�
	 * @param name ���ӳ�����
	 * @param driver ���ݿ�����
	 * @param URL ���ݿ����ӵ�ַ
	 * @param user ���ݿ������û���
	 * @param password ����
	 * @param maxConn ���������
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
	 * �ͷ�����
	 * @param con ���ݿ�����
	 */
	public synchronized void freeConnection(Connection con) {
		this.freeConnections.add(con);
		this.inUsed--;
	}
	
	/**
	 * ��ȡ���ݿ�����
	 * @param timeout ����timeout��ѡ��ʲôʱ��������ݿ�����
	 */
	public synchronized Connection getConnection(long timeout) {
		Connection con = null;
		// ���ȿ�Ŀǰ�Ƿ��п��е����ݿ����ӣ��еĻ����ÿ��е����ݿ����ӣ�û�еĻ����½����ݿ�����
		if (this.freeConnections.size() > 0) {
			con = this.freeConnections.get(0);
			if (con == null) { 
				con = getConnection(timeout);	// �ǿյģ����û�л�ȡ�ɹ��������ݹ��ȡ����
			} else { // ��Ϊ�գ���ȡ�ɹ����ӿ���������ɾ��
				this.freeConnections.remove(0);
			}
		} else {
			con = newConnection();
		}
		
		this.isBeyond(con);
		
		return con;
	}
	
	/**
	 * ��ȡ���ݿ�����
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		if (this.freeConnections.size() > 0) {
			con = this.freeConnections.get(0);
			if (con == null) { 
				con = getConnection();	// �ǿյģ����û�л�ȡ�ɹ��������ݹ��ȡ����
			} else { // ��Ϊ�գ���ȡ�ɹ����ӿ���������ɾ��
				this.freeConnections.remove(0);
			}
		} else {
			con = newConnection(); // �½�����
		}
		
		this.isBeyond(con);
		
		return con;
	}
	
	/**
	 * �ͷ�ȫ������
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
	 * �����µ�����
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
			System.err.println("�������ݿ����");
		}
		
		return this.con;
	}
	
	/**
	 * �ж��Ƿ񳬹����������
	 * @param con ���ݿ�����
	 */
	void isBeyond(Connection con) {
		if (this.maxConn < this.inUsed) { // �ﵽ�������������ʱ���ܻ�ȡ������
			con = null; 
		}
		
		if (con != null) {
			this.inUsed++;
		}
	}
	
	/**
	 * ��ʱ������
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
