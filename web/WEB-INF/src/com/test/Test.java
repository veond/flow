package com.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Test {
	private static class UidAndCityIdClass {
        private Long uid;
        private Integer cityId;

        private UidAndCityIdClass(Long uid, Integer cityId) {
            this.uid = uid;
            this.cityId = cityId;
        }
        private UidAndCityIdClass() {
            super();
        }

        private Long getUid() {
            return uid;
        }

        private Integer getCityId() {
            return cityId;
        }

        @Override
        public String toString() {
            return uid+"|"+cityId;
        }
    }
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
		/*****
		List<UidAndCityIdClass> tt = new ArrayList<UidAndCityIdClass>();
		tt.add(new UidAndCityIdClass(1L, 11));
		tt.add(new UidAndCityIdClass(2L, 22));
		tt.add(new UidAndCityIdClass(3L, 33));
		tt.add(new UidAndCityIdClass(4L, 44));
		System.out.println(tt.toString().substring(1, tt.toString().length()-1));
		
		
		
		System.out.println(new Test().ttt());
		System.out.println(new Test().fff());
		System.out.println(new Test().ccc());
		
		
		String str = "asdf_klsd_fsdf_qqq";
		
		String[] strs = str.split("_");
		String temp = "";
		for(int i=1; i<strs.length; i++) {
			temp += strs[i].replaceFirst(strs[i].substring(0,1), strs[i].substring(0,1).toUpperCase());			
		}
		System.out.println(strs[0]+temp);
		
		
		*****/
		/*Properties p = new Properties();
		p.load(new FileInputStream(System.getProperty("user.dir")+"/config/application.properties"));
		String driverName=p.getProperty("organization.driverClassName"), 
				url=p.getProperty("organization.db_url"), 
				user=p.getProperty("organization.db_username"), 
				password=p.getProperty("organization.db_password");
		Class.forName(driverName);
		Connection conn = DriverManager.getConnection(url, user, password);

		DatabaseMetaData dmd = conn.getMetaData();
		ResultSet rs = dmd.getTables(null, null, null, null);
		while(rs.next()) {
			System.out.println(rs.getString(3));  //表名
			System.out.println("-------------------------");
		}
		
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from user limit 0,0");
		ResultSetMetaData rsmd = rs.getMetaData();
		for(int i=1; i<=rsmd.getColumnCount(); i++) {
			System.out.print(columnToVarName(rsmd.getColumnName(i))+"  ");
		}*/


		/************/
		
		
//		System.out.println(UUID.randomUUID().toString());
	/*	
		User u1 = new User();
		User u2 = new User();
		u2.setUsername("张三ff");
		u1.setUsername("张三");
		
		System.out.println(InCommonUseUtil.isEquals(u1, u2, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				User u1= (User) o1;
				User u2= (User) o2;
				if(u1.getUsername().equals(u2.getUsername())) {
					return 0;
				}
				return -1;
				
			}
		}));
*/
//		
//		String str = "11111F";
//		System.out.println(str.substring(0, str.length()-1));
		
//		Random ra =new Random();
//		for(int i=0; i<3000; i++) {
//			System.out.print(ra.nextInt(12)+"-");
//			
//		}

//		
//		List<Integer> lis = new ArrayList<Integer>();
//		for(int i=1; i<=10; i++) {
//			lis.add(i);			
//		}
//		//
		/*
		System.out.println(lis.size()/8);
		int maxPage = lis.size()/8; 
		int end = 8;
		for(int i=0; i<=(maxPage); i++) {
			if(i*8<lis.size() && maxPage>=(i+1)) {
				end = (i+1)*8;
				if(i == (maxPage)) {
					end = lis.size();				
				}
				System.out.println(lis.subList(i*8, end) + "----["+(i+1)+"]");
			}
		}
		
		System.out.println(lis.size()/8);
*/
//
//		List<Integer> l = lis.subList(0, 3);
//		System.out.println(l);
//		Collections.shuffle(lis);
//		for(int i=0; i<3; i++) {
//			System.out.println(lis.subList(i*3, (i+1)*3));			
//		}
//		
//		System.out.println(lis.containsAll(l));
//		
//		lis.set(0, 999);
//		System.out.println(lis);
//		
		
	}
	
	
	/**
	 * 将数据库的字段名称，转换成 model 里面的变量名称 （如：create_time 转 createTime）
	 * @param columnName
	 * @return
	 */
	private static String columnToVarName(String columnName) {
		String temp = "";
		String[] strs = columnName.split("_");
		if(strs.length <= 0) {
			return columnName;
		}
		for(int i=1; i<strs.length; i++) {
			temp += strs[i].replaceFirst(strs[i].substring(0,1), strs[i].substring(0,1).toUpperCase());			
		}
		return (strs[0]+temp);
	}

	
	public String ttt() {
		String tt = "ss";
		try {
			tt = "aaa";
			return tt;			
		}catch (Exception e) {
			tt = "bbb";
			return tt;			
		}finally {
			tt = "ccc";
			return tt;			
		}
	}
	
	

	public String fff() {
		String tt = "ss";
		try {
			tt = "aaa";
			return "oooo";			
		}catch (Exception e) {
			tt = "bbb";
			return tt;			
		}finally {
			tt = "ccc";
			return "xxxfff";			
		}
	}
	
	public String ccc() {
		try {
			return "oooo";			
		}catch (Exception e) {
			return "ggg";			
		}finally {
			return "nnnnnn";			
		}
	}
}
