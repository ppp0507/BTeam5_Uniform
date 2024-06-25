package servlet.UserServlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*
* ログアウト機能の処理を行うサーブレットクラス
* (担当者：山里奈緒)
*/

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// セッション情報をクリアする
		HttpSession session = request.getSession();
		
		if (session != null) {
			session.invalidate();
		}
		
		// login.jspにフォワードする
		request.getRequestDispatcher("/view/User/login.jsp").forward(request, response);
		
	}
}