package model.tool.contentTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bean.JHttpRequest;
import model.paramter.JAddAgree;
import model.paramter.JAddCommentParamter;
import model.paramter.JGetNotesParamter;
import model.paramter.JQuestionParamters;
import model.paramter.JRouteParamters;
import model.paramter.JSearchSceneryParamter;
import model.paramter.JTicketParamters;
import model.tool.JTravelDAO;
import model.tool.JTravelDaoFactory;

/**
 * JHttpContentTool的子工具类，对应于处理景点相关的响应内容
 */

public class JTravelContentTool implements JContentDAO {
	/**
	 * 根据请求获取景点
	 * @param request
	 */
	public Map<String, Object> QuerySceneryWithParamters(JHttpRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JTravelDAO travelDAO = JTravelDaoFactory.getTravelDaoInstance();
		
		JSearchSceneryParamter sceneryParamter = new JSearchSceneryParamter();
		try {
			sceneryParamter.setKeyWorld((String)request.getParamter("keyWord"));
			sceneryParamter.setKeyWorldType((String)request.getParamter("keyWordType"));
			sceneryParamter.setLimit(new Integer((String)request.getParamter("limit")));
			if (request.getParamter("sinceId") != null) {
				String sinceIdsString = ((String)request.getParamter("sinceId")).trim();
				sceneryParamter.setSinceId(new Integer(sinceIdsString));
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			System.err.println("参数转化错误:");
			e.printStackTrace();
		}
		
		ArrayList<Map<String, Object>> arrayList = (ArrayList<Map<String,Object>>)travelDAO.getScenery(sceneryParamter);
		resultMap.put("scenerys", arrayList);
		
		return resultMap;
	}

	/**
	 * 根据请求增加一条评论
	 * @param request
	 */
	public Map<String, Object> AddCommentWithParamters(JHttpRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JTravelDAO travelDAO = JTravelDaoFactory.getTravelDaoInstance();
		JAddCommentParamter commentParamter = new JAddCommentParamter();
		commentParamter.setAccount((String)request.getParamter("account"));
		commentParamter.setCommentType((String)request.getParamter("commentType"));
		commentParamter.setContent((String)request.getParamter("content"));
		commentParamter.setTimestamp((String)request.getParamter("timestamp"));
		commentParamter.setImageUrl((String)request.getParamter("imageUrl"));
		commentParamter.setNoteId((String)request.getParamter("noteId"));
		
		boolean result = travelDAO.addComment(commentParamter);
		String resultString = result == true ? "true" : "false";
		resultMap.put("AddCommentResult", resultString);
		
		return resultMap;
	}

	/**
	 * 根据请求获取帖子
	 * @param request
	 */
	public Map<String, Object> GetNoteWithParamters(JHttpRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JTravelDAO travelDAO = JTravelDaoFactory.getTravelDaoInstance();
		
		JGetNotesParamter notesParamter = new JGetNotesParamter();
		notesParamter.setNoteType((String)request.getParamter("NoteType"));
		if (request.getParamter("limit") != null) {
			String limitString = ((String)request.getParamter("limit")).trim();
			notesParamter.setLimit(new Integer(limitString));
		} 
		
		ArrayList<Map<String, Object>> list = (ArrayList<Map<String,Object>>)travelDAO.getNotes(notesParamter);
		resultMap.put("Notes", list);
		
		return resultMap;
	}

	@Override
	public Map<String, Object> AddRouteWithParamters(JHttpRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JTravelDAO travelDAO = JTravelDaoFactory.getTravelDaoInstance();
		
		JRouteParamters routeParamters = new JRouteParamters();
		routeParamters.setAccount((String)request.getParamter("account"));
		if (request.getParamter("agreeCount") != null) {
			routeParamters.setAgreeCount(new Integer((String)request.getParamter("agreeCount")));
		}
		routeParamters.setContent((String)request.getParamter("content"));
		routeParamters.setImageUrl((String)request.getParamter("imageUrl"));
		routeParamters.setTimestamp((String)request.getParamter("timestamp"));
		routeParamters.setTitle((String)request.getParamter("title"));
		routeParamters.setType((String)request.getParamter("NoteType"));
		
		boolean result = travelDAO.addRoute(routeParamters);
		resultMap.put("AddRouteResult", result);
		
		
		return resultMap;
	}

	@Override
	public Map<String, Object> AddQuestionWithParamters(JHttpRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JTravelDAO travelDAO = JTravelDaoFactory.getTravelDaoInstance();
		
		JQuestionParamters questionParamters = new JQuestionParamters();
		questionParamters.setAccount((String)request.getParamter("account"));
		if (request.getParamter("agreeCount") != null) {
			String agreeC = ((String)request.getParamter("agreeCount")).trim();
			questionParamters.setAgreeCount(new Integer(agreeC));	
		}
		questionParamters.setContent((String)request.getParamter("content"));
		questionParamters.setImageUrl((String)request.getParamter("imageUrl"));
		questionParamters.setTimestamp((String)request.getParamter("timestamp"));
		questionParamters.setTitle((String)request.getParamter("title"));
		questionParamters.setType((String)request.getParamter("NoteType"));
		
		boolean result = travelDAO.addQuestion(questionParamters);
		resultMap.put("AddQuestionResult", result);
		
		
		return resultMap;
	}

	@Override
	public Map<String, Object> queryTicketWithParamters(JHttpRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JTravelDAO travelDAO = JTravelDaoFactory.getTravelDaoInstance();
		
		JTicketParamters ticketParamters = new JTicketParamters();
		if (request.getParamter("sceneryId") != null) {
			ticketParamters.setSceneryId(new Integer((String)request.getParamter("sceneryId")));
		}
		List<Map<String, Object>> list = travelDAO.getSceneryTicket(ticketParamters);
		resultMap.put("Tickets", list);
	
		return resultMap;
	}

	@Override
	public Map<String, Object> increaseAgreeWithParamters(JHttpRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JTravelDAO travelDAO = JTravelDaoFactory.getTravelDaoInstance();
	
		JAddAgree addAgree = new JAddAgree();
		if(request.getParamter("noteId") != null) {
			String notedIIIString = ((String)request.getParamter("noteId")).trim();
			addAgree.setNoteId(new Integer(notedIIIString));
		}
		boolean result = travelDAO.increaseAgree(addAgree);
		resultMap.put("addAgreeCountResult", result);
		
		return resultMap;
	}
	
	
	
}
