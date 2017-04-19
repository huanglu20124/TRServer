package model.tool.contentTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bean.JHttpRequest;
import model.paramter.IdParamter;
import model.paramter.JAddAgree;
import model.paramter.JAddCommentParamter;
import model.paramter.JDeleteParamter;
import model.paramter.JGetCommentParamter;
import model.paramter.JGetNotesParamter;
import model.paramter.JQuestionParamters;
import model.paramter.JRouteParamters;
import model.paramter.JSearchSceneryParamter;
import model.paramter.JTicketParamters;
import model.tool.JImageDAO;
import model.tool.JImageDAOImp;
import model.tool.JTravelDAO;
import model.tool.JTravelDaoFactory;

/**
 * JHttpContentTool���ӹ����࣬��Ӧ�ڴ��?����ص���Ӧ����
 */

public class JTravelContentTool implements JContentDAO {
	/**
	 * 根据id获取信息
	 */
	@Override
	public Map<String, Object>InfoForIdWithParamters(JHttpRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String tag = null;
		
		IdParamter idParamter = new IdParamter();
		idParamter.setInfoType(((String)request.getParamter("infoType")).trim());
		idParamter.setIds(((String)request.getParamter("ids")).trim());
		
		if ("route".equals(idParamter.getInfoType())) {
			tag = "route";
		} else if("scenery".equals(idParamter.getInfoType())) {
			tag = "scenery";
		} else if("question".equals(idParamter.getInfoType())) {
			tag = "question";
		} else if ("routeForCommentId".equals(idParamter.getInfoType())) {
			tag = "routeForCommentId";
		} else if ("questionForCommentId".equals(idParamter.getInfoType())) {
			tag = "questionForCommentId";
		}
		
		
		JTravelDAO travelDAO = JTravelDaoFactory.getTravelDaoInstance();
		
		List<Map<String, Object>> list = travelDAO.InfoForIdWithParamters(idParamter);
		resultMap.put(tag, list);
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> GetCommnetWithParamters(JHttpRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JTravelDAO travelDAO = JTravelDaoFactory.getTravelDaoInstance();
		JGetCommentParamter getCommentParamter = new JGetCommentParamter();
		if(request.getParamter("noteId") != null) {
			getCommentParamter.setNoteId(new Integer(((String)request.getParamter("noteId")).trim()));
		}
		if (request.getParamter("sinceId") != null) {
			getCommentParamter.setSinceId(new Integer(((String)request.getParamter("sinceId")).trim()));
		}
		
		if (request.getParamter("limit") != null) {
			getCommentParamter.setLimit(new Integer(((String)request.getParamter("limit")).trim()));
		}
		
		if (request.getParamter("account") != null) {
			getCommentParamter.setAccount(((String)request.getParamter("account")).trim());
		}
		
		List<Map<String, Object>> list = travelDAO.GetComment(getCommentParamter);
		resultMap.put("comments", list);
		
		return resultMap;
	}
	
	/**
	 * ��������ȡ����
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
			
			if("sceneryIds".equals(sceneryParamter.getKeyWorldType())) {
				sceneryParamter.setScenerysIds((String)request.getParamter("keyWord"));
			}
			
		} catch (NumberFormatException e) {
			// TODO: handle exception
			System.err.println("����ת������:");
			e.printStackTrace();
		}
		
		ArrayList<Map<String, Object>> arrayList = (ArrayList<Map<String,Object>>)travelDAO.getScenery(sceneryParamter);
		resultMap.put("scenerys", arrayList);
		
		return resultMap;
	}

	/**
	 * �����������һ������
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
		
		if (commentParamter.getImageUrl() != null) {
			// 说明用户上传了图片
			JImageDAO imageDAO = new JImageDAOImp();
			String image = (String)request.getParamter("image");
			String imageUrl = (String)request.getParamter("imageUrl");
			imageDAO.saveImageInServerByImageUrl(image, imageUrl);
		}
		
		boolean result = travelDAO.addComment(commentParamter);
		String resultString = result == true ? "true" : "false";
		resultMap.put("AddCommentResult", resultString);
		
		return resultMap;
	}

	/**
	 * ��������ȡ����
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
		
		if(request.getParamter("sinceId") != null) {
			notesParamter.setSinceId(Integer.parseInt(((String)request.getParamter("sinceId")).trim()));
		}
		
		if (request.getParamter("account") != null) {
			notesParamter.setAccount(((String)request.getParamter("account")).trim());
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
		
		if(request.getParamter("sceneries") != null) {
			routeParamters.setSceneris((String)request.getParamter("sceneries"));
		}
		
		if (routeParamters.getImageUrl() != null) {
			// 说明用户上传了图片
			JImageDAO imageDAO = new JImageDAOImp();
			String image = (String)request.getParamter("image");
			String imageUrl = (String)request.getParamter("imageUrl");
			imageDAO.saveImageInServerByImageUrl(image, imageUrl);
		}
		
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
		
		if (questionParamters.getImageUrl() != null) {
			// 说明用户上传了图片
			JImageDAO imageDAO = new JImageDAOImp();
			String image = (String)request.getParamter("image");
			String imageUrl = (String)request.getParamter("imageUrl");
			imageDAO.saveImageInServerByImageUrl(image, imageUrl);
		}
		
		
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
		
		addAgree.setAgreeType((String)request.getParamter("agreeType"));
		
		
		boolean result = travelDAO.increaseAgree(addAgree);
		resultMap.put("addAgreeCountResult", result);
		
		return resultMap;
	}

	@Override
	public Map<String, Object> DeleteWithParamters(JHttpRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JTravelDAO travelDAO = JTravelDaoFactory.getTravelDaoInstance();
	
		// 1、确定删除模型的参数
		JDeleteParamter deleteParamter = new JDeleteParamter();
		deleteParamter.setDeleteTable(((String)request.getParamter("deleteTable")).trim());
		deleteParamter.setIdName(((String)request.getParamter("IdName")).trim());
		deleteParamter.setId(((String)request.getParamter("Id")).trim());
		
		boolean result = travelDAO.Delete(deleteParamter);
		resultMap.put("deleteResult", result);
		
		return resultMap;
	}
	
	
	
}
