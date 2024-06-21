package servlet.CartServlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/deleteCart")
public class DeleteCartServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//エンコードを設定
		request.setCharacterEncoding("UTF-8");

		//セッション定義
		HttpSession session = request.getSession();

		//商品のindexをパラメータで取得
		int i = Integer.parseInt(request.getParameter("i"));
		
		try {
			//セッションから"order_list"のList配列を取得
			ArrayList<Order> order_list = (ArrayList<Order>) session.getAttribute("order_list");
			order_list.remove(i);
		}
		catch(Exception e) {}
		finally {
			
			request.getRequestDispatcher("/view/Cart/showCart.jsp").forward(request, response);
		}
	}
}
