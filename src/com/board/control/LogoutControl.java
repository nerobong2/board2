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
		
		//���� �ʱ�ȭ
		HttpSession session = (HttpSession)model.get("session");
		session.invalidate();
		
		
		//�Խñ� ����Ʈ�� �׻󺸿���� �ϹǷ� ��� ������Ʈ�� ó�� ���־���Ѵ�.
		model.put("boardList",boardDao.getBoardList());
		
		
		
		return "foward:view/main.jsp";
		
	}

}
