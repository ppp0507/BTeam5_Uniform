package servlet.UserServlet;

import java.io.IOException;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*
 * 神田ユニフォーム注文管理システムにログインするための処理を行う
 * サーブレットクラス
 * (担当：山里奈緒)
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";

		try {
			// 入力パラメータを取得
			String userid = (String) request.getParameter("email");
			String password = (String) request.getParameter("password");

			// UserDAOインスタンス化
			UserDAO objUserDao = new UserDAO();

			// ユーザー情報の検索
			User user = objUserDao.selectByUser(userid, password);

			// ユーザー情報のチェック
			if (user.getEmail() == null) {
				error = "入力データが間違っています。";
				return;
			}

			// ユーザー情報がある場合、セッションスコープにuserという名前で登録する
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

		} catch (IllegalStateException e) {
			error = "DB接続エラーのため、ログインできません。";

		} finally {
			if (error.equals("")) {
				request.getRequestDispatcher("/view/Common/menu.jsp").forward(request, response);

			} else {
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);

			}
		}

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request,response);
	}

}