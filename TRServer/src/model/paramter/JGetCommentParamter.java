package model.paramter;

public class JGetCommentParamter {
	private Integer noteId = 0;
	private Integer limit = 0;
	
	
	private Integer sinceId = 0;
	
	// ÕËºÅ£¨Ë­µÄÆÀÂÛ£©
	private String account;
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getNoteId() {
		return noteId;
	}
	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
	public Integer getLimit() {
		if (limit == 0) {
			return 20;
		}
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getSinceId() {
		return sinceId;
	}
	public void setSinceId(Integer sinceId) {
		this.sinceId = sinceId;
	}
	
	
}
