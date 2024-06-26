<%@page import="bean.Product,dao.ProductDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
//エラーメッセージを格納する変数を宣言
String error = null;

//遷移先を決める変数cmdを宣言
String cmd = null;

//Productオブジェクトを作成
Product product = null;

try {
	//パラメータのnameを考え中(仮：product_id)
	//productList.jspで商品番号パラメータを取得
	int id = Integer.parseInt(request.getParameter("product_id"));

	//ProductDAOオブジェクトを作成
	ProductDAO productDao = new ProductDAO();

	//取得した商品番号の商品情報を取得
	product = productDao.getDetail(id);

} catch (IllegalStateException e) {
	//DB接続エラーが発生した場合
	error = "DB接続エラーのため、商品編集画面を表示できませんでした。";
	cmd = "";

} finally {
	//エラーが発生した場合
	if (error != null) {
		//errorをリクエストスコープに登録
		request.setAttribute("error", error);
	}

	if (cmd != null) {
		//cmdをリクエストスコープに登録
		request.setAttribute("cmd", cmd);
	}

	if (error != null) {
		//error.jspに処理を遷移
		request.getRequestDispatcher("view/Common/error.jsp").forward(request, response);
	}

}

session.setAttribute("delete_product_id", product.getId());

%>

<!DOCTYPE html>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/style.css">
<html>
<head>
<meta charset="UTF-8">
<title>商品編集</title>
</head>
<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">

		<!-- ヘッダー:今ログインしているユーザー表示-->
		<jsp:include page="/common/header.jsp">
			<jsp:param name="headName">
				<jsp:attribute name="value">
					商品編集
				</jsp:attribute>
			</jsp:param>
			<jsp:param name="nav">
				<jsp:attribute name="value">
						<div class="nav-padding">
							<a href="<%= request.getContextPath() %>/view/Common/menu.jsp">【メニュー】</a>
							<a href="<%= request.getContextPath() %>/productList">【商品一覧】</a>
						</div>
				</jsp:attribute>
			</jsp:param>
		</jsp:include>

		<!-- メインコンテンツ(本文) -->
		<main>

			<center>
				<form action="<%=request.getContextPath()%>/productEdit" method="get">

					<div class="center-flex">
						<span style="flex: 1"> <変更前情報></span> <span
							style="flex: 1"> <変更後情報></span>
					</div>
					<div class="center-flex">
						<div style="background-color: aliceblue;">
							<div>商品番号</div>
						</div>
						<div>
							<div><%=product.getId()%></div>
						</div>
						<div style="flex: 2">
							<div style="text-align:center"><%=product.getId()%></div>
							<input type="hidden" name="id" value="<%=product.getId()%>">
						</div>
						<span class="flex-indent"></span>

						<div style="background-color: aliceblue;">
							<div>商品名</div>
						</div>
						<div>
							<div><%=product.getName()%></div>
						</div>
						<div style="flex: 2">
							<input type="text" name="name">
						</div>
						<span class="flex-indent"></span>

						<div style="background-color: aliceblue;">
							<div>価格</div>
						</div>
						<div>
							<div><%=product.getPrice()%></div>
						</div>
						<div style="flex: 2">
							<input type="text" name="price">
						</div>
						<span class="flex-indent"></span>

						<div style="background-color: aliceblue;">
							<div>在庫数</div>
						</div>
						<div>
							<div><%=product.getStock()%></div>
						</div>
						<div style="flex: 2">
							<input type="text" name="stock">
						</div>
						<span class="flex-indent"></span>

						<div style="background-color: aliceblue;">
							<div>画像</div>
						</div>
						<div>
							<div><%=product.getImage_url()%></div>
						</div>
						<div style="flex: 2; width: 0;">
							<div>
								<input type="file">
							</div>
						</div>
						<span class="flex-indent"></span>

					</div>
				<div class="flex-bottom">
					<form>
						<input type="hidden" name="cmd" value="update">
						<input type="submit" value="更新" style="margin: 1em;">
					</form>
					<form action="<%=request.getContextPath()%>/productEdit" method="get">
						<input type="hidden" name="cmd" value="delete">
						<input type="submit" value="削除" style="margin: 1em;">
					</form>
				</div>
				</form>
			</center>

		</main>

		<!-- フッター -->
		<%@ include file="/common/footer.jsp"%>
</body>
</html>