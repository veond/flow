import org.junit.Test;

import com.zxw.system.sql.entity.ConditionEnum;
import com.zxw.system.sql.entity.ConnectionEnum;
import com.zxw.system.sql.entity.WhereRule;


/** 
 *
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-13 下午5:32:35 
 * 
 */

public class TestEnum {
	
	@Test
	public void test() {
		ConnectionEnum c = ConnectionEnum.NULL;
		
		System.out.println(c);		
	}

	
	@Test
	public void testMap() {
	}
}


