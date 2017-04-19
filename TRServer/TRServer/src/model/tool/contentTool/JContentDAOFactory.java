package model.tool.contentTool;

/**
 * 内容工具类的对象
 * @author 黄远
 *
 */

public class JContentDAOFactory {
	public static JContentDAO getContentDAOInstance() {
		return new JTravelContentTool();
	}
}
