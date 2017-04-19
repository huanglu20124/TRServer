package model.paramter;

/**
 * 足迹帖子模型
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
	 * 图片地址
	 */
	private String imageUrl;

	/**
	 * 帖子内容
	 */
	private String content;

}
