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
	 * 锟斤拷志锟斤拷录锟斤拷
	 * */
	private final static Logger logger = Logger.
			getLogger(RequestProcessor.class.getCanonicalName());
	
	/** 
	 * 锟斤拷锟侥硷拷锟斤拷
	 * */
	private File rootDirectory;
	

	/** 
	 * 锟揭诧拷锟斤拷get锟斤拷锟斤拷锟斤拷锟斤拷锟侥硷拷锟斤拷路锟斤拷时锟斤拷拼锟斤拷锟斤拷锟斤拷募锟斤拷锟斤拷锟�
	 * */
	private String indexFileName = "index.html";
	

	/** 
	 * 锟酵伙拷锟斤拷锟斤拷锟斤拷
	 * */
	private Socket connection;
	
	/**
	 * 锟酵伙拷锟斤拷Http锟斤拷锟斤拷模锟斤拷
	 **/
	JHttpRequest request;
	
	/***
	 * 锟斤拷锟斤拷锟斤拷Http锟截革拷模锟斤拷
	 */
	JHttpResponse response;
	
	public RequestProcessor(File rootDirectory, 
			String indexFileName, Socket connection) {
		if (rootDirectory.isFile()) {
			throw new IllegalArgumentException(
					"rootDirectory must be a directory, not afile");
		}
		
		try {
			// 锟斤拷锟截规范路锟斤拷锟斤拷锟侥硷拷锟斤拷示
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
			raw = new BufferedOutputStream(
					connection.getOutputStream());
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
			
			// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
			JHttpRequest request = new JHttpRequest(get);
			this.request = request;
			String method = request.getMethod();
			String version = request.getVersion();
			
			if ("GET".equals(method)) {
				String fileName = request.getFile();
				
				// 锟斤拷锟絬rl锟斤拷'/'锟斤拷尾
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
				
				// 锟侥硷拷锟缴讹拷锟斤拷锟斤拷锟斤拷路锟斤拷锟斤拷锟节革拷目录之锟铰ｏ拷锟斤拷锟杰凤拷锟酵ｏ拷锟斤拷止锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷源
				if (theFile.canRead() && theFile.getCanonicalPath().startsWith(root)) {
					byte[] theData;
					FileInputStream fileInputStream = new 
							FileInputStream(theFile);
					theData = new byte[fileInputStream.available()];
					fileInputStream.read(theData);
					
					if (version.startsWith("HTTP/")) {
						// 锟斤拷锟斤拷一锟斤拷MIME锟阶诧拷
						sendHeader(out, "HTTP/1.0 200 OK", 
								contentType, theData.length);
					}
				
					raw.write(theData);
					raw.flush();
					
				} else { // 锟睫凤拷锟揭碉拷锟侥硷拷, 锟斤拷态锟斤拷锟絡son锟侥憋拷锟斤拷锟斤拷
					makeJsonToResponse(out, version);
				} 
			} else { // 锟斤拷锟斤拷GET锟斤拷锟斤拷
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
	 * 锟斤拷态锟斤拷锟絡son锟侥憋拷锟斤拷锟斤拷
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
		out.write("Server锟斤拷 JHTTP 2.0\r\n");
		out.write("Content-length: " + contentType + "\r\n\r\n");
		out.flush();
	}
}
