package model.paramter;

import java.util.ArrayList;
import java.util.List;

public class IdParamter {
	private String ids;

	private String infoType;
	
	private List<String> IdList;

	public List<String> getIdList() {
		return IdList;
	}

	public String getId() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids.trim();
		String[] arr = this.ids.split("\\+");
		this.IdList = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			this.IdList.add(arr[i]);
		}
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	
	
}
