package model.tool.contentTool;

import java.util.Map;

import model.bean.JHttpRequest;


/**
 * 响应内容的接口
 * @author 黄远
 *
 */

public interface JContentDAO {
	
	/**
	 * 上传足迹
	 * @param request
	 * @param response
	 */
	public Map<String, Object> AddRouteWithParamters(JHttpRequest request);
	
	/**
	 * 上传问题
	 * @param request
	 * @param response
	 */
	public Map<String, Object> AddQuestionWithParamters(JHttpRequest request);
	
	/**
	 * 请求票价
	 * @param request
	 * @param response
	 */
	public Map<String, Object> queryTicketWithParamters(JHttpRequest request);
	
	/**
	 * 增加点赞数
	 * @param request
	 * @param response
	 */
	public Map<String, Object> increaseAgreeWithParamters(JHttpRequest request);
	
	/**
	 * 根据请求获取景点
	 * @param request
	 */
	public Map<String, Object> QuerySceneryWithParamters(JHttpRequest request);

	/**
	 * 根据请求增加一条评论
	 * @param request
	 */
	public Map<String, Object> AddCommentWithParamters(JHttpRequest request);
	
	/**
	 * 根据请求获取帖子
	 * @param request
	 */
	public Map<String, Object> GetNoteWithParamters(JHttpRequest request);
}
