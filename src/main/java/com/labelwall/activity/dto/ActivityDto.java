package com.labelwall.activity.dto;

import java.util.List;

import com.labelwall.activity.entity.ActivityInfo;
import com.labelwall.mall.entity.User;
import org.springframework.stereotype.Component;

/**
 * ��
 * @author Administrator
 *
 */
public class ActivityDto extends ActivityInfo {

	private User StartUser;
	private List<User> joinUser;
	private List<User> noCheckedJoinUser;
	private String keyword;
	private String orderFeild;
	private String searchTime;


	public List<User> getNoCheckedJoinUser() {
		return noCheckedJoinUser;
	}

	public void setNoCheckedJoinUser(List<User> noCheckedJoinUser) {
		this.noCheckedJoinUser = noCheckedJoinUser;
	}

	public User getStartUser() {
		return StartUser;
	}
	public void setStartUser(User startUser) {
		StartUser = startUser;
	}
	public List<User> getJoinUser() {
		return joinUser;
	}
	public void setJoinUser(List<User> joinUser) {
		this.joinUser = joinUser;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getOrderFeild() {
		return orderFeild;
	}
	public void setOrderFeild(String orderFeild) {
		this.orderFeild = orderFeild;
	}

	public String getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}
}
