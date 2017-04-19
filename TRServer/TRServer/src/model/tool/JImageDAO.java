package model.tool;

public interface JImageDAO {
	/***
	 * 根据客户端传过来的图片的URL，把图片存入本地
	 */
	public boolean saveImageInServerByImageUrl(byte[] images, String imageUrl);
}
