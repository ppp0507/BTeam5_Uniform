<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="bean.User"%>
<!-- @author 朴姻禹 -->
<%
String name = (String)request.getAttribute("order_name");
User user = (User) session.getAttribute("user");
if(user == null){
	//インスタンス変数 userid, password, email, authority の初期化
	user = new User();
	user.setUsername("ゲスト");
	user.setAuthority_id(3);
}
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
				注文完了
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="headName">
			<jsp:attribute name="value">
				注文完了
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
		<%@ include file="/common/footer.jsp"%>
</body>