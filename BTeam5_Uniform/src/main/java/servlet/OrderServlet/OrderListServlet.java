package servlet.OrderServlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import bean.Order;
import bean.User;
import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author 猪瀬大貴,朴姻禹
 */
@WebServlet("/orderList")
public class OrderListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//エラーメッセージを格納する変数を宣言
		String error = "";
		
				//今日の日付取得するメソッド
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		String formatNowDate = dtf1.format(nowDate);
		
		//ソート順を格納する変数
		String sort = request.getParameter("sort") == null ? "" : request.getParameter("sort");
		String startDate = request.getParameter("startDate") == null ? "" : request.getParameter("startDate");
		String endDate = request.getParameter("endDate") == null ? "" : request.getParameter("endDate");
	
		
		// セッションからユーザー情報を取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null || user.getAuthority_id() != 1) {
		  error = "接続権限のないページです。";
		}

		try {
			//OrderDAOオブジェクトを作成
			OrderDAO orderDao = new OrderDAO();

			//注文リストが格納されているArrayListを宣言
			ArrayList<Order> orderList = new ArrayList<Order>();

			//新着順、古い順の選択により使用するselectAllメソッドを変更
			
			//全体期間で新着順
			if (sort.equals("") && startDate.equals("") && endDate.equals("")) {
				//DAOクラスのメソッドを使い全注文リストを取得
				orderList = orderDao.selectAll();

			} else if(sort.equals("old") && startDate.equals("") && endDate.equals("")) {
				//DAOクラスのメソッドを使い全注文リストを取得
				orderList = orderDao.selectAll("Asc");
			}
			else if(!startDate.equals("") && !endDate.equals("")){
				orderList = orderDao.selectByDate(startDate, endDate, "desc");
			}
			else if(startDate.equals("") && !endDate.equals("")) {
				orderList = orderDao.selectByDate("", endDate, "desc");
			}
			else if(!startDate.equals("") && endDate.equals("")) {
				orderList = orderDao.selectByDate(startDate,formatNowDate , "desc");
			}


			//全注文リストをリクエストスコープに登録
			request.setAttribute("orderList", orderList);

		} catch (IllegalStateException e) {
			//DB接続エラーが発生した場合
			error = "DB接続エラーのため、注文一覧画面を表示できませんでした。";

		} finally {
			
			//エラーが発生した場合
			if (error.equals("")) {
				//orderList.jspに処理を遷移
				request.getRequestDispatcher("/view/Order/orderList.jsp").forward(request, response);

			}

			else {
				//errorをリクエストスコープに登録
				request.setAttribute("error", error);
				//error.jspに処理を遷移
				request.getRequestDispatcher("view/Common/error.jsp").forward(request, response);
			}
		}
	}
}
