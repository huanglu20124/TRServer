package controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.URLConnection;
import java.util.Date;
import java.util.logging.Logger;


import model.bean.JHttpRequest;
import model.bean.JHttpResponse;
import model.tool.contentTool.JHttpContentTool;


public class RequestProcessor implements Runnable {
	
	/** 
	 * ��־��¼��
	 * */
	private final static Logger logger = Logger.
			getLogger(RequestProcessor.class.getCanonicalName());
	
	/** 
	 * ���ļ���
	 * */
	private File rootDirectory;
	

	/** 
	 * �Ҳ���get���������ļ���·��ʱ��ƴ������ļ�����
	 * */
	private String indexFileName = "index.html";
	

	/** 
	 * �ͻ�������
	 * */
	private Socket connection;
	
	/**
	 * �ͻ���Http����ģ��
	 **/
	JHttpRequest request;
	
	/***
	 * ������Http�ظ�ģ��
	 */
	JHttpResponse response;
	
	public RequestProcessor(File rootDirectory, 
			String indexFileName, Socket connection) {
		if (rootDirectory.isFile()) {
			throw new IllegalArgumentException(
					"rootDirectory must be a directory, not afile");
		}
		
		try {
			// ���ع淶·�����ļ���ʾ
			rootDirectory = rootDirectory.getCanonicalFile();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		this.rootDirectory = rootDirectory;
		if (indexFileName != null) {
			this.indexFileName = indexFileName;
		}
		this.connection = connection;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String root = rootDirectory.getPath();
		Writer out = null;
		OutputStream raw = null;
		Reader in = null;
		
		try {
			raw = new BufferedOutputStream(connection.getOutputStream());
			out = new OutputStreamWriter(raw, "UTF-8");
			in = new InputStreamReader(
					new BufferedInputStream(
							connection.getInputStream()), "UTF-8");
			
			StringBuffer requestLine = new StringBuffer();
			while (true) {
				int c = in.read();
				if (c == '\r' || c=='\n' || c==-1) {
					break;
				}
				
				requestLine.append((char)c);
			}
		
			String get = requestLine.toString();
			if (get.length() < 500) {
				System.out.println("New Request" + "  IP:" +connection.getRemoteSocketAddress() + " "+ "    URL:" + get);
			} else {
				System.out.println("New Request" + "  IP:" +connection.getRemoteSocketAddress());
			}
			
			// �����������
			JHttpRequest request = new JHttpRequest(get);
			this.request = request;
			String method = request.getMethod();
			String version = request.getVersion();
			
			if ("GET".equals(method)) {
				String fileName = request.getFile();
				
				// ���url��'/'��β
				if (fileName.endsWith("/")) {
					fileName += indexFileName;
				}
				
				String contentType = URLConnection.getFileNameMap().
						getContentTypeFor(fileName);
				
				int index = fileName.lastIndexOf("HTTP");
				if (index == -1) {
					index = fileName.length()+1;
				}
				File theFile = new File(
						rootDirectory, fileName.substring(1, index-1));
				
				// �ļ��ɶ�������·�����ڸ�Ŀ¼֮�£����ܷ��ͣ���ֹ��������������������Դ
				if (theFile.canRead() && theFile.getCanonicalPath().startsWith(root)) {
					byte[] theData;
					FileInputStream fileInputStream = new 
							FileInputStream(theFile);
					theData = new byte[fileInputStream.available()];
					fileInputStream.read(theData);
					
					if (version.startsWith("HTTP/")) {
						// ����һ��MIME�ײ�
						sendHeader(out, "HTTP/1.0 200 OK", 
								contentType, theData.length);
					}
				
					raw.write(theData);
					raw.flush();
					
				} else { // �޷��ҵ��ļ�, ��̬���json�ı�����
					makeJsonToResponse(out, version);
				} 
			} else { // ����GET����
				makeJsonToResponse(out, version);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				out.close();
				in.close();
				raw.close();
				connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	/**
	 * ��̬���json�ı�����
	 * @param out
	 * @param version
	 * @throws IOException
	 */
	private void makeJsonToResponse(Writer out, String version) throws IOException {
		JHttpResponse response = new JHttpResponse();
		this.response = response;
		String json = JHttpContentTool.getJsonWithRequestAndResponse(request, response);
		
		if (version.startsWith("HTTP/")) {
			sendHeader(out, 
				response.getResponseCode(), response.getContentType(), json.length());
		}
		
		out.write(json);
		out.flush();
	}

	public void sendHeader(Writer out, String responseCode, 
			String contentType, int length) throws IOException {
		out.write(responseCode + "\r\n");
		Date now = new Date();
		out.write("Date: " + now + "\r\n");
		out.write("Server�� JHTTP 2.0\r\n");
		out.write("Content-length: " + contentType + "\r\n\r\n");
		out.flush();
	}
}
