package bean;

/**
 * 商品情報を一つのオブジェクトとしてまとめるためのDTOクラス
 *
 * @author 猪瀬大貴
 * 
 *
 */

public class Product {

	/**
	 * 商品番号
	 */
	private int id;
	
	/**
	 * 商品名
	 */
	private String name;
	
	/**
	 * 商品の金額
	 */
	private int price;
	
	/**
	 * 商品の在庫数
	 */
	private int stock;
	
	/**
	 * 商品の画像URL
	 */
	private String image_url;
	
	/**
	 * コンストラクタ<br>
	 */
	public Product() {
		this.id = 0;
		this.name = null;
		this.price = 0;
		this.stock = 0;
		this.image_url = null;
	}

	/**
	 * 商品番号を取得する
	 * 
	 * @return 商品番号
	 */
	public int getId() {
		return id;
	}

	/**
	 * 商品番号を設定する
	 *
	 * @param id 設定する商品番号
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 商品名を取得する
	 * 
	 * @return 商品名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 商品名を設定する
	 *
	 * @param name 設定する商品名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 商品の金額を取得する
	 * 
	 * @return 商品の金額
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * 商品の金額を設定する
	 *
	 * @param price 設定する商品の金額
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * 商品の在庫数を取得する
	 * 
	 * @return 商品の在庫数
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * 商品の在庫数を設定する
	 *
	 * @param stock 設定する商品の在庫数
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * 商品の画像URLを取得する
	 * 
	 * @return 商品の画像URL
	 */
	public String getImage_url() {
		return image_url;
	}

	/**
	 * 商品の画像URLを設定する
	 *
	 * @param image_url 設定する商品の画像URL
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
}