package model.paramter;
/***
 * ��ȡ���Ӳ���
 * @author ��Զ
 *
 */

public class JGetNotesParamter {
	private String noteType;
	private Integer limit = 20;
	private Integer sinceId = 0;
	
	public Integer getSinceId() {
		return sinceId;
	}
	public void setSinceId(Integer sinceId) {
		if (sinceId == null) {
			this.sinceId = new Integer(0);
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
