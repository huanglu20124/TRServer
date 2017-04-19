package model.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/***
 * 图片处理工具类
 * @author 黄远
 *
 */
public class JImageDAOImp implements JImageDAO {
	@Override
	public boolean saveImageInServerByImageUrl(byte[] images, String imageUrl) {
		// TODO Auto-generated method stub
		File file = new File(imageUrl);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream fo = null;
		
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

}
