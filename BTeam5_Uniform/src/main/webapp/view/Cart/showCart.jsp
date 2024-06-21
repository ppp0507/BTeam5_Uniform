<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,bean.Order,dao.ProductDAO"%>
<!-- @author 朴姻禹 -->
<%
ProductDAO objProductDao = new ProductDAO();
%>

<!DOCTYPE html>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/style.css">
<head>
<title>カート確認</title>
</head>

<body>

	<!-- ヘッダー -->
	<%@ include file="/common/header.jsp"%>

	<!-- メニュー移動 -->
	<nav>
		<div style="padding: 0.5em;">
			<a href>メニュー</a> <a href>商品一覧</a> <span>これはnavです</span>
		</div>
	</nav>

	<!-- メインコンテンツ(本文) -->
	<main>
		<center>
			<div>
				<table border="1" class="table-padding solid-table"
					style="margin: 2em;">
					<tr>
						<th>商品名</th>
						<th>個数</th>
						<th>金額</th>
						<th></th>
					</tr>
					<%
	
					//セッションから"order_list"のList配列を取得
					ArrayList<Order> order_list = (ArrayList<Order>) session.getAttribute("order_list");
					if (order_list != null) {
						for (int i = 0; i < order_list.size(); i++) {
							Order order = (Order) order_list.get(i);
							int id = order.getProductid();
							String name = objProductDao.getDetail(id).getName();
							int quantity = order.getQuantity();
							int price = objProductDao.getDetail(id).getPrice();
					
					%>
					<tr>
					   <td><%=name%></td>
					   <td><%=quantity%></td>
					   <td><%=price %>円</td>
					   <td><a href="<%=request.getContextPath()%>/deleteCart?i=<%= i%>">カートから削除</a></td>
					</tr>

					<%
					}
					}
					%>
				</table>
			</div>

			<br>
			<br>
			<br>
			<h3>購入情報入力</h3>
			<span>お客様の情報をデフォルトで設定しております。</span> <br> <span>
				違う配送先への発送をご希望の場合、下のフォームから修正お願いします。 </span>

			<form>
				<div class="center-flex" style="border: solid 1px gray">

					<div>
						<div>お名前</div>
					</div>
					<div>
						<input type="text" name="name">
					</div>
					<span class="flex-indent"></span>

					<div>
						<div>配送先住所</div>
					</div>
					<div>
						<input type="text" name="address">
					</div>
					<span class="flex-indent"></span>

					<div>
						<div>E-mail</div>
					</div>
					<div>
						<input type="text" name="mail">
					</div>
					<span class="flex-indent"></span>

					<div>
						<div>電話番号</div>
					</div>
					<div>
						<input type="text" name="phone">
					</div>
					<span class="flex-indent"></span>

				</div>
				<div class="flex-bottom">
					<input type="submit" value="ショッピングを続ける"> <input
						type="submit" value="これで注文する">
				</div>
			</form>
		</center>
	</main>


	<!-- ヘッダー -->
	<%@ include file="/common/footer.jsp"%>
</body>