package controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JHTTPServer {
	/** 
	 * 创建日志管理对象
	 * */
	private static final Logger logger = Logger.getLogger(
			"JHTTP.class.getCaonicalName");
	
	/** 
	 * 最大线程数量
	 * */
	private static final int NUM_THREADS = 1000; 

	/** 
	 * 创建文件默认路径
	 * */
	private static final String INDEX_FILE = "index.html";

	/** 
	 * 文件根目录
	 * */
	private final File rootDirectory;

	/** 
	 * 服务器端口
	 * */
	private final int port;
	
	public JHTTPServer(File rootDirectory, int port) throws IOException {
		// TODO Auto-generated constructor stub
		if (!rootDirectory.isDirectory()) {
			throw new IOException(rootDirectory + "does not exist as" +
					"a directory");
		}
		
		this.port = port;
		this.rootDirectory = rootDirectory;
	}
	
	/** 
	 * 	启动服务器
	 * */
	public void start() {
		ExecutorService threadPool = Executors.newFixedThreadPool(NUM_THREADS);
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port, NUM_THREADS, InetAddress.getLocalHost());
			logger.info("Accepting connections on port" + " "
					+ serverSocket.getLocalPort());
			logger.info("localhost: " + serverSocket.getInetAddress().getHostAddress());
			logger.info("Document Root: " + rootDirectory);
			logger.info("开始监听客户端请求");
			
			while (true) {
				try {
					Socket request = serverSocket.accept();
//					logger.info("有新的请求到来:" + request);
					RequestProcessor requestProcessor = new RequestProcessor(rootDirectory, INDEX_FILE, request);  
					threadPool.submit(requestProcessor);
				} catch (Exception e) {
					// TODO: handle exception
					logger.log(Level.WARNING, "Error accepting caonnection");
				}
			}
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
}
