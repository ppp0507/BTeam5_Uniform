package servlet.OrderServlet;

import java.io.IOException;

import bean.Order;
import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 注文詳細機能
 * 但馬　舞子
 */

@WebServlet("/orderDetail")
public class OrderDetailServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//③エラー処理で使う変数errorとcmdを初期化しています。
		String error = "";

		// ①OrderDAOクラスのオブジェクトを生成します。
		OrderDAO objDao = new OrderDAO();

		//②表示する書籍情報を格納するOrderオブジェクトを生成します。
		Order order =new Order();

		try{

			//パラメータの取得
			int id = Integer.parseInt(request.getParameter("id"));
			
			//メソッド呼び出し
			order = objDao.getDetail(id);

			if (order.getId() == 0) {
				error = "指定したIDの商品は存在しません。";
			}
			
			//⑥取得した書籍情報を「order」という名前でリクエストスコープに登録します。
			request.setAttribute("order",order);

		}catch (IllegalStateException e) {
			error ="DB接続エラーの為、登録できませんでした。";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>" + e;
			e.printStackTrace();
		}finally{
			if (error.length() == 0) {
				// フォワード処理
				request.setAttribute("order", order);
				request.getRequestDispatcher("/view/Order/orderDetail.jsp").forward(request, response);
			} else {
				// エラー文をerrorという名前でリクエストスコープに保存
				request.setAttribute("error", error);
				// フォワード処理
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}
		}
	}
}