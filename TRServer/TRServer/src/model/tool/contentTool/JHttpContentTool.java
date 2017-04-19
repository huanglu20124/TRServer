package model.tool.contentTool;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import model.bean.JHttpRequest;
import model.bean.JHttpResponse;
import model.tool.JImageDAO;
import model.tool.JImageDAOImp;

/**
 * 根据请求的类型来构建响应内容
 * 从数据请求层读取对应的数据，然后在本文件中转化为JSON格式文本
 */
public class JHttpContentTool {
	public static String getJsonWithRequestAndResponse(JHttpRequest request, JHttpResponse response) {
		// 根据请求的类型从请求层读取对应的数据，然后转化为JSON
		switch (request.getType()) {
		case "queryScenery":   
			JHttpContentTool.QuerySceneryWithParamters(request, response);
			break;

		case "addComment" :	
			JHttpContentTool.AddCommentWithParamters(request, response);
			break;
			
		case "GetNote" :
			JHttpContentTool.GetNoteWithParamters(request, response);
			break;
			
		case "upImage" :
			JHttpContentTool.saveImageWithParamters(request, response);
			break;
		
		case "upRoute" :
			JHttpContentTool.AddRouteWithParamters(request, response);
			break;
		
		case "upQuestion" :
			JHttpContentTool.AddQuestionWithParamters(request, response);
			break;
			
		case "query_Ticket" :
			JHttpContentTool.queryTicketWithParamters(request, response);
			break;
			
		case "agree" :
			JHttpContentTool.increaseAgreeWithParamters(request, response);
			break;
		
		default:
			// 如果请求的类型有问题，将响应码改为404
			response.setResponseCode("HTTP/1.0 404 Not Found");
			break;
		}
		return response.getJson();
	}
	
	
	/**
	 * 增加点赞数
	 * @param request
	 * @param response
	 */
	private static void increaseAgreeWithParamters(JHttpRequest request, JHttpResponse response) {
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			response.setJson(JHttpContentTool.JsonFromData(contentDAO.increaseAgreeWithParamters(request)));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 请求票价
	 * @param request
	 * @param response
	 */
	private static void queryTicketWithParamters(JHttpRequest request, JHttpResponse response) {

		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.queryTicketWithParamters(request);
			// 将数据转化为JSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传问题
	 * @param request
	 * @param response
	 */
	private static void AddQuestionWithParamters(JHttpRequest request, JHttpResponse response) {
		
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.AddQuestionWithParamters(request);
			// 将数据转化为JSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 上传足迹
	 * @param request
	 * @param response
	 */
	private static void AddRouteWithParamters(JHttpRequest request, JHttpResponse response) {
		
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.AddRouteWithParamters(request);
			// 将数据转化为JSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据请求，将图片存入服务器
	 * @param request
	 * @param response
	 */
	public static void saveImageWithParamters(JHttpRequest request, JHttpResponse response) {
		
		try {
			JImageDAO imageDAO = new JImageDAOImp();
			boolean result = imageDAO.saveImageInServerByImageUrl(request.getByteFile("image"), (String)request.getParamter("imageUrl"));
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("saveImageResult", result);
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 请求景点信息
	 * @param request
	 * @param response
	 */
	private static void QuerySceneryWithParamters(JHttpRequest request, JHttpResponse response) {
		
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.QuerySceneryWithParamters(request);
			// 将数据转化为JSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加一条评论
	 * @param request
	 * @param response
	 */
	private static void AddCommentWithParamters(JHttpRequest request, JHttpResponse response) {

		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.AddCommentWithParamters(request);
			// 将数据转化为JSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取帖子信息
	 * @param request
	 * @param response
	 */
	private static void GetNoteWithParamters(JHttpRequest request, JHttpResponse response) {
	
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.GetNoteWithParamters(request);
			// 将数据转化为JSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 将数据转化为JSON
	 * @param map
	 * @return String
	 */
	private static String JsonFromData(Map<String, Object> map) {
		String json = null;
		JSONObject jsonObject = JSONObject.fromObject(map);
		json = jsonObject.toString();
		return json;
	}
}
