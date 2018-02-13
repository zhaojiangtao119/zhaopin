package com.labelwall.mall.entity;

import java.math.BigDecimal;

public class RentalCard {
	private Integer cardId   ;//  显卡ID    
	private String cardType   ;// 显卡类型
	private String cardName  ;// 显卡品牌
	private String cardCore  ;// 显卡芯片
	private Integer cardSize ;//显存大小
	private BigDecimal cardPrice ;//
	private String cardIMG;//图片
	private String cardDetail;//详情
	
	
	public String getCardIMG() {
		return cardIMG;
	}
	public void setCardIMG(String cardIMG) {
		this.cardIMG = cardIMG;
	}
	public String getCardDetail() {
		return cardDetail;
	}
	public void setCardDetail(String cardDetail) {
		this.cardDetail = cardDetail;
	}
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardCore() {
		return cardCore;
	}
	public void setCardCore(String cardCore) {
		this.cardCore = cardCore;
	}
	public Integer getCardSize() {
		return cardSize;
	}
	public void setCardSize(Integer cardSize) {
		this.cardSize = cardSize;
	}
	public BigDecimal getCardPrice() {
		return cardPrice;
	}
	public void setCardPrice(BigDecimal cardPrice) {
		this.cardPrice = cardPrice;
	}
	
	
	
}
