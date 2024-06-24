package servlet.ProductServlet;

import java.io.IOException;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author 猪瀬大貴
 */
@WebServlet("/productEdit")
public class ProductEditServlet extends HttpServlet {

	public void doGet(HttpServletRequest request ,HttpServletResponse response) 
			throws ServletException ,IOException{

		//エラーメッセージを格納する変数を宣言
		String error = null;

		//遷移先を決める変数cmdを宣言
		String cmd = null;

		try {
			//商品ID取得
			//productEdit.jspの商品IDパラメータを取得
			int id = Integer.parseInt(request.getParameter("id"));

			//商品名を取得
			//productEdit.jspの商品名パラメータを取得
			String name = request.getParameter("name");

			//商品名が空文字の場合
			if(name.equals("")) {

				error = "商品名を入力してください。";
				return;
			}

			//価格を取得
			//productEdit.jspの価格パラメータを取得
			String price = request.getParameter("price");

			//価格が空欄の場合
			if(price.equals("")) {
				error = "価格を入力してください。";
				return;
			}

			//priceをint型に変換
			int price2 = Integer.parseInt(price);

			//価格が負の数の場合
			if(price2 < 0) {
				error = "価格は0円以上の金額を入力してください。";
				return;
			}

			//在庫数を取得
			//productEdit.jspの在庫数パラメータを取得
			String stock = request.getParameter("stock");

			//在庫数が空欄の場合
			if(stock.equals("")) {
				error = "在庫数を入力してください。";
				return;
			}

			//stockをint型に変換
			int stock2 = Integer.parseInt(stock);

			//在庫数が負の数の場合
			if(stock2 < 0) {
				error = "在庫数は0以上の数字を入力してください。";
				return;
			}

			//ProductDAOオブジェクトを作成
			ProductDAO productDao = new ProductDAO();

			//ProductDAOのメソッドを使用してDBの情報を更新(写真URLはnull)
			productDao.editDetail(id, name, price2, stock2, null);

			//productList.jspに処理を遷移
			request.getRequestDispatcher("/view/Product/productList.jsp").forward(request, response);

		}catch(IllegalStateException e) {
			//DB接続エラーが発生した場合
			error = "DB接続エラーのため、商品を編集できませんでした。";

		}catch(NumberFormatException e) {
			//price、stockに文字列を入力した場合
			error = "数字を入力してください。";

		}finally {
			//エラーが発生した場合
			if(error != null) {
				//errorをリクエストスコープに登録
				request.setAttribute("error", error);

				//error.jspに処理を遷移
				request.getRequestDispatcher("view/Common/error.jsp").forward(request, response);
			}

		}

	}
}