package model.paramter;

public class JQuestionParamters extends JNoteParamters {
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
