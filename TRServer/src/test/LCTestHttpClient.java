package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.NEW;


public class LCTestHttpClient {
	public static void main(String[] args) throws IOException {
		
		GetComment();
//		testAddRoute();
		
//		LCTestHttpClient.GetScenerysInfo();
		
//		GetScenerysInfoById();
		
//		testAddComment();
		
//		GetNotesInfo();
		
//		testAddQuestion();
		
//		testAddAgree();
		
//		GetInfoById("routeForCommentId", "2+1");
		
		// Delet("comment", "commentId", 1);
	}
	
	private static void GetInfoById(String infoType, String ids) throws IOException {
//		String url = "http://" + "119.29.121.44" + 
//				":8349";
		 String url = "http://" + InetAddress.getLocalHost().getHostAddress() + 
		 		":8349";
		String param = String.format("type=InfoForId&infoType=%s&ids=%s", 
			infoType, ids);
		String result = LCTestHttpClient.sendGet(url, param);
		 System.out.println(result);
	}
	
	private static void Delet(String tableName, String IdName, Integer Id) throws IOException {
//		String url = "http://" + "119.29.121.44" + 
//				":8349";
		String url = "http://" + InetAddress.getLocalHost().getHostAddress() + 
				":8349";
		String param = String.format("type=delete&deleteTable=%s&IdName=%s&Id=%d", 
			tableName, IdName, Id);
		String result = LCTestHttpClient.sendGet(url, param);
		 System.out.println(result);
	}
	
	
	/**
	 * ��ȡ����
	 * @throws IOException
	 */
	private static void GetComment() throws IOException {
		String url = "http://" + InetAddress.getLocalHost().getHostAddress() + 
				":8349";
		String param = "type=GetComment&noteId=3&sinceId=10";
		String result = LCTestHttpClient.sendGet(url, param);
		System.out.println(result);
	}
	
	private static void testAddAgree() throws IOException {
		String url = "http://" + InetAddress.getLocalHost().getHostAddress() + 
				":13569";
		String param = "type=agree&noteId=2";
		String result = LCTestHttpClient.sendGet(url, param);
		System.out.println(result);
	}
	
	/**
	 * �ϴ�����
	 */
	private static void testAddQuestion() throws IOException {
		SimpleDateFormat sdf = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sdf.format(new Date());
		String paramString = 
				String.format("type=upQuestion&account=%s&timestamp=%s&title=%s&NoteType=%s&content=%s",
						"��Զ", timestamp, "��1����������", "Question", "�麣�������");
		int port = 13569;
		String IP = InetAddress.getLocalHost().getHostAddress();
		socketData(IP, port, paramString);
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @throws IOException 
	 */
	private static void GetNotesInfo() throws IOException {
		String url = "http://" + InetAddress.getLocalHost().getHostAddress() + 
				":8349";
		String param = "type=GetNote&NoteType=Question";
		String result = LCTestHttpClient.sendGet(url, param);
		System.out.println(result);
	}
//	
	/**
	 * ����һ������
	 * @throws IOException 
	 */
	private static void testAddComment() throws IOException {
		SimpleDateFormat sdf = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sdf.format(new Date());
		String paramString = 
				String.format(
			"type=addComment&account=%s&timestamp=%s&commentType=%s" +
			"&content=%s&imageUrl=%s&noteId=%d",
						"��Զ", timestamp, "Route", "17", "NULL", 1);
		int port = 13569;
		String IP = InetAddress.getLocalHost().getHostAddress();
		socketData(IP, port, paramString);
	}
	
	/**
	 * ���Բ����㼣
	 * @throws IOException 
	 */
	private static void testAddRoute() throws IOException {
		SimpleDateFormat sdf = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sdf.format(new Date());
		String paramString = 
				String.format("type=upRoute&account=%s&timestamp=%s&title=%s&NoteType=%s&content=%s" +
						"&sceneries=%s",
						"��Զ", timestamp, "��n���㼣", "Route", "����ȥ�˰���", "3025+2026+1027+3001");
		int port = 8349;
		String IP = InetAddress.getLocalHost().getHostAddress();
		socketData(IP, port, paramString);
	}
	
	
	 /**
     * ��ָ��URL����GET����������
     * 
     * @param url
     *            ���������URL
     * @param param
     *            ��������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return URL ����Զ����Դ����Ӧ���(����õ�������)
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
        	String urlNameString;
        	URL realUrl;
        	if (param != null) {
        		urlNameString = url + "?" + param;
                realUrl = new URL(urlNameString);
			} else{
				urlNameString = url;
				realUrl = new URL(urlNameString);
			}
            
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            
            // ���������Ժ�һֱ��ȡ���͹�������� ��Ϊ�յĻ�
            // �����̣߳�ʹ��CPU�����߳�ʹ�ã���Ӱ���û���UI����
            
            while ((line = in.readLine()) != null) { // ���������Ժ�һֱ��ȡ���͹��������     	
                result += line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	
	
	/**
     * ��ָ�� URL ����POST����������
     * 
     * @param url
     *            ��������� URL
     * @param param
     *            ��������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return ����Զ����Դ����Ӧ���
     */
    public static String sendPost(String url, String param) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            String contentLenString = String.format("%d", param.getBytes().length);
            conn.setRequestProperty("Content-Length", contentLenString);
            conn.setRequestMethod("POST");
            
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            // ����ʵ�ʵ�����
            conn.connect();
            
            // ��ȡURLConnection�����Ӧ�������
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // �����������
            out.write(param);
            // flush������Ļ���
            out.flush();
            
            
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("���� POST ��������쳣��"+e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	
	/**
	 * ͨ��socket�������
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
     * ��ݾ���ID��ȡ������Ϣ
     * @throws IOException 
     */
    static void GetScenerysInfoById() throws IOException{
    	String urlString = "http://" + InetAddress.getLocalHost().getHostAddress() + 
				":8349?type=queryScenery&limit=20&keyWordType=sceneryIds&keyWord=3305+2001+2303";
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                 "TRClient 1.0");
        connection.setRequestMethod("GET");
        
        connection.connect();
        
        int code = connection.getResponseCode();
        System.out.println("��Ӧͷ�� " + code);
        
		InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");
		int tem;
		StringBuffer boBuffer = new StringBuffer();
		while (true) {
			tem = reader.read();
			if (tem == -1) {
				break;
			}
			boBuffer.append((char)tem);
		}
		
		System.out.println("���������" + boBuffer);
		reader.close();
    }
    
    /**
     * ��ȡ������Ϣ
     * */
	static void GetScenerysInfo() throws IOException{
		String urlString = "http://" + InetAddress.getLocalHost().getHostAddress() + 
				":8349?type=queryScenery&limit=20&keyWordType=city&keyWord=��üɽ";
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                 "TRClient 1.0");
        connection.setRequestMethod("GET");
        
        connection.connect();
        
        int code = connection.getResponseCode();
        System.out.println("��Ӧͷ�� " + code);
        
		InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");
		int tem;
		StringBuffer boBuffer = new StringBuffer();
		while (true) {
			tem = reader.read();
			if (tem == -1) {
				break;
			}
			boBuffer.append((char)tem);
		}
		
		System.out.println("���������" + boBuffer);
		reader.close();
	}
	
	/**
	 * ����get�������ֽ��ļ�
	 * */
	public static byte[] sendGetByte(String url, String param) {
		byte[] data = null;
		
		BufferedInputStream in = null;
        try {
        	String urlNameString;
        	URL realUrl;
        	if (param != null) {
        		urlNameString = url + "?" + param;
                realUrl = new URL(urlNameString);
			} else{
				urlNameString = url;
				realUrl = new URL(urlNameString);
			}
            
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
//            HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
        	
        	// ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "TRClient 1.0");
            // ����ʵ�ʵ�����
            connection.connect();
            
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
            byte[] buffer = new byte[1024];  
            int len = 0;  
            while( (len=in.read(buffer)) != -1 ) {
            	// ����ȡlen���ȵ��ֽ�д��ByteArrayOutputStream
                outStream.write(buffer, 0, len);  
            }  
            data = outStream.toByteArray();
            
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		return data;
	}
	
}
