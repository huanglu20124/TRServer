package model.tool;

import java.util.List;
import java.util.Map;

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

public interface JTravelDAO {
	/**
	 * 获取景点信息，信息是无排序的
	 * @param idParamter
	 * @return
	 */
	public List<Map<String, Object>> InfoForIdWithParamters(IdParamter idParamter);
	
	/**
	 * 删除数据
	 * @param deParamter
	 * @return
	 */
	public boolean Delete(JDeleteParamter deParamter);
	
	/**
	 * ��ȡ����
	 * @param getCommentParamter
	 * @return
	 */
	public List<Map<String, Object>> GetComment(JGetCommentParamter getCommentParamter);
	
	/**
	 * ���ؾ�����Ϣ
	 * @param sceneryParamter
	 * @return
	 */
	public List<Map<String, Object>> getScenery(JSearchSceneryParamter sceneryParamter);
	
	/**
	 * ����һ������
	 * @param commentParamter
	 * @return
	 */
	public boolean addComment(JAddCommentParamter commentParamter);

	/**
	 * ��ȡ����
	 * @param notesParamter
	 * @return
	 */
	public List<Map<String, Object>> getNotes(JGetNotesParamter notesParamter);
	
	/**
	 * ����һ���㼣����
	 * @param routeParamters
	 * @return
	 */
	public boolean addRoute(JRouteParamters routeParamters);
	
	/**
	 * ����һ����������
	 * @param questionParamters
	 * @return
	 */
	public boolean addQuestion(JQuestionParamters questionParamters);
	
	/**
	 * ����ĳһ�������Ӧ��Ʊ����Ϣ
	 * @param ticketParamters
	 * @return
	 */
	public List<Map<String, Object>> getSceneryTicket(JTicketParamters ticketParamters);
	
	/**
	 * ���ӵ�����
	 * @param addAgree
	 * @return
	 */
	public boolean increaseAgree(JAddAgree addAgree);
}
