package model.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/***
 * ͼƬ���?����
 * @author ��Զ
 *
 */
public class JImageDAOImp implements JImageDAO {
	@Override
	public boolean saveImageInServerByImageUrl(String image, String imageUrl) {
		// TODO Auto-generated method stub
		String savePath = "sources/images/";
		
		int lastIndex = imageUrl.lastIndexOf("/");
		String imageName = imageUrl.substring(lastIndex+1);
		savePath = savePath + imageName;
		
 		File file = new File(savePath);
		if (!file.exists()) {
			try {
				boolean tag = file.createNewFile();
				if (tag) {
					System.out.println("成功创建");
				} else {
					System.out.println("创建失败");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream fo = null;
		byte[] images = hex2byte(image);
		try {
			fo = new FileOutputStream(file);
			fo.write(images);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} finally {
			
			try {
				if (fo != null) {
					fo.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 将字符串转化成二进制
	 * @param str
	 * @return
	 */
	public static byte[] hex2byte(String str) { 
	    if (str == null)  
	     return null;  
	    str = str.trim();  
	    int len = str.length();  
	    if (len == 0 || len % 2 == 1)  
	     return null;  
	    byte[] b = new byte[len / 2];  
	    try {  
	     for (int i = 0; i < str.length(); i += 2) {  
	      b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();  
	     }  
	     return b;  
	    } catch (Exception e) {  
	     return null;  
	   } 
	}
}
