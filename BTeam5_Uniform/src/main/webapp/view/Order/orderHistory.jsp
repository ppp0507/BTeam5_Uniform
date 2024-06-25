<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,bean.Order,dao.ProductDAO,bean.User" %>

<%
ArrayList<Order> orderList = (ArrayList<Order>)request.getAttribute("orderList");
ProductDAO productDao = new ProductDAO();
User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath() %>/css/style.css">
	
<html>
<head>
<meta charset="UTF-8">
<title>注文履歴</title>
</head>
<body>
    <!-- ヘッダー:今ログインしているユーザー表示　-->
	<jsp:include page="/common/header.jsp">
		<jsp:param name="title">
			<jsp:attribute name="value">
				タイトル
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="headName">
			<jsp:attribute name="value">
				<%=user.getUsername()%>様の注文履歴
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
				<table border="1" class="table-padding solid-table" style="margin: 2em;">
					<tr>
						<th>
							注文番号
						</th>
						<th>
							注文商品
						</th>
						<th>
							値段
						</th>
						<th>
							個数
						</th>
						<th>
							合計金額
						</th>
						<th>
							注文日
						</th>
						<th>
							発送状況
						</th>
					</tr>

				<%
				if(orderList != null){
					for(Order order : orderList){
						
						//価格＊注文数で合計金額を算出
						int sum = productDao.getDetail(order.getProductid()).getPrice() * order.getQuantity();
						
						//発注状況の文字列を格納する変数
						String deliveryState = null;
						
						//Orderオブジェクトのdelivery_state_idの値
						int deliveryStateId = order.getDeliveryStateId();
						
						//delivery_state_idの値によって格納する変数を変える
						if(deliveryStateId == 1){
							//1は発送済
							deliveryState = "発送済";
							
						}else if(deliveryStateId == 2){
							//2は発送中
							deliveryState = "発送中";
							
						}else if(deliveryStateId == 3){
							//3は未発送
							deliveryState = "未発送";
						}
				%>
				
					<tr>
						<td>
							<%= order.getId() %>
						</td>
						<td>
							<%= productDao.getDetail(order.getProductid()).getName() %>
						</td>  
						<td>
							<%= productDao.getDetail(order.getProductid()).getPrice()%>
						</td>
						<td>
							<%= order.getQuantity() %>
						</td>
						<td>
							<%= sum %>
						</td>     
						<td>
							<%= order.getDate() %>
						</td>
						<td>
							<%= deliveryState %>
						</td>
					</tr>
				<%
					}
				} else { %>
				
				<h3>注文がありません。</h3>
					
				<% }
				%>	
				</table>
			</div>
		</center>
	</main>

	<!-- フッター -->
    <%@ include file="/common/footer.jsp"%>
</body>
</html>