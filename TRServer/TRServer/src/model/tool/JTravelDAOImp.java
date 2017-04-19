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
		
		// 获取帖子评论数目
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
	 * 生成帖子表(帖子父表,问题帖子，足迹景点表,评论表)
	 */
	private void createNote() {
		String CreateNoteSql = "create table if not exists Note (					#贴子父表" +
		"`noteId` 		int NOT NULL auto_increment,			#帖子id(PK, FK)" +
		"`account` 		VARCHAR(50) NULL,			#发帖者账号" +
		"`agreeCount` 	int NULL,				#点赞数" +
		"`timestamp` 	DATETIME NULL,				#时间撮" +
		"`title`			VARCHAR(50) NULL,			#标题" +
		"`type` 			VARCHAR(10) NULL,			#类型" +
		"primary key (noteId));";
		SQLUtil.executeUpdateSQL(CreateNoteSql);
	}
	
	/**
	 * 生成问题帖
	 */
	private void createQuestion() {
		String createQuestionSql = "create table if not exists Question  (			#问题贴表"+
		"`noteId` 		int NOT NULL auto_increment,	#足迹Id(PK, FK)"+
		"`questionId` 	int NOT NULL,	#问题id(PK)"+ 
		"`content` 		VARCHAR(1000),		#问题的内容"+
		"`imageUrl` 		VARCHAR(1000),		#问题的图片"+
    	"foreign key (noteId) references Note(noteId),"+
		"primary key (noteId,questionId));";
		SQLUtil.executeUpdateSQL(createQuestionSql);
	}
	
	/**
	 * 生成景点顺序表
	 */
	private void createRoute_Scenery() {
		String createRoute_ScenerySql = "create table if not exists Route_Scenery  (		#足迹-景点表" +
				"`routeId` 		int NOT NULL auto_increment,	#足迹Id(PK, FK)" +
				"`sceneryId` 	int NOT NULL auto_increment,	#景点ID(PK, FK)" +
				"`scenerySq` 	int,				#景点在路径中的顺序" +
				"primary key (routeId,sceneryId)," +
				"foreign key (routeId) 	references Route(routeId)," +
				"foreign key (sceneryId) references Scenery(sceneryId));";
		SQLUtil.executeUpdateSQL(createRoute_ScenerySql);
	}
	
	/**
	 * 生成足迹表
	 */
	private void createRoute() {
		String createRouteSql = " create table if not exists Route  (					#足迹贴表" +
				"`noteId` 		int NOT NULL auto_increment,			#帖子id(PK, FK)" +
				"`routeId` 		int NOT NULL auto_increment,			#足迹Id(PK)" +
				"`imageUrl` 		VARCHAR(1000),				#用户上传的图片" +
				"`content` 		VARCHAR(1000),				#心得" +
				"foreign key (noteId) references Note(noteId)," +
				"primary key (noteId,routeId));";
		SQLUtil.executeUpdateSQL(createRouteSql);
	}
	
	/**
	 * 生成景点表
	 */
	private void createScenery() {
		String sql = "create table if not exists Scenery (					#景点表 " +
				"`sceneryName` 		VARCHAR(20) NULL,		#景点名字 " +
				"`sceneryAddress` 	VARCHAR(50) NULL,		#景点具体地址 " +
				"`sceneryImageUrl` 	VARCHAR(1000) NULL,		#景点介绍图片 " +
				"`scenerySummary` 	VARCHAR(1000) NULL,		#景点简介 " +
				"`ticketSummary` 	VARCHAR(1000) NULL,		#票价描述 " +
				"`province` 			VARCHAR(10) NULL,		#省份 " +
				"`city` 				VARCHAR(10) NULL,		#城市 " +
				"`sceneryId`			int NOT NULL,		#景点ID(PK) " +
				"primary key (sceneryId) " +
			");";
		
		SQLUtil.executeUpdateSQL(sql);
	}
	
	/**
	 * 生成评论表
	 */
	private void createComment() {
		String sql = "create table if not exists Comment(					#评论" +
		"`commentId` int NOT NULL,				#评论Id(PK)" +
		"`content` 	VARCHAR(200) NULL,				#评论文字内容" +
		"`account` 	VARCHAR(50) NULL,				#评论者的账号" +
		"`timestamp` DATETIME NULL,					#时间撮" +
		"`imageUrl` 	VARCHAR(50) NULL,				#评论的图片" +
		"`noteId` 	int NOT NULL auto_increment,				#帖子id(PK, FK)" +
		"primary key (commentId,noteId));";
		SQLUtil.executeUpdateSQL(sql);
	}
	
	/**
	 * 生成景点票价表
	 */
	private void createScenery_Tickey() {
		String createScenery_TickeySql = "create table if not exists Scenery_Ticket  (		#景点票价"+
		"`description` 	VARCHAR(1000),		#票的情况"+
		"`money` 		int,				#价格"+
		"`sceneryId` 	int auto_increment,				#景点ID(PK, FK)"+
		"`TicketId` 	int auto_increment,				#票价ID(PK)"+
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
		
		// 先插入父表
		String insertNoteSql = 
				String.format("insert into Note(account, agreeCount, timestamp, title, type) " +
				"values('%s', %d, '%s', '%s', '%s');", 
				routeParamters.getAccount(), routeParamters.getAgreeCount(), 
				routeParamters.getTimestamp(), routeParamters.getTitle(), 
				routeParamters.getType());
		SQLUtil.executeUpdateSQL(insertNoteSql);
		
		// 获取父表的Id
		Integer noteId = null;
		String queryNoteIdSql = 
				String.format("select * from Note where account like '%s' and timestamp='%s'", 
				routeParamters.getAccount(), routeParamters.getTimestamp());
		List<Map<String, Object>> list = SQLUtil.executeQuerySQL(queryNoteIdSql);
		Map<String, Object> map = list.get(0);
		noteId = new Integer((String)map.get("noteId"));
		
		// 插入足迹表
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
		
		// 先插入父表
		String insertNoteSql = 
				String.format("insert into Note(account, agreeCount, timestamp, title, type) " +
				"values('%s', %d, '%s', '%s', '%s')", 
				questionParamters.getAccount(), questionParamters.getAgreeCount(), 
				questionParamters.getTimestamp(), questionParamters.getTitle(), 
				questionParamters.getType());
		SQLUtil.executeUpdateSQL(insertNoteSql);
		
		// 获取父表的Id
		Integer noteId = null;
		String queryNoteIdSql = 
				String.format("select * from Note where account like '%s' and timestamp='%s'", 
				questionParamters.getAccount(), questionParamters.getTimestamp());
		List<Map<String, Object>> list = SQLUtil.executeQuerySQL(queryNoteIdSql);
		Map<String, Object> map = list.get(0);
		noteId = new Integer((String)map.get("noteId"));
		
		// 插入问题表
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
		// 查询获取原来的点赞数
		String queryAgreeSql = String.format("select * from Note where noteId=%d", addAgree.getNoteId());
		List<Map<String, Object>> list = SQLUtil.executeQuerySQL(queryAgreeSql);
		Map<String, Object> map = list.get(0);
		Integer agree = new Integer((String)map.get("agreeCount"));
	
		// 点赞数 +1
		agree += 1;
		
		// 写回数据库
		String realoadSql = String.format("update Note set agreeCount = %d where noteId=%d", 
				agree.intValue(), addAgree.getNoteId());
		result = SQLUtil.executeUpdateSQL(realoadSql);
		
		return result;
	}
	
}
