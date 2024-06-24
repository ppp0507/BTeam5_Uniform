package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.User;

/** 以下、このファイルのメソッドの種類
 * 
 * selectByUser:DBのuserから email , passwordが一致するデータを取得
 *  => login, userdetailで使ってください
 *  
 * insert:DBに新しいユーザーを登録
 * ⇒ signupで使ってください **権限はとりあえず 2(一般ユーザ)で固定します
 * 
 * delete:DBからユーザーを削除(脱会)
 * 
 * selectAll :全てのユーザーの一覧取得
 * ⇒userlist で使ってください
 * 
 * getUserbyId:IDの一致するユーザーの取得
 * ⇒屋比久作成 accountEdit.jspで利用しています
 *  
 * update:指定されたUserオブジェクトのデータを更新
 * ⇒屋比久作成 AccountEditServlet.javaで利用しています
 * 
 */

public class UserDAO {

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

	//selectByUser:DBのuserからemail, passwordが一致するデータを取得
	public User selectByUser(String email, String password) {
		Connection con = null;
		Statement smt = null;

		User user = new User();

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQL文
			String sql = "SELECT * FROM user WHERE email ='" + email + "' AND password='" + password + "'";

			ResultSet rs = smt.executeQuery(sql);

			//rsから取得したデータをuserオブジェクトに格納
			if (rs.next()) {
				user.setUserid(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setAuthority_id(rs.getInt("authority_id"));
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
		return user;
	}

	/**
	 * insert:DBに新しいユーザーを登録
	 * passwordの値でゲスト登録か、一般登録かを分岐させます
	 * password == "" ならゲスト(権限:3)
	 * @param name
	 * @param password
	 * @param email
	 * @param address
	 */
	public void insert(String name, String password, String email, String address) {
		Connection con = null;
		Statement smt = null;
		int authority = 2;
		if(password.equals("")) {
			authority = 3;
		}
		try {
			con = getConnection();
			smt = con.createStatement();

			String sql = "INSERT INTO user(name, password, email, address, authority_id) VALUES('" + name + "','"
					+ password + "','" + email + "','" + address + "',"+authority+")";

			smt.executeQuery(sql);
		}

		catch (Exception e) {
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
	
	//delete:DBからユーザーを削除(脱会)
	public void delete(String email) {

		Connection con = null;
		Statement smt = null;

		try {
			con = getConnection();
			smt = con.createStatement();
			//SQL文
			String sql = "DELETE FROM user WHERE email = '" + email + "'";
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
	
	//selectAll :全てのユーザーの一覧取得
	public ArrayList<User> selectAll() {
		
		Connection con = null;
		Statement smt = null;
	
		ArrayList<User> userList = new ArrayList<User>();

		try {
			con = getConnection();
			smt = con.createStatement();
			
			//SQL文
			String sql = "SELECT * FROM user ORDER BY id";
			//rsに取得したデータ格納
			ResultSet rs = smt.executeQuery(sql);
			
			//rsからデータ取り出し、User型オブジェクトでuserListに格納
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setAuthority_id(rs.getInt("authority_id"));
				userList.add(user);
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
		return userList;
	}

	// getUserbyId:DBのuserからidが一致するデータを取得
	// 作成者: 屋比久
	public User getUserbyId(int id) {
		Connection con = null;
		Statement smt = null;

		User user = new User();

		try {
			con = getConnection();
			smt = con.createStatement();

			//SQL文
			String sql = "SELECT * FROM user WHERE id = " + id;

			ResultSet rs = smt.executeQuery(sql);

			//rsから取得したデータをuserオブジェクトに格納
			if (rs.next()) {
				user.setUserid(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setAuthority_id(rs.getInt("authority_id"));
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
		return user;
	}
	
	// update:指定されたUserオブジェクトのデータを更新
	// 作成者: 屋比久

}