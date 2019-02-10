package com.vietjack.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.vietjack.core.Category;
import com.vietjack.dbconnection.DBConnection;

public class CategoryDao {
	private Connection conn;

	public Connection getConnection() throws SQLException {
		return DBConnection.getDbCon().getConn();
	}

	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public double calculateRevenueOfCategory(long categoryid) throws SQLException {//tinh doanh thu cua the loai 
		long revenue = 0l;
		String query ="select bc.id, bc.name "
				+ ",ifnull(sum(bb.sold_number*bb.price),0) as revenue from bs_category bc"
				+ " left join bs_book bb on bb.category_id=bc.id where bc.id="+categoryid
				+ " group by bc.id "
				+ " order by revenue desc";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);

		while (resultSet.next()) {
			return  resultSet.getDouble("revenue");
		}
		return revenue;
	}
	
	public ArrayList<Category> findAllCatergory() throws SQLException {//tim danh sach tat ca cac the loai
		String query = "select * from bs_category ";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);
		ArrayList<Category> categoryList = new ArrayList<Category>();

		while (resultSet.next()) {
			Category category = new Category(resultSet);
			category.setRevenue(calculateRevenueOfCategory(category.getId()));
			categoryList.add(category);
		}
		return categoryList;
	}

	public Category findCategoryOfBook(long categoryid) throws SQLException {//tim the loai cua sach
		String query = "SELECT bs_category.id, bs_category.name FROM bs_book, bs_category WHERE  " + "bs_category.id = "
				+ categoryid;
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);
		Category category = new Category();
		if (resultSet.next()) {
			category.setId(resultSet.getLong("bs_category.id"));
			category.setName(resultSet.getString("bs_category.name"));
		}
		return category;
	}

	public boolean addNewCategory(Category category) throws SQLException {//them the loai moi
		String query = "insert into bs_category(id,name) values (" + category.getId() + ",'" + category.getName()
				+ "')";
		Statement stmt = getConnection().createStatement();
		int n = stmt.executeUpdate(query);

		if (n != 0)
			return true;
		return false;
	}

	public boolean modifyCategory(Category category) throws SQLException {//sua the loai
		String query = "update bs_category set name='" + category.getName() + "' where id=" + category.getId();
		Statement stmt = getConnection().createStatement();
		int n = stmt.executeUpdate(query);

		if (n != 0)
			return true;
		return false;
	}
	public boolean deleteCategoryById(long id) throws SQLException {//xoa the loai theo id
		String query = "delete from bs_category where id = ?";
		java.sql.PreparedStatement stmt = getConnection().prepareStatement(query);
		stmt.setLong(1, id);
		int n = stmt.executeUpdate();

		if (n != 0) {
			System.out.println(n + " rows deleted");
		}
		return false;
	}
	public boolean deleteCategoryByName(String name) throws SQLException {//xoa the loai theo ten
		String query = "delete from bs_category where name = ?";
		java.sql.PreparedStatement stmt = getConnection().prepareStatement(query);
		stmt.setString(1, name);
		int n = stmt.executeUpdate();

		if (n != 0) {
			System.out.println(n + " rows deleted");
		}
		return false;
	}

	public Category findCategoryByName(String name) throws SQLException {//tim the loai theo ten
		String query = "select * from bs_category where name='" + name + "'";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);

		if (resultSet.next()) {
			Category category = new Category(resultSet);
			return category;
		} else
			return null;

	}

	public Category findCategoryById(long id) throws SQLException {//tim the loai boi id
		String query = "select * from bs_category where id='" + id + "'";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);

		if (resultSet.next()) {
			Category category = new Category(resultSet);
			return category;
		} else
			return null;

	}

	public long generateId() throws SQLException {//tao id
		String query = "select max(id) as maxid from bs_category ";
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery(query);

		if (resultSet.next()) {
			return resultSet.getLong("maxid") + 1;
		} else {
			return 0;
		}
	}
}
