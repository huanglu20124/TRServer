package model.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sun.awt.image.BytePackedRaster;

public class JHttpRequest {
	/**
	 * 客户端IP地址
	 */
	private String IP;
	
	/**
	 * 客户端请求的类型
	 */
	private String Type;
	
	/**
	 * 客户端请求方法
	 */
	private String Method;
	
	/***
	 * 客户端请求的URL
	 */
	private String URL;

	/**
	 * 请求文件的地址
	 */
	private String file;
	
	/**
	 * HTTP版本
	 */
	private String version = "HTTP/";
	
	/**
	 * 请求参数
	 */
	private Map<String, Object> Paramters;
	
	public JHttpRequest(String url) {
		// TODO Auto-generated constructor stub
		this.URL = url;
		// 		\\s表示   空格,回车,换行等空白符
//		String[] tokens = url.split("\\s+");
		String[] tokens =url.split("\\s+", 2);
		this.Method = tokens[0];
		this.file = tokens[1];
		
		// 初始化版本
		this.initVersion(tokens);
		
		// 获取所有参数
		this.getAllParamters();
	
		// 获取请求的类型
		this.initType();
	}

	// 获取URL中所有参数
	private void getAllParamters() {
		// 把前面后面截掉
		int start = this.URL.indexOf(" ");
		int end = this.URL.indexOf("HTTP",	start + 1);
		String paramterString = this.URL.substring(start + 2, end);
		
		this.Paramters = new HashMap<String, Object>();
		String[] arrStrings = paramterString.split("[&|\\?]");
		for (int i = 0; i < arrStrings.length; i++) {
			if(arrStrings[i].length() < 1) {
				continue;
			}
				
			String []key_value = arrStrings[i].split("=");
			String key = key_value[0];
			String value = key_value[1];
			this.Paramters.put(key, value);
		}
	}
	
	// 初始化版本
	public void initVersion(String[] tokens) {
		if (tokens.length > 2) {
			version = tokens[2];
		}
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	// 获取请求的类型
	private void initType() {
		this.Type = (String) this.Paramters.get("type"); 
	}
	
	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getMethod() {
		return Method;
	}

	public void setMethod(String method) {
		Method = method;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}
	
	
	/**
	 * 获取参数
	 */
	public Object getParamter(String key) {
		return this.Paramters.get(key);
	}
	
	/**
	 * 获取字节文件
	 */
	public byte[] getByteFile(String key) {
		return (byte[])this.Paramters.get(key);
	}
}
