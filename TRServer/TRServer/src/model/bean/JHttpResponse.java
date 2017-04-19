package model.bean;

public class JHttpResponse {
	/**
	 * 响应码
	 * */
	private String responseCode = "HTTP/1.0 200 OK";
	
	/***
	 * MIME类型
	 */
	private String contentType = "application/json";
	
	/***
	 * 响应内容
	 */
	private String json = "默认响应文本";
	
	
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	
}
