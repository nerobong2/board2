<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.board.vo.Board" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"src="common.js"></script>
<style type="text/css">

.viewDiv{

width : 100%;
margin-top: 20px;
height: 10%;

}



</style>
</head>
<body>
<%
	Board board = (Board)request.getAttribute("board");
	String appContextPath = application.getContextPath();
%>


<jsp:include page="header.jsp"></jsp:include>
	<%
	if(board != null){
	%>
		<div>
		<input type="text" class="viewDiv"id="boardNo" value=<%=board.getBoardNo()%> readonly="readonly"><br>
		<input type="text" class="viewDiv" id="boardSubject" value=<%=board.getBoardSubject()%> readonly="readonly"><br>
		<textarea class="viewDiv" id="boardContent" readonly="readonly"><%=board.getBoardContent()%></textarea><br>
		<input type="text" class="viewDiv" id="boardWriterName" value=<%=board.getWriterName()%> readonly="readonly"><br>
		<input class="viewDiv" id="boardRegDate"	value=<%=board.getRegDate()%> readonly="readonly">
		
	</div>
	<%
	}
	%>
	
	<a href="<%=appContextPath%>/main.do">목록으로</a>

<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>