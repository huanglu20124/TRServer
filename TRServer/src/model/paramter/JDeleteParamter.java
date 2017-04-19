package model.paramter;

public class JDeleteParamter {
	//表名  	 route   question  comment
	private String deleteTable;
	
	
	//字段名	 routeId  questionId  commentId
	private String IdName;
	
	//Id的值
	private String Id;
	
	public String getIdName() {
		return IdName;
	}
	public void setIdName(String idName) {
		IdName = idName;
	}
	public String getDeleteTable() {
		return deleteTable;
	}
	public void setDeleteTable(String deleteTable) {
		this.deleteTable = deleteTable;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	
	
}
