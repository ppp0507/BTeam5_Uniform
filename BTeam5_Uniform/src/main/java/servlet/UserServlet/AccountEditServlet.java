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

@WebServlet("/accountEdit")
public class AccountEditServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		// エラー文を格納する変数
		String error = "";
		
		// ユーザー情報へのアクセスを提供するDAO
		UserDAO userDao = new UserDAO();
		
		// セッションからユーザー情報を取得
		HttpSession session = request.getSession();
		
		// 変更前のユーザー情報を格納するオブジェクト
		User beforeUser = null;
		
		// 変更後のユーザー情報を格納するオブジェクト
		User afterUser = null;
		
		User user = (User)session.getAttribute("user");
		
		try {
			// 編集対象のユーザーのidを管理する変数
			int id;
			String password;
			int authority_id;
			
			// パラメータから各種更新内容を取得
			String name = request.getParameter("name");
			String mail = request.getParameter("mail");
			String address = request.getParameter("address");
			
			if (user.getAuthority_id() == 1) {
				// 管理者なら
				// フォームから送信された値を取得 今回はGETリクエストで「ID」というパラメータを受け取る想定
				id = Integer.parseInt(request.getParameter("id"));
				
				password = userDao.getUserbyId(id).getPassword();
				authority_id = userDao.getUserbyId(id).getAuthority_id();
			} else if (user.getAuthority_id() == 2) {
				// 一般ユーザーなら
				id = user.getUserid();
				
				password = user.getPassword();
				authority_id = user.getAuthority_id();
			} else if (user.getAuthority_id() == 3) {
				// ゲストアカウントなら
				error = "ゲストアカウントでユーザ一編集機能は利用できません。";
				return;
			} else {
				// 例外
				error = "不明な権限です。";
				return;
			}

			// 変更処理用のUserを作成
			User changeUser = userDao.getUserbyId(id);
			
			// 変更前のユーザー情報を格納
			beforeUser = new User();
			beforeUser.setUsername(changeUser.getUsername());
			beforeUser.setEmail(changeUser.getEmail());
			beforeUser.setAddress(changeUser.getAddress());
			
			// 変更後のユーザー情報を格納
			afterUser = new User();
			afterUser.setUsername(name);
			afterUser.setEmail(mail);
			afterUser.setAddress(address);
			
			// 更新処理実行
			userDao.update(changeUser, name, password, mail, address, authority_id);
			
			session.setAttribute("user", userDao.getUserbyId(id));
			
		} catch (IllegalStateException e) {
			// エラー処理
			error = "DB接続エラーの為、一覧表示はできませんでした。";
			e.printStackTrace();
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>" + e;
			e.printStackTrace();
		} finally {
			if (error.length() == 0) {
				// フォワード処理
				request.setAttribute("before", beforeUser);
				request.setAttribute("after", afterUser);
				request.getRequestDispatcher("/view/User/accountEditCompleted.jsp").forward(request, response);
			} else {
				// エラー文をerrorという名前でリクエストスコープに保存
				request.setAttribute("error", error);
				// フォワード処理
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}
		}
		
	}
}