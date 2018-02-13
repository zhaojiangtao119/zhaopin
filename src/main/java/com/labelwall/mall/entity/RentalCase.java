package com.labelwall.mall.entity;

import java.math.BigDecimal;

public class RentalCase {
	private Integer caseId  ;//机箱ID    
	private String caseName ;// 机箱品牌
	private String caseType  ;//机箱类型
	private BigDecimal casePrice;//
	
	private String caseIMG;//图片
	private String caseDetail;//详情
	
	
	public String getCaseIMG() {
		return caseIMG;
	}
	public void setCaseIMG(String caseIMG) {
		this.caseIMG = caseIMG;
	}
	public String getCaseDetail() {
		return caseDetail;
	}
	public void setCaseDetail(String caseDetail) {
		this.caseDetail = caseDetail;
	}
	public Integer getCaseId() {
		return caseId;
	}
	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public BigDecimal getCasePrice() {
		return casePrice;
	}
	public void setCasePrice(BigDecimal casePrice) {
		this.casePrice = casePrice;
	}
	
	
	
}
