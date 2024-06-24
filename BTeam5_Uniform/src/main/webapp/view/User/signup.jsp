<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 担当：山里奈緒 -->

<html>

<head>

<title>アカウント登録</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">

</head>

<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">

		<!-- ヘッダー -->
		<%@ include file="/common/header.jsp"%>


		<!-- メニュー移動 -->
		<nav>
			<div style="padding: 0.5em;">
				<a href="<%=request.getContextPath()%>/view/common/menu.jsp">メニュー</a>
				<a href="<%=request.getContextPath()%>/view/Product/productList.jsp">商品一覧</a>
			</div>
		</nav>

		<!-- メインコンテンツ(本文) -->
		<main>
			<div
				style="border: solid 1px lightgray; border-radius: 4px; padding: 1.5em; width: 350px; margin: auto; text-align: center;">
				<form action="<%=request.getContextPath()%>/signup">
					<h3>アカウントを登録する</h3>

					<div style="margin: 1em;">
						<label style="display: block;">名前</label> <input type="text"
							style="width: 100%; height: 25px;" name="username">
					</div>

					<div style="margin: 1em;">
						<label style="display: block;">メールアドレス</label> <input type="text"
							style="width: 100%; height: 25px;" name="email">
					</div>

					<div style="margin: 1em;">
						<label style="display: block;">パスワード</label> <input
							type="password" style="width: 100%; height: 25px;"
							name="password">
					</div>

					<div style="margin: 1em;">
						<label style="display: block;">住所</label> <input type="text"
							style="width: 100%; height: 25px;" name="address">
					</div>

					<input type="submit" value="登録"
						style="cursor: pointer; font-size: medium; font-weight: bold; padding: 2px 55px; color: white; background-color: #FFC107; border: none; border-radius: 5px;">
					<div style="margin-top: 1em;"></div>
					<a href="<%=request.getContextPath()%>/view/User/login.jsp"
						style="font-size: small;">ログインする</a>

				</form>
			</div>
		</main>


		<!-- フッター -->
		<%@ include file="/common/footer.jsp"%>

	</div>

</body>

</html>
