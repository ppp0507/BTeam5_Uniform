package servlet.ProductServlet;

import java.io.IOException;

import bean.Product;
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
			//パラメータのnameを考え中(仮：product_id)
			//productList.jspで商品番号パラメータを取得
			int id = Integer.parseInt(request.getParameter("product_id"));

			//ProductDAOオブジェクトを作成
			ProductDAO productDao = new ProductDAO();

			//取得した商品番号の商品情報を取得
			Product product = productDao.getDetail(id);
			
			//商品情報をリクエストスコープに登録
			request.setAttribute("product", product);
			
			//productEdit.jspに処理を遷移
			request.getRequestDispatcher("/view/Product/productEdit.jsp").forward(request, response);
			
		}catch(IllegalStateException e) {
			//DB接続エラーが発生した場合
			error = "a";
			cmd = "b";
			
		}finally {
			//エラーが発生した場合
			if(error != null) {
				//errorをリクエストスコープに登録
				request.setAttribute("error", error);
			}
			
			if(cmd != null) {
				//cmdをリクエストスコープに登録
				request.setAttribute("cmd", cmd);
			}
			
			if(error != null) {
				//error.jspに処理を遷移
				request.getRequestDispatcher("view/Common/error.jsp").forward(request, response);
			}
			
		}

	}
}