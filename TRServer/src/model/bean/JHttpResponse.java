package model.bean;

public class JHttpResponse {
	/**
	 * ��Ӧ��
	 * */
	private String responseCode = "HTTP/1.0 200 OK";
	
	/***
	 * MIME����
	 */
	private String contentType = "application/json";
	
	/***
	 * ��Ӧ����
	 */
	private String json = "Ĭ����Ӧ�ı�";
	
	
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
