<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입 페이지</title>
<script type="text/javascript"src="common.js"></script>
<script type="text/javascript">

	function moveMainPage(){
		location.href= rootPath+"/main.do";
	}
	
	function validationCheck(){
	
		var userId 			= document.getElementById("userId").value;
		var userPassword 	= document.getElementById("userPassword").value;
		var rePassword		= document.getElementById("rePassword").value;
		
		console.log(userId + ":::" + userPassword + ":::" + rePassword);
		console.log("validationCheck()...");
		/* if(userId = null){
			
			alert("id 입력안됨.");
			return false;
			
		}
		if(userPassword = null){
			
			alert("pw 입력안됨.");
			return false;
			
		}
		if(userPassword != rePassword){
			
			alert("비밀번호 확인이 잘못되었음");
			return false;
			
		} */
			
		return true;
			
		
	
	}
</script>
</head>

<%

	String appContextPath = application.getContextPath();

%>
<body>

	<form action="<%=appContextPath%>/signUp.do"method="post" onsubmit="return validationCheck()">
	
		id : <input type="text" name="userId" id="userId"><br>
		pw : <input type="password" name="userPassword" id="userPassword"><br>
		이름 : <input type="text" name="userName" id="userName"><br>
		pw확인 : <input type="password" id="rePassword"><br>
		
		<input type="submit"value="회원가입"><input type="button" value="취소" onclick="moveMainPage()">
	</form>
</body>
</html>