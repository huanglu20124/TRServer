package model.paramter;

import java.util.ArrayList;
import java.util.List;

/**
 * �㼣����ģ��
 */

public class JRouteParamters extends JNoteParamters {
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * ͼƬ��ַ
	 */
	private String imageUrl;

	/**
	 * ��������
	 */
	private String content;
	
	/**
	 * �㼣���ľ���
	 */
	private List<Route_Scenery> sceneries;

	public String getSceneryIds() {
		return sceneryIds;
	}

	private String sceneryIds;
	
	
	public List<Route_Scenery> getSceneries() {
		return sceneries;
	}

	/**
	 * ���ַ�ת��Ϊ�㼣���ľ���
	 */
	public void setSceneris(String sceneris) {
		sceneris = sceneris.trim();
		sceneryIds = sceneris;
		String[] arr = sceneris.split("\\+");
		sceneries = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			Route_Scenery scenery = new Route_Scenery();
			scenery.setSceneryId(Integer.parseInt(arr[i]));
			scenery.setScenerySq(i+1);
			
			sceneries.add(scenery);
		}
	}
}
