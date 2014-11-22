package com.board.control;

import java.util.HashMap;

import com.board.annotation.Component;
import com.board.dao.mysqlDao.BoardDao;
import com.board.dao.mysqlDao.UserDao;
import com.board.vo.Board;

@Component("write.do")
public class BoardWriteControl implements PageControl , DataBinding{

	
	UserDao userDao;
	BoardDao boardDao;
	
	
	public BoardWriteControl setUserDao(UserDao userDao){
		
		this.userDao = userDao;
		return this;
	}
	public BoardWriteControl setBoardDao(BoardDao boardDao){
		this.boardDao = boardDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[]{"board",com.board.vo.Board.class};
	}
	@Override
	public String excute(HashMap<String, Object> model) {
		
		boardDao.writeBoard((Board)model.get("board"));
		model.put("boardList", boardDao.getBoardList());
		
		
		return "foward:view/main.jsp";
	}

}
