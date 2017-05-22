package test;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class LCTestUpImage {
	
	/**
	 * 通socket浼犻�佹暟鎹�
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
    public static void socketData(String IP, int port, String param) throws UnknownHostException, IOException {
    	Socket client = new Socket(InetAddress.getByName(IP), port);
		OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());
		// 注锟解！锟斤拷锟斤拷   锟斤拷锟斤拷锟斤拷址锟斤拷圆锟斤拷鼙锟�
		out.write("Post  "+param + "  HTTP/1.0\r\n");
		out.flush();
		
		InputStreamReader reader = new InputStreamReader(client.getInputStream(), "UTF-8");
		int c;
		StringBuffer bf = new StringBuffer();
		while (true) {
			c = reader.read();
			if (c == -1) {
				break;
			}
			bf.append((char)c);
		}
		System.out.println(bf);
		
	
		reader.close();
		client.close();
	}
	
    /**
     * 鎶婂瓧鑺傝浆鍖栧瓧绗︿覆
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b)
    {  
       StringBuffer sb = new StringBuffer();  
       String stmp = "";  
       for (int n = 0; n < b.length; n++) {  
        stmp = Integer.toHexString(b[n] & 0XFF);  
        if (stmp.length() == 1){  
            sb.append("0" + stmp);  
        }else{  
            sb.append(stmp);  
        }  
          
       }  
       return sb.toString();  
    }  
	
    /**
     * 鑾峰彇涓�瀹氶暱搴︾殑闅忔満瀛楃涓�
     * @param length
     * @return
     */
    public static String getRandomString(int length) { 
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789QWERTYUIOPASDFGHJKLZXCVBNM";  
	    Random random = new Random();
	    StringBuffer sb  = new StringBuffer();
	    for (int i = 0; i < length; i++) {
			sb.append(base.charAt(random.nextInt(length)));
		}
	    
	    sb = sb.append(".png");
	    
	    return sb.toString();   
	 } 
    
    
    /**
     * --------------------涓婇潰涓嶇敤鐪嬶紝鐩存帴澶嶅埗灏卞ソ---------------------------------------------------------------------
     */
    
    
    
    
    
    /**
     *	-------------------------涓婁紶甯﹀浘鐗囩殑瓒宠抗  绀轰緥---------------------------------------------------------
     * 涓婁紶甯﹀浘鐗囩殑瓒宠抗  绀轰緥
     * @throws Exception
     */
    public static void Route() throws IOException {
    	FileInputStream inputStream = new FileInputStream("sources/images/000.png");
		byte[] imageArr = new byte[inputStream.available()];
		inputStream.read(imageArr);
		String imageUrl = "http://119.29.121.44:8349/images/" + getRandomString(20); // 20鍐欐濂戒簡 
		testAddRoute("璇昏�呯┖鐧�", "鍘讳簡婢抽棬", "杩欐槸涓�鏉″唴瀹�", "2025+2026+2027+3001", imageArr, imageUrl);
	}
    
    /**
	 * 涓婁紶甯﹀浘鐗囩殑瓒宠抗甯栧瓙绀轰緥  			(涓婁紶闂甯栧拰璇勮锛屼篃鏄被浼肩殑)
	 * @throws IOException 
	 */
	private static void testAddRoute(String account, String title, String content, String sceneries, 
			byte[] imageArr, String imageUrl) throws IOException {
		String secp = "UTF-8";
		account = URLEncoder.encode(account, secp);
		title = URLEncoder.encode(title, secp);
		content = URLEncoder.encode(content, secp);
		
		SimpleDateFormat sdf = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sdf.format(new Date());
		
		String image = byte2hex(imageArr); 
		
		
		String paramString = 
				String.format("type=upRoute&account=%s&timestamp=%s&title=%s&NoteType=%s" +
						"&content=%s&sceneries=%s&imageUrl=%s&image=%s",
						account, timestamp, title, "Route", content, sceneries, imageUrl, image);
		int port = 8349;
		String IP = "119.29.121.44";
		socketData(IP, port, paramString);
	}
    
    
    
    /**
     * -------------------------------涓婁紶甯﹀浘鐗囩殑闂  绀轰緥---------------------------------------------------
     * 涓婁紶甯﹀浘鐗囩殑闂  绀轰緥
     * @throws Exception
     */
    public static void Question() throws IOException {
    	FileInputStream inputStream = new FileInputStream("sources/images/000.png");
		byte[] imageArr = new byte[inputStream.available()];
		inputStream.read(imageArr);
		String imageUrl = "http://119.29.121.44:8349/images/" + getRandomString(20); // 20鍐欐濂戒簡
		testAddQuestion("璇昏�呯┖鐧�", "鎴戠殑闂", "闂鍐呭", imageArr, imageUrl);
	}
    
    private static void testAddQuestion(String account, String title, String content, 
    		byte[] imageArr, String imageUrl) throws IOException {
		SimpleDateFormat sdf = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sdf.format(new Date());
		String image = byte2hex(imageArr); 
		
		String paramString = 
				String.format("type=upQuestion&account=%s&timestamp=%s&title=%s&NoteType=%s&content=%s" +
						"&imageUrl=%s&image=%s",
						account, timestamp, title, "Question", content, imageUrl, image);
		int port = 8349;
		
		String IP = "119.29.121.44";
		socketData(IP, port, paramString);
	}
    
    
    
    
    /**
     * ------------------------------涓婁紶甯﹀浘鐗囩殑璇勮绀轰緥-----------------------------------------------------
     * 涓婁紶甯﹀浘鐗囩殑璇勮绀轰緥
     */
    public static void Comment() throws IOException {
    	FileInputStream inputStream = new FileInputStream("sources/images/000.png");
		byte[] imageArr = new byte[inputStream.available()];
		inputStream.read(imageArr);
		String imageUrl = "http://119.29.121.44:8349/images/" + getRandomString(20); // 20鍐欐濂戒簡
		testAddComment("璇昏�呯┖鐧�", "鎴戠殑璇勮", imageArr, imageUrl);
    }
    
    /**
	 * @throws IOException 
	 */
	private static void testAddComment(String account, String content, 
    		byte[] imageArr, String imageUrl) throws IOException {
		SimpleDateFormat sdf = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sdf.format(new Date());
		String image = byte2hex(imageArr);
		String paramString = 
				String.format(
			"type=addComment&account=%s&timestamp=%s&commentType=%s" +
			"&content=%s&imageUrl=%s&image=%s&noteId=%d",
						account, timestamp, "Route", content, imageUrl, image, 1);
		int port = 8349;
		String IP = "119.29.121.44";
		socketData(IP, port, paramString);
	}
    
    
    
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		涓婁紶甯﹀浘鐗囩殑瓒宠抗  绀轰緥
		Route();
		
		
//		涓婁紶甯﹀浘鐗囩殑闂  绀轰緥
//		Question();
		
		
//		涓婁紶甯﹀浘鐗囩殑璇勮绀轰緥
//		Comment();
	}

}
