<%-- 作成者: 屋比久 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%

	// セッションからエラー情報を取得
	String error = (String)request.getAttribute("error");

	if (error == null){
		if (error.equals("")){
			error = "エラーが存在しません";
		}
	}

%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="<%= request.getContextPath() %>/css/style.css" rel="stylesheet">
		<title>エラー</title>
	</head>
	
	<body>
	
		<jsp:include page="/common/header.jsp">
	        <jsp:param name="title">
	            <jsp:attribute name="value">
	                エラー
	            </jsp:attribute>
	        </jsp:param>
	        <jsp:param name="headName">
	            <jsp:attribute name="value">
	                エラー
	            </jsp:attribute>
	        </jsp:param>
	        <jsp:param name="nav">
	            <jsp:attribute name="value">
	                    <div class="nav-padding">
	                        <a href="<%= request.getContextPath() %>/view/Common/menu.jsp">【メニュー画面】</a>
	                    </div>
	            </jsp:attribute>
	        </jsp:param>
	    </jsp:include>
	
		<main>
			<center>
				<h2>●●●エラー●●●</h2>
				<%= error %>
			</center>
		</main>
	</body>
</html>