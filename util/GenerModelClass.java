import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import com.zxw.system.constant.ConstantValue;
import com.zxw.system.sql.reverse.SQLReverse;
import com.zxw.util.StringUtil;


/** 
 * 连接表数据库生成model class
 * @author 作者: zxw    
 *		E-mail: veond@163.com
 * @since 创建时间：2014-3-10 下午4:08:01 
 * 
 */

public class GenerModelClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "";
		//1
		String[] tables = {"work_todo", "work_have_todo"};
		path = "com.zxw.work.engine.model";
		generModelClass(path, tables, getConnection(ConstantValue.SQLSESSION_WORK));
	}

	/**
	 * 生成指定表的model文件
	 * @param generPath
	 * @param tables
	 * @param connection
	 */
	private static void generModelClass(String generPathStr, String[] tables, Connection connection) {
		String generPath = generPathStr;
		generPath = System.getProperty("user.dir")+"/web/WEB-INF/src/" + generPath.replaceAll("\\.", "/")+"/";
		for(String table : tables) {
			try {
				File file = new File(generPath);
				System.out.println("目录创建....: (" + file.mkdirs()+")");
				String className = StringUtil.toFirstUpperCase(StringUtil.columnToVarName(table));
				file = new File(generPath + className + ".java");
				if(file.exists()) {
					System.err.println("ERROR : '"+file.getPath()+"' 文件已经存在！");
				}else {
					boolean fileIsCreateSuccess = file.createNewFile(); 
					System.out.println("文件创建....: (" + fileIsCreateSuccess +")");
					if(fileIsCreateSuccess) {
						System.out.println("开始写入model内容....");
						SQLReverse revers = null;
						try {
							revers = new SQLReverse(table, connection);
						}catch (Exception e) {
							System.err.print("ERROR : 数据库表连接异常.....");
							return;
						}
						String modelStr = 
								"package "+generPathStr+";\n\n" +
								"import com.zxw.system.model.BaseModel;\n\n" +
								"import java.io.Serializable;\n\n" +
								"public class "+className+" extends BaseModel implements Serializable {\n\n";
						for(String varStr : revers.getModelVarList()) {
							if(!"remark".equals(varStr) && !"updateAt".equals(varStr) && !"createAt".equals(varStr)) {
								modelStr += "	private String "+varStr+" = \"\";\n";
							}
						}
						modelStr += "}";
						//写入文件		
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(modelStr.getBytes());
						fos.flush();
						fos.close();
						System.out.println("SUCCSS ----- : （" + className + "）   写入成功");
					}else {
						throw new Exception("'"+file.getPath()+"' 无法生成模型文件!  table=("+table+")");						
					}					
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("------------("+table+") 创建结束...-------------------\n\n");
		}
	}

	/**
	 * 获得指定数据库的连接
	 * @param constr   ConstantValue 里面 sqlsession 的常量值
	 * @return
	 */
	private static Connection getConnection(String constr) {
		try {
			Properties pro = new Properties();
			pro.load(new FileInputStream(new File(System.getProperty("user.dir")+"/config/application.properties")));
			//work_sqlSession
			constr = constr.replaceAll("_sqlSession", "");
			String driverName=pro.getProperty(constr+".driverClassName"), 
					url=pro.getProperty(constr+".db_url"), 
					user=pro.getProperty(constr+".db_username"), 
					password=pro.getProperty(constr+".db_password");
			Class.forName(driverName);
			return DriverManager.getConnection(url, user, password);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Test
	public void test() {
		
		String str = "zggm.zxw.zxc";
		
		
		
	}
	
	

}


