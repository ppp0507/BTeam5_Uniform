<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="bean.User"%>
<!-- @author 朴姻禹 -->
<%
String name = (String)request.getAttribute("order_name");
User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/style.css">
<head>

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
	<main style="text-align: center;">
		<div>

			<h3>注文完了</h3>
			------------------------------------------------------ <br> <br>
			<%= name %>様のご注文を受け付けました。 <br> 注文情報をメールにてお送りしました。
		</div>

		[<a href="<%=request.getContextPath()%>/view/Common/menu.jsp">メニュー</a>]

		<div style="margin-top: 20px;">

			<!-- ゲストユーザーの場合 -->
			<%
         if ( user.getAuthority_id() == 3) { %>

			会員登録されますと、次回から個人情報記入の短縮が可能になります。 <br> [<a
				href="<%=request.getContextPath()%>/view/User/signup.jsp">会員登録</a>]
		</div>

		<% }
        
        
        //会員登録されているユーザーの場合
        else { %>
        	<br>
        	[<a href="<%=request.getContextPath()%>/orderHistory">購入履歴</a>]
        <% } %>

	</main>

	<!-- フッター -->
	<footer>
		<div style="padding: 1em;">2024 copyright なんちゃら これはフッターです</div>
	</footer>
</body>