<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bean.User,bean.Product" %>

<%

// セッションからユーザー情報を取得
User user = (User)session.getAttribute("user");

// ユーザーがNULLならゲストアカウントにする
if (user == null){
//インスタンス変数 username, authority の初期化
user = new User();
user.setUsername("ゲスト");
user.setAuthority_id(3);
}

// サーブレットから商品情報を取得
Product product = (Product)request.getAttribute("product");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<%= request.getContextPath() %>/css/style.css" rel="stylesheet">
<title>Insert title here</title>
</head>

<body>
<jsp:include page="/common/header.jsp">
<jsp:param name="headName">
<jsp:attribute name="value">
商品詳細
</jsp:attribute>
</jsp:param>
<jsp:param name="nav">
<jsp:attribute name="value">
<div class="nav-padding">
<a href="<%= request.getContextPath() %>/view/Common/menu.jsp">【メニュー】</a>
<a href="<%= request.getContextPath() %>/productList">【商品ページ】</a>
<a href="<%=request.getContextPath()%>/view/Cart/showCart.jsp">【カート確認】</a>
</div>
</jsp:attribute>
</jsp:param>
</jsp:include>

    <!-- メインコンテンツ(本文) -->
    <main>
        <div style="display: flex;">
            <div class="explanation" style="width:50%;padding: 1em 2em;">
                <h2><%= product.getName() %></h2>
                <hr>
            </div>
            <div class="add-cart" style="width:50%">
                <div style="width: 50%; margin: auto; text-align: center;padding: 1em 2em;border: solid 1px lightgray;border-radius: 5px;">
                    <h2>
                        <%= product.getPrice() %>円
                    </h2>
                    <hr>
                    <p>
                    <% if (user.getAuthority_id() == 3) { %>
                    
                    <%-- ゲストアカウントなら --%>
                        <label style="font-size: 0.8em;color: gray;">配送先</label>
                        <p>注文時に指定</p>
                        
                    <% } else { %>
                   
                    <%-- そうでないなら --%>
                        <label style="font-size: 0.8em;color: gray;">配送先</label>
                        <p><%= user.getAddress() %></p>
                    
                    <% } %>
                    </p>
                    <hr>
                    
                    <% if (product.getStock() != 0) { %>
                    
                    <%-- 在庫が存在するなら --%>
                    <a class="blue-button" href="<%= request.getContextPath() %>/insertIntoCart?id=<%= product.getId() %>">カートに入れる</a>
                    <% } else { %>
                    
                    <%-- 在庫が存在しないなら --%>
                    <a class="blue-button" href>在庫がありません</a>
                    <% } %>
                </div>
            </div>
        </div>
    </main>

<jsp:include page="/common/footer.jsp" />
</body>
</html>