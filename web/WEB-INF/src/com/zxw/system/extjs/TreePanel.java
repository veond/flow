package com.zxw.system.extjs;

import java.io.Serializable;
import java.util.List;

/**
 * extjs 展现树的对象模型
 * 
 * @author 19lou-zxw
 * 
 */
public class TreePanel implements Serializable {

	private static final long serialVersionUID = -5055241349931347792L;

	private String id = ""; // id
	private String text = ""; // tree 显示的文本
	private String cls = "folder"; // tree 显示的样式（就是名称前面那个小图标） 默认为 文件夹 样式
	private boolean expanded = false; // 是否展现
	private boolean leaf = true; // 是否是叶子节点
	private String icon = "";   //节点小图标的位置
	private List<?> children = null; // 子节点 , 方便多个使用，所以这里为 object 其实应该是：TreePanel

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public List<?> getChildren() {
		return children;
	}

	public void setChildren(List<?> children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
