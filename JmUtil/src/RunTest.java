import jm.net.Dao;
import jm.net.DataEntity;
import jm.sec.auth.LoginUtil;


public class RunTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Dao dao = Dao.getInstance();
		DataEntity dataEntity = new DataEntity();
		
		DataEntity whereEntity = new DataEntity();
		
		dataEntity.put("id", "kjmorc");
		dataEntity.put("passwd", "kjm4123");
		dataEntity.put("name", "김종민21");
		
		whereEntity.put("id", "kjmorc");
		whereEntity.put("passwd", "kjm4123");
		
		try {
//			dao.inertData("cdi_user", dataEntity);
			int res = 0;
			LoginUtil log = new LoginUtil();
			
//			res = dao.deleteData("cdi_user", whereEntity);
			if(log.checkId("kjmorc", "kjm4123")){
				System.out.println("no");
			} else {
				System.out.println("yes");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
