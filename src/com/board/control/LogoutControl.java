package com.board.control;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.board.annotation.Component;
import com.board.dao.mysqlDao.BoardDao;
import com.board.dao.mysqlDao.UserDao;

@Component("logout.do")
public class LogoutControl implements PageControl{

	UserDao userDao;
	BoardDao boardDao;
	
	public LogoutControl setUserDao(UserDao userDao){
		
		this.userDao = userDao;
		return this;
	}
	public LogoutControl setBoardDao(BoardDao boardDao){
		this.boardDao = boardDao;
		return this;
	}
	@Override
	public String excute(HashMap<String, Object> model) {
		
		System.out.println("LogoutControl...");
		
		//세션 초기화
		HttpSession session = (HttpSession)model.get("session");
		session.invalidate();
		
		
		//게시글 리스트는 항상보여줘야 하므로 모든 리퀘스트에 처리 해주어야한다.
		model.put("boardList",boardDao.getBoardList());
		
		
		
		return "foward:view/main.jsp";
		
	}

}
