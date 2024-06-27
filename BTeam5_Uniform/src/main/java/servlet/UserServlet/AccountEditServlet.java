// 作成者: 屋比久

package servlet.UserServlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bean.User;
import dao.OrderDAO;
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
		
		// フォワード先を指定する変数
		String toForward = "/view/Common/error.jsp";
		
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
			
			// エラー処理
			if (request.getParameter("cmd") == null) {
				error = "無効なアクセスです";
				return;
			}

			// 変更処理用のUserを作成
			User changeUser = userDao.getUserbyId(id);

			if (request.getParameter("cmd").equals("update")) {
				// アカウント更新処理
				
				// 入力値チェック
				
				// nameに何も入力されていないなら
				if (name.length() == 0) {
					error = "名前を空にする事はできません。";
					return;
				}

				// addressに何も入力されていないなら
				if (address.length() == 0) {
					error = "住所を空にする事はできません。";
					return;
				}
				
				// mailの形式検査用
				Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
				Matcher matcher = pattern.matcher(mail);

				// mailの形式が正くなければ
				if (matcher.matches() == false) {
					error = "メールアドレスの形式が正しくありません。";
					return;
				}
				
				// エラー処理
				if (name == null || mail == null || address == null) {
					error = "無効なアクセスです";
					return;
				}
				
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
				
				session.setAttribute("user", userDao.getUserbyId(user.getUserid()));
				
				// リクエストスコープに変更前後のUserオブジェクトを保存
				request.setAttribute("before", beforeUser);
				request.setAttribute("after", afterUser);
				
				toForward = "/view/User/accountEditCompleted.jsp";
				
			} else if (request.getParameter("cmd").equals("delete")) {
				// アカウント削除処理
				
				// Foreign key制約があるので先にOrderを削除する為にDAOを生成
				OrderDAO orderDao = new OrderDAO();
				
				// 削除処理実行
				orderDao.deleteOrdersByUser_id(changeUser.getUserid());
				userDao.delete(changeUser.getEmail());
				
				// セッション情報の削除
				session.setAttribute("user", null);
				
				toForward = "/view/User/login.jsp";
				
			}
			
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
				request.getRequestDispatcher(toForward).forward(request, response);
			} else {
				// エラー文をerrorという名前でリクエストスコープに保存
				request.setAttribute("error", error);
				// フォワード処理
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}
		}
		
	}
}