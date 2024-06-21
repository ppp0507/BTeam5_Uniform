package dao;

import java.sql.Connection;
import java.sql.DriverManager;

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
}
