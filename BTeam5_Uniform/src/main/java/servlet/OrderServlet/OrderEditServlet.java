// 担当: 屋比久
package servlet.OrderServlet;

import java.io.IOException;

import bean.Order;
import bean.Product;
import bean.User;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.SendMail;

@WebServlet("/orderEdit")
public class OrderEditServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// エラー処理用
		String error = "";

		OrderDAO orderDao = new OrderDAO();
		UserDAO userDao = new UserDAO();
		ProductDAO productDao = new ProductDAO();
		
		User user = new User();
		Product product = new Product();
		Order order = new Order();
		
		int id = 0;
		
		//Sendmailインスタンス生成
		SendMail sendMail = new SendMail();
		
		String mailText = "";

		try{

			//パラメータの取得
			HttpSession session = request.getSession();
			id = (int)session.getAttribute("order_id");
			int deliveryState = Integer.parseInt(request.getParameter("delivery_state"));
			int isPaymentNum = Integer.parseInt(request.getParameter("is_payment"));
			
			// isPaymentNumを2値に変換
			boolean isPayment = isPaymentNum == 1?true:false;
			
			//メール本文用の変数
			String mailPayment = isPayment ? "入金済" : "入金待ち";
			String mailDelivery = "未発送";
			if(deliveryState == 1) {
				mailDelivery = "発送済";
			}
			else if(deliveryState == 2) {
				mailDelivery = "発送中";
			}
			
			order = orderDao.getDetail(id);
			user = userDao.getUserbyId(order.getUserid());
			product = productDao.getDetail(order.getProductid());
			
			//メソッド呼び出し
			orderDao.editDetail(id, deliveryState, isPayment);
			
			mailText = user.getUsername() + "様、この度はご購入ありがとうございました。\n"
					+ "ご注文の入金状況、または、発送状況を以下のように更新しました。\n\n"
					+ "\n商品名: "+ product.getName() + " 数量:" + order.getQuantity() + " 価格: " + product.getPrice() + "円(1枚あたり)\n"
							+ "入金状況:" + mailPayment + "\n発送状況:" + mailDelivery;
			
			sendMail.setMailText(mailText);
			sendMail.setMailSubject("神田ユニフォーム : 注文状況更新のお知らせ");
			sendMail.setReceiverAddress(user.getEmail());
			
			//メール送信
			sendMail.sendMail();

		}catch (IllegalStateException e) {
			error ="DB接続エラーの為、登録できませんでした。";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>" + e;
			e.printStackTrace();
		}finally{
			if (error.equals("")) {
				// フォワード処理

				request.getRequestDispatcher("/orderList").forward(request, response);
			} else {
				// エラー文をerrorという名前でリクエストスコープに保存
				request.setAttribute("error", error);
				// フォワード処理
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}
		}
	}
}