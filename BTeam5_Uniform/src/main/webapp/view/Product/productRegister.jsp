<%-- 作成者: 屋比久 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bean.User" %>

<%

// セッションからユーザー情報を取得
User user = (User)session.getAttribute("user");

// ユーザーがNULLならゲストアカウントにする
if (user == null){
	//インスタンス変数 userid, password, email, authority の初期化
	user = new User();
	user.setUsername("ゲスト");
	user.setAuthority_id(3);
}
	
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="<%= request.getContextPath() %>/css/style.css" rel="stylesheet">
		<title>商品登録</title>
	</head>

	<body>
	
		<%-- 権限による分岐 --%>
		<% if (user.getAuthority_id() != 1){
			// 一般,ゲストユーザーのアクセス拒否
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		
		} else { %>
		
		<jsp:include page="/common/header.jsp">
			<jsp:param name="headName">
				<jsp:attribute name="value">
					商品登録
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
				<form action="<%= request.getContextPath() %>/productRegister">
					<div class="center-flex" style="border: solid 1px gray">
	
						<div><div>商品名</div></div>
						<div><input type="text" name="name"></div>
						<span class="flex-indent"></span>
	
						<div><div>価格</div></div>
						<div><input type="text" name="price"></div>
						<span class="flex-indent"></span>
	
						<div><div>在庫数</div></div>
						<div><input type="text" name="stock"></div>
						<span class="flex-indent"></span>
	
						<div><div>商品画像</div></div>
						<div style="width: 0;"><input type="file" name="image"></div>
						<span class="flex-indent"></span>
	
					</div>
					<div class="flex-bottom">
						<input type="submit" name="regist" value="登録">
					</div>
				</form>
			</center>
		</main>
	    
		<jsp:include page="/common/footer.jsp" />

		<% } %>
	</body>
</html>