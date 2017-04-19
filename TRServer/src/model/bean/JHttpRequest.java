package model.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sun.awt.image.BytePackedRaster;

public class JHttpRequest {
	/**
	 * �ͻ���IP��ַ
	 */
	private String IP;
	
	/**
	 * �ͻ������������
	 */
	private String Type;
	
	/**
	 * �ͻ������󷽷�
	 */
	private String Method;
	
	/***
	 * �ͻ��������URL
	 */
	private String URL;

	/**
	 * �����ļ��ĵ�ַ
	 */
	private String file;
	
	/**
	 * HTTP�汾
	 */
	private String version = "HTTP/";
	
	/**
	 * �������
	 */
	private Map<String, Object> Paramters;
	
	public JHttpRequest(String url) {
		// TODO Auto-generated constructor stub
		this.URL = url;
		// 		\\s��ʾ   �ո�,�س�,���еȿհ׷�
		String[] tokens =url.split("\\s+", 2);
		this.Method = tokens[0];
		this.file = tokens[1];
		
		// ��ʼ���汾
		this.initVersion(tokens);
		
		// ��ȡ���в���
		this.getAllParamters();
	
		// ��ȡ���������
		this.initType();
	}

	// ��ȡURL�����в���
	private void getAllParamters() {
		// ��ǰ�����ص�
		int start = this.URL.indexOf(" ");
		int end = this.URL.indexOf("HTTP",	start + 1);
		String paramterString = null;
		try {
			paramterString = this.URL.substring(start + 2, end);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(paramterString == null) {
			return ;
		}
		
		this.Paramters = new HashMap<String, Object>();
		String[] arrStrings = paramterString.split("[&|\\?]");
		for (int i = 0; i < arrStrings.length; i++) {
			if(arrStrings[i].length() <= 1) continue;
			String []key_value = arrStrings[i].split("=");
			if(key_value.length < 2) continue;
			String key = key_value[0];
			String value = key_value[1];
			try {
				// 不是图片，才需要解码
				if (value.length() < 500 && !value.contains("+")) {
					value = URLDecoder.decode(value.trim(), "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.Paramters.put(key, value);
		}
	}
	
	// ��ʼ���汾
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

	// ��ȡ���������
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
	 * ��ȡ����
	 */
	public Object getParamter(String key) {
		return this.Paramters.get(key);
	}
	
	/**
	 * ��ȡ�ֽ��ļ�
	 */
	public byte[] getByteFile(String key) {
		return (byte[])this.Paramters.get(key);
	}
}
