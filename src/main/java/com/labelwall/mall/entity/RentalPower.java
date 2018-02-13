package com.labelwall.mall.entity;

import java.math.BigDecimal;

public class RentalPower {
	private Integer powerId   ;//  电源ID        
	private String powerName ;// 电源品牌    
	private String powerSize ;// 电源功率    
	private String powerPLUS ;// 电源PLUS认证
	private BigDecimal powerPrice ;// 价格
	private String powerIMG;//图片
	private String powerDetail;//详情
	
	
	
	public String getPowerIMG() {
		return powerIMG;
	}
	public void setPowerIMG(String powerIMG) {
		this.powerIMG = powerIMG;
	}
	public String getPowerDetail() {
		return powerDetail;
	}
	public void setPowerDetail(String powerDetail) {
		this.powerDetail = powerDetail;
	}
	public Integer getPowerId() {
		return powerId;
	}
	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}
	public String getPowerName() {
		return powerName;
	}
	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}
	public String getPowerSize() {
		return powerSize;
	}
	public void setPowerSize(String powerSize) {
		this.powerSize = powerSize;
	}
	public String getPowerPLUS() {
		return powerPLUS;
	}
	public void setPowerPLUS(String powerPLUS) {
		this.powerPLUS = powerPLUS;
	}
	public BigDecimal getPowerPrice() {
		return powerPrice;
	}
	public void setPowerPrice(BigDecimal powerPrice) {
		this.powerPrice = powerPrice;
	}
	
	
}
