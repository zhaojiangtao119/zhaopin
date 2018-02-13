package com.labelwall.mall.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 租售商品临时订单类
 * @author Administrator
 *
 */
public class RentalOrder {
	private Integer id;
	private String orderNum;
	private Integer userId;
	private Integer shopId;
	private Integer orderType;//订单类型  0 分期租赁  1 全款购买
	
	private Integer cpuId ;//  CPU
	private Integer memoryId ;//  内存
	private Integer cardId ;//  显卡类型
	private Integer diskId;// 硬盘类型
	private Integer boardId;//主板
	private Integer powerId;//电源
	private Integer caseId;//机箱
	private Integer CDRom;//有无光驱，0 无   1 有
	private Integer radiatorId;//散热器
	
	
	private BigDecimal unitPrice;//总的单价
	private Integer quantity;//数量
	private BigDecimal totalPrice;//总价
	private String orderName;//订单详情
	private Date createTime;
	private Date updateTime;

	
	
	
	private RentalCPU cpu;
	private RentalMemory memo;
	private RentalCard card;
	private RentalDisk disk;
	private RentalBoard board;
	private RentalPower power;
	private RentalCase cas;
	private RentalRadiator radiator;
	
	
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCpuId() {
		return cpuId;
	}
	public void setCpuId(Integer cpuId) {
		this.cpuId = cpuId;
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
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	
	
}
