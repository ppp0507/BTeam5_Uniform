<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList,bean.User,bean.Order,bean.Product,dao.ProductDAO"%>
<!-- @author 朴姻禹 -->
<%
ProductDAO productDao = new ProductDAO();
User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/style.css">
<head>
<title>カート確認</title>
</head>

<body>

	<!-- ヘッダー -->
	<jsp:include page="/common/header.jsp">
		<jsp:param name="title">
			<jsp:attribute name="value">
				タイトル
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="headName">
			<jsp:attribute name="value">
				ページに表示するタイトル
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="nav">
			<jsp:attribute name="value">
					<div class="nav-padding">
					<a href="<%=request.getContextPath()%>/view/Common/menu.jsp">【メニュー】</a>
					<a href="<%=request.getContextPath()%>/productList">【商品ページ】</a>	
					</div>
			</jsp:attribute>
		</jsp:param>
	</jsp:include>

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
						//for文start
						for (int i = 0; i < order_list.size(); i++) {

							Order order = (Order) order_list.get(i);
							int id = order.getProductid();

							//DBから商品の情報取得
							Product product = productDao.getDetail(id);

							//DBに存在する商品か確認
							if (product.getId() == 0) {
						request.getRequestDispatcher("/deleteCart?i=" + i).forward(request, response);
							}
							;

							String name = product.getName();
							int quantity = order.getQuantity();
							int price = product.getPrice();

							//DBの在庫チェック
							if (product.getStock() < order.getQuantity()) {
						order.setQuantity(product.getStock());
						order_list.set(i, order);
						quantity = order.getQuantity();
							}
					%>
					<tr>
						<td><%=name%></td>
						<td><%=quantity%></td>
						<td><%=price%>円 * <%=quantity%> = <%=price * quantity%>円</td>
						<td><a
							href="<%=request.getContextPath()%>/deleteCart?i=<%=i%>">カートから削除</a></td>
					</tr>
					<%
					}
					//for文end

					}
					//if文end

					else {
					%>
					<h4>カートに商品が存在しません。</h4>
					<%
					}
					%>
				</table>
				<span>取り扱いのない商品はカートから自動的に削除されます。</span> <br> <span>在庫数に合わせてカート内の数量が変更されます。</span>
			</div>

			<br> <br> <br>
			<h3>購入情報入力</h3>
			<span>ログイン中の場合、お客様の登録情報をデフォルト配送先で設定しております。</span> <br> <span>
				ログインせずに購入する場合、下のフォームから情報の入力をお願いします。 </span>
			<%
			//注文情報:name,user_id,address,comment
			String name = "";
			String address = "";
			String email = "";
			String comment = "";

			if (user != null && user.getAuthority_id() == 2) {
				name = user.getUsername();
				address = user.getAddress();
				email = user.getEmail();
			}
			%>
			<form action="<%=request.getContextPath()%>/buyCart" method="POST">
				<div class="center-flex" style="border: solid 1px gray">
					<div>
						<div>お名前</div>
					</div>
					<div>
						<input type="text" name="name" value="<%=name%>">
					</div>
					<span class="flex-indent"></span>
					<div>
						<div>配送先住所</div>
					</div>
					<div>
						<textarea name="address" cols="50" rows="5" maxlength="256"><%=address%></textarea>
					</div>
					<span class="flex-indent"></span>

					<div>
						<div>E-mail</div>
					</div>
					<div>
						<input type="text" name="email" value="<%=email%>">
					</div>
					<span class="flex-indent"></span>
					<div>
						<div>備考欄</div>
					</div>
					<div>
						<textarea name="comment" cols="50" rows="5" maxlength="200"></textarea>
					</div>
					<span class="flex-indent"></span>
				</div>
				<div class="flex-bottom">
					<a href="<%=request.getContextPath()%>/view/Common/menu.jsp">ショッピングを続ける</a>
					<% 
					if(order_list == null || order_list.size() == 0){%>
						<input type="submit" value="これで注文する" disabled />
					<%
					} else {
						%>
						<input type="submit" value="これで注文する">
						<%
					}
					%>
					
				</div>
			</form>
		</center>
	</main>


	<!-- ヘッダー -->
	<%@ include file="/common/footer.jsp"%>
</body>