package model.tool;

/***
 * ���㹤��
 * @author ��Զ
 *
 */

public class JTravelDaoFactory {
	public static JTravelDAO getTravelDaoInstance() {
		return new JTravelDAOImp();
	}
}
