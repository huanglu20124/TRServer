package model.paramter;
/***
 * 获取帖子参数
 * @author 黄远
 *
 */

public class JGetNotesParamter {
	private String noteType;
	private Integer limit = 20;
	private Integer sinceId = 0;
	/**
	 * 帖子对应的账户
	 */
	private String account;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getSinceId() {
		if(sinceId == 0) {
			sinceId = 99999;
		}
		
		return sinceId;
	}
	public void setSinceId(Integer sinceId) {
		if (sinceId == null) {
			this.sinceId = new Integer(99999);
		} else {
			this.sinceId = sinceId;
		}
	}
	public String getNoteType() {
		return noteType;
	}
	public void setNoteType(String noteType) {
		noteType = noteType.trim();
		this.noteType = noteType;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
