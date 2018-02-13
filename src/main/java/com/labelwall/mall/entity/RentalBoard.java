package com.labelwall.mall.entity;

import java.math.BigDecimal;

public class RentalBoard {
	private Integer boardId ;// 主板ID          
	private String boardName ;// 主板品牌      
	private String boardType ;// 类型            
	private String boardCore ;//  核心组         
	private BigDecimal boardPrice;// 价格            
	private String boardModel ;//主板内存类型
	
	private String boardIMG;//图片
	private String boardDetail;//详情
	
	
	public String getBoardIMG() {
		return boardIMG;
	}
	public void setBoardIMG(String boardIMG) {
		this.boardIMG = boardIMG;
	}
	public String getBoardDetail() {
		return boardDetail;
	}
	public void setBoardDetail(String boardDetail) {
		this.boardDetail = boardDetail;
	}
	public Integer getBoardId() {
		return boardId;
	}
	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public String getBoardCore() {
		return boardCore;
	}
	public void setBoardCore(String boardCore) {
		this.boardCore = boardCore;
	}
	public BigDecimal getBoardPrice() {
		return boardPrice;
	}
	public void setBoardPrice(BigDecimal boardPrice) {
		this.boardPrice = boardPrice;
	}
	public String getBoardModel() {
		return boardModel;
	}
	public void setBoardModel(String boardModel) {
		this.boardModel = boardModel;
	}
	
	
}
