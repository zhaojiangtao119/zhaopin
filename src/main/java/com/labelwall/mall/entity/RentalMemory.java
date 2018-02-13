package com.labelwall.mall.entity;

import java.math.BigDecimal;

public class RentalMemory {
	private Integer memoryId  ;// 内存ID    
	private String  memoryType ;//内存类型
	private Integer memorySize ;// 内存大小
	private String memoryName ;//内存品牌
	private BigDecimal memoryPrice;//
	private String memoryIMG;//图片
	private String memoryDetail;//详情
	
	
	public String getMemoryIMG() {
		return memoryIMG;
	}
	public void setMemoryIMG(String memoryIMG) {
		this.memoryIMG = memoryIMG;
	}
	public String getMemoryDetail() {
		return memoryDetail;
	}
	public void setMemoryDetail(String memoryDetail) {
		this.memoryDetail = memoryDetail;
	}
	public Integer getMemoryId() {
		return memoryId;
	}
	public void setMemoryId(Integer memoryId) {
		this.memoryId = memoryId;
	}
	public String getMemoryType() {
		return memoryType;
	}
	public void setMemoryType(String memoryType) {
		this.memoryType = memoryType;
	}
	public Integer getMemorySize() {
		return memorySize;
	}
	public void setMemorySize(Integer memorySize) {
		this.memorySize = memorySize;
	}
	public String getMemoryName() {
		return memoryName;
	}
	public void setMemoryName(String memoryName) {
		this.memoryName = memoryName;
	}
	public BigDecimal getMemoryPrice() {
		return memoryPrice;
	}
	public void setMemoryPrice(BigDecimal memoryPrice) {
		this.memoryPrice = memoryPrice;
	}
	
	
	
}
