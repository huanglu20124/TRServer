package model.paramter;
/***
 * 增加一条评论参数模型
 * @author 黄远
 *
 */
public class JAddCommentParamter {
	/**
	 * 帖子Id
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
	 * 图片请求URL
	 */
	private String imageUrl;
	
	/***
	 * 评论者的账号
	 */
	private String account;
	
	/***
	 * 时间撮
	 */
	private String timestamp;
	
	/***
	 * 评论类型
	 * “Question” 或者 “Route“
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
