package model.paramter;

/**
 * ����ģ��
 * @author ��Զ
 *
 */

public class JNoteParamters {
	/**
	 * ��������
	 */
	private String type;
	
	/**
	 * �㼣��Ӧ���˻�
	 */
	private String account;

	/**
	 * ������Ŀ
	 */
	private Integer agreeCount = 0;
	
	/**
	 * ʱ���
	 */
	private String timestamp;
	
	/**
	 * ����
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
