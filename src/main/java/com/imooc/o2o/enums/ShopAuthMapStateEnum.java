package com.imooc.o2o.enums;

public enum ShopAuthMapStateEnum {
	FAIL(0, "操作失败"),
	SUCCESS(1, "操作成功"),
	;

	private int state;

	private String stateInfo;

	private ShopAuthMapStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static ShopAuthMapStateEnum stateOf(int index) {
		for (ShopAuthMapStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
