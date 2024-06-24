package servlet.CartServlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Order;
import bean.User;
import dao.OrderDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author 朴姻禹
 */
@WebServlet("/buyCart")
public class BuyCartServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		// エラー文を格納する変数
		String error = "";

		//OrderDAO
		OrderDAO orderDao = new OrderDAO();

		//UserDAO
		UserDAO userDao = new UserDAO();

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
			}

			//ゲスト購入なら会員登録してuser_id貰う
			else {
				System.out.println("ゲスト");
				name = request.getParameter("name");
				address = request.getParameter("address");
				email = request.getParameter("email");

				userDao.insert(name, "", email, address);
				user_id = userDao.selectByUser(email, "").getUserid();
			}

			//カートの商品情報取得
			ArrayList<Order> order_list = (ArrayList<Order>) session.getAttribute("order_list");

			//order_listを巡回し、一つづつDBへ転送します。
			for (Order i : order_list) {
				//DBへ注文転送
				orderDao.insert(user_id, i.getProductid(), i.getQuantity(), comment);
			}

			//セッションの注文リストを初期化(注文が終わったため)
			session.setAttribute("order_list", null);

		} catch (Exception e) {

		} finally {

			if (error.equals("")) {
				// フォワード処理
				response.sendRedirect(request.getContextPath() + "/view/Common/menu.jsp");
			} else {
				// エラー文をerrorという名前でリクエストスコープに保存
				request.setAttribute("error", error);
				// フォワード処理
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}
		}
	}

}
