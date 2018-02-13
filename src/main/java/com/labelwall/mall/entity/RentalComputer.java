package com.labelwall.mall.entity;

import java.math.BigDecimal;

/*
 * 租售电脑商品实体类
 */
public class RentalComputer {
	private Integer cpuId ;//  CPU
	private Integer memoryId ;//  内存
	private Integer cardId ;//  显卡类型
	private Integer diskId;// 硬盘类型
	private Integer boardId;//主板
	private Integer powerId;//电源
	private Integer caseId;//机箱
	private Integer CDRom;//有无光驱，0 无   1 有
	private Integer radiatorId;//散热器
	
	private RentalCPU cpu;
	private RentalMemory memo;
	private RentalCard card;
	private RentalDisk disk;
	private RentalBoard board;
	private RentalPower power;
	private RentalCase cas;
	private RentalRadiator radiator;
	
//	private Integer rentalMounth;//租赁月份
	private BigDecimal price_buy;//购买价格
	private BigDecimal price_6  ;//  每个月价格
	private BigDecimal price_12  ;//  每个月价格
	private BigDecimal price_24  ;//  每个月价格
//	private Integer rentalShopId ;//   商铺Id
//	private Date createTime;
//	private Date updataTime;
	
	
	
	
	public Integer getCpuId() {
		return cpuId;
	}
	public BigDecimal getPrice_buy() {
		return price_buy;
	}
	public void setPrice_buy(BigDecimal price_buy) {
		this.price_buy = price_buy;
	}
	public void setCpuId(Integer cpuId) {
		this.cpuId = cpuId;
	}
	public RentalCPU getCpu() {
		return cpu;
	}
	public void setCpu(RentalCPU cpu) {
		this.cpu = cpu;
	}
	public RentalMemory getMemo() {
		return memo;
	}
	public void setMemo(RentalMemory memo) {
		this.memo = memo;
	}
	public RentalCard getCard() {
		return card;
	}
	public void setCard(RentalCard card) {
		this.card = card;
	}
	public RentalDisk getDisk() {
		return disk;
	}
	public void setDisk(RentalDisk disk) {
		this.disk = disk;
	}
	public RentalBoard getBoard() {
		return board;
	}
	public void setBoard(RentalBoard board) {
		this.board = board;
	}
	public RentalPower getPower() {
		return power;
	}
	public void setPower(RentalPower power) {
		this.power = power;
	}
	public RentalCase getCas() {
		return cas;
	}
	public void setCas(RentalCase cas) {
		this.cas = cas;
	}
	public RentalRadiator getRadiator() {
		return radiator;
	}
	public void setRadiator(RentalRadiator radiator) {
		this.radiator = radiator;
	}
	
	
	public Integer getMemoryId() {
		return memoryId;
	}
	public void setMemoryId(Integer memoryId) {
		this.memoryId = memoryId;
	}
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	public Integer getDiskId() {
		return diskId;
	}
	public void setDiskId(Integer diskId) {
		this.diskId = diskId;
	}
	public Integer getBoardId() {
		return boardId;
	}
	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}
	public Integer getPowerId() {
		return powerId;
	}
	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}
	public Integer getCaseId() {
		return caseId;
	}
	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}
	public Integer getCDRom() {
		return CDRom;
	}
	public void setCDRom(Integer cDRom) {
		CDRom = cDRom;
	}
	public Integer getRadiatorId() {
		return radiatorId;
	}
	public void setRadiatorId(Integer radiatorId) {
		this.radiatorId = radiatorId;
	}
	public BigDecimal getPrice_6() {
		return price_6;
	}
	public void setPrice_6(BigDecimal price_6) {
		this.price_6 = price_6;
	}
	public BigDecimal getPrice_12() {
		return price_12;
	}
	public void setPrice_12(BigDecimal price_12) {
		this.price_12 = price_12;
	}
	public BigDecimal getPrice_24() {
		return price_24;
	}
	public void setPrice_24(BigDecimal price_24) {
		this.price_24 = price_24;
	}
	
	
}
