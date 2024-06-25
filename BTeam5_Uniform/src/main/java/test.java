import dao.OrderDAO;

public class test {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		OrderDAO orderDao = new OrderDAO();
		
		orderDao.selectByDate("2023-01-03","2024-06-21","asc");
	}

}
