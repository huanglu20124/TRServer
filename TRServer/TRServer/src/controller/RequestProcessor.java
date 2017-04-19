package controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
	 * 日志记录者
	 * */
	private final static Logger logger = Logger.
			getLogger(RequestProcessor.class.getCanonicalName());
	
	/** 
	 * 根文件夹
	 * */
	private File rootDirectory;
	

	/** 
	 * 找不到get请求里面文件的路径时，拼接这个文件发送
	 * */
	private String indexFileName = "index.html";
	

	/** 
	 * 客户端连接
	 * */
	private Socket connection;
	
	/**
	 * 客户端Http请求模型
	 **/
	JHttpRequest request;
	
	/***
	 * 服务器Http回复模型
	 */
	JHttpResponse response;
	
	public RequestProcessor(File rootDirectory, 
			String indexFileName, Socket connection) {
		if (rootDirectory.isFile()) {
			throw new IllegalArgumentException(
					"rootDirectory must be a directory, not afile");
		}
		
		try {
			// 返回规范路径的文件表示
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
			System.out.println("New Request" + "  IP:" +connection.getRemoteSocketAddress() + " "+ "    URL:" + get);
			
			// 创建请求对象
			JHttpRequest request = new JHttpRequest(get);
			this.request = request;
			String method = request.getMethod();
			String version = request.getVersion();
			
			if ("GET".equals(method)) {
				String fileName = request.getFile();
				
				// 如果url以'/'结尾
				if (fileName.endsWith("/")) {
					fileName += indexFileName;
				}
				
				String contentType = URLConnection.getFileNameMap().
						getContentTypeFor(fileName);
				
				File theFile = new File(
						rootDirectory, fileName.substring(1, fileName.length()));
				
				// 文件可读，并且路径是在根目录之下，才能发送，防止请求道本地主机的其他资源
				if (theFile.canRead() && theFile.getCanonicalPath().startsWith(root)) {
					byte[] theData;
					FileInputStream fileInputStream = new 
							FileInputStream(theFile);
					theData = new byte[fileInputStream.available()];
					fileInputStream.read(theData);
					
					if (version.startsWith("HTTP/")) {
						// 发送一个MIME首部
						sendHeader(out, "HTTP/1.0 200 OK", 
								contentType, theData.length);
					}
				
					raw.write(theData);
					raw.flush();
					
				} else { // 无法找到文件, 动态生成json文本发送
					makeJsonToResponse(out, version);
				} 
			} else { // 不是GET请求
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
	 * 动态生成json文本发送
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
		out.write("Server： JHTTP 2.0\r\n");
		out.write("Content-length: " + contentType + "\r\n\r\n");
		out.flush();
	}
}
