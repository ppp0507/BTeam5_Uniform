package servlet.ProductServlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Product;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/productList")
public class ProductListServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String error = "";
		String cmd = "";

		try{

			// ① ProductDAOをインスタンス化する
			ProductDAO objDao = new ProductDAO();

			// ②関連メソッドを呼び出し、戻り値としてProductオブジェクトのリストを取得する
			ArrayList<Product>  productList = objDao.selectAll();

			// ③②で取得したListをリクエストスコープに"product_list"という名前で格納する
			request.setAttribute("product_list", productList);
			
		}catch (IllegalStateException e) {
			error = "DB接続エラーの為、登録できませんでした。";
			cmd = "menu";

		}finally{
			if(error.equals("")) {
				// productList.jspにフォワード
				request.getRequestDispatcher("/view/Product/productList.jsp").forward(request, response);
				
			} else {
				//リクエストスコープへデータの登録
			request.setAttribute("error", error);
			request.setAttribute("cmd", cmd);

			//エラーが有る場合はerror.jspにフォワードする
			request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}

		}
	}
}