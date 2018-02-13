package com.labelwall.mall.entity;

import java.math.BigDecimal;

public class RentalDisk {
	private Integer diskId  ;//硬盘ID    
	private String diskName ;// 硬盘品牌
	private String diskSize ;// 硬盘大小
	private BigDecimal diskPrice ;// 价格      
	private String diskType;//硬盘类型
	private String diskIMG;//图片
	private String diskDetail;//详情
	
	
	public String getDiskIMG() {
		return diskIMG;
	}
	public void setDiskIMG(String diskIMG) {
		this.diskIMG = diskIMG;
	}
	public String getDiskDetail() {
		return diskDetail;
	}
	public void setDiskDetail(String diskDetail) {
		this.diskDetail = diskDetail;
	}
	public Integer getDiskId() {
		return diskId;
	}
	public void setDiskId(Integer diskId) {
		this.diskId = diskId;
	}
	public String getDiskName() {
		return diskName;
	}
	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}
	public String getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}
	public BigDecimal getDiskPrice() {
		return diskPrice;
	}
	public void setDiskPrice(BigDecimal diskPrice) {
		this.diskPrice = diskPrice;
	}
	public String getDiskType() {
		return diskType;
	}
	public void setDiskType(String diskType) {
		this.diskType = diskType;
	}
	
	
}
