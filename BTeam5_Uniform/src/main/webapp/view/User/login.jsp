<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// セッションからデータを取得
String userid = "";
String password = "";
String error = (String) request.getAttribute("error");

if (error == null) {
	error = "";
}
%>

<!-- 担当：山里奈緒 -->

<html>

<head>

<title>ログイン</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">

</head>

<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">

		<!-- ヘッダー -->
	<jsp:include page="/common/header.jsp">
		<jsp:param name="title">
			<jsp:attribute name="value">
				ログイン
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="headName">
			<jsp:attribute name="value">
				ログイン
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="nav">
			<jsp:attribute name="value">
					<div class="nav-padding">
					<a href="<%=request.getContextPath()%>/view/Common/menu.jsp">【メニュー】</a>
					<a href="<%=request.getContextPath()%>/productList">【商品一覧】</a>	
					</div>
			</jsp:attribute>
		</jsp:param>
	</jsp:include>


		<!-- メインコンテンツ(本文) -->
		<main>
			<div
				style="border: solid 1px lightgray; border-radius: 4px; padding: 1.5em; width: 350px; margin: auto; text-align: center;">
				<p><%=error%></p>
				<form action="<%=request.getContextPath()%>/login" method="POST">
					<h3>ログイン</h3>
					<div style="margin: 1em;">
						<label style="display: block;">ユーザーID</label> <input type="text"
							style="width: 100%; height: 25px;" name="email"
							value="<%=userid%>">
					</div>

					<div style="margin: 1em;">
						<label style="display: block;">パスワード</label> <input
							type="password" style="width: 100%; height: 25px;"
							name="password" value="<%=password%>">
					</div>

					<input type="submit" value="サインイン"
						style="cursor: pointer; font-size: medium; font-weight: bold; padding: 2px 55px; color: white; background-color: #FFC107; border: none; border-radius: 5px;"
						href="<%=request.getContextPath()%>/view/common/menu.jsp">
					<div style="margin-top: 1em;">
						<a href="<%=request.getContextPath()%>/view/User/signup.jsp" style="font-size: small;">アカウントを登録する</a>
					</div>
				</form>
			</div>
		</main>


		<!-- フッター -->
		<%@ include file="/common/footer.jsp"%>

	</div>

</body>

</html>
