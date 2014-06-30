import org.apache.log4j.Logger;
import org.junit.Test;

import com.zxw.util.StringUtil;


/** 
 *
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-11 下午6:21:11 
 * 
 */

public class TestLog4j {
	
	@Test
	public void testLog4j() {
		Logger log = Logger.getLogger(this.getClass());
		log.info("xxxxxx");		
		log.info("xxxxxx");		
		log.info("xxxxxx");		
		log.info("xxxxxx");		
		log.info("xxxxxx");		
		log.info(StringUtil.getUUID());		
	}

}


