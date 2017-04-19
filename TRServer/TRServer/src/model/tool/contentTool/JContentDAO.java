package model.tool.contentTool;

import java.util.Map;

import model.bean.JHttpRequest;


/**
 * ��Ӧ���ݵĽӿ�
 * @author ��Զ
 *
 */

public interface JContentDAO {
	
	/**
	 * �ϴ��㼣
	 * @param request
	 * @param response
	 */
	public Map<String, Object> AddRouteWithParamters(JHttpRequest request);
	
	/**
	 * �ϴ�����
	 * @param request
	 * @param response
	 */
	public Map<String, Object> AddQuestionWithParamters(JHttpRequest request);
	
	/**
	 * ����Ʊ��
	 * @param request
	 * @param response
	 */
	public Map<String, Object> queryTicketWithParamters(JHttpRequest request);
	
	/**
	 * ���ӵ�����
	 * @param request
	 * @param response
	 */
	public Map<String, Object> increaseAgreeWithParamters(JHttpRequest request);
	
	/**
	 * ���������ȡ����
	 * @param request
	 */
	public Map<String, Object> QuerySceneryWithParamters(JHttpRequest request);

	/**
	 * ������������һ������
	 * @param request
	 */
	public Map<String, Object> AddCommentWithParamters(JHttpRequest request);
	
	/**
	 * ���������ȡ����
	 * @param request
	 */
	public Map<String, Object> GetNoteWithParamters(JHttpRequest request);
}
