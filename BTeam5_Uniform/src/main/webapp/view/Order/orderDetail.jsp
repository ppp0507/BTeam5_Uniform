<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="bean.User,bean.Order,bean.Product,dao.UserDAO,dao.ProductDAO,jakarta.servlet.http.HttpSession" %>
    
<%

// DAOの作成
UserDAO userDao = new UserDAO();
ProductDAO productDao = new ProductDAO();

Order order = (Order)request.getAttribute("order");
int id = order.getId();//注文番号

// セッションに編集対象のOrderのidを登録
session.setAttribute("order_id", id);

int user_id = order.getUserid();//ユーザ一id

User user = userDao.getUserbyId(user_id);

String userName = user.getUsername();	// ユーザ一名
String mail = user.getEmail();		// メールアドレス

int product_id = order.getProductid();//商品番号

Product product = productDao.getDetail(product_id);

String productName = product.getName();	// 商品名
int price = product.getPrice();			// 値段

String date = order.getDate();//発注日
int quantity = order.getQuantity();//個数
boolean is_payment = order.getIsPayment();//入金状況
int delivery_state = order.getDeliveryStateId();//発送状況
String comment = order.getComment();//備考

%>

<!DOCTYPE html>
<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath() %>/css/style.css">
<head>

</head>

<body>
<!-- ヘッダー -->
	<jsp:include page="/common/header.jsp">
		<jsp:param name="title">
			<jsp:attribute name="value">
				注文詳細
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="headName">
			<jsp:attribute name="value">
				注文詳細
			</jsp:attribute>
		</jsp:param>
		<jsp:param name="nav">
			<jsp:attribute name="value">
					<div class="nav-padding">
					<a href="<%=request.getContextPath()%>/view/Common/menu.jsp">【メニュー】</a>
					<a href="<%=request.getContextPath()%>/orderList">【注文一覧】</a>	
					</div>
			</jsp:attribute>
		</jsp:param>
	</jsp:include>

    <!-- メインコンテンツ(本文) -->
    <main>
        <center>
	            <div class="center-flex" style="border: solid 1px gray">
	                <table>
	                <tr>
	                <td>注文番号：</td>
	                <td><%= id %></td>
	                <span class="flex-indent"></span>
	                </tr>
	                <tr>
	                <td>顧客メール：</td>
	                <td><%= mail %></td>
	                <span class="flex-indent"></span>
	                </tr>
	                <tr>
	                <td>名前：</td>
	                <td><%= userName %></td>
	                <span class="flex-indent"></span>
	                </tr>
	                <tr>
	                <td>注文商品：</td>
	                <td><%= productName %></td>
	                <span class="flex-indent"></span>
					</tr>
					<tr>
	                <td>値段：</td>
	                <td><%= price %></td>
	                <span class="flex-indent"></span>
	                </tr>
	                <tr>
	                <td>個数：</td>
	                <td><%= quantity %></td>
	                <span class="flex-indent"></span>
	                </tr>
	                <tr>
	                <td>合計金額：</td>
	                <td><%= price * quantity %></td>
	                <span class="flex-indent"></span>
	                </tr>
	                <tr>
	                <td>発注日：</td>
	                <td><%= date %></td>
	                <span class="flex-indent"></span>
	                </tr>
	                <tr>
	                <td>入金状況：</td>
	                 <form action="<%=request.getContextPath()%>/orderEdit" method="get">
						<td>
	                    <div class="select-box">
	                            <select name="is_payment">
	                                <option value="0" <% if(is_payment == false) { %>selected<% }%>>入金待ち</option>
	                                <option value="1" <% if(is_payment == true) { %>selected<% }%>>入金済み</option>
	                            </select>
	                            <input type="submit" value="更新">
	                            </td>
	                    </div>
	                <span class="flex-indent"></span>
	                </tr>
	                <tr>
	                <td>発送状況</td>
	                <td>
	                    <div class="select-box">
	                            <select name="delivery_state">
	                                <option value="1" <% if(delivery_state == 1) { %>selected<% }%>>発送済み</option>
	                                <option value="2" <% if(delivery_state == 2) { %>selected<% }%>>発送準備中</option>
	                                <option value="3" <% if(delivery_state == 3) { %>selected<% }%>>未発送</option>
	                            </select>
	                            <input type="submit" value="更新">
	                            </td>
	                    </div>
	                 </form>
	                <span class="flex-indent"></span>
	                </tr>
					<tr>
	                <td>備考欄</td>
	                <td><%= comment %></td>
	                <span class="flex-indent"></span>
	                </tr>
	                </table>
	            </div>
        </center>
    </main>

    		<!-- フッター -->
		<%@ include file="/common/footer.jsp"%>
</body>
</html>