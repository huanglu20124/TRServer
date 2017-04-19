package model.paramter;

/**
 * 帖子模型
 * @author 黄远
 *
 */

public class JNoteParamters {
	/**
	 * 帖子类型
	 */
	private String type;
	
	/**
	 * 足迹对应的账户
	 */
	private String account;

	/**
	 * 点赞数目
	 */
	private Integer agreeCount = 0;
	
	/**
	 * 时间撮
	 */
	private String timestamp;
	
	/**
	 * 标题
	 */
	private String title;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		type = type.trim();
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getAgreeCount() {
		return agreeCount;
	}

	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}
