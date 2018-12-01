package com.imooc.o2o.enums;

public enum UserProductMapStateEnum {
	FAIL(0, "操作失败"),
	SUCCESS(1, "操作成功"),
	;

	private int state;

	private String stateInfo;

	private UserProductMapStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static UserProductMapStateEnum stateOf(int index) {
		for (UserProductMapStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
