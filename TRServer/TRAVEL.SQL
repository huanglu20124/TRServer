create database travel;
use travel;
create table Scenery (					#景点表
	`sceneryName` 		text NULL,		#景点名字
	`sceneryAddress` 	text NULL,		#景点具体地址
	`sceneryImageUrl` 	text NULL,		#景点介绍图片
	`scenerySummary` 	text NULL,		#景点简介
	`ticketSummary` 	text NULL,		#票价描述
	`province` 			text NULL,		#省份
	`city` 				text NULL,		#城市
	`sceneryId`			BIGINT auto_increment NOT NULL,		#景点ID(PK)
	primary key (sceneryId)
);

insert into Note(account, agreeCount, timestamp, title, type)
	values('黄远', 0, '2017-01-05 17:33:48', '第1条问题帖子', 'Question');

create table Note   (					#贴子父表
	`noteId` 		BIGINT auto_increment NOT NULL,			#帖子id(PK, FK)
	`account` 		char(18) NOT NULL,			#发帖者账号
	`agreeCount` 	BIGINT NULL,				#点赞数
	`timestamp` 	DATETIME NULL,				#时间撮
	`title`			text NULL,			#标题
	`type` 			text NULL,			#类型
	primary key (noteId)
);

create table Route  (					#足迹贴表
	`noteId` 		BIGINT NOT NULL,						#帖子id(PK, FK)
	`routeId` 		BIGINT auto_increment NOT NULL,			#足迹Id(PK)
	`imgaeUrl` 		text,									#用户上传的图片
	`content` 		text,									#心得
	foreign key (noteId) references Note(noteId),
	primary key (routeId)
);


create table Route_Scenery  (		#足迹-景点表
	`noteId` 		BIGINT NOT NULL,	#足迹Id(PK, FK)
	`sceneryId` 	BIGINT NOT NULL,	#景点ID(PK, FK)
	`scenerySq` 	BIGINT,				#景点在路径中的顺序
    primary key (noteId,sceneryId),
    foreign key (noteId) 	references note(noteId),
    foreign key (sceneryId) references Scenery(sceneryId)
);

create table Question  (			#问题贴表
	`noteId` 		BIGINT NOT NULL,	#帖子Id(PK, FK)
	`questionId` 	BIGINT auto_increment NOT NULL,	#问题id(PK) 
	`content` 		text,		#问题的内容
	`imageUrl` 		text,		#问题的图片
    foreign key (noteId) references note(noteId),
	primary key (questionId)
);

create table Comment (					#评论
	`commentId` BIGINT auto_increment NOT NULL,				#评论Id(PK)
	`content` 	text NULL,				#评论文字内容
	`account` 	char(18) NOT NULL,				#评论者的账号
	`timestamp` DATETIME NULL,					#时间撮
	`imageUrl` 	text NULL,				#评论的图片
	`noteId` 	BIGINT NOT NULL,				#帖子id(PK, FK)
	primary key (commentId,noteId)
);


create table Scenery_Ticket  (		#景点票价
	`description` 	text,		#票的情况
	`money` 		BIGINT,				#价格
	`sceneryId` 	BIGINT,				#景点ID(PK, FK)
	`TicketId` 		BIGINT,				#票价ID(PK)
    foreign key (sceneryId) references Scenery(sceneryId),
	primary key (sceneryId,TicketId)
);

create table user (				#用户表
	`account`	char(18) NOT NULL, 				#用户id
	`name`		text,							#昵称
    `pwd`		text,							#密码
    `summary`	text,							#简介
    primary key (account)
)
