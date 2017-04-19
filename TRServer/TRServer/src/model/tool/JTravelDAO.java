package model.tool;

import java.util.List;
import java.util.Map;

import model.paramter.JAddAgree;
import model.paramter.JAddCommentParamter;
import model.paramter.JGetNotesParamter;
import model.paramter.JQuestionParamters;
import model.paramter.JRouteParamters;
import model.paramter.JSearchSceneryParamter;
import model.paramter.JTicketParamters;

public interface JTravelDAO {
	/**
	 * 返回景点信息
	 * @param sceneryParamter
	 * @return
	 */
	public List<Map<String, Object>> getScenery(JSearchSceneryParamter sceneryParamter);
	
	/**
	 * 增加一条评论
	 * @param commentParamter
	 * @return
	 */
	public boolean addComment(JAddCommentParamter commentParamter);

	/**
	 * 获取帖子
	 * @param notesParamter
	 * @return
	 */
	public List<Map<String, Object>> getNotes(JGetNotesParamter notesParamter);
	
	/**
	 * 增加一条足迹帖子
	 * @param routeParamters
	 * @return
	 */
	public boolean addRoute(JRouteParamters routeParamters);
	
	/**
	 * 增加一条问题帖子
	 * @param questionParamters
	 * @return
	 */
	public boolean addQuestion(JQuestionParamters questionParamters);
	
	/**
	 * 请求某一个景点对应的票价信息
	 * @param ticketParamters
	 * @return
	 */
	public List<Map<String, Object>> getSceneryTicket(JTicketParamters ticketParamters);
	
	/**
	 * 增加点赞数
	 * @param addAgree
	 * @return
	 */
	public boolean increaseAgree(JAddAgree addAgree);
}
