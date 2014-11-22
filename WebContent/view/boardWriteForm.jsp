<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.board.vo.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글쓰기</title>
<script type="text/javascript"src="common.js"></script>
<script type="text/javascript">
	
	function moveMainPage(){
		location.href= rootPath+"/main.do";
	}
	


</script>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<%
	User userInfo = (User)session.getAttribute("userInfo");
	String appContextPath = application.getContextPath();
	
%>
	<form action="<%=appContextPath %>/write.do" method="post">
		제목 : <input type="text" name="boardSubject"><br>
		내용 : <input type="text" name="boardContent"><br>
				<input type="text" name="writerNo"  value="<%= (int)userInfo.getUserNo()%>" style= "visibility: hidden;">
		작성자 : <input type="text" value="<%= userInfo.getUserName()%>" readonly="readonly"><br>
		<input type="submit"value="등록"><input type="button" value="취소" onclick="moveMainPage()">	
	</form>
	
<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>