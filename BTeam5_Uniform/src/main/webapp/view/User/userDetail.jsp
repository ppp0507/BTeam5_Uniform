<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.ArrayList,bean.User,bean.Order,dao.ProductDAO,dao.OrderDAO"%>

<%
OrderDAO orderDao = new OrderDAO();
ProductDAO productDao = new ProductDAO();

ArrayList<Order> list = new ArrayList<Order>();
User userElement = (User) request.getAttribute("userElement");

list = orderDao.selectByUser(userElement.getUserid());

ArrayList<Order> orderList = (ArrayList<Order>) request.getAttribute("orderList");

%>

<html>
<head>
<title>ユーザー一覧</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
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
				ユーザー詳細
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="nav">
			<jsp:attribute name="value">
					<div class="nav-padding">
					<a href="<%=request.getContextPath()%>/view/Common/menu.jsp">【メニュー】</a>
					<a href="<%=request.getContextPath()%>/userList">【ユーザー一覧】</a>	
					</div>
			</jsp:attribute>
		</jsp:param>
	</jsp:include>


	<!-- メインコンテンツ(ユーザー一覧) -->
	<main>

		<div>

			<%
			if (userElement.getAuthority_id() == 1) {
			%>
			<p style="text-align: left">
				権限 ：管理者</p>
			<p style="text-align: left">
				名前 ：<%=userElement.getUsername()%></p>
			<p style="text-align: left">
				Email：<%=userElement.getEmail()%></p>
			住所 ：
			<%=userElement.getAddress()%></p>

			<%
			} else {
			%>
			<p style="text-align: left">
				権限 ：<%=userElement.getAuthority_id() == 2 ? "会員" : "ゲスト"%></p>
			<p style="text-align: left">
				名前 ：<%=userElement.getUsername()%></p>
			<p style="text-align: left">
				Email：<%=userElement.getEmail()%></p>
			住所 ：
			<%=userElement.getAddress()%></p>

			<center>
				<h3>注文内歴</h3>

				<table border="1" class="table-padding solid-table"
					style="margin: 2em;">
					<tr>
						<th>注文番号</th>
						<th>個数</th>
						<th>合計</th>
						<th>発注日</th>
						<th>入金状況</th>
						<th>発送状況</th>
						<th></th>
					</tr>

					<tbody>
						<%
						int total = 0;
						%>
						<%
						if (orderList != null) {
							for (Order order : orderList) {
						%>
						<tr>
							<!-- 注文番号 -->
							<td><%=order.getId()%></td>
							<!-- 個数 -->
							<td><%=order.getQuantity()%></td>
							<!-- 合計 -->
							<td><%=order.getQuantity() * productDao.getDetail(order.getProductid()).getPrice()%>円</td>
							<!-- 発注日 -->
							<td><%=order.getDate()%></td>
							<!-- 入金状況 -->
							<td><%=order.getIsPayment() ? "入金済み" : "入金待ち"%></td>
							<!-- 発送状況 -->
							<td><%
							if(order.getDeliveryStateId() == 3){
								%> 未発送 <%
							}
							else if(order.getDeliveryStateId() == 2){
								%>発送中<%
							}
							else if(order.getDeliveryStateId() == 1){
								%>発送済<%
							}
							%>
							</td>
							<td><a
								href="<%=request.getContextPath()%>/orderDetail?id=<%=order.getId()%>">詳細</a>
							</td>

						</tr>
						<%
						}
						}
						%>
					</tbody>

				</table>
				<%
				if(orderList.size() == 0){
					%>
					<h3>注文がございません。</h3>
					 <%
				}
				%>
			</center>
			<%
			}
			%>
		</div>

	</main>

	<!-- フッター -->
	<%@ include file="/common/footer.jsp"%>
</body>
</html>