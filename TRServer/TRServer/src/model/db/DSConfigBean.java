package model.db;
/***
 * �����ļ�Bean��
 * �洢�������ݿ�������ϢBean
 * */
public class DSConfigBean {
	/**
	 * ���ݿ�����
	 */
	private String type; 
	
	/**
	 * ���ӳ�����
	 */
	private String name;
	
	/**
	 * ���ݿ�����
	 */
	private String driver;
	
	/**
	 * ���ݿ����ӵ�ַ
	 */
	private String url;
	
	/**
	 * �û���
	 */
	private String username;

	/**
	 * ����
	 */
	private String password;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	/**
	 * ���������
	 */
	private int maxConn;
	
	
}
