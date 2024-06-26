package servlet.CartServlet;

import java.io.IOException;
import java.util.ArrayList;

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

/**
 * @author 朴姻禹
 */
@WebServlet("/buyCart")
public class BuyCartServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//エンコードを設定
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// エラー文を格納する変数
		String error = "";

		//OrderDAO
		OrderDAO orderDao = new OrderDAO();

		//UserDAO
		UserDAO userDao = new UserDAO();

		//ProductDAO
		ProductDAO productDao = new ProductDAO();

		//Sendmailインスタンス生成
		SendMail sendMail = new SendMail();

		String mailText = "";

		HttpSession session = request.getSession();

		try {
			//注文者情報の変数
			String name;
			int user_id = 0;
			String address;
			String email;
			String comment = request.getParameter("comment");

			//ログインユーザーなら
			User user = (User) session.getAttribute("user");

			if (user != null) {
				user_id = user.getUserid();
				name = user.getUsername();
				email = user.getEmail();
				address = user.getAddress();
			}

			//ゲスト購入なら会員登録してuser_id貰う
			else {

				name = request.getParameter("name");
				address = request.getParameter("address");
				email = request.getParameter("email");
				//情報が入力されていない場合
				if (name.equals("") || email.equals("") || address.equals("")) {
					error = "購入のための情報が正しく入力されていません。";
					return;
				}

				//メールの検査
				User sameUser = userDao.selectByUser(email);
			
				//userテーブルに既にあるメールかを検査
				if (sameUser.getEmail() != null) {
					//1-1.既にある・同じメールのアカウントが一般ユーザ
					if (sameUser.getAuthority_id() == 2) {
						error = "既に会員登録されているEmailです。\nログインしてから購入をお願いします。";
						return;
					}
					//1-2.既にある・同じメールのアカウントがゲスト
					else if (sameUser.getAuthority_id() == 3) {
						user_id = sameUser.getUserid();
						userDao.update(sameUser,name,"",email,address,3);
					}
				}
				//2.登録されてないメール(登録してもOK)
				else {
					//DBにゲストユーザー登録(権限は3, ゲスト)
					userDao.insert(name, "", email, address);
					user_id = userDao.selectByUser(email, "").getUserid();
				}

			}

			//カートの商品情報取得
			ArrayList<Order> order_list = (ArrayList<Order>) session.getAttribute("order_list");

			//カートが空っぽの場合
			if (order_list == null || order_list.size() == 0) {
				error = "カートに商品がないため、注文が出来ませんでした。";
				return;
			}

			//order_listを巡回し、一つづつDBへ転送します。
			for (Order i : order_list) {
				Product product = productDao.getDetail(i.getProductid());
				//DBへ注文転送
				orderDao.insert(user_id, i.getProductid(), i.getQuantity(), comment);

				mailText += "\n商品名: " + product.getName() + " \n数量:" + i.getQuantity() + " \n価格: " + product.getPrice()
						+ "円(1枚あたり)";
			}

			mailText = name + "様、この度はご購入ありがとうございました。\n以下、ご注文の詳細となります。\n"
					+ mailText + "\n\n配送先:" + address;
			sendMail.setMailText(mailText);
			sendMail.setMailSubject("神田ユニフォーム注文受付のご案内");
			sendMail.setReceiverAddress(email);

			//orderConfiremd.jspへ送るユーザーの名前
			request.setAttribute("order_name", name);

			//メール送信
			sendMail.sendMail();

			//セッションの注文リストを初期化(注文が終わったため)
			session.setAttribute("order_list", null);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、購入は出来ません。";
		} finally {

			if (error.equals("")) {
				// フォワード処理
				request.getRequestDispatcher("/view/Order/orderConfirmed.jsp").forward(request, response);
			} else {
				// エラー文をerrorという名前でリクエストスコープに保存
				request.setAttribute("error", error);
				// フォワード処理
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}
		}
	}

}
