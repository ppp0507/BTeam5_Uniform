package servlet.UserServlet;

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

@WebServlet("/userDetail")
public class UserDetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// エラー文を格納する変数
		String error = "";

		// DAOクラスのオブジェクト
		UserDAO objDao = new UserDAO();
		OrderDAO orderDao = new OrderDAO();
		User user = new User();
		Order order = new Order();

		try {

			// フォームから送信された値を取得 今回はGETリクエストで「ID」というパラメータを受け取る想定
			int user_id = Integer.parseInt(request.getParameter("Userid"));
			
			ArrayList<Order> orderList = orderDao.selectByUser(user_id);

			/*if (order.getId() == 0) {
				error = "指定したIDの注文情報は存在しません。";
			}*/
			
			user = objDao.getUserbyId(user_id);

			// リクエストスコープに格納
			request.setAttribute("userElement", user);
			request.setAttribute("orderList", orderList);

		} catch (IllegalStateException e) {
			// エラー処理
			error = "DB接続エラーの為、ユーザー詳細は表示できませんでした。";
			e.printStackTrace();
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>" + e;
			e.printStackTrace();
		} finally {
			if (error.length() == 0) {
				// フォワード処理
				request.getRequestDispatcher("/view/User/userDetail.jsp").forward(request, response);
			} else {
				// エラー文をerrorという名前でリクエストスコープに保存
				request.setAttribute("error", error);
				// フォワード処理
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}
		}

	}
}