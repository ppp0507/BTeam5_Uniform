// 担当: 屋比久
package servlet.OrderServlet;

import java.io.IOException;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/orderEdit")
public class OrderEditServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// エラー処理用
		String error = "";

		OrderDAO orderDao = new OrderDAO();

		int id = 0;

		try{

			//パラメータの取得
			HttpSession session = request.getSession();
			id = (int)session.getAttribute("order_id");
			int deliveryState = Integer.parseInt(request.getParameter("delivery_state"));
			int isPaymentNum = Integer.parseInt(request.getParameter("is_payment"));
			System.out.println(id + " " + deliveryState +" " + isPaymentNum);
			// isPaymentNumを2値に変換
			boolean isPayment = isPaymentNum == 1?true:false;
			
			//メソッド呼び出し
			orderDao.editDetail(id, deliveryState, isPayment);
	

		}catch (IllegalStateException e) {
			error ="DB接続エラーの為、登録できませんでした。";
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。<br>" + e;
			e.printStackTrace();
		}finally{
			if (error.equals("")) {
				// フォワード処理

				request.getRequestDispatcher("/orderList").forward(request, response);
			} else {
				// エラー文をerrorという名前でリクエストスコープに保存
				request.setAttribute("error", error);
				// フォワード処理
				request.getRequestDispatcher("/view/Common/error.jsp").forward(request, response);
			}
		}
	}
}