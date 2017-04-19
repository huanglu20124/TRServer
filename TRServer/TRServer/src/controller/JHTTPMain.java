package controller;
/***
 * 服务器启动入口
 */
import java.io.File;

public class JHTTPMain {
	public static void main(String[] args) {
		File docRoot = null; // 得到文档根
		try {
			docRoot = new File("sources");
			if (docRoot.isDirectory()) {
				System.err.println("is Director and Path: " + docRoot.getCanonicalPath());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Usage: java JHTTP docRoot port");
		}
		
		int port = 13569;
		try {
			JHTTPServer webServer = new JHTTPServer(docRoot, port);
			webServer.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
