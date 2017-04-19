package model.paramter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/***
 * Ѱ�Ҿ������
 * @author ��Զ
 *
 */
public class JSearchSceneryParamter {
	private String keyWorld;
	private String keyWorldType;
	private Integer	limit = 0;
	private Integer	sinceId = 0;
	private List<String> sceneryIds;
	
	
	public void setScenerysIds(String sceneryIds) {
		sceneryIds = sceneryIds.trim();
		String[] arr = sceneryIds.split("\\+");
		this.sceneryIds = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			this.sceneryIds.add(arr[i]);
		}
	}
	
	public List<String> getSceneryIds() {
		return sceneryIds;
	}
	public Integer getSinceId() {
		if (sinceId == null) {
			sinceId = new Integer(0);
		}
		
		return sinceId;
	}
	public void setSinceId(Integer sinceId) {
		this.sinceId = sinceId;
	}
	public Integer getLimit() {
		if (limit == 0) {
			limit = 20;
		}
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getKeyWorld() {
		return keyWorld;
	}
	public void setKeyWorld(String keyWorld) {
		if (keyWorld == null) {
			return;
		}
		
		keyWorld = keyWorld.trim();
		try {
			keyWorld = URLDecoder.decode(keyWorld, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.keyWorld = keyWorld;
	}
	public String getKeyWorldType() {
		return keyWorldType;
	}
	public void setKeyWorldType(String keyWorldType) {
		keyWorldType = keyWorldType.trim();
		this.keyWorldType = keyWorldType;
	}
	
	
}
