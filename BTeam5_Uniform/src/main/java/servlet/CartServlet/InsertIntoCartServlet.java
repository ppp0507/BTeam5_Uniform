package servlet.CartServlet;

import java.io.IOException;
import java.util.ArrayList;

import bean.Order;
import bean.Product;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 * 起動url = "/insertIntoCart?id=商品番号"
 *  商品番号(id)を用いて商品をカートに追加します
 *  
 *  1.DBに存在しない商品は追加されません。
 *  2.カートに既に存在する商品をまた追加する場合、数量を+1します。
 *  3.DBに登録されている在庫が、追加しようとする数量より少ないなら追加されません。
 * @author 朴姻禹
 */

@WebServlet("/insertIntoCart")
public class InsertIntoCartServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//エラーメッセージ格納
		String error = "";

		//エンコードを設定
		request.setCharacterEncoding("UTF-8");

		//ProductDAO インスタンス化
		ProductDAO productDao = new ProductDAO();

		//セッション定義
		HttpSession session = request.getSession();

		//商品のidをパラメータで取得
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			//DBから一致するIDの商品情報を取得
			Product product = productDao.getDetail(id);
			
			//DBから商品が存在するか確認
			if(product.getId() == 0) {
				error = "存在しない商品のため、追加できません。";
				return;
			}

			Order order = new Order();

			//セッションから"order_list"のList配列を取得
			ArrayList<Order> order_list = (ArrayList<Order>) session.getAttribute("order_list");

			//セッションにlistがない場合、新規作成
			if (order_list == null) {
				
				order_list = new ArrayList<Order>();
				
				//DBで在庫確認
			    if(product.getStock() < 1 ) {
			    	error = "ご希望の商品の在庫がないため、カートに追加できません。";
			    	return;
			    }
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
						
						productAlreadyExist = true;
						//DBで在庫確認
						if (product.getStock() < i.getQuantity() + 1) {
							error = "ご希望の数量が在庫より多いため、カートに追加できません。";
							return;
						}
						i.setQuantity(i.getQuantity() + 1);
						break;
					}
				}
				if (!productAlreadyExist) {
					//DBで在庫確認
				    if(product.getStock() < 1 ) {
				    	error = "ご希望の商品の在庫がないため、カートに追加できません。";
				    	return;
				    }
					order.setProductid(id);
					order.setQuantity(1);
					order_list.add(order);
				}

			}

			session.setAttribute("order_list", order_list);

		}
		//もしDB接続エラー起きたら
		catch (IllegalStateException e) {
			error = "DB接続エラーの為、処理は行えませんでした。";
		}

		finally {
			//エラーがない⇒フォワード
			if (error.equals("")) {
				request.getRequestDispatcher("/view/Cart/showCart.jsp").forward(request, response);
			}

			//エラーがあったためエラーページへフォワード
			else {
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}

		}

	}
}
