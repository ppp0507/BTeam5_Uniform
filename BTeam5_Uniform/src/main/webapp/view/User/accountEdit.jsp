<%-- 作成者: 屋比久 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bean.User,dao.UserDAO" %>

<%

	// セッションからユーザー情報を取得
	User user = (User)session.getAttribute("user");

	// ユーザー情報を扱うDAO
	UserDAO userDao = new UserDAO();

	// 操作対象のユーザーのidを格納する変数
	int id;
	
	// 操作対象のユーザーを格納する変数
	User editUser = null;

	// ユーザーがNULLならゲストアカウントにする
	if (user == null){
		//インスタンス変数 userid, password, email, authority の初期化
		user = new User();
		user.setUsername("ゲスト");
		user.setAuthority_id(3);
	}
	
	if (user.getAuthority_id() == 1) {
		// 管理者なら
			
		// パラメータ「id」からユーザーIDを取得
		id = Integer.parseInt(request.getParameter("id"));
		
		
		// ユーザーIDからUserを取得
		editUser = userDao.getUserbyId(id);
	} else if (user.getAuthority_id() == 2){
		// 一般ユーザーなら
		
		// ログインしているユーザーを編集対象に指定
		editUser = user;
	} else if (user.getAuthority_id() == 3){
		// ゲストアカウントなら
		
		//エラー画面に遷移させる
		
		String error = "ゲストアカウントでユーザ一編集機能は利用できません。";
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

	<main>
	
		<jsp:include page="/common/header.jsp">
			<jsp:param name="nav">
				<jsp:attribute name="value">
						<div class="nav-padding">
							<a href="<%= request.getContextPath() %>/productList">【商品ページ】</a>
							<a href="<%= request.getContextPath() %>/showCart">【カート確認】</a>
						</div>
				</jsp:attribute>
			</jsp:param>
		</jsp:include>
	
		<center>
		
			<form action="<%= request.getContextPath() %>/accountEdit" method="GET">
				<div class="center-flex" style="border: solid 1px gray">
					
					<div style="background-color: aliceblue;"><div>項目</div></div>
					<div style="background-color: aliceblue;"><div>変更前</div></div>
					<div style="background-color: aliceblue;flex: 2">変更後</div>
					<input type="hidden" name="id" value="<%= editUser.getUserid() %>">
					<span class="flex-indent"></span>

					<div><div>名前</div></div>
					<div><div><%= editUser.getUsername() %></div></div>
					<div style="flex: 2"><input type="text" name="name"></div>
					<span class="flex-indent"></span>

					<div><div>住所</div></div>
					<div><div><%= editUser.getAddress() %></div></div>
					<div style="flex: 2"><input type="text" name="address"></div>
					<span class="flex-indent"></span>
					
					<div><div>メールアドレス</div></div>
					<div><div><%= editUser.getEmail() %></div></div>
					<div style="flex: 2"><input type="text" name="mail"></div>
					<span class="flex-indent"></span>

				</div>
				<div class="flex-bottom">
					<input type="submit" value="変更">
					<% if (user.getAuthority_id() == 1) { %>
					<%-- 管理者なら --%>
					<input type="submit" value="ユーザー削除">
					<% } else if (user.getAuthority_id() == 2) { %>
					<%-- 一般ユーザーなら --%>
					<input type="submit" value="退会">
					<% } %>
				</div>
			</form>
			
		</center>
	</main>

	<jsp:include page="/common/footer.jsp" />
</body>
</html>
