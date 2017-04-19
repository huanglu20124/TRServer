package model.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.db.SQLUtil;
import model.paramter.JAddAgree;
import model.paramter.JAddCommentParamter;
import model.paramter.JGetNotesParamter;
import model.paramter.JQuestionParamters;
import model.paramter.JRouteParamters;
import model.paramter.JSearchSceneryParamter;
import model.paramter.JTicketParamters;

public class JTravelDAOImp implements JTravelDAO {

	@Override
	public List<Map<String, Object>> getScenery(JSearchSceneryParamter sceneryParamter) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<>();
//		this.createScenery();
		String querySQLString = null;
		if ("city".equals(sceneryParamter.getKeyWorldType())) {
			querySQLString = String.format("select * from Scenery where city like '%s' and sceneryId >= %d order by sceneryId limit %d;", 
					sceneryParamter.getKeyWorld(), sceneryParamter.getSinceId().intValue(), sceneryParamter.getLimit());
		} else if("scenery".equals(sceneryParamter.getKeyWorldType())) {
			querySQLString = String.format("select * from Scenery where sceneryName like '%s' and sceneryId >= %d order by sceneryId limit %d;", 
					sceneryParamter.getKeyWorld(), sceneryParamter.getSinceId().intValue(), sceneryParamter.getLimit());
		}
		
		list = SQLUtil.executeQuerySQL(querySQLString);
		return list;
	}

	@Override
	public boolean addComment(JAddCommentParamter commentParamter) {
//		this.createComment();
		
		String sqlString = String.format("insert into Comment(content, account," +
				"timestamp, imageUrl, noteId) values('%s', '%s', '%s', '%s', %s) ", 
				commentParamter.getContent(), commentParamter.getAccount(), commentParamter.getTimestamp(), 
				commentParamter.getImageUrl(), commentParamter.getNoteId());
		
		return SQLUtil.executeUpdateSQL(sqlString);
	}

	@Override
	public List<Map<String, Object>> getNotes(JGetNotesParamter notesParamter) {

		List<Map<String, Object>> list = new ArrayList<>();
		String type = notesParamter.getNoteType();
		String sql = null;
		if ("Route".equals(type)) {
			sql = String.format("select * from Route, Note where Route.noteId = Note.noteId and Note.noteId > %d order by Note.noteId limit %d;", 
					notesParamter.getSinceId(), notesParamter.getLimit());
		}  else if("Question".equals(type)) {
			sql = String.format("select * from Question, Note where Question.noteId = Note.noteId and Note.noteId > %d order by Note.noteId limit %d;", 
					notesParamter.getSinceId(), notesParamter.getLimit());
		}
		list = SQLUtil.executeQuerySQL(sql);
		
		// ��ȡ����������Ŀ
		String countCommentSql = null;
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Integer noteId = new Integer((String)map.get("noteId"));
			countCommentSql = String.format("select count(*) as commentCount from comment where noteId = %d;", noteId);
			List<Map<String, Object>> resultList = SQLUtil.executeQuerySQL(countCommentSql);
			Map<String, Object> rm = resultList.get(0);
			map.put("commentCount", rm.get("commentCount"));
		}
		
		return list;
	}

	/**
	 * �������ӱ�(���Ӹ���,�������ӣ��㼣�����,���۱�)
	 */
	private void createNote() {
		String CreateNoteSql = "create table if not exists Note (					#���Ӹ���" +
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
	 * ����������
	 */
	private void createQuestion() {
		String createQuestionSql = "create table if not exists Question  (			#��������"+
		"`noteId` 		int NOT NULL auto_increment,	#�㼣Id(PK, FK)"+
		"`questionId` 	int NOT NULL,	#����id(PK)"+ 
		"`content` 		VARCHAR(1000),		#���������"+
		"`imageUrl` 		VARCHAR(1000),		#�����ͼƬ"+
    	"foreign key (noteId) references Note(noteId),"+
		"primary key (noteId,questionId));";
		SQLUtil.executeUpdateSQL(createQuestionSql);
	}
	
	/**
	 * ���ɾ���˳���
	 */
	private void createRoute_Scenery() {
		String createRoute_ScenerySql = "create table if not exists Route_Scenery  (		#�㼣-�����" +
				"`routeId` 		int NOT NULL auto_increment,	#�㼣Id(PK, FK)" +
				"`sceneryId` 	int NOT NULL auto_increment,	#����ID(PK, FK)" +
				"`scenerySq` 	int,				#������·���е�˳��" +
				"primary key (routeId,sceneryId)," +
				"foreign key (routeId) 	references Route(routeId)," +
				"foreign key (sceneryId) references Scenery(sceneryId));";
		SQLUtil.executeUpdateSQL(createRoute_ScenerySql);
	}
	
	/**
	 * �����㼣��
	 */
	private void createRoute() {
		String createRouteSql = " create table if not exists Route  (					#�㼣����" +
				"`noteId` 		int NOT NULL auto_increment,			#����id(PK, FK)" +
				"`routeId` 		int NOT NULL auto_increment,			#�㼣Id(PK)" +
				"`imageUrl` 		VARCHAR(1000),				#�û��ϴ���ͼƬ" +
				"`content` 		VARCHAR(1000),				#�ĵ�" +
				"foreign key (noteId) references Note(noteId)," +
				"primary key (noteId,routeId));";
		SQLUtil.executeUpdateSQL(createRouteSql);
	}
	
	/**
	 * ���ɾ����
	 */
	private void createScenery() {
		String sql = "create table if not exists Scenery (					#����� " +
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
	 * �������۱�
	 */
	private void createComment() {
		String sql = "create table if not exists Comment(					#����" +
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
	 * ���ɾ���Ʊ�۱�
	 */
	private void createScenery_Tickey() {
		String createScenery_TickeySql = "create table if not exists Scenery_Ticket  (		#����Ʊ��"+
		"`description` 	VARCHAR(1000),		#Ʊ�����"+
		"`money` 		int,				#�۸�"+
		"`sceneryId` 	int auto_increment,				#����ID(PK, FK)"+
		"`TicketId` 	int auto_increment,				#Ʊ��ID(PK)"+
		"foreign key (sceneryId) references Scenery(sceneryId),"+
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
				String.format("insert into Note(account, agreeCount, timestamp, title, type) " +
				"values('%s', %d, '%s', '%s', '%s');", 
				routeParamters.getAccount(), routeParamters.getAgreeCount(), 
				routeParamters.getTimestamp(), routeParamters.getTitle(), 
				routeParamters.getType());
		SQLUtil.executeUpdateSQL(insertNoteSql);
		
		// ��ȡ�����Id
		Integer noteId = null;
		String queryNoteIdSql = 
				String.format("select * from Note where account like '%s' and timestamp='%s'", 
				routeParamters.getAccount(), routeParamters.getTimestamp());
		List<Map<String, Object>> list = SQLUtil.executeQuerySQL(queryNoteIdSql);
		Map<String, Object> map = list.get(0);
		noteId = new Integer((String)map.get("noteId"));
		
		// �����㼣��
		String insertRouteSql = 
				String.format("insert into Route(noteId, imgaeUrl, content) " +
				"values(%d, '%s', '%s')", noteId.intValue(), routeParamters.getImageUrl(), 
				routeParamters.getContent());
		result = SQLUtil.executeUpdateSQL(insertRouteSql);
		
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
				String.format("insert into Note(account, agreeCount, timestamp, title, type) " +
				"values('%s', %d, '%s', '%s', '%s')", 
				questionParamters.getAccount(), questionParamters.getAgreeCount(), 
				questionParamters.getTimestamp(), questionParamters.getTitle(), 
				questionParamters.getType());
		SQLUtil.executeUpdateSQL(insertNoteSql);
		
		// ��ȡ�����Id
		Integer noteId = null;
		String queryNoteIdSql = 
				String.format("select * from Note where account like '%s' and timestamp='%s'", 
				questionParamters.getAccount(), questionParamters.getTimestamp());
		List<Map<String, Object>> list = SQLUtil.executeQuerySQL(queryNoteIdSql);
		Map<String, Object> map = list.get(0);
		noteId = new Integer((String)map.get("noteId"));
		
		// ���������
		String insertRouteSql = 
				String.format("insert into Question(noteId, imageUrl, content) " +
				"values(%d, '%s', '%s')", noteId.intValue(), questionParamters.getImageUrl(), 
				questionParamters.getContent());
		result = SQLUtil.executeUpdateSQL(insertRouteSql);
		
		return result;
	}

	@Override
	public List<Map<String, Object>> getSceneryTicket(JTicketParamters ticketParamters) {
//		createScenery_Tickey();
		String queySql = String.format("select * from Scenery_Ticket where sceneryId like %d", 
				ticketParamters.getSceneryId()); 
		return SQLUtil.executeQuerySQL(queySql);
	}
	
	@Override
	public boolean increaseAgree(JAddAgree addAgree) {
		boolean result = false;
		// ��ѯ��ȡԭ���ĵ�����
		String queryAgreeSql = String.format("select * from Note where noteId=%d", addAgree.getNoteId());
		List<Map<String, Object>> list = SQLUtil.executeQuerySQL(queryAgreeSql);
		Map<String, Object> map = list.get(0);
		Integer agree = new Integer((String)map.get("agreeCount"));
	
		// ������ +1
		agree += 1;
		
		// д�����ݿ�
		String realoadSql = String.format("update Note set agreeCount = %d where noteId=%d", 
				agree.intValue(), addAgree.getNoteId());
		result = SQLUtil.executeUpdateSQL(realoadSql);
		
		return result;
	}
	
}
