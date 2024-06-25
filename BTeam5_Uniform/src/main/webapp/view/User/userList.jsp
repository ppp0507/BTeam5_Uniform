<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,bean.User"%>

<%
ArrayList<User> userList = (ArrayList<User>) request.getAttribute("userList");
//セッションからユーザー情報を取得
User user = (User)session.getAttribute("user");
%>

<html>
<head>
<title>ユーザー一覧</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">

	<!-- ヘッダー -->
	<jsp:include page="/common/header.jsp">
		<jsp:param name="title">
			<jsp:attribute name="value">
				タイトル
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="headName">
			<jsp:attribute name="value">
				ユーザー一覧
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

		<!-- メインコンテンツ(ユーザー一覧) -->
		<main>
			<center>
				<div>
					<table border="1" class="table-padding solid-table"
						style="margin: 2em;">
						<tr>
							<th>ユーザーID</th>
							<th>メールアドレス</th>
							<th>ユーザー名</th>
							<th>住所</th>
							<th></th>
						</tr>

						<tbody>
							<%
							if (userList != null) {
								for (User userElement : userList) {
							%>
							<tr>
								<td><%=userElement.getUserid()%></td>
								<td><%=userElement.getEmail()%></td>
								<td><%=userElement.getUsername()%></td>
								<td><%=userElement.getAddress()%></td>
								<td><a
									href="<%=request.getContextPath()%>/userDetail?Userid=<%=userElement.getUserid()%>&cmd=detail">詳細</a>
								</td>

							</tr>
							<%
							}
							}
							%>
						</tbody>

					</table>
				</div>
			</center>
		</main>

		<!-- フッター -->
		<%@ include file="/common/footer.jsp"%>
	</div>
</body>
</html>