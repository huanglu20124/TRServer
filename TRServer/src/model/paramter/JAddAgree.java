package model.paramter;

/**
 * ���ӵ�����
 * @author ��Զ
 *
 */

public class JAddAgree {
	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	private Integer noteId;
	
	// in 或者   sub
	private	String agreeType;

	public String getAgreeType() {
		return agreeType;
	}

	public void setAgreeType(String agreeType) {
		this.agreeType = agreeType.trim();
	}
}
