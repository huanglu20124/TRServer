package model.paramter;

/**
 * �㼣����ģ��
 */

public class JRouteParamters extends JNoteParamters {
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * ͼƬ��ַ
	 */
	private String imageUrl;

	/**
	 * ��������
	 */
	private String content;

}
