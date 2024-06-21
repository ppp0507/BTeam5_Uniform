package bean;

public class User {

	//インスタンス変数 userid, password, email, authority
	private int userid;
	private String username;
	private String password;
	private String email;
	private String address;
	private int authority_id;

	//userIDメソッド
	public int getUserid() {
		return this.userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	//usernameメソッド
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	//passwordメソッド
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	//emailメソッド
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	//addressメソッド
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	//authority_id メソッド
	public int getAuthority_id() {
		return this.authority_id;
	}
	public void setAuthority_id(int authority_id) {
		this.authority_id = authority_id;
	}

}
