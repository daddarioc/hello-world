package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import assetRecord.Record;

public class DbConnection {
	private Connection conn;
	private ResultSet rs;
	private Statement stmt;
	
	
	private Connection getConn() {
		return conn;
	}

	private void setConn(Connection conn) {
		this.conn = conn;
	}

	private ResultSet getRs() {
		return rs;
	}

	private void setRs(ResultSet rs) {
		this.rs = rs;
	}

	private Statement getStmt() {
		return stmt;
	}

	private void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public DbConnection() {

	}
	
	/**
	 * add database results to local record pojos and return arraylist
	 * @return
	 */
	public ArrayList<Record> populateRecords() {
		ArrayList<Record> list = new ArrayList<Record>();
		Record i = null;
		rs = getRows("select AUTO_ASSET_ID, ASSET_NAME, STATUS_ID_FK, END_DATE\r\n" + 
				"from AM_AUTO_ASSET\r\n" + 
				"where AUTO_ASSET_ID in (6377, 6383, 6384, 6385, 6388)\r\n" + 
				"order by ASSET_NAME");
		try {
			while (rs.next()) {
				i = new Record(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4));
				list.add(i);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void updateRows(String update) {
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(update);
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void connectionClose() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		try {
			String url = "jdbc:sqlserver://sqdatad99c03vc2;databaseName=ESEQA_Metrics;integratedSecurity=true;";
			conn = DriverManager.getConnection(url, "daddaric", "b33rLike03");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return conn;
	}
	
	public ResultSet getRows(String query) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
}
