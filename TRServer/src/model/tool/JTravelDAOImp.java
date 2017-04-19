package model.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.db.SQLUtil;
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
import model.paramter.Route_Scenery;

/**
 * 注意：景点顺序表的表名改为：Route_Scenery
 * @author 黄远
 *
 */

public class JTravelDAOImp implements JTravelDAO {

	@Override
	public List<Map<String, Object>> InfoForIdWithParamters(IdParamter idParamter) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer("select * from ");
		
		if ("route".equals(idParamter.getInfoType())) {
			sql.append("route where routeId in(");
		} else if("scenery".equals(idParamter.getInfoType())) {
			sql.append("scenery where sceneryId in(");
		} else if("question".equals(idParamter.getInfoType())) {
			sql.append("question where questionId in(");
		} else if ("routeForCommentId".equals(idParamter.getInfoType())) {
			sql.append("note,route where note.noteId=route.noteId and route.noteId in(select noteId from comment where commentId in(");
		} else if ("questionForCommentId".equals(idParamter.getInfoType())) {
			sql.append("note,question where note.noteId=question.noteId and question.noteId in(select noteId from comment where commentId in(");
		}
		
		int len = idParamter.getIdList().size();
		for (int i = 0; i < len; i++) {
			if (i == (len-1)) {
				sql.append(idParamter.getIdList().get(i) + ")");
			} else {
				sql.append(idParamter.getIdList().get(i) + ",");
			}
		}
		
		if ("routeForCommentId".equals(idParamter.getInfoType()) || "questionForCommentId".equals(idParamter.getInfoType())) {
			sql.append(")");
		}
		
		list = SQLUtil.executeQuerySQL(sql.toString());
		
		return list;
	}
	
	@Override
	public boolean Delete(JDeleteParamter deParamter) {
		boolean rs = true;
 		
		// 临时使外键失效
		StringBuffer forBuffer = new StringBuffer("SET FOREIGN_KEY_CHECKS = 0;");
		
		String sql = null;
		if ("comment".equals(deParamter.getDeleteTable())) {
			sql = String.format("delete from %s where %s=%s", 
					deParamter.getDeleteTable(), deParamter.getIdName(), deParamter.getId());
		} else {
			sql = String.format("delete %s.*, note.* from %s,note where %s=%s and %s.noteId=note.noteId;", 
	 				deParamter.getDeleteTable(), deParamter.getDeleteTable(), deParamter.getIdName(), deParamter.getId(),
	 				deParamter.getDeleteTable());
		}
		forBuffer.append(sql);
		
		forBuffer.append("SET FOREIGN_KEY_CHECKS = 1;");
		
		rs = SQLUtil.executeUpdateSQL(sql.toString());
		
		return rs;
	}
	
	@Override
	public List<Map<String, Object>> GetComment(JGetCommentParamter getCommentParamter) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<>();
		String sqlString = null;
		
		if (getCommentParamter.getAccount() == null) {
			sqlString = String.format("select * from comment where noteId=%d and commentId>%d limit %d;", 
					getCommentParamter.getNoteId(), getCommentParamter.getSinceId(), getCommentParamter.getLimit());
		} else {
			sqlString = String.format("select * from comment where " +
					"commentId>%d and account like '%s' limit %d;", 
					getCommentParamter.getSinceId(), getCommentParamter.getAccount(),
					getCommentParamter.getLimit());
		}
		
		list = SQLUtil.executeQuerySQL(sqlString);
		
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getScenery(JSearchSceneryParamter sceneryParamter) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<>();
//		this.createScenery();
		String querySQLString = null;
		if ("city".equals(sceneryParamter.getKeyWorldType())) {
			querySQLString = String.format("select * from scenery where city like '%s' and sceneryId >= %d order by sceneryId limit %d;", 
					sceneryParamter.getKeyWorld(), sceneryParamter.getSinceId().intValue(), sceneryParamter.getLimit());
		} else if("scenery".equals(sceneryParamter.getKeyWorldType())) {
			querySQLString = String.format("select * from scenery where sceneryName like '%s' and sceneryId >= %d order by sceneryId limit %d;", 
					sceneryParamter.getKeyWorld(), sceneryParamter.getSinceId().intValue(), sceneryParamter.getLimit());
		} else if("sceneryIds".equals(sceneryParamter.getKeyWorldType())) {
			list = new ArrayList<>();
			for (int i = 0; i < sceneryParamter.getSceneryIds().size(); i++) {
				StringBuffer sql = new StringBuffer("select * from scenery where sceneryId=");
//				System.out.println(sceneryParamter.getSceneryIds().get(i));
				sql.append(sceneryParamter.getSceneryIds().get(i));
				list.add(SQLUtil.executeQuerySQL(sql.toString()).get(0));
				
			}
			return list;
		}
		
		list = SQLUtil.executeQuerySQL(querySQLString);
		return list;
	}

	@Override
	public boolean addComment(JAddCommentParamter commentParamter) {
		String sqlString = String.format("insert into comment(content, account," +
				"timestamp, imageUrl, noteId, type) values('%s', '%s', '%s', '%s', %s, '%s');", 
				commentParamter.getContent(), commentParamter.getAccount(), commentParamter.getTimestamp(), 
				commentParamter.getImageUrl(), commentParamter.getNoteId(), commentParamter.getCommentType());
		System.out.println(sqlString);
		return SQLUtil.executeUpdateSQL(sqlString);
	}

	@Override
	public List<Map<String, Object>> getNotes(JGetNotesParamter notesParamter) {

		List<Map<String, Object>> list = new ArrayList<>();
		String type = notesParamter.getNoteType();
		String sql = null;
		if ("Route".equals(type)) {
			sql = String.format("select * from route, note where route.noteId = note.noteId and note.noteId < %d ", 
					notesParamter.getSinceId());
		}  else if("Question".equals(type)) {
			sql = String.format("select * from question, note where question.noteId = note.noteId and note.noteId < %d ", 
					notesParamter.getSinceId(), notesParamter.getLimit());
		}
		
		if (notesParamter.getAccount() != null) {
			sql = sql + String.format("and account like '%s' ", notesParamter.getAccount());
		}
		
		
		sql = sql + String.format("order by note.noteId desc limit %d;", notesParamter.getLimit());
		list = SQLUtil.executeQuerySQL(sql);
		
		// 获取每个帖子对应的评论数量
		String commentSql = null;
		Integer count;
		String key = "commentCount";
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			String noteId = (String)map.get("noteId");
			commentSql = String.format("select * from comment where noteId=%s", noteId);
			count = SQLUtil.getCountFromSql(commentSql);
		
			map.put(key, count);
		}
		
		
		return list;
	}

	/**
	 * ������ӱ�(���Ӹ���,�������ӣ��㼣�����,���۱�)
	 */
	private void createNote() {
		String CreateNoteSql = "create table if not exists note (					#���Ӹ���" +
		"`noteId` 		int NOT NULL auto_increment,			#����id(PK, FK)" +
		"`account` 		VARCHAR(50) NULL,			#�������˺�" +
		"`agreeCount` 	int NULL,				#������" +
		"`timestamp` 	DATETIME NULL,				#ʱ���" +
		"`title`			VARCHAR(50) NULL,			#����" +
		"`type` 			VARCHAR(10) NULL,			#����" +
		"primary key (noteId));";
		SQLUtil.executeUpdateSQL(CreateNoteSql);
	}
	
	/**
	 * ���������
	 */
	private void createQuestion() {
		String createQuestionSql = "create table if not exists question  (			#�������"+
		"`noteId` 		int NOT NULL auto_increment,	#�㼣Id(PK, FK)"+
		"`questionId` 	int NOT NULL,	#����id(PK)"+ 
		"`content` 		VARCHAR(1000),		#���������"+
		"`imageUrl` 		VARCHAR(1000),		#�����ͼƬ"+
    	"foreign key (noteId) references note(noteId),"+
		"primary key (noteId,questionId));";
		SQLUtil.executeUpdateSQL(createQuestionSql);
	}
	
	/**
	 * ��ɾ���˳���
	 */
	private void createRoute_Scenery() {
		String createRoute_ScenerySql = "create table if not exists route_scenery  (		#�㼣-�����" +
				"`routeId` 		int NOT NULL auto_increment,	#�㼣Id(PK, FK)" +
				"`sceneryId` 	int NOT NULL auto_increment,	#����ID(PK, FK)" +
				"`scenerySq` 	int,				#������·���е�˳��" +
				"primary key (routeId,sceneryId)," +
				"foreign key (routeId) 	references route(routeId)," +
				"foreign key (sceneryId) references scenery(sceneryId));";
		SQLUtil.executeUpdateSQL(createRoute_ScenerySql);
	}
	
	/**
	 * ����㼣��
	 */
	private void createRoute() {
		String createRouteSql = " create table if not exists route  (					#�㼣���" +
				"`noteId` 		int NOT NULL auto_increment,			#����id(PK, FK)" +
				"`routeId` 		int NOT NULL auto_increment,			#�㼣Id(PK)" +
				"`imageUrl` 		VARCHAR(1000),				#�û��ϴ���ͼƬ" +
				"`content` 		VARCHAR(1000),				#�ĵ�" +
				"foreign key (noteId) references note(noteId)," +
				"primary key (noteId,routeId));";
		SQLUtil.executeUpdateSQL(createRouteSql);
	}
	
	/**
	 * ��ɾ����
	 */
	private void createScenery() {
		String sql = "create table if not exists scenery (					#����� " +
				"`sceneryName` 		VARCHAR(20) NULL,		#�������� " +
				"`sceneryAddress` 	VARCHAR(50) NULL,		#��������ַ " +
				"`sceneryImageUrl` 	VARCHAR(1000) NULL,		#�������ͼƬ " +
				"`scenerySummary` 	VARCHAR(1000) NULL,		#������ " +
				"`ticketSummary` 	VARCHAR(1000) NULL,		#Ʊ������ " +
				"`province` 			VARCHAR(10) NULL,		#ʡ�� " +
				"`city` 				VARCHAR(10) NULL,		#���� " +
				"`sceneryId`			int NOT NULL,		#����ID(PK) " +
				"primary key (sceneryId) " +
			");";
		
		SQLUtil.executeUpdateSQL(sql);
	}
	
	/**
	 * ������۱�
	 */
	private void createComment() {
		String sql = "create table if not exists comment(					#����" +
		"`commentId` int NOT NULL,				#����Id(PK)" +
		"`content` 	VARCHAR(200) NULL,				#������������" +
		"`account` 	VARCHAR(50) NULL,				#�����ߵ��˺�" +
		"`timestamp` DATETIME NULL,					#ʱ���" +
		"`imageUrl` 	VARCHAR(50) NULL,				#���۵�ͼƬ" +
		"`noteId` 	int NOT NULL auto_increment,				#����id(PK, FK)" +
		"primary key (commentId,noteId));";
		SQLUtil.executeUpdateSQL(sql);
	}
	
	/**
	 * ��ɾ���Ʊ�۱�
	 */
	private void createScenery_Tickey() {
		String createScenery_TickeySql = "create table if not exists scenery_ticket  (		#����Ʊ��"+
		"`description` 	VARCHAR(1000),		#Ʊ�����"+
		"`money` 		int,				#�۸�"+
		"`sceneryId` 	int auto_increment,				#����ID(PK, FK)"+
		"`TicketId` 	int auto_increment,				#Ʊ��ID(PK)"+
		"foreign key (sceneryId) references scenery(sceneryId),"+
		"primary key (sceneryId,TicketId));";
		SQLUtil.executeUpdateSQL(createScenery_TickeySql);
	}

	@Override
	public boolean addRoute(JRouteParamters routeParamters) {
		// TODO Auto-generated method stub
		boolean result = false;
//		this.createNote();
//		this.createRoute();
		
		// �Ȳ��븸��
		String insertNoteSql = 
				String.format("insert into note(account, agreeCount, timestamp, title, type) " +
				"values('%s', %d, '%s', '%s', '%s');", 
				routeParamters.getAccount(), routeParamters.getAgreeCount(), 
				routeParamters.getTimestamp(), routeParamters.getTitle(), 
				routeParamters.getType());
		SQLUtil.executeUpdateSQL(insertNoteSql);
		
		// ��ȡ�����Id
		Integer noteId = null;
		String queryNoteIdSql = 
				String.format("select * from note where account like '%s' and timestamp='%s'", 
				routeParamters.getAccount(), routeParamters.getTimestamp());
		List<Map<String, Object>> list = SQLUtil.executeQuerySQL(queryNoteIdSql);
		Map<String, Object> map = list.get(0);
		noteId = new Integer((String)map.get("noteId"));
		
		// �����㼣��
		String insertRouteSql = 
				String.format("insert into route(noteId, imageUrl, content, sceneryIds) " +
				"values(%d, '%s', '%s', '%s')", noteId.intValue(), routeParamters.getImageUrl(), 
				routeParamters.getContent(), routeParamters.getSceneryIds());
		result = SQLUtil.executeUpdateSQL(insertRouteSql);
		
		for (Route_Scenery route_Scenery : routeParamters.getSceneries()) {
			route_Scenery.setNoteId(noteId.intValue());
		}
		
		// �����㼣�����
		SQLUtil.executeUpdataBatch(routeParamters.getSceneries());
		
		return result;
	}

	@Override
	public boolean addQuestion(JQuestionParamters questionParamters) {
		// TODO Auto-generated method stub
		boolean result = false;
//		this.createNote();
//		this.createQuestion();
		
		// �Ȳ��븸��
		String insertNoteSql = 
				String.format("insert into note(account, agreeCount, timestamp, title, type) " +
				"values('%s', %d, '%s', '%s', '%s')", 
				questionParamters.getAccount(), questionParamters.getAgreeCount(), 
				questionParamters.getTimestamp(), questionParamters.getTitle(), 
				questionParamters.getType());
		SQLUtil.executeUpdateSQL(insertNoteSql);
		
		// ��ȡ�����Id
		Integer noteId = null;
		String queryNoteIdSql = 
				String.format("select * from note where account like '%s' and timestamp='%s'", 
				questionParamters.getAccount(), questionParamters.getTimestamp());
		List<Map<String, Object>> list = SQLUtil.executeQuerySQL(queryNoteIdSql);
		Map<String, Object> map = list.get(0);
		noteId = new Integer((String)map.get("noteId"));
		
		// ���������
		String insertRouteSql = 
				String.format("insert into question(noteId, imageUrl, content) " +
				"values(%d, '%s', '%s')", noteId.intValue(), questionParamters.getImageUrl(), 
				questionParamters.getContent());
		result = SQLUtil.executeUpdateSQL(insertRouteSql);
		
		return result;
	}

	@Override
	public List<Map<String, Object>> getSceneryTicket(JTicketParamters ticketParamters) {
//		createScenery_Tickey();
		String queySql = String.format("select * from scenery_ticket where sceneryId like %d", 
				ticketParamters.getSceneryId()); 
		return SQLUtil.executeQuerySQL(queySql);
	}
	
	@Override
	public boolean increaseAgree(JAddAgree addAgree) {
		boolean result = false;
		// ��ѯ��ȡԭ���ĵ�����
		String queryAgreeSql = String.format("select * from note where noteId=%d", addAgree.getNoteId());
		List<Map<String, Object>> list = SQLUtil.executeQuerySQL(queryAgreeSql);
		Map<String, Object> map = list.get(0);
		Integer agree = new Integer((String)map.get("agreeCount"));
	
		if ("in".equals(addAgree.getAgreeType())) {
			agree += 1;
		} else if("sub".equals(addAgree.getAgreeType())) {
			agree -= 1;
		}
		
		
		// д����ݿ�
		String realoadSql = String.format("update note set agreeCount = %d where noteId=%d", 
				agree.intValue(), addAgree.getNoteId());
		result = SQLUtil.executeUpdateSQL(realoadSql);
		
		return result;
	}
	
}
