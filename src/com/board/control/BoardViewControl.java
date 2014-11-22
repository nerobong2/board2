package com.board.control;

import java.util.HashMap;

import com.board.annotation.Component;
import com.board.dao.mysqlDao.BoardDao;
import com.board.dao.mysqlDao.UserDao;
import com.board.vo.Board;


@Component("view.do")
public class BoardViewControl implements PageControl , DataBinding{

	UserDao userDao;
	BoardDao boardDao;
	
	
	public BoardViewControl setUserDao(UserDao userDao){
		
		this.userDao = userDao;
		return this;
	}
	public BoardViewControl setBoardDao(BoardDao boardDao){
		this.boardDao = boardDao;
		return this;
	}
	@Override
	public Object[] getDataBinders() {
		return new Object[]{"board",com.board.vo.Board.class};
	}
	
	@Override
	public String excute(HashMap<String, Object> model) {
		System.out.println("BoardViewControl...");
		
		/*HttpServletRequest request = (HttpServletRequest)model.get("request");
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));*/
			
		model.put("board", boardDao.getBoard((Board)model.get("board")));
		
		return "foward:view/boardViewForm.jsp";
		
		
	}

	

}
