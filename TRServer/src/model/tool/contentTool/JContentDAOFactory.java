package model.tool.contentTool;

/**
 * ���ݹ�����Ķ���
 * @author ��Զ
 *
 */

public class JContentDAOFactory {
	public static JContentDAO getContentDAOInstance() {
		return new JTravelContentTool();
	}
}
