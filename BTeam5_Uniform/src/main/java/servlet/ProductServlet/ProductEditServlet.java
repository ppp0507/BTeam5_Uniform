package servlet.ProductServlet;

import java.io.IOException;

import dao.OrderDAO;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

		// フォワード先を指定する変数
		String toForward = "/view/Common/error.jsp";
		
		HttpSession session = request.getSession();
		
		try {

			// 操作内容の取得
			cmd = request.getParameter("cmd");
			
			if (cmd.equals("update")) {
				// 更新処理
				
				//商品ID取得
				//productEdit.jspの商品IDパラメータを取得
				int id = Integer.parseInt(request.getParameter("id"));
				
				String name = request.getParameter("name");
	
				//商品名が空文字の場合
				if(name.equals("")) {
	
					error = "商品名を入力してください。";
					return;
				}
	
				//価格を取得
				//productEdit.jspの価格パラメータを取得
				String price = request.getParameter("price");
	
				//価格が空欄の場合
				if(price.equals("")) {
					error = "価格を入力してください。";
					return;
				}
	
				//priceをint型に変換
				int price2 = Integer.parseInt(price);
	
				//価格が負の数の場合
				if(price2 < 0) {
					error = "価格は0円以上の金額を入力してください。";
					return;
				}
	
				//在庫数を取得
				//productEdit.jspの在庫数パラメータを取得
				String stock = request.getParameter("stock");
	
				//在庫数が空欄の場合
				if(stock.equals("")) {
					error = "在庫数を入力してください。";
					return;
				}
	
				//stockをint型に変換
				int stock2 = Integer.parseInt(stock);
	
				//在庫数が負の数の場合
				if(stock2 < 0) {
					error = "在庫数は0以上の数字を入力してください。";
					return;
				}
	
				//ProductDAOオブジェクトを作成
				ProductDAO productDao = new ProductDAO();
	
				//ProductDAOのメソッドを使用してDBの情報を更新(写真URLはnull)
				int editCount = productDao.editDetail(id, name, price2, stock2, null);
				
				if (editCount < 1) {
					error = "存在しない商品です。";
					return;
				}
				
				toForward = "/productList";
				
			} else if (cmd.equals("delete")) {
				//削除処理
				
				// ここでエラー
				int product_id = (int)session.getAttribute("delete_product_id");
				
				// DAOオブジェクトを作成
				OrderDAO orderDao = new OrderDAO();
				ProductDAO productDao = new ProductDAO();
				
				// FOREIGN KEY制約があるので、注文テーブルに登録されている削除対象のproduct_idをダミーに置き換え
				orderDao.moveDeletedOrdersByProduct_id(product_id);
				// 商品の削除
				int delCount = productDao.delete(product_id);
				
				if (delCount < 1) {
					error = "存在しない商品です。";
					return;
				}
				
				toForward = "/productList";
				
			}

		}catch(IllegalStateException e) {
			//DB接続エラーが発生した場合
			error = "DB接続エラーのため、商品を編集できませんでした。";
			e.printStackTrace();

		}catch(NumberFormatException e) {
			//price、stockに文字列を入力した場合
			error = "数字を入力してください。";
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			//エラーが発生した場合
			if(error != null) {
				//errorをリクエストスコープに登録
				request.setAttribute("error", error);

				//error.jspに処理を遷移
				request.getRequestDispatcher("view/Common/error.jsp").forward(request, response);
			}
			//エラー発生しなかった場合
			else {
				request.getRequestDispatcher(toForward).forward(request, response);
			}

		}

	}
}