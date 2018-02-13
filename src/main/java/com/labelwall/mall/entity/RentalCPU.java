package com.labelwall.mall.entity;

import java.math.BigDecimal;

public class RentalCPU {
	private Integer cpuId    ;//          
	private String cupType    ;// cpu类型
	private String cpuGeneration ;//  型号   
	private String cpuNum ;// 型号   
	private BigDecimal cpuPrice;//
	private String cpuIMG;//图片
	private String cpuDetail;//详情
	
	
	public String getCpuIMG() {
		return cpuIMG;
	}
	public void setCpuIMG(String cpuIMG) {
		this.cpuIMG = cpuIMG;
	}
	public String getCpuDetail() {
		return cpuDetail;
	}
	public void setCpuDetail(String cpuDetail) {
		this.cpuDetail = cpuDetail;
	}
	public Integer getCpuId() {
		return cpuId;
	}
	public void setCpuId(Integer cpuId) {
		this.cpuId = cpuId;
	}
	public String getCupType() {
		return cupType;
	}
	public void setCupType(String cupType) {
		this.cupType = cupType;
	}
	public String getCpuGeneration() {
		return cpuGeneration;
	}
	public void setCpuGeneration(String cpuGeneration) {
		this.cpuGeneration = cpuGeneration;
	}
	public String getCpuNum() {
		return cpuNum;
	}
	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}
	public BigDecimal getCpuPrice() {
		return cpuPrice;
	}
	public void setCpuPrice(BigDecimal cpuPrice) {
		this.cpuPrice = cpuPrice;
	}
	
	
	
}
