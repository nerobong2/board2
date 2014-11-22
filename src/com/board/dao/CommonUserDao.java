package com.board.dao;

import com.board.vo.User;

public interface CommonUserDao {
	public void userSignUp(User user);
	public User userLogin(User user);
}
