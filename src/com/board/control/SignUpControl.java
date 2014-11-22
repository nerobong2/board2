package com.board.control;

import java.util.HashMap;

import com.board.annotation.Component;
import com.board.dao.mysqlDao.BoardDao;
import com.board.dao.mysqlDao.UserDao;
import com.board.vo.User;

@Component("signUp.do")
public class SignUpControl implements PageControl, DataBinding{

	UserDao userDao;
	BoardDao boardDao;
	
	
	public SignUpControl setUserDao(UserDao userDao){
		
		this.userDao = userDao;
		return this;
	}
	public SignUpControl setBoardDao(BoardDao boardDao){
		this.boardDao = boardDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		
		return new Object[]{"user",com.board.vo.User.class};
	}


	@Override
	public String excute(HashMap<String,Object> model) {
		// TODO Auto-generated method stub
		System.out.println("SignUpControl...");
		
		/*HttpServletRequest request = (HttpServletRequest)model.get("request");
		String userId			   = (String)request.getParameter("userId");
		String userPassword		   = (String)request.getParameter("userPassword");
		String userName		       = (String)request.getParameter("userName");*/
		
		User user = userDao.isSignUp((User)model.get("user"));
		
		
		//아이디에 따른 계정이 존재할경우
		if(user.getUserNo() != 0){
			
			return "sendRedirect:view/error.jsp";
			
		}else{
			
			userDao.userSignUp(user);
			//게시글 리스트는 항상보여줘야 하므로 모든 리퀘스트에 처리 해주어야한다.
			model.put("boardList", boardDao.getBoardList());
			
			
			return "foward:view/main.jsp";
			
		}
		
	}


	
	
	
}
