import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.zxw.system.sql.entity.ConditionEnum;
import com.zxw.system.sql.entity.ConnectionEnum;
import com.zxw.system.sql.entity.WhereRule;
import com.zxw.system.sql.entity.WhereRuleList;
import com.zxw.system.sql.reverse.SQLReverse;
import com.zxw.work.engine.model.ProcessInstance;



public class TestSQLReverse {
	
	private Connection connection = null;
	private static SQLReverse SQLReverse = null; 
	
	@Before
	public void createConnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		Properties p = new Properties();
		p.load(new FileInputStream(System.getProperty("user.dir")+"/config/application.properties"));
		String driverName=p.getProperty("organization.driverClassName"), 
				url=p.getProperty("organization.db_url"), 
				user=p.getProperty("organization.db_username"), 
				password=p.getProperty("organization.db_password");
		Class.forName(driverName);
		connection = DriverManager.getConnection(url, user, password);
		
		SQLReverse = new SQLReverse("process_instance", ProcessInstance.class);
	}
	
	@Test
	public void testSelectPri() {


		
	}

	
	
	
	
	
}

















