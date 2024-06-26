package servlet.ProductServlet;

import java.io.IOException;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/productRegister")
public class ProductRegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// エラー文を格納する変数
		String error = "";
		String name = null;
		int price = 0;
		int stock = 0;

		// 商品へのアクセスを提供するDAO
		ProductDAO productDao = new ProductDAO();

		try {

			// フォームから送信された値を取得
			name = request.getParameter("name");
			price = Integer.parseInt(request.getParameter("price"));
			stock = Integer.parseInt(request.getParameter("stock"));

			if (name.equals("")) {
				error = "商品名を入力してください。";
				return;
			} else if (price == 0) {
				error = "金額を入力してください。";
				return;
			} else if (stock == 0) {
				error = "個数を入力してください。";
				return;
			}

			// 画像の登録機能は実装していないのでNULLを代わりに代入
			productDao.insert(name, price, stock, null);

		} catch (IllegalStateException e) {
			// エラー処理
			error = "DB接続エラーの為、一覧表示はできませんでした。";
			e.printStackTrace();
		} catch (Exception e) {
			error = "入力値が正しくありません。";
		} finally {
			if (error.length() == 0) {
				// フォワード処理
				request.getRequestDispatcher("/productList").forward(request, response);
			} else {
				// エラー文をerrorという名前でリクエストスコープに保存
				request.setAttribute("error", error);
				// フォワード処理
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}
		}

	}
}