# TRServer
爱自由的服务端实现
利用Java语言实现了C/S架构的HTTP服务器，包括：<br/><br/>
1.0 基础逻辑实现版本
（1）自定义了数据连接池<br/>
（2）使用反射机制，减少数据库代码的冗余<br/>
（3）使用MVC + 工厂+ DAO设计模式，保证了程序的可维护性<br/>
（4）安卓客户端数据访问层API编写及实现<br/>
（5）网络接口API的编写及实现<br/>
（6）负责数据库的设计<br/>
（7）负责Linux系统上服务器的部署工作<br/>
（8）负责组员任务的分配，负责项目进度的控制<br/>
（9）采用单例模式，减少内存占用，提高程序维护性<br/>

<br/><br/>
1.1 版本新增功能
（1）采用Redis缓存部分常用景点信息，异步更新缓存信息（24h更新一次）<br/>
（2）主服务器和数据库服务器分离<br/>
（3）主服务器和备份数据库分离<br/>
（4）SSL加密数据，异步更新备用数据库<br/>
（5）使用map存储固定的URL，如果请求的URL包含有 在map里面的任意一个URL，<br/>
	 把对应的字符串（key）转化成方法，执行<br/>
	 // 定义两个Map，分别为ClassMap、MethodMap，
	a、先通过URL去ClassMap中查找对应的类名(字符串)，通过该字符串得到对应的Class对象  Class urlClass = Class.forName(classStr);  <br/>
	b、再通过URL去MethodMap中查找对应的方法（字符串），通过 Method md = urlClass.getMethod("方法名字", new Class[] {xxxx.class, ..}) <br/>
	c、生成新对象，使用该方法：<br/>
		Object[] arguments = new Object[] {ipInfo,pw};    //获得参数Object <br/>
		md.invoke(urlClass.newInstance(), arguments);	  //执行方法 <br/>

