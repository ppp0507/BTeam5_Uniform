package servlet.CartServlet;

import java.io.IOException;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author 朴姻禹
 * 
 * showCart.jspに機能入れすぎちゃって現在showCartServletなくてもカート見れます。
 */
@WebServlet("/showCart")
public class ShowCartServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//エラーメッセージ格納
		String errormsg = "";

		//エンコードを設定
		request.setCharacterEncoding("UTF-8");

		//セッション定義
		HttpSession session = request.getSession();
		
		//ProductDAO
		ProductDAO objProductDao = new ProductDAO();
		
		//カートに入っている商品のリスト

	}
}
