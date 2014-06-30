import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.zxw.system.sql.entity.WhereRule;
import com.zxw.system.sql.entity.WhereRuleList;
import com.zxw.work.engine.mapper.sql.provider.ProcessInstanceSQLProvider;
import com.zxw.work.engine.mapper.sql.provider.WorkItemSQLProvider;
import com.zxw.work.engine.mapper.sql.provider.WorkTodoSQLProvider;


/** 
 *
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-6 下午2:04:53 
 * 
 */

public class TestSQLProvider {
	
	@Test
	public void test() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", 1);
		param.put("limit", 10);
		param.put("partakeUser", "aaaaaa");
		System.out.println(new WorkTodoSQLProvider().todoWorkPageInfoByUser(param ));
	}
	
	
	@Test
	public void testWorkItem() {
		WhereRuleList whereRules = new WhereRuleList();
		whereRules.add(new WhereRule("id", null, "#{id}", null));
		System.out.println(new WorkItemSQLProvider().selectById());
	}
	

}


