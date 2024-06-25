<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="bean.User,bean.Order" %>
    
 <%

Order order = (Order) request.getAttribute("order");
int id = order.getId();//注文番号
int user_id = order.getUserid();//名前
int product_id = order.getProductid();//注文番号
int quantit = order.getQuantity();//個数
String date = order.getDate();//発送日
boolean is_payment = order.getIsPayment();//入金状況
int delivery_state_i = order.getDeliveryStateId();//発送状況
String comment = order.getComment();//備考

%>

<!DOCTYPE html>
<link rel="stylesheet" type="text/css"
	href="style.css">
<head>

</head>

<body>
    <!-- ヘッダー:今ログインしているユーザー表示　-->
    <header>
        <%@ include file="/common/header.jsp"%>
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
                <table>
                	<tr>
                		<td>注文番号</td>
                		<td><%= id %></td>
                			<span class="flex-indent"></span>
                	</tr>
                
                	<tr>
                		<td>顧客メール</td>
                		<td>example.com</td>
                			<span class="flex-indent"></span>
                	</tr>
                	<tr>
                		<td>名前</td>
                		<td><%= user_id %></td>
                			<span class="flex-indent"></span>
                	</tr>
                	<tr>
                		<td>注文商品</td>
                		<td><%= product_id %></td>
                			<span class="flex-indent"></span>
					</tr>
					<tr>
                		<td>値段</td>
                		<td>100</td>
                			<span class="flex-indent"></span>
                	</tr>
                	<tr>
                		<td>個数</td>
                		<td><%= quantit %></td>
                			<span class="flex-indent"></span>
                	</tr>
                	<tr>
                		<td>合計金額</td>
                		<td>2500</td>
                			<span class="flex-indent"></span>
                	</tr>
                	<tr>
                		<td>発注日</td>
               			<td><%= date %></td>
                		<span class="flex-indent"></span>
               		 </tr>
                </table>
                
                <div><div>入金状況</div></div>
                <div>
                    <div class="select-box">
                        <form>
                            <div>
                                <select>
                                    <%= is_payment %>
                                </select>
                                <input type="submit" value="更新">
                            </div>
                        </form>
                    </div></div>
                <span class="flex-indent"></span>
                
                <div><div>発送状況</div></div>
                <div>
                    <div class="select-box">
                        <form>
                            <div>
                                <select>
                                    <%= delivery_state_i %>
                                </select>
                                <input type="submit" value="更新">
                            </div>
                        </form>
                    </div>
                </div>
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