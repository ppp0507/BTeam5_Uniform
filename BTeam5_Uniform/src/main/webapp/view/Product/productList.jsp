<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="bean.User"%>
<%@page import="java.util.ArrayList,bean.Product"%>


<%
//セッションからユーザー情報を取得
User loginUser = (User)session.getAttribute("user");
//ユーザーがNULLならゲストアカウントにする
if (loginUser == null){
//インスタンス変数 loginUserid, password, email, authority の初期化
loginUser = new User();
loginUser.setUsername("ゲスト");
loginUser.setAuthority_id(3);
}

// 商品リストをリクエストスコープから取得
ArrayList<Product> productList =(ArrayList<Product>)request.getAttribute("product_list");

%>

<html>
	<head>
		<meta charset="UTF-8">
		<link href="<%= request.getContextPath() %>/css/style.css" rel="stylesheet">
		<title>商品一覧</title>
	</head>
	<body>
	
	
		<jsp:include page="/common/header.jsp">
			<jsp:param name="headName">
				<jsp:attribute name="value">
					商品一覧
				</jsp:attribute>
			</jsp:param>
			<jsp:param name="nav">
				<jsp:attribute name="value">
						<div class="nav-padding">
							<a href="<%= request.getContextPath() %>/view/Cart/showCart.jsp">【カート確認】</a>
							<a href="<%= request.getContextPath() %>/view/Common/menu.jsp">【メニュー画面】</a>
						</div>
				</jsp:attribute>
			</jsp:param>
		</jsp:include>
		
		<main>
			
			<!-- 管理者なら -->
			
			<% if (loginUser.getAuthority_id() == 1) { %>
			
			<div style="margin-bottom: 250px">				
				
				<table style="margin: auto">
					<tr>
						<th style="background-color: #6666ff; width: 200px">商品番号</th>
						<th style="background-color: #6666ff; width: 200px">商品名</th>
						<th style="background-color: #6666ff; width: 200px">値段</th>
						<th style="background-color: #6666ff; width: 200px">個数</th>
						<th style="background-color: #6666ff; width: 200px">商品イメージ</th>
						<th style="background-color: #6666ff; width: 250px" colspan="2">編集</th>
					</tr>
					
					<%
						
						
						if(productList != null){
							for(int i=0;i<productList.size();i++){
							Product products = (Product)productList.get(i);
								
							%>
							
								<tr>
									<td style="text-align: center; width: 200px"><%= products.getId()%></td>
									<td style="text-align: center; width: 200px"><%= products.getName()%></td>
									<td style="text-align: center; width: 200px"><%= products.getPrice() %></td>
									<td style="text-align: center; width: 200px"><%= products.getStock()%></td>
									<td style="text-align: center; width: 200px"><%= products.getImage_url()%></td>
									<td style="text-align: center; width: 125px">
									<a href="<%=request.getContextPath()%>/view/Product/productEdit.jsp?product_id=<%=products.getId()%>">編集</a>
									</td>
								</tr>
							
							<%
							}
						}
					%>
				</table>
				[<a href="<%=request.getContextPath()%>/view/Product/productRegister.jsp">商品登録</a>]
			</div>
			
			<% } else if (loginUser.getAuthority_id() == 2 || loginUser.getAuthority_id() == 3) { %>

			<!-- 一般ユーザーかゲストアカウントなら -->
					
			<div class="card-flex">
			<%
				
				
				if(productList != null){
					for(int i=0;i<productList.size();i++){
						Product products = (Product)productList.get(i);
						%>
						
						<div class="card">
						
							<div>
								<a href="<%=request.getContextPath()%>/productDetail?id=<%=products.getId()%>" style="display:inline-block;text-decoration: none; color: black;height: 80px;width: 100%;overflow: hidden;">
									<h3><%= products.getName()%></h3>
								</a>
							</div>
							
							<a href="<%=request.getContextPath()%>/productDetail?id=<%=products.getId()%>">商品詳細</a>
							<a href="<%=request.getContextPath()%>/insertIntoCart?id=<%=products.getId()%>" class="blue-button">カートに入れる</a>
						</div>
						
						
						<% 
					}
				}
				
			%>
			</div>
			
			<% } %>
			
		</main>
		
		<jsp:include page="/common/footer.jsp" />
		
	</body>
</html>
