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
	 * ͨsocket传送数据
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
    public static void socketData(String IP, int port, String param) throws UnknownHostException, IOException {
    	Socket client = new Socket(InetAddress.getByName(IP), port);
		OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());
		// ע�⣡����   ������ַ��Բ��ܱ�
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
     * 把字节转化字符串
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
     * 获取一定长度的随机字符串
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
     * --------------------上面不用看，直接复制就好---------------------------------------------------------------------
     */
    
    
    
    
    
    /**
     *	-------------------------上传带图片的足迹  示例---------------------------------------------------------
     * 上传带图片的足迹  示例
     * @throws Exception
     */
    public static void Route() throws IOException {
    	FileInputStream inputStream = new FileInputStream("sources/images/000.png");
		byte[] imageArr = new byte[inputStream.available()];
		inputStream.read(imageArr);
		String imageUrl = "http://119.29.121.44:8349/images/" + getRandomString(20); // 20写死好了 
		testAddRoute("读者空白", "去了澳门", "这是一条内容", "2025+2026+2027+3001", imageArr, imageUrl);
	}
    
    /**
	 * 上传带图片的足迹帖子示例  			(上传问题帖和评论，也是类似的)
	 * @throws IOException 
	 */
	private static void testAddRoute(String account, String title, String content, String sceneries
			, byte[] imageArr, String imageUrl) throws IOException {
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
     * -------------------------------上传带图片的问题  示例---------------------------------------------------
     * 上传带图片的问题  示例
     * @throws Exception
     */
    public static void Question() throws IOException {
    	FileInputStream inputStream = new FileInputStream("sources/images/000.png");
		byte[] imageArr = new byte[inputStream.available()];
		inputStream.read(imageArr);
		String imageUrl = "http://119.29.121.44:8349/images/" + getRandomString(20); // 20写死好了
		testAddQuestion("读者空白", "我的问题", "问题内容", imageArr, imageUrl);
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
     * ------------------------------上传带图片的评论示例-----------------------------------------------------
     * 上传带图片的评论示例
     */
    public static void Comment() throws IOException {
    	FileInputStream inputStream = new FileInputStream("sources/images/000.png");
		byte[] imageArr = new byte[inputStream.available()];
		inputStream.read(imageArr);
		String imageUrl = "http://119.29.121.44:8349/images/" + getRandomString(20); // 20写死好了
		testAddComment("读者空白", "我的评论", imageArr, imageUrl);
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
//		上传带图片的足迹  示例
		Route();
		
		
//		上传带图片的问题  示例
//		Question();
		
		
//		上传带图片的评论示例
//		Comment();
	}

}
