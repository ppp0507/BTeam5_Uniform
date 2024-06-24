package servlet.UserServlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 会員登録機能の処理を行うサーブレットクラス
 * (担当：山里奈緒)
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";

		try {
			//画面からの入力情報を受け取るためのエンコード設定
			request.setCharacterEncoding("UTF-8");

			// 入力パラメータ取得
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String address = request.getParameter("address");

			// UserDAOインスタンス化
			UserDAO objUserDao = new UserDAO();

			// 取得したパラメータのエラーチェック
			// 全データの空白チェック
			if (username.equals("")) {
				error = "氏名が未入力のため、会員登録が行えませんでした。";
				return;
			}

			if (email.equals("")) {
				error = "メールアドレスが未入力のため、会員登録が行えませんでした。";
				return;
			}

			if (password.equals("")) {
				error = "パスワードが未入力のため、会員登録が行えませんでした。";
				return;
			}

			if (address.equals("")) {
				error = "住所が未入力のため、会員登録が行えませんでした。";
				return;
			}

			// 入力値チェック
			// email、passwordに全角半角仮名文字が含まれていないか
			Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
			Pattern passwordPattern = Pattern.compile("^[A-Za-z0-9+_.-]");
			Matcher matcher = pattern.matcher(email);
			Matcher passwordMatcher = passwordPattern.matcher(password);
			if (matcher.matches()) {
			} else {
				error = "メールアドレスに不正な文字が含まれているため、会員登録が行えませんでした。";
			}

			if (passwordMatcher.matches()) {
				error = "パスワードに不正な文字が含まれているため、会員登録が行えませんでした。";
				return;
			}

			// 取得したパラメータをデータベースに登録
			objUserDao.insert(username, password, email, address);

		} catch (IllegalStateException e) {
			error = "DB接続エラーのため、会員登録は行えませんでした。";

		} finally {
			// フォワード処理
			if (error.equals("")) {
				// エラー無し
				// insert.jspにフォワード
				request.getRequestDispatcher("/view/Common/menu.jsp").forward(request, response);

			} else {
				// エラー有り
				request.setAttribute("error", error);

				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);

			}
		}

	}

}