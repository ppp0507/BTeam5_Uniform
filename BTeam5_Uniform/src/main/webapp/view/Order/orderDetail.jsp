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
    <!-- ヘッダー:今ログインしているユーザー表示　-->
    <header>
        <div><p class="admin-user">ログイン:管理ユーザー</p></div>
    </header>

    <!-- メニュー移動 -->
    <nav>
        <div style="padding: 0.5em;">
            <a href="<%= request.getContextPath() %>/view/Common/menu.jsp">【メニュー】</a>
							<a href="<%= request.getContextPath() %>/productList">【商品一覧】</a>
        </div>
    </nav>

    <!-- メインコンテンツ(本文) -->
    <main>
        <center>
        	
	            <div class="center-flex" style="border: solid 1px gray">
	                
	                <div><div>注文番号</div></div>
	                <div><div><%= id %></div></div>
	                <span class="flex-indent"></span>
	                
	                <div><div>顧客メール</div></div>
	                <div><div><%= mail %></div></div>
	                <span class="flex-indent"></span>
	                
	                <div><div>名前</div></div>
	                <div><div><%= userName %></div></div>
	                <span class="flex-indent"></span>
	                
	                <div><div>注文商品</div></div>
	                <div><div><%= productName %></div></div>
	                <span class="flex-indent"></span>
	
	                <div><div>値段</div></div>
	                <div><div><%= price %></div></div>
	                <span class="flex-indent"></span>
	                
	                <div><div>個数</div></div>
	                <div><div><%= quantity %></div></div>
	                <span class="flex-indent"></span>
	                
	                <div><div>合計金額</div></div>
	                <div><div><%= price * quantity %></div></div>
	                <span class="flex-indent"></span>
	                
	                <div><div>発注日</div></div>
	                <div><div><%= date %></div></div>
	                <span class="flex-indent"></span>
	                
	                
	                <div><div>入金状況</div></div>
	                 <form action="<%=request.getContextPath()%>/orderEdit" method="get">
	                <div>
	               
	                    <div class="select-box">
	                        <div>
	                            <select name="is_payment">
	                                <option value="0" <% if(is_payment == false) { %>selected<% }%>>入金待ち</option>
	                                <option value="1" <% if(is_payment == true) { %>selected<% }%>>入金済み</option>
	                            </select>
	                            <input type="submit" value="更新">
	                        </div>
	                    </div>
	                </div>
	                <span class="flex-indent"></span>
	                
	                <div><div>入金状況</div></div>
	                <div>
	                    <div class="select-box">
	                        <div>
	                            <select name="delivery_state">
	                                <option value="1" <% if(delivery_state == 1) { %>selected<% }%>>発送済み</option>
	                                <option value="2" <% if(delivery_state == 2) { %>selected<% }%>>発送準備中</option>
	                                <option value="3" <% if(delivery_state == 3) { %>selected<% }%>>未発送</option>
	                            </select>
	                            <input type="submit" value="更新">
	                        </div>
	                    </div>
	                </div>
	                 </form>
	                <span class="flex-indent"></span>
	
	                <div><div>備考欄</div></div>
	                <div><div><%= comment %></div></div>
	                <span class="flex-indent"></span>
	                
	            </div>
           
        </center>
    </main>

    		<!-- フッター -->
		<%@ include file="/common/footer.jsp"%>
</body>
</html>