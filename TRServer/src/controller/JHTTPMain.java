package controller;
/***
 * �������������
 */
import java.io.File;

public class JHTTPMain {
	public static void main(String[] args) {
		File docRoot = null; // �õ��ĵ���
		try {
			docRoot = new File("sources");
			if (docRoot.isDirectory()) {
				System.err.println("is Director and Path: " + docRoot.getCanonicalPath());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Usage: java JHTTP docRoot port");
		}
		
		int port = 8349;
//		int port = 6237;
		try {
			JHTTPServer webServer = new JHTTPServer(docRoot, port);
			webServer.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
