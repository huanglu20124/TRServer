package model.tool;

/***
 * ¾°µã¹¤³§
 * @author »ÆÔ¶
 *
 */

public class JTravelDaoFactory {
	public static JTravelDAO getTravelDaoInstance() {
		return new JTravelDAOImp();
	}
}
