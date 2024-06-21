package bean;

public class Order {
	
	/**
	 * 注文番号、注文者ID(emailじゃないです)
	 * 注文商品の商品番号(ID)
	 * 注文した日、注文個数
	 * 配送状況、入金状況、備考欄
	 * 
	 * !! SnakeCaseとCamelCase混じってるので注意してください
	 * 例えばuser_idのgetterは
	 * getUser_idじゃなくて、
	 * getUserid です。泣
	 */
	private int id;
	private int user_id;
	private int product_id;
	private String date;
	private int quantity;
	private int delivery_state_id;
	private boolean is_payment;
	private String comment;
	
	// 注文番号 = id メソッド
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	// 注文者ID(emailじゃないです) = user_id メソッド
	public int getUserid() {
		return this.user_id;
	}
	public void setUserid(int user_id) {
		this.user_id = user_id;
	}
	
	// 注文商品の商品番号(ID)
	public int getProductid() {
		return this.product_id;
	}
	public void setProductid(int product_id) {
		this.product_id = product_id;
	}
	
	//注文した日
	public String getDate() {
		return this.date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	//注文個数
	public int getQuantity() {
		return this.quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	//配送状況
	public int getDeliveryStateId() {
		return this.delivery_state_id;
	}
	public void setDeliveryStateId(int delivery_state_id) {
		this.delivery_state_id = delivery_state_id;
	}
	
	//入金状況
	public boolean getIsPayment() {
		return this.is_payment;
	}
	public void setIsPayment(boolean is_payment) {
		this.is_payment = is_payment;
	}
	
	//備考欄
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
