package com.labelwall.util.storage;


public enum ShopEnum {
	USERINFO_LACK(-1,"个人信息不完善"),
	SUCCESS(0,"OK"),
	REPEAT(-2,"您已经有一家商铺"),
	NO_DATA(-3,"没有数据"),
	DATA_PROBLEM(-4,"数据异常"),
	DATA_DEFECT(-5,"信息缺失"),
	ERRO(-6,"系统错误"),
	NO_SHOP(-7,"您还没有商铺，开店加入我们吧~"),
	ADD_FAIL(-8,"添加商品失败"),
	SHOP_IMG_FAIL(-9,"商铺主题图片上传失败"),
	SERVICE_IMG_FAIL(-10,"服务图片上传失败"),
	;
	private int state;
	private String stateInfo;
	
	private ShopEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	
	public static ShopEnum stateOf(int index){
		for (ShopEnum state : values()) {
			if(state.getState()==index){
				return state;
			}
		}
		return null;
	}
}
