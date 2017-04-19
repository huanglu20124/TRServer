package model.db;
/***
 * 配置文件Bean类
 * 存储单个数据库连接信息Bean
 * */
public class DSConfigBean {
	/**
	 * 数据库类型
	 */
	private String type; 
	
	/**
	 * 连接池名字
	 */
	private String name;
	
	/**
	 * 数据库驱动
	 */
	private String driver;
	
	/**
	 * 数据库连接地址
	 */
	private String url;
	
	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
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
	 * 最大连接数
	 */
	private int maxConn;
	
	
}
