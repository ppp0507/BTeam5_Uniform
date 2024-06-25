package servlet.UserServlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.User;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/userList")
public class UserListServlet extends HttpServlet {

	// 書籍一覧機能の実装
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";

		try {
			// DAOクラスのインスタンス化
			UserDAO objDao = new UserDAO();

			// 検索したユーザー情報を格納するArrayListオブジェクト
			ArrayList<User> userList = objDao.selectAll();

			// リクエストスコープに格納
			request.setAttribute("userList", userList);
			
			// セッションからユーザー情報を取得
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user == null || user.getAuthority_id() != 1) {
			  error = "接続権限のないページです。";
			  return;
			}

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、一覧表示は行えませんでした。";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>" + e;
			e.printStackTrace();
		} finally {
			// フォワード処理
			if (error.equals("")) {
				// エラー無し
				// userList.jspにフォワード
				request.getRequestDispatcher("/view/User/userList.jsp").forward(request, response);

			} else {
				// エラー有り
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);

			}
		}

	}

}