package model.paramter;
/***
 * ����һ�����۲���ģ��
 * @author ��Զ
 *
 */
public class JAddCommentParamter {
	/**
	 * ����Id
	 */
	private String noteId;
	
	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	private String content;
	
	/**
	 * ͼƬ����URL
	 */
	private String imageUrl;
	
	/***
	 * �����ߵ��˺�
	 */
	private String account;
	
	/***
	 * ʱ���
	 */
	private String timestamp;
	
	/***
	 * 评论的类型
	 * Question 或者  	Route
	 */
	private String commentType;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		commentType = commentType.trim();
		this.commentType = commentType;
	}
}
