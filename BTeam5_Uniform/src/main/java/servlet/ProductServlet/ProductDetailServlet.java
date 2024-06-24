package servlet.ProductServlet;

import java.io.IOException;

import bean.Product;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/productDetail")
public class ProductDetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		// エラー文を格納する変数
		String error = "";
		
		// 商品へのアクセスを提供するDAO
		ProductDAO productDao = new ProductDAO();
		
		Product product = new Product();
		
		try {
			
			// フォームから送信された値を取得 今回はGETリクエストで「ID」というパラメータを受け取る想定
			int id = Integer.parseInt(request.getParameter("id"));
			
			product = productDao.getDetail(id);
			
			if (product.getId() == 0) {
				error = "指定したIDの商品は存在しません。";
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
				request.setAttribute("product", product);
				request.getRequestDispatcher("/view/Product/productDetail.jsp").forward(request, response);
			} else {
				// エラー文をerrorという名前でリクエストスコープに保存
				request.setAttribute("error", error);
				// フォワード処理
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}
		}
		
	}
}