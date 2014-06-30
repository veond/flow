package com.zxw.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * 
 * 常用工具
 * 
 * @author 19lou-zxw
 * 
 */
public class InCommonUseUtil {

	/**
	 * 随机出指定范围内，指定个数的int
	 * 
	 * @param num
	 *            要随机出几个数
	 * @param beginNum
	 *            随机数最小值
	 * @param endNum
	 *            随机数最大值
	 * @return
	 * @return
	 */
	public static List<Integer> getRandomInts(int num, int beginNum, int endNum) {
		List<Integer> resultList = new ArrayList<Integer>();
		if (num <= (endNum - beginNum)) {
			List<Integer> ranNumList = new ArrayList<Integer>();
			for (int i = beginNum; i <= endNum; i++) {
				ranNumList.add(i);
			}
			int ind = 0;
			int itIndex = 0;
			Random ran = new Random();
			for (int i = 1; i <= num; i++) {
				itIndex = 0;
				ind = ran.nextInt(ranNumList.size());
				resultList.add(ranNumList.get(ind));
				Iterator<Integer> it = ranNumList.iterator();
				while (it.hasNext()) {
					it.next();
					if (ind == itIndex) {
						it.remove();
					}
					itIndex = itIndex + 1;
				}
			}
		} else {
			for (int i = beginNum; i <= endNum; i++) {
				resultList.add(i);
			}
		}
		return resultList;
	}

	/**
	 * 比较两个任意类型的值是否相等，    string类型 \ 数值类型 \ 对象类型都可以比较
	 *  1、 如果两个对象中有一个对象为null ，则直接返回false
	 *  2、 如果 comparator == null， 直接进行equals 比较
	 *  3、 如果 comparaotr 有值，直接调用 compare 方法， 如果  <0为不等     =0 相等   >0不相等
	 * @param obj1 
	 * @param obj2
	 * @return 
	 */
	public static boolean isEquals(Object obj1, Object obj2, Comparator comparator) {
		if(obj1 == null || obj2 == null) {
			return false;
		}
		if(comparator == null) {
			return obj1.equals(obj2);
		}
		//其它 对象
		if(comparator.compare(obj1, obj2) == 0) {
			return true;				
		}
		return (obj1 == obj2);
	}

}
