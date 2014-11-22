package com.board.vo;

import java.sql.Date;

public class User {

	private int userNo;
	private String userName;
	private String userId;
	private String userPassword;
	private Date regDate;
	
	public int getUserNo() {
		return userNo;
	}
	public User setUserNo(int userNo) {
		this.userNo = userNo;
		return this;
	}
	public String getUserName() {
		return userName;
	}
	public User setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	public String getUserId() {
		return userId;
	}
	public User setUserId(String userId) {
		this.userId = userId;
		return this;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public User setUserPassword(String userPassword) {
		this.userPassword = userPassword;
		return this;
	}
	public Date getRegDate() {
		return regDate;
	}
	public User setRegDate(Date regDate) {
		this.regDate = regDate;
		return this;
	}
	@Override
	public String toString() {
		return "User [userNo=" + userNo + ", userName=" + userName
				+ ", userId=" + userId + ", userPassword=" + userPassword
				+ ", regDate=" + regDate + "]";
	}
	
	
}
