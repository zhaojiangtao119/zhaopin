package com.labelwall.mall.entity;

import java.util.Date;

public class ServiceType {
	
	private Integer id;
	private String name;
	private Integer parsentId;
	private Date createTime;
	private Date updateTime;
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParsentId() {
		return parsentId;
	}
	public void setParsentId(Integer parsentId) {
		this.parsentId = parsentId;
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
	
	
	
}
