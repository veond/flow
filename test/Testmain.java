import com.zxw.util.StringUtil;


/** 
 *
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-4-9 下午5:16:26 
 * 
 */

public class Testmain {
	
	public static void main(String[] args) {
//		String a = "ALTER TABLE `work_item` ADD `work_from_id` varchar(36) NOT NULL DEFAULT '' COMMENT '工作项处理表单的配制信息';";
//		String b = "ALTER TABLE `work_item` ADD `work_from_id` varchar(36) NOT NULL DEFAULT '' COMMENT '工作项处理表单的配制信息';";
//		
//		System.out.println(a.equals(b));
		
		
		
		for(int i=0; i<=10; i++) {
			System.out.println(StringUtil.getUUID());
			System.out.println();
		}
		
	}

}


