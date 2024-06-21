package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.Product;

/** @author 朴姻禹 
 * 以下、このファイルのメソッドの種類
 *  selectAll():商品一覧を取得
 *  getDetail():商品の詳細取得
 *  insert():商品登録
 *  editDetail():商品修正
 *  delete():商品削除
 */

public class ProductDAO {

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

	//selectAll():商品一覧を取得
	public ArrayList<Product> selectAll() {
		Connection con = null;
		Statement smt = null;

		ArrayList<Product> ProductList = new ArrayList<Product>();

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQL文発行
			String sql = "SELECT * FROM product";
			ResultSet rs = smt.executeQuery(sql);

			//rsからデータ取り出し、ProductListへ格納
			while (rs.next()) {
				Product product = new Product();

				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setStock(rs.getInt("stock"));
				product.setImage_url(rs.getString("image_url"));

				ProductList.add(product);
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
		return ProductList;
	}

	//getDetail():商品の詳細取得
	//商品番号(id)で取得します。
	public Product getDetail(int id) {
		Connection con = null;
		Statement smt = null;

		Product product = new Product();
		try {
			con = getConnection();
			smt = con.createStatement();

			//SQL文発行
			String sql = "SELECT * FROM product WHERE id=" + id;
			ResultSet rs = smt.executeQuery(sql);

			//rsからデータ取り出し、Productへ格納
			while (rs.next()) {

				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setStock(rs.getInt("stock"));
				product.setImage_url(rs.getString("image_url"));
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
		return product;
	}

	//insert():商品登録
	public void insert(String name, int price, int stock, String image_url) {
		Connection con = null;
		Statement smt = null;

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQL文発行
			String sql = "INSERT INTO product(name,price,stock,image_url) VALUES('" + name + "'," + price + "," + stock
					+ ",'" + image_url + "');";
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
	
	//editDetail():商品修正
	 //修正したい商品の商品番号(ID)必要です。
	public void editDetail(int id,String name ,int price, int stock, String image_url) {
		//修正したくない項目は元のデータそのまま入れてください
		
		Connection con = null;
		Statement smt = null;

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQL文発行
			String sql = "UPDATE product SET name='"+name+"', price="+price+", stock="+stock+", image_url='"+image_url+"' WHERE id="+id;
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
	

	/**delete():商品削除
	 * 削除したい商品の商品番号(ID)必要です。
	 * ！！注文されている商品は削除できません！(ForeignKeyとして使われているためエラー起きます)
	 * @param id
	 */
	public void delete(int id) {
		Connection con = null;
		Statement smt = null;

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQL文発行
			String sql = "DELETE FROM product WHERE id=" + id;
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
