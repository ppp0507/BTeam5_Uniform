<%@page import="dao.UserDAO"%>
<%@page import="dao.ProductDAO"%>
<%@page import="bean.Order"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- @author 猪瀬大貴,朴姻禹 -->

<%
ArrayList<Order> orderList = (ArrayList<Order>)request.getAttribute("orderList");
ProductDAO productDao = new ProductDAO();
UserDAO userDao = new UserDAO();

%>

<!DOCTYPE html>
<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath() %>/css/style.css">
<html>
<head>
<meta charset="UTF-8">
<title>注文一覧</title>
</head>

<body>

	<!-- ヘッダー:今ログインしているユーザー表示　-->
	<%@ include file="/common/header.jsp"%>
	
	 <!-- メインコンテンツ(本文) -->
    <main>
        <center>
            <div>
                <form action="<%= request.getContextPath()%>/orderList">
                    <button name= "sort" value="">新着順</button>
                    <button name= "sort" value="old">古い順</button>
                    <input type="date" name="startDate">から
                    <input type="date" name="endDate">までの注文を表示
                    <input type="submit" value="検索">
                </form>
            </div>
            
            <div>
                <table border="1" class="table-padding solid-table" style="margin: 2em;">
                    <tr>
                        <th>
                            注文番号
                        </th>
                        <th>
                            会員名
                        </th>
                        <th>
                        	メールアドレス
                        </th>
                        <th>
                            注文商品
                        </th>
                        <th>
                        	値段
                        </th>
                        <th>
                            個数
                        </th>
                        <th>
                            合計金額
                        </th>
                        <th>
                            注文日
                        </th>
                        <th>
                            入金状況
                        </th>
                        <th>
                            発送状況
                        </th>
                    </tr>

				<%
				if(orderList != null){
					for(Order order : orderList){
				
						//価格＊注文数で合計金額を算出
						int sum = productDao.getDetail(order.getProductid()).getPrice() * order.getQuantity();
						
						//発送状況の文字列を格納する変数
						String deliveryState = null;
						
						//Orderオブジェクトのdelivery_state_idの値
						int deliveryStateId = order.getDeliveryStateId();
						
						//delivery_state_idの値によって変数に格納する文字を変える
						if(deliveryStateId == 1){
							//1は発送済
							deliveryState = "発送済";
							
						}else if(deliveryStateId == 2){
							//2は発送中
							deliveryState = "発送中";
							
						}else if(deliveryStateId == 3){
							//3は未発送
							deliveryState = "未発送";
						}
						
						//入金状況の文字列を格納する変数
						String paymentState = null;
						
						//Orderオブジェクトのis_paymentの値
						boolean isPayment = order.getIsPayment();
						
						//is_payment列の値によって変数に格納する文字を変える
						if(isPayment == true){
							//trueは支払い済み
							paymentState = "支払い済み";
						}else{
							//falseは未払い
							paymentState = "未払い";
						}
				%>
                    <tr>
                        <td>
							<%= order.getId() %>
						</td>
						<td>
							<%= userDao.getUserbyId(order.getUserid()).getUsername() %>
						</td>
						<td>
							<%= userDao.getUserbyId(order.getUserid()).getEmail() %>
						</td>
						<td>
							<%= productDao.getDetail(order.getProductid()).getName() %>
						</td>  
						<td>
							<%= productDao.getDetail(order.getProductid()).getPrice()%>
						</td>
						<td>
							<%= order.getQuantity() %>
						</td>
						<td>
							<%= sum %>
						</td>     
						<td>
							<%= order.getDate() %>
						</td>
						<td>
							<%= paymentState %>
						</td>
						<td>
							<%= deliveryState %>
						</td>
                        <td>
                            <a href="<%= request.getContextPath() %>/view/Order/orderDetail.jsp">
                            詳細  
                            </a>
                        </td>
                    </tr>
                <%
					}
				}
                %>  
                </table>
            </div>
        </center>
    </main>
	
    <!-- フッター -->
    <%@ include file="/common/footer.jsp"%>
</body>
</html>