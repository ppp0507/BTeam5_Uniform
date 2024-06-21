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

@WebServlet("/insertIntoCart")
public class InsertIntoCartServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//エラーメッセージ格納
		String errormsg = "";

		//エンコードを設定
		request.setCharacterEncoding("UTF-8");

		//セッション定義
		HttpSession session = request.getSession();

		//商品のidをパラメータで取得
		int id = Integer.parseInt(request.getParameter("id"));

		try {

			Order order = new Order();

			//セッションから"order_list"のList配列を取得
			ArrayList<Order> order_list = (ArrayList<Order>) session.getAttribute("order_list");

			//セッションにlistがない場合、新規作成
			if (order_list == null) {
				order_list = new ArrayList<Order>();
				order.setProductid(id);
				order.setQuantity(1);
				order_list.add(order);
			}
			
			//セッションにlistが既にある⇒商品もすでにあるか確認
			else {
				boolean productAlreadyExist = false;

				//order_listに既に同じ商品あるか確認
				for (Order i : order_list) {
					if (i.getProductid() == id) {
						i.setQuantity(i.getQuantity() + 1);
						productAlreadyExist = true;
						break;
					}
				}
				if (!productAlreadyExist) {
					order.setProductid(id);
					order.setQuantity(1);
					order_list.add(order);
				}

			}

			session.setAttribute("order_list", order_list);

		}
		//もしDB接続エラー起きたら
		catch (IllegalStateException e) {
			errormsg = "DB接続エラーの為、処理は行えませんでした。";
		}

		finally {
			//エラーがない⇒フォワード
			if (errormsg.equals("")) {
				request.getRequestDispatcher("/view/Cart/showCart.jsp").forward(request, response);
			}

			//エラーがあったためエラーページへフォワード
			else {
				request.setAttribute("errormsg", errormsg);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}

		}

	}
}
