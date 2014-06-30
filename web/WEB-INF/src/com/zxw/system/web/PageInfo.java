/**
 * 分页信息
 */

package com.zxw.system.web;

import java.util.List;

public class PageInfo {

	private int totalCount = 0; // 总记录数
	private int start = 0; // 起始位
	private int limit = 0; // 每页数量
	private List<?> items = null; // 返回结果集

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}

}
