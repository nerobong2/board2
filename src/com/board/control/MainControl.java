package com.board.control;

import java.util.HashMap;

import com.board.annotation.Component;
import com.board.dao.mysqlDao.BoardDao;
import com.board.dao.mysqlDao.UserDao;


@Component("main.do")
public class MainControl implements PageControl{

	UserDao userDao;
	BoardDao boardDao;
	
	
	public MainControl setUserDao(UserDao userDao){
		
		this.userDao = userDao;
		return this;
	}
	public MainControl setBoardDao(BoardDao boardDao){
		this.boardDao = boardDao;
		return this;
	}


	@Override
	public String excute(HashMap<String,Object> model) {
		
		System.out.println("mainControl...");
		
		//게시글 리스트는 항상보여줘야 하므로 모든 리퀘스트에 처리 해주어야한다.
		model.put("boardList", boardDao.getBoardList());
		
		return "foward:view/main.jsp";
	}
}
