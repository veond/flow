package com.zxw.system.extjs;

import java.io.Serializable;

public class CheckedTree extends TreePanel implements Serializable {

	private static final long serialVersionUID = 8426436812227798658L;

	private boolean checked = false; // 是否选择中 多选择框

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
