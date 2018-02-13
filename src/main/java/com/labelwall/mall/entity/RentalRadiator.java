package com.labelwall.mall.entity;

import java.math.BigDecimal;

public class RentalRadiator {
	private Integer radiatorId  ;// 散热器ID    
	private String radiatorName ;// 散热器品牌
	private String radiatorType ;// 散热器类型
	private String radiatorModel ;//散热器方式
	private BigDecimal radiatorPrice;//
	private String radiatorIMG;//图片
	private String radiatorDetail;//详情
	
	
	public String getRadiatorIMG() {
		return radiatorIMG;
	}
	public void setRadiatorIMG(String radiatorIMG) {
		this.radiatorIMG = radiatorIMG;
	}
	public String getRadiatorDetail() {
		return radiatorDetail;
	}
	public void setRadiatorDetail(String radiatorDetail) {
		this.radiatorDetail = radiatorDetail;
	}
	public Integer getRadiatorId() {
		return radiatorId;
	}
	public void setRadiatorId(Integer radiatorId) {
		this.radiatorId = radiatorId;
	}
	public String getRadiatorName() {
		return radiatorName;
	}
	public void setRadiatorName(String radiatorName) {
		this.radiatorName = radiatorName;
	}
	public String getRadiatorType() {
		return radiatorType;
	}
	public void setRadiatorType(String radiatorType) {
		this.radiatorType = radiatorType;
	}
	public String getRadiatorModel() {
		return radiatorModel;
	}
	public void setRadiatorModel(String radiatorModel) {
		this.radiatorModel = radiatorModel;
	}
	public BigDecimal getRadiatorPrice() {
		return radiatorPrice;
	}
	public void setRadiatorPrice(BigDecimal radiatorPrice) {
		this.radiatorPrice = radiatorPrice;
	}
	
	
	
}
