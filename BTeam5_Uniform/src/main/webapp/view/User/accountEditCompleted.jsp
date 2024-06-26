<%-- 作成者: 屋比久 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bean.User,dao.UserDAO" %>

<%

	// セッションからユーザー情報を取得
	User user = (User)session.getAttribute("user");

	// 変更前のユーザー情報
	User beforeUser = (User)request.getAttribute("before");
	// 変更後のユーザー情報
	User afterUser = (User)request.getAttribute("after");
	
	if (beforeUser == null || afterUser == null) {
		// 送信されるパラメータが存在しないなら
		// エラー画面に遷移させる
		
		String error = "表示するユーザー情報が存在しません。";
		// エラー文をerrorという名前でリクエストスコープに保存
		request.setAttribute("error", error);
		// フォワード処理
		request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
	}

%>
<!DOCTYPE html>
	<head>
		<meta charset="UTF-8">
		<link href="<%= request.getContextPath() %>/css/style.css" rel="stylesheet">
		<title>アカウント編集</title>
	</head>

<body>

	<jsp:include page="/common/header.jsp">
		<jsp:param name="nav">
			<jsp:attribute name="value">
					<div class="nav-padding">
						<a href="<%= request.getContextPath() %>/productList">【商品ページ】</a>
						<a href="<%= request.getContextPath() %>/view/Cart/showCart.jsp">【カート確認】</a>
						
					</div>
			</jsp:attribute>
		</jsp:param>
	</jsp:include>
	
	<main>
	
	
		<center>
		
			<div class="center-flex" style="border: solid 1px gray">
				
				<div style="background-color: aliceblue;"><div>項目</div></div>
				<div style="background-color: aliceblue;"><div>変更前</div></div>
				<div style="background-color: aliceblue;">変更後</div>
				<span class="flex-indent"></span>
	
				<div><div>名前</div></div>
				<div><div><%= beforeUser.getUsername() %></div></div>
				<div><div><%= afterUser.getUsername() %></div></div>
				<span class="flex-indent"></span>
	
				<div><div>住所</div></div>
				<div><div><%= beforeUser.getAddress() %></div></div>
				<div><div><%= afterUser.getAddress() %></div></div>
				<span class="flex-indent"></span>
				
				<div><div>メールアドレス</div></div>
				<div><div><%= beforeUser.getEmail() %></div></div>
				<div><div><%= afterUser.getEmail() %></div></div>
				<span class="flex-indent"></span>
			
			</div>
			<a href="<%= request.getContextPath() %>/view/Common/menu.jsp">【メニュー画面へ】</a>
		</center>
	</main>

	<jsp:include page="/common/footer.jsp" />
</body>
</html>