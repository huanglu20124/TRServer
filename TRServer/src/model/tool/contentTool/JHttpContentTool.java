package model.tool.contentTool;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import model.bean.JHttpRequest;
import model.bean.JHttpResponse;
import model.tool.JImageDAO;
import model.tool.JImageDAOImp;

/**
 * ��������������������Ӧ����
 * �����������ȡ��Ӧ����ݣ�Ȼ���ڱ��ļ���ת��ΪJSON��ʽ�ı�
 */
public class JHttpContentTool {
	public static String getJsonWithRequestAndResponse(JHttpRequest request, JHttpResponse response) {
		// �����������ʹ�������ȡ��Ӧ����ݣ�Ȼ��ת��ΪJSON
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
			
		case "upImage" :  // 不用的，考虑不周
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
		
		case "GetComment" :
			JHttpContentTool.GetCommentWithParamters(request, response);
			break;
			
		case "delete" :
			JHttpContentTool.DeleteWithParamters(request, response);
			break;
		case "InfoForId" :
			JHttpContentTool.InfoForIdWithParamters(request, response);
			break;
		default:
			response.setResponseCode("HTTP/1.0 404 Not Found");
			break;
		}
		return response.getJson();
	}
	
	
	/**
	 * 根据Id获取信息
	 * @param request
	 * @param response
	 */
	private static void InfoForIdWithParamters(JHttpRequest request, JHttpResponse response) {
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			response.setJson(JHttpContentTool.JsonFromData(contentDAO.InfoForIdWithParamters(request)));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除帖子
	 * @param request
	 * @param response
	 */
	private static void DeleteWithParamters(JHttpRequest request, JHttpResponse response) {
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			response.setJson(JHttpContentTool.JsonFromData(contentDAO.DeleteWithParamters(request)));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/** ��ȡ����
	 * @param request
	 * @param response
	 */
	private static void GetCommentWithParamters(JHttpRequest request, JHttpResponse response) {
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			response.setJson(JHttpContentTool.JsonFromData(contentDAO.GetCommnetWithParamters(request)));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ӵ�����
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
	 * ����Ʊ��
	 * @param request
	 * @param response
	 */
	private static void queryTicketWithParamters(JHttpRequest request, JHttpResponse response) {

		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.queryTicketWithParamters(request);
			// �����ת��ΪJSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * �ϴ�����
	 * @param request
	 * @param response
	 */
	private static void AddQuestionWithParamters(JHttpRequest request, JHttpResponse response) {
		
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.AddQuestionWithParamters(request);
			// �����ת��ΪJSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * �ϴ��㼣
	 * @param request
	 * @param response
	 */
	private static void AddRouteWithParamters(JHttpRequest request, JHttpResponse response) {
		
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.AddRouteWithParamters(request);
			// �����ת��ΪJSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ������󣬽�ͼƬ���������
	 * @param request
	 * @param response
	 */
	public static void saveImageWithParamters(JHttpRequest request, JHttpResponse response) {
		
		try {
//			JImageDAO imageDAO = new JImageDAOImp();
//			boolean result = imageDAO.saveImageInServerByImageUrl(request.getByteFile("image"), (String)request.getParamter("imageUrl"));
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//			resultMap.put("saveImageResult", result);
//			response.setJson(JHttpContentTool.JsonFromData(resultMap));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ���󾰵���Ϣ
	 * @param request
	 * @param response
	 */
	private static void QuerySceneryWithParamters(JHttpRequest request, JHttpResponse response) {
		
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.QuerySceneryWithParamters(request);
			// �����ת��ΪJSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ����һ������
	 * @param request
	 * @param response
	 */
	private static void AddCommentWithParamters(JHttpRequest request, JHttpResponse response) {

		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.AddCommentWithParamters(request);
			// �����ת��ΪJSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @param request
	 * @param response
	 */
	private static void GetNoteWithParamters(JHttpRequest request, JHttpResponse response) {
	
		try {
			JContentDAO contentDAO = JContentDAOFactory.getContentDAOInstance();
			Map<String, Object> resultMap = contentDAO.GetNoteWithParamters(request);
			// �����ת��ΪJSON
			response.setJson(JHttpContentTool.JsonFromData(resultMap));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ת��ΪJSON
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
