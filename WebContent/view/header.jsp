<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.board.vo.User"%>
<%
	String appContextPath = application.getContextPath();
	

	if((User)session.getAttribute("userInfo") != null){
		User userInfo = (User)session.getAttribute("userInfo");
		
		%>
		<div id="userInfo">
			<h1><%=userInfo.getUserName()%>님 환영합니다</h1>
			<a href="<%=appContextPath%>/logout.do">로그아웃</a>
		</div>		
		<%
	}else{
		%>
		<div id="loginInfo">
			<form action="<%=appContextPath%>/login.do" method="post">
				id:<input type="text" name="userId"><br>
				pw:<input type="password" name="userPassword"><br>
				<input type="submit"value="로그인"><input type="button"value="회원가입" id="signUp_Btn" onclick="moveSignUpPage()">
			</form>
			
		</div>
		<%
	}

%>

