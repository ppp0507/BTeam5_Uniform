<%-- 作成者: 屋比久 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bean.User" %>

<%

	// セッションからユーザー情報を取得
	User user = (User)session.getAttribute("user");

	// ユーザーがNULLならゲストアカウントにする
	if (user == null){
		//インスタンス変数 userid, password, email, authority の初期化
		user = new User();
		user.setUsername("ゲスト");
		user.setAuthority_id(3);
	}
	
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="<%= request.getContextPath() %>/css/style.css" rel="stylesheet">
		<title>メニュー</title>
	</head>
	
	<body>
	
	
	<% if (user.getAuthority_id() == 1) { %>
	<%-- 管理者の場合 --%>
	
	<jsp:include page="/common/header.jsp">
		<jsp:param name="headName">
			<jsp:attribute name="value">
				メニュー
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="nav">
			<jsp:attribute name="value">
					<div class="nav-padding">
						
					</div>
			</jsp:attribute>
		</jsp:param>
	</jsp:include>
	
	    <main>
			<center>
				<div class="left-center-list link-button">
					<div><a href="<%= request.getContextPath() %>/orderList">【注文一覧】</a></div>
					<div><a href="<%= request.getContextPath() %>/productList">【商品一覧】</a></div>
					<div><a href="<%= request.getContextPath() %>/userList">【ユーザ一一覧】</a></div>
				</div>
			</center>
	    </main>
	    
	<% } else if (user.getAuthority_id() == 2) { %>
	<%-- 一般ユーザーの場合 --%>
	
	<jsp:include page="/common/header.jsp">
		<jsp:param name="headName">
			<jsp:attribute name="value">
				メニュー
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="nav">
			<jsp:attribute name="value">
					<div class="nav-padding">
						
					</div>
			</jsp:attribute>
		</jsp:param>
	</jsp:include>
	
	
	    <main>
			<center>
				<div class="left-center-list link-button">
					<div><a href="<%= request.getContextPath() %>/productList">【商品一覧】</a></div>
					<div><a href="<%= request.getContextPath() %>/view/Cart/showCart.jsp">【カート確認】</a></div>
					<div><a href="<%= request.getContextPath() %>/orderHistory">【購入履歴】</a></div>
					<div><a href="<%= request.getContextPath() %>/view/User/accountEdit.jsp">【アカウント編集】</a></div>
				</div>
			</center>
	    </main>
	
	<% } else if (user.getAuthority_id() == 3) { %>
	<%-- ゲストの場合 --%>
	
	<jsp:include page="/common/header.jsp">
		<jsp:param name="headName">
			<jsp:attribute name="value">
				メニュー
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="nav">
			<jsp:attribute name="value">
					<div class="nav-padding">
						
					</div>
			</jsp:attribute>
		</jsp:param>
	</jsp:include>
	
	
	    <main>
			<center>
				<div class="left-center-list link-button">
					<div><a href="<%= request.getContextPath() %>/productList">【商品一覧】</a></div>
					<div><a href="<%= request.getContextPath() %>/view/Cart/showCart.jsp">【カート確認】</a></div>
					<div><a href="<%= request.getContextPath() %>/view/User/login.jsp">【ログイン】</a></div>
					<div><a href="<%= request.getContextPath() %>/view/User/signup.jsp">【アカウント作成】</a></div>
				</div>
			</center>
	    </main>
	    
	<% } %>
	
	<jsp:include page="/common/footer.jsp" />
	    
	</body>
</html>
