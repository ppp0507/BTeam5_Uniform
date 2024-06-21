package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import bean.Order;

/** 以下、このファイルのメソッドの種類
 * 
 * selectAll : 全体の注意リストを取得
 * ⇒ orderListで使ってください
 * 
 * selectByUser : 特定ユーザーの注文だけ取得
 * ⇒ orderHistory.jsp, OrderHistoryServletで使ってください
 * 
 * getDetail : 一件の注文のデータ取得
 * ⇒ orderDetailで使ってください
 * 
 * editDetail : 一件の注文のデータ変更・更新
 * ⇒ OrderEditServlet で使ってください
 * 
 * insert : 新しい注文登録
 * ⇒ ProductRegisterServlet.javaで使ってください
 * 
 */

public class OrderDAO {

	//DBドライバー、住所、ユーザー、パスワード
	private static final String RDB_DRIVE = "org.mariadb.jdbc.Driver";
	private static final String URL = "jdbc:mariadb://localhost/UniformDB";
	private static final String USER = "root";
	private static final String PASSWD = "root123";

	//Connection定義
	private static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(RDB_DRIVE);
			con = DriverManager.getConnection(URL, USER, PASSWD);
			return con;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	//selectAll : 全体の注意リストを取得
	//表示は 最新の注文からになります。時間余ったら逆も作ります。
	public ArrayList<Order> selectAll() {
		Connection con = null;
		Statement smt = null;

		ArrayList<Order> OrderList = new ArrayList<Order>();

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQL文発行
			String sql = "SELECT * FROM orderinfo ORDER BY date";
			ResultSet rs = smt.executeQuery(sql);

			//rsからデータ取り出し、OrderListへ格納
			while (rs.next()) {
				Order order = new Order();

				order.setId(rs.getInt("id"));
				order.setUserid(rs.getInt("user_id"));
				order.setProductid(rs.getInt("product_id"));
				order.setDate(rs.getString("date"));
				order.setQuantity(rs.getInt("quantity"));
				order.setDeliveryStateId(rs.getInt("delivery_state_id"));
				order.setIsPayment(rs.getBoolean("is_payment"));
				order.setComment(rs.getString("comment"));

				OrderList.add(order);
			}

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		//リソース解放
		finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return OrderList;
	}

	//selectByUser : 特定ユーザーの注文だけ取得
	//ユーザーのidが必要。(emailじゃなくてidで取得します。)
	public ArrayList<Order> selectByUser(int user_id) {
		Connection con = null;
		Statement smt = null;

		ArrayList<Order> OrderList = new ArrayList<Order>();

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQL文発行
			String sql = "SELECT * FROM orderinfo WHERE user_id =" + user_id + " ORDER BY date";

			ResultSet rs = smt.executeQuery(sql);

			//rsからデータ取り出し、OrderListへ格納
			while (rs.next()) {
				Order order = new Order();

				order.setId(rs.getInt("id"));
				order.setUserid(rs.getInt("user_id"));
				order.setProductid(rs.getInt("product_id"));
				order.setDate(rs.getString("date"));
				order.setQuantity(rs.getInt("quantity"));
				order.setDeliveryStateId(rs.getInt("delivery_state_id"));
				order.setIsPayment(rs.getBoolean("is_payment"));
				order.setComment(rs.getString("comment"));

				OrderList.add(order);
			}

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		//リソース解放
		finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return OrderList;
	}

	//getDetail : 一件の注文のデータ取得
	//注文の id (注文番号)が必要です。
	public Order getDetail(int id) {
		Connection con = null;
		Statement smt = null;

		Order order = new Order();

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQL文発行
			String sql = "SELECT * FROM orderinfo WHERE id =" + id;
			ResultSet rs = smt.executeQuery(sql);

			//rsからデータ取り出し、orderへ格納
			while (rs.next()) {
				order.setId(rs.getInt("id"));
				order.setUserid(rs.getInt("user_id"));
				order.setProductid(rs.getInt("product_id"));
				order.setDate(rs.getString("date"));
				order.setQuantity(rs.getInt("quantity"));
				order.setDeliveryStateId(rs.getInt("delivery_state_id"));
				order.setIsPayment(rs.getBoolean("is_payment"));
				order.setComment(rs.getString("comment"));
			}

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		//リソース解放
		finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return order;
	}

	//editDetail : 一件の注文のデータ変更・更新
	//注文の id (注文番号),更新したい項目のデータ(配送、入金)が必要です。
	public void editDetail(int id, int delivery_state_id, boolean is_payment) {
		//もし配送だけ変更したい場合、is_paymentは既存のデータをそのまま入れてください

		Connection con = null;
		Statement smt = null;

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQL文発行
			String sql = "UPDATE orderinfo SET delivery_state_id=" + delivery_state_id + ", is_payment=" + is_payment
					+ " WHERE id=" + id;

			smt.executeUpdate(sql);

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		//リソース解放
		finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}

	//insert : 新しい注文登録
	// 注文日付は nowDateが自動的に設定されます。
	// 発送状況は'未発送'、入金状況は'入金待ち'がデフォルト
	public void insert(int user_id, int product_id, int quantity,
			 String comment) {

		Connection con = null;
		Statement smt = null;
		
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		String formatNowDate = dtf1.format(nowDate);

		try {
			con = getConnection();
			smt = con.createStatement();
			

			//SQL文発行
			String sql = "INSERT INTO orderinfo(user_id,product_id,date,quantity,delivery_state_id,is_payment,comment) VALUES("
					+ user_id + "," + product_id + ",'" + formatNowDate + "'," + quantity + "," + 3 + ",false,'"
					+ comment + "')";
			System.out.println(sql);
			smt.executeUpdate(sql);

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		//リソース解放
		finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}

}
