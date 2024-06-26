package servlet.OrderServlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Order;
import bean.User;
import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author 猪瀬大貴
 */
@WebServlet("/orderHistory")
public class OrderHistoryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response) 
			throws ServletException ,IOException{

		//エラーメッセージを格納する変数を宣言
		String error = null;

		//遷移先を決める変数cmdを宣言
		String cmd = null;
		
		// セッションからユーザー情報を取得
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		//userID 取得
		int id = user.getUserid();

		try {
			//OrderDAOオブジェクトを作成
			OrderDAO orderDao = new OrderDAO();

			//DAOクラスのメソッドを使い全注文リストを取得
			ArrayList<Order> orderList = orderDao.selectByUser(id);

			//全注文リストをリクエストスコープに登録
			request.setAttribute("orderList", orderList);

			//orderHistory.jspに処理を遷移
			request.getRequestDispatcher("/view/Order/orderHistory.jsp").forward(request, response);

		}catch(IllegalStateException e) {
			//DB接続エラーが発生した場合
			error = "DB接続エラーのため、購入履歴画面を表示できませんでした。";
			cmd = "b";

		}finally {
			//エラーが発生した場合
			if(error != null) {
				//errorをリクエストスコープに登録
				request.setAttribute("error", error);
			}

			if(cmd != null) {
				//cmdをリクエストスコープに登録
				request.setAttribute("cmd", cmd);
			}

			if(error != null) {
				//error.jspに処理を遷移
				request.getRequestDispatcher("view/Common/error.jsp").forward(request, response);
			}
		}
	}
}