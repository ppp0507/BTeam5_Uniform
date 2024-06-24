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
	
	// ユーザ一名を表示する部分のスタイルを指定する変数
	String userPlateName = "";
	
	if (user.getAuthority_id() == 1){
		// 管理者なら
		userPlateName = "admin-user";
	} else if (user.getAuthority_id() == 2){
		// 一般ユーザーなら
		userPlateName = "common-user";
	} else if (user.getAuthority_id() == 3){
		// ゲストアカウントなら
		userPlateName = "guest-user";
	}

%>


<%-- ヘッダー:今ログインしているユーザー表示　--%>
<header>
    <h3>
    	<%-- ページ上部に表示する内容 --%>
    	${ param.headName }
    </h3>
    <div>
	    <p class="<%= userPlateName %>">
	    	<%= user.getUsername() %>
	    </p>
    </div>
</header>

<!-- メニュー移動 -->
<nav>
    <div style="padding: 0.5em;">
    	${ param.nav }
    	<% if (user.getAuthority_id() != 3) { %>
    	<div class="nav-padding" style="float: right;"><a href="<%= request.getContextPath() %>/logout">ログアウト</a></div>
    	<% } %>
    	<%--
	        <a href>メニュー</a>
	        <a href>商品一覧</a>
	        <span>これはnavです</span>
        --%>
    </div>
</nav>