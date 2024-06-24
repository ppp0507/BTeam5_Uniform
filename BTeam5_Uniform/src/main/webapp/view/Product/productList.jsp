<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.User"%>
<%@page import="java.util.ArrayList,bean.Product"%>


<%
 	//セッションからユーザー情報を取得
    User user = (User)session.getAttribute("user");
 	//ユーザーがNULLならゲストアカウントにする
 	if (user == null){
 		//インスタンス変数 userid, password, email, authority の初期化
 		user = new User();
 		user.setUsername("ゲスト");
 		user.setAuthority_id(3);
 	}
  
	//権限の設定
	String authority = "";
	if (user.getAuthority_id() == 1) {
		authority = "管理者";
		
		%>

<html>
<head>
<meta charset="UTF-8">
<title>商品一覧</title>
</head>
<body>

	<!-- ヘッダー:今ログインしているユーザー表示　-->
	<%@ include file="/common/header.jsp"%>

	<div style="margin-bottom: 250px">
		<table style="margin: auto">
			<tr>
				<td>
					<form action="<%=request.getContextPath()%>/search">
						<input type=text size="30" name=“day”></input> <input
							type="submit" name="search" value="検索"></input>
					</form>
				</td>
				<td>
					<form action="<%=request.getContextPath()%>/productList">
						<input type="submit" name="searchall" value="全件表示"></input>
					</form>
				</td>
			</tr>
		</table>


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
				
				ArrayList<Product> productList =(ArrayList<Product>)request.getAttribute("product_list");
				
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
				<td style="text-align: center; width: 125px"><a
					href="<%= request.getContextPath() %>/productEdit?id=<%= products.getId()%>&cmd=update">編集</a>
				</td>
			</tr>
			<%
				}
				}
	}
				%>
		</table>
	</div>

	<%
	} else if (user.getAuthority_id() == 2 && user.getAuthority_id() == 3) {
		authority = "一般ユーザー";
		%>

	<!-- メニュー移動 -->
	<nav>
		<div style="padding: 0.5em;">
			<a href="<%=request.getContextPath() %>/view/menu.jsp">メニュー</a>
		</div>
	</nav>

	<!-- メインコンテンツ(本文) -->
	<main>
		<%
				
				ArrayList<Product> productList =(ArrayList<Product>)request.getAttribute("product_list");
				
				if(productList != null){
					for(int i=0;i<productList.size();i++){
						Product products = (Product)productList.get(i);
				%>
		<div class="card-flex">
			<div class="card">
				<div style="height: 100%;">
					<a href="#" style="text-decoration: none; color: black;">
						<h3><%= products.getName()%></h3> <img class="anime_test"
						src="../imege/frenet.JPG" alt="ユニフォーム" width="300" height="200">
					</a><a href="<%=request.getContextPath()%>/productDetail?id=<%=request.getId()%>">商品詳細</a>
					<a href="<%=request.getContextPath()%>/insertIntoCart?id=<%=request.getId()%>">カートに入れる</a>
				</div>
			</div>
	</main>

	<jsp:include page="/common/footer.jsp" />

				<% 
				}
				}
	}
	%>

</body>
</html>